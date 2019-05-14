package com.easysoft.commons.mybatis.dao;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
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
	public Page<T> getListByExample(T record);
 	
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
	
}
