package com.qa.test;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

public class GetAPITest extends TestBase {
	TestBase testBase;
	String serviceurl;
	String apiurl;
	String url;
	RestClient restclient;
	CloseableHttpResponse closablehttpResponse;
	
	@BeforeMethod
	public void setup() throws ClientProtocolException, IOException {
		testBase=new TestBase();
		serviceurl=prop.getProperty("URL");
		apiurl=prop.getProperty("serviceURL");
		
		//url
		url=serviceurl+apiurl;
		
	}
	
	@Test(priority=1)
	public void getAPITestwithoutHeaders() throws ClientProtocolException, IOException {
		restclient=new RestClient();
		closablehttpResponse=restclient.get(url);
		
		 //a.status code 
		  int statuscode=closablehttpResponse.getStatusLine().getStatusCode();
		  System.out.println("status code::::=>" + statuscode);
		  
		  Assert.assertEquals(statuscode,RESPONSE_STATUS_CODE_200,"status code is not 200");
		  
		  //b.json string 
		  String responsestring=EntityUtils.toString(closablehttpResponse.getEntity(), "UTF-8");
		  
		  JSONObject responseJSON = new JSONObject(responsestring);
		  System.out.println("Response JSON from API:::=>" + responseJSON);
		  
		  
		  //single-value assertion
		  //total value
		  String totalvalue=TestUtil.getValueByJPath(responseJSON,"/total");
		  System.out.println("value of total::=>"+ totalvalue.trim()); 
		  Assert.assertEquals(Integer.parseInt(totalvalue),12);
		  
		  //per page 
		  String perpagevalue=TestUtil.getValueByJPath(responseJSON,"/per_page");
		  System.out.println("value of per page::=>"+ perpagevalue.trim());
		  Assert.assertEquals(Integer.parseInt(perpagevalue),3);
		  
		  //get the value from json array
		  String lastName=TestUtil.getValueByJPath(responseJSON,"/data[0]/last_name");
		  String id=TestUtil.getValueByJPath(responseJSON,"/data[0]/id");
		  String avatar=TestUtil.getValueByJPath(responseJSON,"/data[0]/avatar");
		  String firstName=TestUtil.getValueByJPath(responseJSON,"/data[0]/first_name");
		  
		  System.out.println(lastName);
		  System.out.println(id);
		  System.out.println(avatar);
		  System.out.println(firstName);
		  
		  //c.all Headers 
		  Header[] headerarray = closablehttpResponse.getAllHeaders();
		  HashMap<String, String> allHeader = new HashMap<String, String>();
		  
		  //putting into hashmap for key and value 
		  for (Header header : headerarray) {
		  allHeader.put(header.getName(), header.getValue()); 
		  }
		  
		  System.out.println("Headers Array:::=>" + allHeader);
	}
	
	@Test(priority=2)
	public void getAPITestwithHeaders() throws ClientProtocolException, IOException {
		restclient=new RestClient();
		
		HashMap<String,String> headermap=new HashMap<String,String>();
		headermap.put("content-type", "application/json");
		
		closablehttpResponse=restclient.get(url,headermap);
		
		 //a.status code 
		  int statuscode=closablehttpResponse.getStatusLine().getStatusCode();
		  System.out.println("status code::::=>" + statuscode);
		  
		  Assert.assertEquals(statuscode,RESPONSE_STATUS_CODE_200,"status code is not 200");
		  
		  //b.json string 
		  String responsestring=EntityUtils.toString(closablehttpResponse.getEntity(), "UTF-8");
		  
		  JSONObject responseJSON = new JSONObject(responsestring);
		  System.out.println("Response JSON from API:::=>" + responseJSON);
		  
		  
		  //single-value assertion
		  //total value
		  String totalvalue=TestUtil.getValueByJPath(responseJSON,"/total");
		  System.out.println("value of total::=>"+ totalvalue.trim()); 
		  Assert.assertEquals(Integer.parseInt(totalvalue),12);
		  
		  //per page 
		  String perpagevalue=TestUtil.getValueByJPath(responseJSON,"/per_page");
		  System.out.println("value of per page::=>"+ perpagevalue.trim());
		  Assert.assertEquals(Integer.parseInt(perpagevalue),3);
		  
		  //get the value from json array
		  String lastName=TestUtil.getValueByJPath(responseJSON,"/data[0]/last_name");
		  String id=TestUtil.getValueByJPath(responseJSON,"/data[0]/id");
		  String avatar=TestUtil.getValueByJPath(responseJSON,"/data[0]/avatar");
		  String firstName=TestUtil.getValueByJPath(responseJSON,"/data[0]/first_name");
		  
		  System.out.println(lastName);
		  System.out.println(id);
		  System.out.println(avatar);
		  System.out.println(firstName);
		  
		  //c.all Headers 
		  Header[] headerarray = closablehttpResponse.getAllHeaders();
		  HashMap<String, String> allHeader = new HashMap<String, String>();
		  
		  //putting into hashmap for key and value 
		  for (Header header : headerarray) {
		  allHeader.put(header.getName(), header.getValue()); 
		  }
		  
		  System.out.println("Headers Array:::=>" + allHeader);
	}
	
	
	
	
	
	

}
