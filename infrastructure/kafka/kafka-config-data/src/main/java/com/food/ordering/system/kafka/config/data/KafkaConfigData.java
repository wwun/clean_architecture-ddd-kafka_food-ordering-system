package com.food.ordering.system.kafka.config.data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka-config")
public class KafkaConfigData {

    //contenedor para las propiedades generales de configuración de Kafka. Se llena automáticamente con los valores definidos en el archivo .yml (como application.yml o application-kafka.yml) gracias a las anotaciones de Spring (@ConfigurationProperties)
    //centraliza la configuración general de Kafka, que será utilizada tanto por productores como por consumidores, y posiblemente por otros componentes que interactúen con el clúster de Kafka

    private String bootstrapServers;
    private String schemaRegistryUrlKey;
    private String schemaRegistryUrl;
    private Integer numOfPartitions;
    private Short replicationFactor;
}
