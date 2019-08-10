package com.qa.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class RestClient {

	//1.GET METHOD without Headers
	public CloseableHttpResponse get(String URL) throws ClientProtocolException, IOException {
		//to esablish connect
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(URL); // http get request
		CloseableHttpResponse closablehttpResponse = httpclient.execute(httpget); // hit the get URL
		return closablehttpResponse;
	}
	
	//2.GET METHOD with Headers
		public CloseableHttpResponse get(String URL,HashMap<String,String> headermap) throws ClientProtocolException, IOException {
			//to esablish connect
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpGet httpget = new HttpGet(URL); // http get request
			
			for(Map.Entry<String,String> entry:headermap.entrySet()) {
				httpget.addHeader(entry.getKey(),entry.getValue());
			}
			CloseableHttpResponse closablehttpResponse = httpclient.execute(httpget); // hit the get URL
			return closablehttpResponse;
		}
	
		
	//3.POST METHOD
		public CloseableHttpResponse post(String URL,String entitystring,HashMap<String,String> headermap) throws ClientProtocolException, IOException {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httppost=new HttpPost(URL);//http post request
			httppost.setEntity(new StringEntity(entitystring));//for payload
			
			//for headers
			for(Map.Entry<String,String> entry:headermap.entrySet()) {
				httppost.addHeader(entry.getKey(),entry.getValue());
			}
			
			CloseableHttpResponse closablehttpResponse = httpclient.execute(httppost); // hit the get URL
			return closablehttpResponse;
		}
}
