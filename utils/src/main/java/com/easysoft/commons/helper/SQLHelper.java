package com.easysoft.commons.helper;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.CaseFormat;

/**
 * @desc: sql 模糊查询 匹配工具
 * @time: 2017年7月27日 下午1:31:12
 * @author IvanHsu
 */
public class SQLHelper {
	private static final String[] characters = { "%", "_" };
	private static final String HQL_ESCAPE_KEY = "^";

	/**
	 * 关键词屏蔽 防SQL注入
	 * @param likeStr
	 * @param start
	 * @param end
	 * @return
	 */
	public static String escape(String likeStr,boolean start,boolean end)
	{
		StringBuffer resultStr = new StringBuffer("'");
		if(start){
			resultStr.append("%");
		}
		if(isNeedEscape(likeStr))
		{
			likeStr = likeStr.replace("_", "^_");
			likeStr = likeStr.replace("%", "^%");
			resultStr.append(likeStr);
			if(end){
				resultStr.append("%");
			}
			resultStr.append("' escape '").append(HQL_ESCAPE_KEY).append("'");
		}else{
			resultStr.append(likeStr);
			if(end)
				resultStr.append("%");
			resultStr.append("'");
		}
		return resultStr.toString();
	}
	
	private static boolean isNeedEscape(String likeStr)
	{
		for (String s : characters) {
			if (likeStr.indexOf(s) > -1) {
				return true;
			}
		}
		return false;
	}
	
	/**
	* <p>Title: getTableName</p>
	* <p>Description: 按照规则将类名准成实际对应的表明 </p>
	* @param classSimpleName
	* @return
	 */
	public static String getRealTableName(String classSimpleName,String prefix)
	{
		prefix = StringUtils.isNotBlank(prefix) ? prefix : "";
		StringBuffer tablename = new StringBuffer(prefix);
		tablename.append(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, classSimpleName));
		return tablename.toString();
	}
}
