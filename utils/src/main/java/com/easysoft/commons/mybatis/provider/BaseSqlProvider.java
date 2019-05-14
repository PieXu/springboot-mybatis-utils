package com.easysoft.commons.mybatis.provider;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import com.easysoft.commons.annotation.PrimaryKey;
import com.easysoft.commons.annotation.TableName;
import com.easysoft.commons.annotation.VirtualColumn;
import com.easysoft.commons.helper.SQLHelper;
import com.google.common.base.CaseFormat;

/**
 * 
* <p>Title: BaseSqlProvider</p>
* <p>Description: 自定义SQL拼装</p>
* <p>Company: easysoft.ltd</p> 
* @author IvanHsu
* @date 2019年5月12日
 */
@Component
public class BaseSqlProvider<T> {
	
	private static Logger logger = LoggerFactory.getLogger(BaseSqlProvider.class);

	/**
	 * 
	* <p>Title: getById</p>
	* <p>Description: 按照主键查找</p>
	* @param id
	* @return
	 */
	public String get(T bean)
	{
		SQL	querySQL = new SQL();
		try {
			querySQL.SELECT("*").FROM(this.getTableName(bean)).WHERE("id=#{id}");
		} catch (Exception e) {
			logger.error("{}主键查询方法异常：—{}",bean.getClass().getName(),e.getMessage());
		}
		return querySQL.toString();
	}
	
	/**
	 * 
	* <p>Title: getListByExample</p>
	* <p>Description: 按照对象条件查询 </p>
	* @param bean
	* @return
	 */
	public String getListByExample(T bean)
	{
		/*
		 * 1、取得当前的参数信息
		 */
		Field[] fs = new Field[]{};
		fs = getBeanFields(bean.getClass(),fs);
		Map<String, Object> initValueMap = this.initValueMap(bean, fs);
		/*
		 * 2、查询SQL拼装
		 */
		SQL querySQL = new SQL();
		querySQL.SELECT("*").FROM(this.getTableName(bean));
		/*
		 * 3、封装查询条件的SQL部分
		 */
		for (Field fileId : fs) {
			if(!fileId.isAnnotationPresent(VirtualColumn.class)){
				 if(initValueMap.containsKey(fileId.getName())){
					 querySQL.WHERE(" _column = #{_field}".replaceAll("_field", fileId.getName()).replaceAll("_column",
								CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fileId.getName())));
				 }
			}
		}
		return querySQL.toString();
	}
	
	/**
	 * 
	* <p>Title: insert</p>
	* <p>Description: 保存的方法 </p>
	* @param record
	* @return
	 */
	public String insert(T bean)
	{
		/*
		 * 1、取得当前的参数信息
		 */
		Field[] fs = new Field[]{};
		fs = getBeanFields(bean.getClass(),fs);
		Map<String, Object> initValueMap = this.initValueMap(bean, fs);
		/*
		 * 2、查询SQL拼装
		 */
		SQL insertSQL = new SQL();
		insertSQL.INSERT_INTO(this.getTableName(bean));
		/*
		 * 3、封装查询条件的SQL部分
		 */
		for (Field fileId : fs) {
			if(!fileId.isAnnotationPresent(VirtualColumn.class)){
				 if(initValueMap.containsKey(fileId.getName())){
					 insertSQL.INTO_COLUMNS(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fileId.getName()));
					 insertSQL.INTO_VALUES("#{_field}".replaceAll("_field", fileId.getName()));
				 }
			}
		}
		return insertSQL.toString();
	}
	
	/**
	 * 
	* <p>Title: update</p>
	* <p>Description: 更新的方法</p>
	* @param bean
	* @return
	 */
	public String update(T bean)
	{
		/*
		 * 1、取得当前的参数信息
		 */
		Field[] fs = new Field[]{};
		fs = getBeanFields(bean.getClass(),fs);
		Map<String, Object> initValueMap = this.initValueMap(bean, fs);
		/*
		 * 2、查询SQL拼装
		 */
		SQL updateSQL = new SQL();
		updateSQL.UPDATE(this.getTableName(bean));
		/*
		 * 3、封装查询条件的SQL部分
		 */
		for (Field fileId : fs) {
			if(!fileId.isAnnotationPresent(VirtualColumn.class)){
				 if(initValueMap.containsKey(fileId.getName())){
					 updateSQL.SET(" _column = #{_field}".replaceAll("_field", fileId.getName()).replaceAll("_column",
								CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fileId.getName())));
				 }
			}
		}
		/*
		 * 更新条件，按照ID
		 */
		updateSQL.WHERE("id=#{id}");
		
		return updateSQL.toString();
	}
	
	/**
	 * 
	* <p>Title: deleteById</p>
	* <p>Description: 删除方法</p>
	* @param bean
	* @return
	 */
	public String delete(T bean)
	{
		SQL	delSQL = new SQL();
		try {
			if(this.checkPrimaryKeyExistValue(bean)){
				delSQL.DELETE_FROM(this.getTableName(bean)).WHERE("id=#{id}");
			}
		} catch (Exception e) {
			logger.error("{}删除方法异常：—{}",bean.getClass().getName(),e.getMessage());
		}
		return delSQL.toString();
	}
	
	
	/**
	 * 获取所有的字段属性
	 * @param cls
	 * @param fs
	 * @return
	 */
	private static Field[] getBeanFields(Class<?> cls,Field[] fs)
	{
		if(null == fs)
			fs = new Field[]{};
        fs = (Field[])  ArrayUtils.addAll(fs, cls.getDeclaredFields());
        if(cls.getSuperclass()!=null){
            Class<?> clsSup = cls.getSuperclass();
            fs = getBeanFields(clsSup,fs);
        }
        return fs;
    }
	
	/**
	 * 
	* <p>Title: initValueMap</p>
	* <p>Description: 封装初始化参数值， 以fileId 为key getMethod的值为value返回</p>
	* @param clazz
	* @return
	 */
	private Map<String,Object> initValueMap(T bean,Field[] fileIds)
	{
		Map<String,Object> valmap = new HashMap<String,Object>();
		String fileIdName = null;
		try {
			if(ArrayUtils.isNotEmpty(fileIds)){
				for(Field fileId : fileIds)
				{
					fileIdName = fileId.getName();
					//没有注释虚拟列的放到对象里
					if(!fileId.isAnnotationPresent(VirtualColumn.class)){
						Method m = bean.getClass().getMethod("get"+fileIdName.substring(0, 1).toUpperCase() + fileIdName.substring(1));
						Object invoke = m.invoke(bean);
						if(null!=invoke){
							valmap.put(fileIdName, invoke);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("{}类的属性字段{}解析异：{}",bean.getClass().getName(),fileIdName,e.getMessage());
		} 
		return valmap;
	}
	
	/**
	 * 获取表名称
	* <p>Title: getTableName</p>
	* <p>Description: </p>
	* @param bean
	* @param fileIds
	* @return
	 */
	private String getTableName(T bean)
	{
		String tableName= null;
		try {
			TableName annotation = bean.getClass().getAnnotation(TableName.class);
			if(null != annotation){
				tableName = annotation.value();
			}
			/*
			 * 当没有指定具体的表名的时候
			 * 用驼峰格式解析类的名称对应表名
			 */
			if(null == tableName || StringUtils.isEmpty(tableName)){
				tableName =SQLHelper.getRealTableName(bean.getClass().getSimpleName(),null);
			}
		} catch (Exception e) {
			logger.error("{}类的对应表名{}解析异：{}",bean.getClass().getName(),tableName,e.getMessage());
		} 
		return tableName;
	}
	
	/**
	 * 
	* <p>Title: checkPrimaryKeyExistValue</p>
	* <p>Description: 检查对象中主键是否有值 </p>
	* @param bean
	* @return
	 */
	private boolean checkPrimaryKeyExistValue(T bean)
	{
		boolean exsit = false;
		String idColumn= null;
		try {
			PrimaryKey annotation = bean.getClass().getAnnotation(PrimaryKey.class);
			if(null != annotation){
				idColumn = annotation.value();
				if(null != idColumn && StringUtils.isEmpty(idColumn)){
					Method m = bean.getClass().getMethod("get"+idColumn.substring(0, 1).toUpperCase() + idColumn.substring(1));
					Object idValue = m.invoke(bean);
					if(null!=idValue)
						exsit = true;
				}
			}
		} catch (Exception e) {
			logger.error("{}类的对没有配置主键字段，请在对象类@PrimaryKey(\"idColumn\")，异常描述：{}",bean.getClass().getName(),e.getMessage());
		} 
		return exsit;
	}
	
}
