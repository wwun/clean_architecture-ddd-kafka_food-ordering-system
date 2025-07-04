package com.food.ordering.system.order.service.dataaccess.order.entity;

import java.util.Objects;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter //Lombok: genera getters para todos los campos
@Setter //Lombok: genera setters para todos los campos
@Entity
@Builder
@NoArgsConstructor  //Lombok: genera un constructor sin argumentos
@AllArgsConstructor //Lombok: genera un constructor con todos los argumentos
public class OrderItemEntityId {
    private Long id;
    private OrderEntity order;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemEntityId that = (OrderItemEntityId) o;
        return id.equals(that.id) && order.equals(that.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order);
    }
}


//clase auxiliar (POJO) que define los campos de la clave primaria compuesta.
//Debe tener los mismos nombres y tipos que los campos marcados con @Id en la entidad.
//JPA usará ambos campos (id y order) para identificar de forma única cada fila en la tabla order_items