package com.example.bank.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class Config {

    public static final String QUEUE_NAME = "bankQueue";

    @Bean
    public ModelMapper modelMapper(Set<Converter> converterSet) {
        ModelMapper modelMapper = new ModelMapper();
        converterSet.forEach(modelMapper::addConverter);

        return modelMapper;
    }

    @Bean
    public Queue myQueue() {
        return new Queue(QUEUE_NAME);
    }
}
