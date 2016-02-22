package com.qicai.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * @author Beyond
 */
public class MessageSender {
	private static String sendUrl = "http://222.73.117.158/msg/";// Ӧ�õ�ַ
	private static String account = "jupinghui02";// �˺�
	private static String pswd = "wyx@getmore01";// ����
	private static String testInfo = "���ã�������֤����123456";// ��������
	
	public static HttpSendResult sendMsg(String mobile, String msg) throws Exception{
		if(msg==null){
			msg=testInfo;
		}
		return parseResult(
				send(sendUrl, account, pswd, mobile, msg, true, null, null
						));
	}
	public static void main(String[] args) {
		try {
			System.out.println(sendMsg("18621761401",
					"�𾴵�СQ,����è�Ҿӷ���������ķ���,����������http://www.getmore.cn-�˶��ظ�TD").getInfo());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static HttpSendResult parseResult(String info){
		//�Ƿ�ɹ�
		String[] isok=info.split("\n");
		if(isok.length>1){
			return new HttpSendResult(true, "���ŷ��ͳɹ���") ;
		}else{
			isok=info.split(",");
			String key=isok[1].trim();//��ȡ�ڶ�����ʾ
			String tips=null;
			switch (Integer.parseInt(key)) {
			case 101:
				tips = "�޴��û�";
				break;
			case 102:
				tips = "�������";
				break;
			case 104:
				tips = "ϵͳ��æ";
				break;
			case 105:
				tips = "�������ݰ������д�";
				break;
			case 106:
				tips = "��Ϣ���ȹ��������ܴ���536";
				break;
			case 107:
				tips = "�ֻ��������";
				break;
			case 108:
				tips = "�ֻ������������";
				break;
			case 109:
				tips = "���Ŷ��������";
				break;
			case 110:
				tips = "���ڷ���ʱ����";
				break;
			case 111:
				tips = "�������˻����·��Ͷ������";
				break;
			case 112:
				tips = "�޴˲�Ʒ���û�û�ж����ò�Ʒ";
				break;
			case 113:
				tips = "extno��ʽ�������ֻ��߳��Ȳ��ԣ�";
				break;
			case 115:
				tips = "�Զ���˲���";
				break;
			case 116:
				tips = "ǩ�����Ϸ���δ��ǩ��";
				break;
			case 117:
				tips = "IP��ַ��֤��";
				break;
			case 118:
				tips = "�û�û����Ӧ�ķ���Ȩ��";
				break;
			case 119:
				tips = "�û��ѹ���";
				break;
			default:
				tips = "";
				break;
			}
			return new HttpSendResult(false, "�����˻���"+tips+"("+key+")") ;
		}
	}
	/**
	 * @param url Ӧ�õ�ַ��������http://ip:port/msg/
	 * @param account �˺�
	 * @param pswd ����
	 * @param mobile �ֻ����룬�������ʹ��","�ָ�
	 * @param msg ��������
	 * @param needstatus �Ƿ���Ҫ״̬���棬��Ҫtrue������Ҫfalse
	 * @return ����ֵ����μ�HTTPЭ���ĵ�
	 * @throws Exception
	 */
	private static String send(String url, String account, String pswd, String mobile, String msg,
			boolean needstatus, String product, String extno) throws Exception {
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod();
		try {
			URI base = new URI(url, false);
			method.setURI(new URI(base, "HttpBatchSendSM", false));
			method.setQueryString(new NameValuePair[] { 
					new NameValuePair("account", account),
					new NameValuePair("pswd", pswd), 
					new NameValuePair("mobile", mobile),
					new NameValuePair("needstatus", String.valueOf(needstatus)), 
					new NameValuePair("msg", msg),
					new NameValuePair("product", product), 
					new NameValuePair("extno", extno), 
				});
			int result = client.executeMethod(method);
			if (result == HttpStatus.SC_OK) {
				InputStream in = method.getResponseBodyAsStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = in.read(buffer)) != -1) {
					baos.write(buffer, 0, len);
				}
				return URLDecoder.decode(baos.toString(), "UTF-8");
			} else {
				throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
			}
		} finally {
			method.releaseConnection();
		}

	}

	/**
	 * 
	 * @param url Ӧ�õ�ַ��������http://ip:port/msg/
	 * @param account �˺�
	 * @param pswd ����
	 * @param mobile �ֻ����룬�������ʹ��","�ָ�
	 * @param msg ��������
	 * @param needstatus �Ƿ���Ҫ״̬���棬��Ҫtrue������Ҫfalse
	 * @return ����ֵ����μ�HTTPЭ���ĵ�
	 * @throws Exception
	 */
	protected static String batchSend(String url, String account, String pswd, String mobile, String msg,
			boolean needstatus, String product, String extno) throws Exception {
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod();
		try {
			URI base = new URI(url, false);
			method.setURI(new URI(base, "HttpBatchSendSM", false));
			method.setQueryString(new NameValuePair[] { 
					new NameValuePair("account", account),
					new NameValuePair("pswd", pswd), 
					new NameValuePair("mobile", mobile),
					new NameValuePair("needstatus", String.valueOf(needstatus)), 
					new NameValuePair("msg", msg),
					new NameValuePair("product", product),
					new NameValuePair("extno", extno), 
				});
			int result = client.executeMethod(method);
			if (result == HttpStatus.SC_OK) {
				InputStream in = method.getResponseBodyAsStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = in.read(buffer)) != -1) {
					baos.write(buffer, 0, len);
				}
				return URLDecoder.decode(baos.toString(), "UTF-8");
			} else {
				throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
			}
		} finally {
			method.releaseConnection();
		}

	}
}
