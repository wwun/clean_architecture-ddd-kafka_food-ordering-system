package com.food.ordering.system.order.service.domain.ports.output.message.publisher.restaurantapproval;

import com.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent;

public interface OrderPaidRestaurantRequestMessagePublisher extends DomainEventPublisher<OrderPaidEvent> {
    // This interface is used to publish restaurant approval request messages when an order is paid.
    // It extends the DomainEventPublisher interface with OrderPaidEvent as the event type.
    // Implementations of this interface will handle the logic for sending messages to the restaurant approval service.

}
