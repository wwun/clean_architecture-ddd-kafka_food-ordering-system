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

    private final OrderCreateHelper orderCreateHelper;

    private final OrderDataMapper orderDataMapper;

    private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;

    public OrderCreateCommandHandler(OrderCreateHelper orderCreateHelper,
            OrderDataMapper orderDataMapper,
            OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher) {
        
        this.orderCreateHelper = orderCreateHelper;
        this.orderDataMapper = orderDataMapper;
        this.orderCreatedPaymentRequestMessagePublisher = orderCreatedPaymentRequestMessagePublisher;
    }

    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        // método que se encarga de crear un pedido, validando el cliente y el restaurante, y guardando el pedido en la base de datos
        // hace uso de OrderDomainService de order-domain-core para validar y crear el pedido, y OrderDataMapper para mapear los objetos de dominio a DTOs
        // esto lo hace a través de un helper que encapsula la lógica de creación de pedidos

        OrderCreatedEvent orderCreatedEvent = orderCreateHelper.persistOrder(createOrderCommand);   // persistOrder se debe al spring proxy aop, por lo qe los annotated metho debe ser invocado por otro bean, en este caso el handler invoca al helpe
        
        log.info("Order is created with id: {}", orderCreatedEvent.getOrder().getId().getValue());

        // wwun agregar comentario
        orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);

        return orderDataMapper.orderToCreateOrderResponse(orderCreatedEvent.getOrder());
    }
    //ver video 22 desde la mitad qe implementa un transactionalEventListener qe es una segunda opción
}
