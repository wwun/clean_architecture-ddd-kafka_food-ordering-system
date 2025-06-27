package com.food.ordering.system.order.service.domain;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OrderCreateCommandHandler {

    // orqesta la ejecución de la lógica para crear una orden, llama al helper, publica eventos, mapea entidad de dominio a un DTO
    // es un handler que se encarga de ejecutar la lógica de negocio específica para el comando de creación de pedidos

    private final OrderCreateHelper orderCreateHelper;  //para la lógica de negocio, encapsula la lógica de negocio específica y detallada para crear una orden: validaciones, consultas a repositorios, conversión de DTO a entidad, persistencia

    private final OrderDataMapper orderDataMapper;  //para transformar entidades

    private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;    //para publicar eventos de pago

    public OrderCreateCommandHandler(OrderCreateHelper orderCreateHelper,
                                     OrderDataMapper orderDataMapper,
                                     OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher) {
        this.orderCreateHelper = orderCreateHelper;
        this.orderDataMapper = orderDataMapper;
        this.orderCreatedPaymentRequestMessagePublisher = orderCreatedPaymentRequestMessagePublisher;
    }

    @Transactional
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        // método que se encarga de crear un pedido, validando el cliente y el restaurante, y guardando el pedido en la base de datos
        // hace uso de OrderDomainService de order-domain-core para validar y crear el pedido, y OrderDataMapper para mapear los objetos de dominio a DTOs
        // esto lo hace a través de un helper que encapsula la lógica de creación de pedidos

        OrderCreatedEvent orderCreatedEvent = orderCreateHelper.persistOrder(createOrderCommand);
        log.info("Order is created with id: {}", orderCreatedEvent.getOrder().getId().getValue());
        orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);
        return orderDataMapper.orderToCreateOrderResponse(orderCreatedEvent.getOrder(), "Order created successfully");
    }

    // API Layer recibe una petición y construye un CreateOrderCommand.
    // Llama a OrderApplicationServiceImpl (implementa la interfaz de casos de uso).
    // Este delega a OrderCreateCommandHandler.
    // El handler llama a OrderCreateHelper para la lógica de negocio.
    // El helper usa repositorios para validar y obtener entidades, y llama a OrderDomainService para aplicar reglas.
    // El helper guarda la orden y devuelve un OrderCreatedEvent.
    // El handler publica el evento con OrderCreatedPaymentRequestMessagePublisher.
    // El handler usa OrderDataMapper para transformar la entidad de dominio a un DTO de respuesta.
    // Devuelve la respuesta a la API Layer

    // handlers orquestan, helpers encapsulan lógica de negocio.
    // input ports definen lo que el sistema puede hacer, output ports lo que necesita del exterior.
    // listeners y sus implementaciones permiten reaccionar a eventos externos.
    // dTOs transportan datos entre capas

}
