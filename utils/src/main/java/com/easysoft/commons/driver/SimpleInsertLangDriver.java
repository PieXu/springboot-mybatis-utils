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
import com.google.common.base.CaseFormat;

/**
 * MyBatis转义 Insert通用
 * 
 * @author IvanHsu
 */
public class SimpleInsertLangDriver extends XMLLanguageDriver implements LanguageDriver 
{
	private final Pattern inPattern = Pattern.compile("\\(#\\{(\\w+)\\}\\)");

	@Override
	public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {

		Matcher matcher = inPattern.matcher(script);
		if (matcher.find()) {
			StringBuilder sb = new StringBuilder();
			StringBuilder tmp = new StringBuilder();
			sb.append("(");
			parameterType.getSuperclass(); 
			Field[] fs = new Field[]{};
					fs = getBeanFields(parameterType,fs);
			for (Field field : fs) {
				 if (!field.isAnnotationPresent(Column.class)) {  
					sb.append(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName()) + ",");
					tmp.append("#{" + field.getName() + "},");
				 }
			}

			sb.deleteCharAt(sb.lastIndexOf(","));
			tmp.deleteCharAt(tmp.lastIndexOf(","));
			sb.append(") values (" + tmp.toString() + ")");

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
