package com.food.ordering.system.order.service.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.entity.Customer;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository;
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.system.order.service.domain.ports.output.repository.RestaurantRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OrderCreateHelper {

    // clase que contiene la lógica específica de creación de pedidos
    
    // clase qe encapsula logica de creación de orden (validar cliente/restaurante, convertir DTO a entidad, llamar al dominio, persistir la orden), es un helper que se encarga de ejecutar la lógica de negocio específica para el comando de creación de pedidos
    // no se pone en OrderCreateCommandHandler porqe esa clase se enfoca en solo recibir el comando y delegar la ejecución a los servicios de dominio, mientras que este helper encapsula la lógica de creación de pedidos

    //valida cliente y restaurante, convierte el comando en entidad de dominio, llama al dominio, persiste la orden

    private final OrderDomainService orderDomainService;    //del core para acceder a datos (payOrder, approvedOrder, cancelOrderPayment, cancelOrder);

    private final OrderRepository orderRepository;  //findByTrackingId

    private final CustomerRepository customerRepository;    //findCustomer
    
    private final RestaurantRepository restaurantRepository;    //findRestaurantInformation

    private final OrderDataMapper orderDataMapper;  // convierte entre DTO y entidad de dominio

    public OrderCreateHelper(OrderDomainService orderDomainService, OrderRepository orderRepository,
            CustomerRepository customerRepository, RestaurantRepository restaurantRepository,
            OrderDataMapper orderDataMapper) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderDataMapper = orderDataMapper;
    }
    
    @Transactional
    public OrderCreatedEvent persistOrder(CreateOrderCommand createOrderCommand) {
        // método que se encarga de crear un pedido, validando el cliente y el restaurante, y guardando el pedido en la base de datos
        // hace uso de OrderDomainService de order-domain-core para validar y crear el pedido, y OrderDataMapper para mapear los objetos de dominio a DTOs
        checkCustomer(createOrderCommand.getCustomerId());  //valida que el cliente exista en el sistema
        Restaurant restaurant = checkRestaurant(createOrderCommand);    //valida que el restaurante exista y obtiene su información
        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);    //convierte el comando recibido (DTO) en una entidad de dominio Order
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, restaurant);   //aplica las reglas de negocio para validar e iniciar la orden
        saveOrder(order);
        log.info("Order is created with id: {}", orderCreatedEvent.getOrder().getId().getValue());
        return orderCreatedEvent;
    }

    private Restaurant checkRestaurant(CreateOrderCommand createOrderCommand) {
        Restaurant restaurant = orderDataMapper.createOrderCommandToRestaurant(createOrderCommand);
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findRestaurantInformation(restaurant);
        if (optionalRestaurant.isEmpty()) {
            log.warn("Could not find restaurant with restaurant id: {}", createOrderCommand.getRestaurantId());
            throw new OrderDomainException("Could not find restaurant with restaurant id: " +
                    createOrderCommand.getRestaurantId());
        }
        return optionalRestaurant.get();
    }

    private void checkCustomer(UUID customerId) {
        Optional<Customer> customer = customerRepository.findCustomer(customerId);  //en este momento aún no se ha implementado la interface para buscar por cliente
        if (customer.isEmpty()) {
            log.warn("Could not find customer with customer id: {}", customerId);
            throw new OrderDomainException("Could not find customer with customer id: " + customer);
        }
    }

    private Order saveOrder(Order order) {
        Order orderResult = orderRepository.save(order);    //en este momento aún no se ha implementado la interface para guarda al cliente
        if (orderResult == null) {
            log.error("Could not save order!");
            throw new OrderDomainException("Could not save order!");
        }
        log.info("Order is saved with id: {}", orderResult.getId().getValue());
        return orderResult;
    }
}
