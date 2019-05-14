package com.easysoft.commons.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

/**
 * MyBatis集成配置DruidDataSource数据源
 * @author IbanHsu
 * @Date 2019-05-09
 *
 */
@EnableTransactionManagement
@MapperScan({ "com.**.dao", "com.**.mapper" }) // 基本扫描包路径设置
@ConditionalOnProperty(name = {
		"spring.datasource.type" }, havingValue = "com.easysoft.commons.db.EasySoftDataSource", matchIfMissing = false)
public class DataSourceConfiguration implements TransactionManagementConfigurer {

	private Logger log = LoggerFactory.getLogger(DataSourceConfiguration.class);
	@Autowired
	private DataSource dataSource;
	/*
	 *  设置MyBatis全局配置文件的位置
	 */
	@Value("${mybatis.globalConfig:classpath:mybatis-config.xml}")
	private String mybatisConfig;
	
	/**
	 * <p>Title: sqlSessionFactoryBean</p>
	 * <p>Description: SqlSessionFactory 设置及加载配置文件</p>
	 * @return
	 */
	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactoryBean() {
		try {
			SqlSessionFactoryBean sessionFacrotyBean = new SqlSessionFactoryBean();
			sessionFacrotyBean.setDataSource(dataSource);
			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			sessionFacrotyBean.setConfigLocation(resolver.getResource(this.mybatisConfig));
			log.info("使用 DruidDataSource自定义设置完成，且加载的MyBatis文件位置为：[{}] ，可以修改 [{}] 属性来设置Mybatis全局属性", this.mybatisConfig,
					"mybatis.globalConfig");
			SqlSessionFactory sessionFactory = sessionFacrotyBean.getObject();
			return sessionFactory;
		} catch (Exception e) {
			log.error("DruidDataSourceConfig 自定义设置失败，失败可能原因：{}", e.getMessage());
		}
		return null;
	}

	/**
	 * @see org.springframework.transaction.annotation.
	 *      TransactionManagementConfigurer#sqlSessionTemplate
	 * @param sqlSessionFactory
	 * @return
	 */
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.transaction.annotation.
	 *      TransactionManagementConfigurer#annotationDrivenTransactionManager()
	 */
	@Bean
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}
}
