package com.food.ordering.system.payment.service.domain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data   //annotation from Lombok to generate getters, setters, toString, equals, and hashCode methods automatically
@Configuration
@ConfigurationProperties(prefix = "payment-service")    // this prefix is used in application.yml which is used to load properties
public class PaymentServiceConfigData {
    private String paymentRequestTopicName;
    private String paymentResponseTopicName;
}

/*La etiqueta @ConfigurationProperties es una anotación en Spring Boot que se utiliza para enlazar propiedades de un archivo de configuración (como application.properties o application.yml) con un objeto Java.
Cuando se agrega la anotación @ConfigurationProperties a una clase, Spring Boot buscará propiedades en el archivo de configuración que coincidan con el prefijo especificado en la anotación. En este caso, el prefijo es "payment-service".
Por ejemplo, si tienes un archivo application.properties con las siguientes propiedades:
Code
payment-service.url=https://api.payment.com
payment-service.username=mi_usuario
payment-service.password=mi_contraseña
Spring Boot enlazará automáticamente las propiedades del archivo de configuración con los campos de la clase PaymentServiceConfig. De esta manera, puedes acceder a las propiedades de configuración de manera segura y tipeada en tu aplicación.
La ventaja de utilizar @ConfigurationProperties es que te permite:
Evitar el uso de @Value para cada propiedad individual
Validar las propiedades de configuración de manera centralizada
Mejorar la legibilidad y mantenibilidad del código*/