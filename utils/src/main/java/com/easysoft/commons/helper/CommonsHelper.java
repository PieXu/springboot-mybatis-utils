package com.easysoft.commons.helper;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

import sun.misc.BASE64Encoder;

/**
 * @desc:
 * @time: 2017年7月10日 上午10:59:06
 * @author IvanHsu
 */
public class CommonsHelper {
	
	/**
	 * UUID 生成
	 * @return
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		return uuid;
	}
	
	/**
	 * 加密
	 * @param str
	 * @return
	 * String
	 * 2017年6月19日
	 */
	public static String encoderPassword(String salt,String password)
	{
		return encoder(password+salt);
	}
	
	/**
	 * 加密
	 * @param str
	 * @return
	 * String
	 * 2017年6月19日
	 */
	public static String encoder(String str)
	{
		String newstr = "";
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			BASE64Encoder base64en = new BASE64Encoder();
			newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return newstr;
	}

	/**
	 * 取盐值
	 * @param length
	 * @return
	 * String
	 * 2017年6月19日
	 */
	public static String getSalt(int length)
	{
		String base = "abcdefghijklmnopqrstuvwxyz1234567890";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(encoderPassword("9r13o", "123456"));
	}
	
}
