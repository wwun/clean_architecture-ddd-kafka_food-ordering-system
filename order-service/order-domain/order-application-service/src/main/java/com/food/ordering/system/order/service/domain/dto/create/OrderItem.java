package com.food.ordering.system.order.service.domain.dto.create;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderItem {
    @NotNull
    private final UUID productId;
    @NotNull
    private final Integer quantity;
    @NotNull
    private BigDecimal price;
    @NotNull
    private BigDecimal subTotal;
}
