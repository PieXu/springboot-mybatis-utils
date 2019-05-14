package com.easysoft.commons.driver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

/**
 * MyBatis in 查询转换
 * @author IvanHsu
 */
public class SimpleInLangDriver extends XMLLanguageDriver implements LanguageDriver {
	private static final Pattern inPattern = Pattern.compile("\\(#\\{(\\w+)\\}\\)");

	@Override
	public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {

		Matcher matcher = inPattern.matcher(script);
		if (matcher.find()) {
			script = matcher.replaceAll("<foreach collection=\"$1\" item=\"_item\" open=\"(\" "
					+ "separator=\",\" close=\")\" >#{_item}</foreach>");
		}
		script = "<script>" + script + "</script>";
		return super.createSqlSource(configuration, script, parameterType);
	}
}
