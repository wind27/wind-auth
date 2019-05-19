package com.wind.auth.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis Key值管理
 *
 * @author qianchun 2018/9/4
 **/
@Service
public class RedisKey  {

    /**
     * 请求访问次数限制
     */
    public static final String AUTH_USER_METHOD_ACCESS_LIMIT_PREFIX = "auth_method_access_limit:";
}
