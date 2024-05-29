package com.example.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;


@Configuration
public class RedisTemplateConfig {

    // To create object bean of RedisTemplate and store in Spring container and use the bean when required
    @Bean
    public RedisTemplate<String, Object> getRedisTemplate(RedisConnectionFactory connectionFactory) {
        // Create a generic RedisTemplate object which takes a key of type String and value of type Object
        // Any type of value can be stored in Redis
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // Set the connection factory for the RedisTemplate object, this is used to connect to Redis
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }
}
