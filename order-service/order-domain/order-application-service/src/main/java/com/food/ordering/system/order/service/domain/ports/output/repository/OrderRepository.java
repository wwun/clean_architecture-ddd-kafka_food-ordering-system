package com.food.ordering.system.order.service.domain.ports.output.repository;

import java.util.Optional;

import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.valueobject.TrackingId;

public interface OrderRepository {

    //interfaces para acceder a la persistencia de órdenes, clientes y restaurantes

    Order save(Order order);

    Optional<Order> findByTrackingId(TrackingId trackingId);

}
