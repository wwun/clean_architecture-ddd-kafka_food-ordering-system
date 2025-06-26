package com.food.ordering.system.order.service.domain.dto.create;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

//acá sí se usa lombok para generar el código boilerplate de getters, constructor y builder, a diferencia de domain-core donde se mantiene independiente de librerías externas
@Getter // esta anotación genera los métodos getter para todos los campos de la clase
@Builder // esta anotación permite crear un constructor de tipo builder, facilitando la creación de instancias de la clase
@AllArgsConstructor // esta anotación genera un constructor que recibe todos los campos de la clase como parámetros, útil para inicializar objetos de forma sencilla
public class CreateOrderCommand {
    @NotNull
    private final UUID customerId;
    @NotNull
    private final UUID restaurantId;
    @NotNull
    private final BigDecimal price;
    @NotNull
    private final List<OrderItem> items;
    @NotNull
    private final OrderAddress address;
}
