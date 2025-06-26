package com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment;

import com.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent;

public interface OrderCancelledPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCancelledEvent> {
    // This interface is used to publish payment request messages when an order is cancelled.
    // It extends the DomainEventPublisher interface with OrderCancelledEvent as the event type.

    
}
