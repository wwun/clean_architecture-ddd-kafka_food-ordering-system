package com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment;

import com.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;

public interface OrderCreatedPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCreatedEvent>{
    // This interface is used to publish payment request messages when an order is created.
    // It extends the DomainEventPublisher interface with OrderCreatedEvent as the event type.
    // Implementations of this interface will handle the logic for publishing the payment request messages to the appropriate message broker or messaging system.

    //usado por OrderCreateCommandHandler para publicar el evento después de crear la orden
    
}
