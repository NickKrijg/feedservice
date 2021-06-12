package com.kwetter.feed.redis;

import com.kwetter.feed.models.Post;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.List;

@Configuration
public class RedisConfig {
    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> builder
                .withCacheConfiguration("feedCache",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(2)));
    }

    @Bean
    RedisTemplate<String, List<Post>> redisTemplate() {
        RedisTemplate<String, List<Post>> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("localhost", 6379);
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }
}
