package com.easysoft.commons.mybatis.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.easysoft.commons.cons.Constants;
import com.easysoft.commons.mybatis.dao.IBaseDao;
import com.easysoft.commons.mybatis.service.IBaseService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * 
* <p>Title: BaseServiceImpl</p>
* <p>Description: service实现类基类</p>
* <p>Company: easysoft.ltd</p> 
* @author IvanHsu
* @date 2019年5月14日
 */
public class BaseServiceImpl<T> implements IBaseService<T>{

	@Autowired
	private IBaseDao<T> baseDao;
	
	/**
	 * 分页查询
	 */
	@Override
	public Page<T> getPage(T record,Integer pageNum,Integer pageSize,String orderBy) 
	{
		/*
		 * 1、设置分页信息i
		 */
		PageHelper.startPage(pageNum, pageSize);
		/*
		 * 2、添加排序信息
		 */
		if(null!=orderBy && StringUtils.isNotBlank(orderBy)){
			PageHelper.orderBy(orderBy);
		}
		return baseDao.getPageByExample(record);
	}

	/*
	 * （非 Javadoc）
	* Title: getList
	* Description: 
	* @param record
	* @return
	* @see com.easysoft.commons.mybatis.service.IBaseService#getList(java.lang.Object)
	 */
	@Override
	public List<T> getList(T record) {
		return baseDao.getListByExample(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public T get(T record) {
		if(null!=record){
			return baseDao.get(record);
		}
		return null;
	}

	/**
	 * 保存
	 */
	@Override
	public void insert(T record) {
		if(null!=record){
			baseDao.insert(record);
		}
	}

	/**
	 * 更新
	 */
	@Override
	public void update(T record) {
		if(null!=record){
			baseDao.update(record);
		}
	}

	/**
	 * 删除
	 */
	@Override
	public void delete(T record) {
		if(null!=record){
			baseDao.delete(record);
		}
	}

	@Override
	public void delByIds(T record,String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] idArr = ids.split(",");
			baseDao.changeDelFlag(record, idArr, Constants.STATUS_DELETE);
		}
	}


}
