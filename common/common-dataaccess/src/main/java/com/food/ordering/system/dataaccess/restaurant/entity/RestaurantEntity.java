package com.food.ordering.system.dataaccess.restaurant.entity;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter //Lombok: genera getters para todos los campos
@Setter //Lombok: genera setters para todos los campos
@Entity
@Builder
@IdClass(RestaurantEntityId.class)  //La anotación @IdClass(RestaurantEntityId.class) indica que la entidad tiene una clave primaria compuesta (formada por más de un campo), En este caso, la clave primaria de RestaurantEntity está formada por los campos: restaurantId y productId
@NoArgsConstructor  //Lombok: genera un constructor sin argumentos
@AllArgsConstructor //Lombok: genera un constructor con todos los argumentos
@Table(name = "order_restaurant_m_view", schema = "restaurant")
public class RestaurantEntity {
    @Id
    private UUID restaurantId;
    @Id
    private UUID productId;
    private String restaurantName;
    private Boolean restaurantActive;
    private String productName;
    private BigDecimal productPrice;
    private Boolean productAvailable;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantEntity that = (RestaurantEntity) o;
        return restaurantId.equals(that.restaurantId) && productId.equals(that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantId, productId);
    }
}
