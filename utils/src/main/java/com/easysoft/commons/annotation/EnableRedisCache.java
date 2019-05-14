package com.easysoft.commons.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.easysoft.commons.configuration.RedisCacheConfiguration;

/**
 * 
* <p>Title: EnableRedisCache</p>
* <p>Description: 开启RedisCache</p>
* <p>Company: easysoft.ltd</p> 
* @author IvanHsu
* @date 2019年5月9日
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ RedisCacheConfiguration.class })
public @interface EnableRedisCache {

	
}
