package com.food.ordering.system.kafka.config.data;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka-consumer-config")
public class KafkaConsumerConfigData {

    //contenedor para las propiedades de configuración del consumidor de Kafka. Al igual que KafkaProducerConfigData, se llena automáticamente con los valores definidos en el archivo .yml gracias a las anotaciones de Spring (@ConfigurationProperties). Estas propiedades se usarán para configurar el comportamiento del consumidor de Kafka, como el deserializador, el grupo de consumidores, el tiempo de espera y las políticas de reintento

    private String keyDeserializer;
    private String valueDeserializer;
    private String autoOffsetReset;
    private String specificAvroReaderKey;
    private String specificAvroReader;
    private Boolean batchListener;
    private Boolean autoStartup;
    private Integer concurrencyLevel;
    private Integer sessionTimeoutMs;
    private Integer heartbeatIntervalMs;
    private Integer maxPollIntervalMs;
    private Long pollTimeoutMs;
    private Integer maxPollRecords;
    private Integer maxPartitionFetchBytesDefault;
    private Integer maxPartitionFetchBytesBoostFactor;
}