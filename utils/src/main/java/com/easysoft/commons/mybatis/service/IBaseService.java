package com.easysoft.commons.mybatis.service;

import java.util.List;

import com.github.pagehelper.Page;

/**
 * 
* <p>Title: IBaseService</p>
* <p>Description: Service接口基类</p>
* <p>Company: easysoft.ltd</p> 
* @author IvanHsu
* @date 2019年5月14日
 */
public interface IBaseService<T> {

	/**
	 * 
	* <p>Title: getPage</p>
	* <p>Description: 分页查询</p>
	* @param record
	* @param pageNum
	* @param pageSize
	* @param orderBy 排序字符串 example: id desc , name asc
	* @return
	 */
	public Page<T> getPage(T record,Integer pageNum,Integer pageSize,String orderBy);
	
	/**
	 * 
	* <p>Title: getPage</p>
	* <p>Description: 查询所有 不分页查询</p>
	* @param record
	* @return
	 */
	public List<T> getList(T record);
	
	/**
	 * 
	* <p>Title: getById</p>
	* <p>Description: 主键查找，将主键封装到对象中 </p>
	* @param record
	* @return
	 */
	public T get(T record);
	
	/**
	 * 
	* <p>Title: insert</p>
	* <p>Description: 插入保存 </p>
	* @param record
	 */
	public void insert(T record);
	
	/**
	 * 
	* <p>Title: update</p>
	* <p>Description: 更新</p>
	* @param record
	 */
	public void update(T record);
	
	/**
	 * 
	* <p>Title: delete</p>
	* <p>Description: 删除 </p>
	* @param record
	 */
	public void delete(T record);
	
	/**
	 * 
	* Title: delByIds
	* Description: 主键批量删除
	* @param record
	* @param ids
	 */
	public void delByIds(T record,String ids);
}
