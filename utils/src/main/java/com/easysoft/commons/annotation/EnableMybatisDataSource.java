package com.easysoft.commons.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.easysoft.commons.configuration.DataSourceConfiguration;

/**
 * @see EnableMybatisDataSource
 * @mail xupai_911@163.com
 * @author IvanHsu
 * 配置Mybatis集成 DruidDataSource 数据源
 * 
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ DataSourceConfiguration.class })
public @interface EnableMybatisDataSource {

}
