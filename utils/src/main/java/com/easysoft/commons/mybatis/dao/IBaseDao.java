package com.easysoft.commons.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.easysoft.commons.mybatis.provider.BaseSqlProvider;
import com.github.pagehelper.Page;


/**
 * 
* <p>Title: IBaseDao</p>
* <p>Description: 基类 Mapper</p>
* <p>Company: easysoft.ltd</p> 
* @author IvanHsu
* @date 2019年5月12日
 */
public interface IBaseDao<T> {

	/**
	 * 
	* <p>Title: getById</p>
	* <p>Description: 主键查找</p>
	* @param id
	* @return
	 */
	@SelectProvider(type = BaseSqlProvider.class, method = "get")
	public T get(T record);
	
	/**
	* <p>Title: getListByExample</p>
	* <p>Description: </p>
	* @param record
	* @param paramMap
	* @return
	 */
 	@SelectProvider(type = BaseSqlProvider.class, method = "getListByExample")
	public Page<T> getPageByExample(T record);
 	
 	/**
 	 * 
 	* Title: getListByExample
 	* Description: 
 	* @param record
 	* @return
 	 */
	@SelectProvider(type = BaseSqlProvider.class, method = "getListByExample")
	public List<T> getListByExample(T record);
 	
 	/**
 	* <p>Title: insert</p>
 	* <p>Description: 保存</p>
 	* @param record
 	* @param valueMap
 	 */
 	@InsertProvider(type = BaseSqlProvider.class, method = "insert")
 	public void insert(T record);
 	
 	/**
 	 * 
 	* <p>Title: update</p>
 	* <p>Description: 更新 </p>
 	* @param record
 	* @param valueMap
 	 */
 	@UpdateProvider(type = BaseSqlProvider.class, method = "update")
 	public void update(T record);
 	
 	/**
 	 * 
 	* <p>Title: delete</p>
 	* <p>Description: </p>
 	* @param id
 	 */
 	@DeleteProvider(type = BaseSqlProvider.class, method = "delete")
 	public void delete(T record);
 	
 	/**
 	 * 
 	* Title: delBathById
 	* Description: 按主键批量删除,逻辑删除 del_flag 字段
 	* @param ids
 	 */
	@UpdateProvider(type = BaseSqlProvider.class, method = "changeDelFlag")
 	public void changeDelFlag(T record,@Param("ids")String[] ids,@Param("delFlag")String delFlag);
	
}
