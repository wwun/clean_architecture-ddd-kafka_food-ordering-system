package com.food.ordering.system.order.service.dataaccess.restaurant.mapper;

import com.food.ordering.system.dataaccess.restaurant.entity.RestaurantEntity;
import com.food.ordering.system.dataaccess.restaurant.exception.RestaurantDataAccessException;
import com.food.ordering.system.domain.valueobject.Money;
import com.food.ordering.system.domain.valueobject.ProductId;
import com.food.ordering.system.domain.valueobject.RestaurantId;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RestaurantDataAccessMapper {

    public List<UUID> restaurantToRestaurantProducts(Restaurant restaurant) {
        return restaurant.getProducts().stream()
                .map(product -> product.getId().getValue())
                .collect(Collectors.toList());
    }

    public Restaurant restaurantEntityToRestaurant(List<RestaurantEntity> restaurantEntities) {
        //Obtiene la entidad principal del restaurante (usualmente la primera de la lista, ya que todas corresponden al mismo restaurante)
        RestaurantEntity restaurantEntity =
                restaurantEntities.stream().findFirst().orElseThrow(() ->
                        new RestaurantDataAccessException("Restaurant could not be found!"));

        //Convierte la lista de entidades RestaurantEntity en una lista de objetos Product del dominio
        List<Product> restaurantProducts = restaurantEntities.stream().map(entity ->
                new Product(new ProductId(entity.getProductId()), entity.getProductName(),
                        new Money(entity.getProductPrice()))).toList();

        //construye y retorna el objeto Restaurant del dominio usando la información obtenida
        return Restaurant.builder()
                .restaurantId(new RestaurantId(restaurantEntity.getRestaurantId()))
                .products(restaurantProducts)
                .active(restaurantEntity.getRestaurantActive())
                .build();
    }
}


/*
¿Por qué se toma el primero de la lista como la entidad principal del restaurante?
En este contexto, cada elemento de la lista restaurantEntities representa un producto de un mismo restaurante.
Esto ocurre porque, al consultar la base de datos, probablemente se hace un JOIN entre la tabla de restaurantes y la de productos, devolviendo varias filas:

Cada fila tiene la información del restaurante (repetida) y la de un producto distinto.
Ejemplo:
Supón que el restaurante "Pizza Place" tiene 3 productos.
La consulta devuelve:

restaurant_id	restaurant_name	product_id	product_name	...
1	Pizza Place	101	Pizza	...
1	Pizza Place	102	Calzone	...
1	Pizza Place	103	Lasagna	...
En la lista restaurantEntities:

Cada elemento tiene los datos del restaurante (iguales) y de un producto (distinto).
Por eso, tomar el primero (findFirst()) es suficiente para obtener los datos generales del restaurante (id, nombre, estado, etc.), ya que son iguales en todos los elementos de la lista

luego recorre toda la lista para armar la lista de productos del restaurante.

Crea un objeto de dominio Restaurant con:

Los datos generales del restaurante (del primer elemento).
La lista de productos (de todos los elementos)

[restaurantEntities]
 ├─ [0] Restaurante: 1, Producto: 101
 ├─ [1] Restaurante: 1, Producto: 102
 └─ [2] Restaurante: 1, Producto: 103

↓
Toma datos generales del restaurante del primero (id, nombre, estado)
↓
Arma lista de productos con todos los elementos
↓
Crea objeto Restaurant del dominio:
  - id: 1
  - nombre: Pizza Place
  - productos: [Pizza, Calzone, Lasagna]

estos datos no son los del proyecto, solo se usa para explicar el codigo
*/