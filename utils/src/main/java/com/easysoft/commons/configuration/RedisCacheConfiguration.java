package com.easysoft.commons.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 
* <p>Title: RedisCacheConfiguration</p>
* <p>Description: Redis缓存设置</p>
* <p>Company: easysoft.ltd</p> 
* @author IvanHsu
* @date 2019年5月9日
 */
//@EnableCaching
//@Configuration
public class RedisCacheConfiguration {

	/**
	 * 缓存管理器
	 * @param redisTemplate
	 * @return
	 * CacheManager
	 * 2017年11月8日
	 */
    @Bean 
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) 
    {
        RedisCacheManager cacheManager = RedisCacheManager.create(connectionFactory);//  new RedisCacheManager(redisTemplate);
        return cacheManager;
    }
    
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory)
    {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(factory);
        setSerializer(stringRedisTemplate);
        stringRedisTemplate.afterPropertiesSet();
        return stringRedisTemplate;
    }
    
    /**
     * 设置序列化工具,防止乱码
     * @param template
     * void
     * 2017年11月8日
     */
	private void setSerializer(StringRedisTemplate redisTemplate) {

		GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
		// 设置值（value）的序列化采用FastJsonRedisSerializer。
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		// redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
		// 设置键（key）的序列化采用StringRedisSerializer。
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.afterPropertiesSet();
	}
	
}
