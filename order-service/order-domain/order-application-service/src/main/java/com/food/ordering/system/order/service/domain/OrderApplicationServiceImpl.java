package com.food.ordering.system.order.service.domain;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;
import com.food.ordering.system.order.service.domain.ports.input.service.OrderApplicationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@Service
class OrderApplicationServiceImpl implements OrderApplicationService{

    //orqesta los casos de usos principales, es el punto de entrada para la API layer
    //no contiene lógica de negocio, solo delega la ejecución a los handlers especializados

    private final OrderCreateCommandHandler orderCreateCommandHandler;  // clase qe contiene la lógica específica de creación de pedidos
    private final OrderTrackCommandHandler orderTrackCommandHandler;

    // handler es un patrón común de arqitectura limpia, cada comando o caso de uso tiene su propio handler, el handler es responsable de ejecutar la lógica de negocio específica para ese comando

    public OrderApplicationServiceImpl(OrderCreateCommandHandler orderCreateCommandHandler,
                                        OrderTrackCommandHandler orderTrackCommandHandler) {
        this.orderCreateCommandHandler = orderCreateCommandHandler;
        this.orderTrackCommandHandler = orderTrackCommandHandler;
    }

    @Override
    public CreateOrderResponse createOrder(@Valid CreateOrderCommand createOrderCommand) {
        return orderCreateCommandHandler.createOrder(createOrderCommand);
    }

    @Override
    public TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery) {
        return orderTrackCommandHandler.trackOrder(trackOrderQuery);
    }
}
