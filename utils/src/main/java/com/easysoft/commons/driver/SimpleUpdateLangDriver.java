package com.easysoft.commons.driver;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import com.easysoft.commons.annotation.Column;
import com.google.common.base.CaseFormat;

/**
 * MyBatis转义Update通用语句
 * @author IvanHsu
 */
public class SimpleUpdateLangDriver extends XMLLanguageDriver implements LanguageDriver {
	private final Pattern inPattern = Pattern.compile("\\(#\\{(\\w+)\\}\\)");

	@Override
	public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
		Matcher matcher = inPattern.matcher(script);
		if (matcher.find()) {
			StringBuilder sb = new StringBuilder();
			sb.append("<set>");

			for (Field field : parameterType.getDeclaredFields()) {
				 if (!field.isAnnotationPresent(Column.class)) {  
					String tmp = "<if test=\"_field != null\">_column=#{_field},</if>";
					sb.append(tmp.replaceAll("_field", field.getName()).replaceAll("_column",
							CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName())));
				 }
			}

			sb.deleteCharAt(sb.lastIndexOf(","));
			sb.append("</set>");

			script = matcher.replaceAll(sb.toString());
			script = "<script>" + script + "</script>";
		}

		return super.createSqlSource(configuration, script, parameterType);
	}
}
