package com.zhumingbei.techblog.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.util.Assert;

@Slf4j
@Configuration
@EnableRedisHttpSession
public class RedisConfig {
    @Bean(name = "RedisTemplate") //自定义Bean RedisTemplate
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
/*
    @Bean("springSessionDefaultRedisSerializer")
    public RedisSerializer<Object> defaultRedisSerializer(){
        log.debug("自定义Redis Session序列化加载成功");
        return valueSerializer();
    }

    private RedisSerializer<Object> valueSerializer() {
        return new CustomJackson2JsonRedisSerializer();
    }

    private class CustomJackson2JsonRedisSerializer extends GenericJackson2JsonRedisSerializer {
        @Override
        public Object deserialize(@Nullable byte[] source) throws SerializationException {
            Object result = null;
            try {
                result = super.deserialize(source);
            } catch (Exception e) {
                log.debug("自定义反序列化错误：{}", e.getMessage());
            }
            return result;
        }
    }


*/
}
