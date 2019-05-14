package com.easysoft.commons.helper;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
/**
 * 
* <p>Title: HttpHelper</p>
* <p>Description: HTTP Client Helper </p>
* <p>Company: easysoft.ltd</p> 
* @author IvanHsu
* @date 2019年5月9日
 */
public class HttpHelper {
	
	//日志打印
	private static Logger logger = LoggerFactory.getLogger(HttpHelper.class);

	/**
	 * 
	* <p>Title: doGet</p>
	* <p>Description: Get请求 </p>
	* @param url
	* @param param
	* @return
	 */
	public static String doGet(String url, Map<String, String> param) 
	{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String resultString = "";
		CloseableHttpResponse response = null;
		try {
			URIBuilder builder = new URIBuilder(url);
			/*
			 * 加载参数条件
			 */
			if(!CollectionUtils.isEmpty(param)) {
				for (String key : param.keySet()) {
					builder.addParameter(key, param.get(key));
				}
			}
			/*
			 * 创建请求并执行
			 */
			URI uri = builder.build();
			HttpGet httpGet = new HttpGet(uri);
			response = httpclient.execute(httpGet);
			/*
			 *  判断返回状态是否为200 ，200为请求成功
			 */
			if (response.getStatusLine().getStatusCode() == 200) {
				resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (Exception e) {
			logger.error("[{}] Get请求失败,可能失败原因：{}",url,e.getMessage());
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				httpclient.close();
			} catch (IOException e) {
				logger.error("[{}] Get请求失败,可能失败原因：{}",url,e.getMessage());
			}
		}
		return resultString;
	}

	/**
	* <p>Title: doPost</p>
	* <p>Description: Post请求</p>
	* @param url
	* @param param
	* @return
	 */
	public static String doPost(String url, Map<String, String> param) 
	{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			/*
			 * 创建Http Post请求
			 * 并 模拟表单 添加参数 
			 * 
			 */
			HttpPost httpPost = new HttpPost(url);
			if(!CollectionUtils.isEmpty(param)) {
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				for (String key : param.keySet()) {
					paramList.add(new BasicNameValuePair(key, param.get(key)));
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
				httpPost.setEntity(entity);
			}
			/*
			 * 执行Post请求
			 */
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			logger.error("[{}] post请求失败,可能失败原因：{}",url,e.getMessage());
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				logger.error("[{}] post请求失败,可能失败原因：{}",url,e.getMessage());
			}
		}
		return resultString;
	}

	/**
	 * 
	* <p>Title: doPostJson</p>
	* <p>Description: </p>
	* @param url
	* @param json
	* @return
	 */
	public static String doPostJson(String url, String json) 
	{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			/*
			 *  创建Http Post请求
			 *  创建请求内容Json格式
			 */
			HttpPost httpPost = new HttpPost(url);
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			/*
			 * 执行HTTP请求
			*/
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (Exception e) {
			logger.error("[{}] doPostJson请求失败,可能失败原因：{}",url,e.getMessage());
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				logger.error("[{}] doPostJson请求失败,可能失败原因：{}",url,e.getMessage());
			}
		}
		return resultString;
	}
	
}
