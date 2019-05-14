package com.easysoft.commons.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
* <p>Title: EasySoftProperitesConfig</p>
* <p>Description: 插件配置属性类</p>
* <p>Company: easysoft.ltd</p> 
* @author IvanHsu
* @date 2019年5月13日
 */
@ConfigurationProperties(prefix="easysoft.mybatis")
public class EasySoftProperitesConfig {

	/*
	 * 业务表的前缀，用户SQL语句的动态生成与封装
	 */
	private String tabPrefix;

	public String getTabPrefix() {
		return tabPrefix;
	}

	public void setTabPrefix(String tabPrefix) {
		this.tabPrefix = tabPrefix;
	}

}
