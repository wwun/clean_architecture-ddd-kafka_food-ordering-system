package com.food.ordering.system.order.service.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public OrderDomainService orderDomainService() {
        return new OrderDomainServiceImpl();    //this is created because OrderDomainServiceImpl is not annotated with bean as it is the domain which doesn't depend on frameworks, but to be used it should be added here
    }
}
