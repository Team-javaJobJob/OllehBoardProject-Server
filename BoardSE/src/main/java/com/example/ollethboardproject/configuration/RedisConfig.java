package com.example.ollethboardproject.configuration;

import com.example.ollethboardproject.domain.entity.Chat;
import com.example.ollethboardproject.domain.entity.Community;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.database}")
    private int redisDatabase;

    /*
    LettuceConnectionFactory 객체를 생성하는 메소드로, RedisStandaloneConfiguration을 사용하여 Redis 서버의 호스트, 포트,
    데이터베이스를 설정하고 이를 반환합니다. 이 메서드가 반환한 객체는 @Primary 어노테이션 때문에 다른 Redis 연결 설정이 없으면
    기본 ConnectionFactory로 사용됩니다.
    */
    @Bean
    @Primary
    public LettuceConnectionFactory lettuceConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisHost, redisPort);
        redisStandaloneConfiguration.setDatabase(redisDatabase);
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    /*
    RedisConnectionFactory를 설정하는 메소드입니다. 현재 설정에서는 호스트와 포트 정보만 사용하여 연결을 설정합니다.
    */

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(redisHost,redisPort);
    }

    //Redis 연산을 위한 RedisTemplate 객체를 생성하는 메소드입니다. 해당 세팅으로 문자열 키와 Chat 엔티티 값 객체를 이용할 수 있도록 설정되어 있습니다.

    @Bean
    public RedisTemplate<String, Chat> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Chat> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer());

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer());

        return redisTemplate;
    }
    //StringRedisSerializer: 키는 문자열로 처리되며, StringRedisSerializer를 통해 해당 역할을 수행합니다.
    private Jackson2JsonRedisSerializer jackson2JsonRedisSerializer() {
        Jackson2JsonRedisSerializer<Object> serializer =
                new Jackson2JsonRedisSerializer<>(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        serializer.setObjectMapper(objectMapper);

        return serializer;
    }

}
