package com.easysoft.commons.driver;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import com.easysoft.commons.annotation.Column;
import com.easysoft.commons.annotation.LikeQuery;
import com.google.common.base.CaseFormat;

/**
 * MyBatis查询转义 Select通用
 * @author IvanHsu
 */
public class SimpleSelectLangDriver extends XMLLanguageDriver implements LanguageDriver 
{
	private final Pattern inPattern = Pattern.compile("\\(#\\{(\\w+)\\}\\)");

	@Override
	public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {

		Matcher matcher = inPattern.matcher(script);
		if (matcher.find()) {
			StringBuilder sb = new StringBuilder();
			sb.append("<where> 1=1 ");
			parameterType.getSuperclass(); 
			Field[] fs = new Field[]{};
			fs = getBeanFields(parameterType,fs);
			for (Field field : fs) {
				 if (!field.isAnnotationPresent(Column.class)) { 
					if(field.isAnnotationPresent(LikeQuery.class)){
						String value = field.getAnnotation(LikeQuery.class).value();
						String patten = "";
						if("full".equalsIgnoreCase(value)){//全匹配
							patten = "<bind name=\"pattern\" value=\"'%'+_field+'%'\"/>";
						}else if("left".equalsIgnoreCase(value)){//左边匹配
							patten = "<bind name=\"pattern\" value=\"'%'+_field\"/>";
						}else if("right".equalsIgnoreCase(value)){//右边匹配
							patten = "<bind name=\"pattern\" value=\"_field+'%'\"/>";
						}
						//TODO 判断值是否需要优化
						String tmp = "<if test=\"_field != null and _field != '' \">"+patten+" AND _column like #{pattern}</if>";
						sb.append(tmp.replaceAll("_field", field.getName()).replaceAll("_column",
								CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName())));
					}else{
						String tmp = "<if test=\"_field != null and _field != '' \">AND _column=#{_field}</if>";
						sb.append(tmp.replaceAll("_field", field.getName()).replaceAll("_column",
								CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName())));
					}
				 }
			}

			sb.append("</where>");
			script = matcher.replaceAll(sb.toString());
			script = "<script>" + script + "</script>";
		}

		return super.createSqlSource(configuration, script, parameterType);
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
}
