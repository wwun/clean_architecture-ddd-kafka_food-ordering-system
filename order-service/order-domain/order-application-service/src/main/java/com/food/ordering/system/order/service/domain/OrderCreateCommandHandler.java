package com.food.ordering.system.order.service.domain;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.entity.Customer;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository;
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.system.order.service.domain.ports.output.repository.RestaurantRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OrderCreateCommandHandler {

    // orqesta la ejecución de la lógica para crear una orden, llama al helper, publica eventos, mapea entidad de dominio a un DTO
    // es un handler que se encarga de ejecutar la lógica de negocio específica para el comando de creación de pedidos

    private final OrderDomainService orderDomainService;

    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;

    private final RestaurantRepository restaurantRepository;

    private final OrderDataMapper orderDataMapper;

    private final ApplicationDomainEventPublisher applicationDomainEventPublisher;

    public OrderCreateCommandHandler(OrderDomainService orderDomainService,
                                     OrderRepository orderRepository,
                                     CustomerRepository customerRepository,
                                     RestaurantRepository restaurantRepository,
                                     OrderDataMapper orderDataMapper,
                                     ApplicationDomainEventPublisher applicationDomainEventPublisher) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderDataMapper = orderDataMapper;
        this.applicationDomainEventPublisher = applicationDomainEventPublisher;
    }

    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        // método que se encarga de crear un pedido, validando el cliente y el restaurante, y guardando el pedido en la base de datos
        // hace uso de OrderDomainService de order-domain-core para validar y crear el pedido, y OrderDataMapper para mapear los objetos de dominio a DTOs
        // esto lo hace a través de un helper que encapsula la lógica de creación de pedidos

        checkCustomer(createOrderCommand.getCustomerId());
        Restaurant restaurant = checkRestaurant(createOrderCommand);
        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, restaurant);
        Order orderResult = saveOrder(order);
        log.info("Order is created with id: {}", orderResult.getId().getValue());
        applicationDomainEventPublisher.publish(orderCreatedEvent);
        return orderDataMapper.orderToCreateOrderResponse(orderResult);
    }
    
    
}
