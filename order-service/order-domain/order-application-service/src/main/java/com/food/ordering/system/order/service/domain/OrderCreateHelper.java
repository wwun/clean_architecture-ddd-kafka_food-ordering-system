package com.food.ordering.system.order.service.domain;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.entity.Customer;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
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
    
    // clase qe encapsula logica de creación de pedidos, es un helper que se encarga de ejecutar la lógica de negocio específica para el comando de creación de pedidos
    // no se pone en OrderCreateCommandHandler porqe esa clase se enfoca en solo recibir el comando y delegar la ejecución a los servicios de dominio, mientras que este helper encapsula la lógica de creación de pedidos

    //valida cliente y restaurante, convierte el comando en entidad de dominio, llama al dominio, persiste la orden

    private final OrderDomainService orderDomainService;

    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;
    
    private final RestaurantRepository restaurantRepository;

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
        checkCustomer(createOrderCommand.getCustomerId());
        Restaurant restaurant = checkRestaurant(createOrderCommand);
        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, restaurant);   //saving in the local database
        saverOrder(order);
        log.info("Order is created with id: {}", order.getId().getValue());
        return orderCreatedEvent;
    }

    private void checkCustomer(UUID customerId) {
        //método que se encarga de validar si el cliente existe en la base de datos
        Optional<Customer> customer = customerRepository.findCustomer(customerId);
        if(customer.isEmpty()) {
            log.warn("Customer with id: {} not found!", customerId);
            throw new OrderDomainException("Customer with id: " + customerId + " not found");
        }
    }

    private Restaurant checkRestaurant(CreateOrderCommand createOrderCommand) {
        // método que se encarga de validar si el restaurante existe en la base de datos
        Restaurant restaurant = orderDataMapper.createOrderCommandToRestaurant(createOrderCommand);
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findRestaurantInformation(restaurant);
        if (optionalRestaurant.isEmpty()) {
            log.warn("Restaurant with id: {} not found!", createOrderCommand.getRestaurantId());
            throw new OrderDomainException("Restaurant with id: " + createOrderCommand.getRestaurantId() + " not found");
        }
        return optionalRestaurant.get();
    }

    private Order saverOrder(Order order) {
        Order orderResult = orderRepository.save(order);
        if(orderResult == null) {
            log.error("Could not save order with id: {}", order.getId().getValue());
            throw new OrderDomainException("Could not save order with id: " + order.getId().getValue());
        }
        return orderResult;
    }
}
