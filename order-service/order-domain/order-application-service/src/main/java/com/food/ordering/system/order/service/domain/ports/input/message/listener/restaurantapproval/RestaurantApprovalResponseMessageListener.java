package com.food.ordering.system.order.service.domain.ports.input.message.listener.restaurantapproval;

import com.food.ordering.system.order.service.domain.dto.message.RestaurantApprovalResponse;

public interface RestaurantApprovalResponseMessageListener {

    //el paqete input define qué necesita el sistema del exterior (infraestructura, persistencia, mensajería)
    //permite que la lógica de negocio y aplicación no dependan de detalles técnicos (base de datos, mensajería, etc.)
    //facilita cambiar la implementación (por ejemplo, de JPA a MongoDB) sin tocar la lógica de negocio

    //interfaces que definen cómo reaccionar a eventos externos (por ejemplo, cuando llega una respuesta de pago o de aprobación de restaurante)

    void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse);
    void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse);
}
