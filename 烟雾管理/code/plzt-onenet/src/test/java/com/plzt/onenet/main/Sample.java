package com.plzt.onenet.main;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Sample {

	public static String sendPost(String uri, String charset) {
		String result = null;
		InputStream in = null;
		try {
			URL url = new URL(uri);
			HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
			urlcon.setRequestProperty("api-key", "RSV5f2rhLBFHD9BF5RVFDxIvsxHhBD/57FJ9PYlQ8ec=");
			urlcon.setRequestMethod("POST");
			urlcon.connect();// 获取连接
			in = urlcon.getInputStream();
			BufferedReader buffer = new BufferedReader(new InputStreamReader(in, charset));
			StringBuffer bs = new StringBuffer();
			String line = null;
			while ((line = buffer.readLine()) != null) {
				bs.append(line);
			}
			result = bs.toString();
		} catch (Exception e) {
			System.out.println("[请求异常][地址：" + uri + "][错误信息：" + e.getMessage() + "]");
		} finally {
			try {
				if (null != in)
					in.close();
			} catch (Exception e2) {
				System.out.println("[关闭流异常][错误信息：" + e2.getMessage() + "]");
			}
		}
		return result;
	}
	
	public static void bind() {
		String url = "http://localhost:8080/api/device/bind?objid=111&devid=33362340";
		System.out.println(sendPost(url, "utf-8"));
	}
	
	public static void smoke() {
		String url = "http://localhost:8080/api/device/smoke?objid=111&devid=33362340";
		System.out.println(sendPost(url, "utf-8"));
	}
	
	public static void removeBind() {
		String url = "http://localhost:8080/api/device/bind/remove?objid=111&devid=33362340";
		System.out.println(sendPost(url, "utf-8"));
	}
	
	public static void main(String[] args) throws Exception {
		bind(); //绑定
		removeBind(); //解绑
		smoke(); //点烟
	}
	
}
