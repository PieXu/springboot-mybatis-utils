package com.easysoft.commons.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * like查询的注解字段
 * @author IvanHsu
 * @2018年5月8日 上午11:58:17
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LikeQuery {
	public abstract String value() default "full";
}
