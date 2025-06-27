package com.food.ordering.system.order.service.domain;

import org.springframework.stereotype.Component;

import com.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OrderTrackCommandHandler {

    // clase qe sirve solo y solo para verificar la existencia

    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return null;
    }
}
