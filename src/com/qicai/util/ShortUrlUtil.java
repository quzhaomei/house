package com.qicai.util;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import com.yeepay.g3.utils.common.json.JSONUtils;

public class ShortUrlUtil {
	private static final String  action="http://jump.sinaapp.com/api.php";
	private static final String  alias="";
	/**
	 * 根据百度第三方url 转换为短地址
	 */
	public static String getShotUrl(String longUrl){
		String shortUrl=longUrl;
		PostMethod method=new PostMethod(action);
		method.addParameters(new NameValuePair[]{
				new NameValuePair("url_long", longUrl),
				new NameValuePair("alias", alias)
				}
					);
		HttpClient client=new HttpClient();
		try {
			int status=client.executeMethod(method);
			if(status==HttpStatus.SC_OK){
			String res=method.getResponseBodyAsString();
			@SuppressWarnings("unchecked")
			Map<String, Object>resultMap=JSONUtils.jsonToMap(res, String.class, String.class);
			shortUrl=(String) resultMap.get("url_short");
			}else{
				return getShotUrl(shortUrl);//递归调用
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return shortUrl;
	}
}
