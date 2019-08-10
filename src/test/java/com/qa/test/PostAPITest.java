package com.qa.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;

import junit.framework.Assert;

public class PostAPITest extends TestBase {
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
	
	@Test
	public void postApiTest() throws JsonGenerationException, JsonMappingException, IOException {
		restclient=new RestClient();
		HashMap<String,String> headermap=new HashMap<String,String>();
		headermap.put("content-type", "application/json");
		
		//jackson API
		ObjectMapper mapper=new ObjectMapper();
		Users users=new Users("morpheous","leader");//expected users object
		
		//object to json file
		mapper.writeValue(new File("I:\\Eclipse_Vijay\\RestAssuredAPI\\src\\main\\java\\com\\qa\\data\\users.json"), users);
		
		//object to json in string
		String usersJsonString=mapper.writeValueAsString(users);
		System.out.println(usersJsonString);
		
		closablehttpResponse=restclient.post(url, usersJsonString, headermap);
		
		//validate response from API
		//1.status code:
		int statuscode=closablehttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statuscode, testBase.RESPONSE_STATUS_CODE_201);
		
		//2.JsonString
		String responseString=EntityUtils.toString(closablehttpResponse.getEntity(),"UTF-8");
		
		JSONObject responseJson=new JSONObject(responseString);
		System.out.println("The response from API is:"+responseJson);
		
		//json to java object
		Users userResObj=mapper.readValue(responseString, Users.class);//actual users object
		System.out.println(userResObj);
		
		Assert.assertTrue(users.getName().equals(userResObj.getName()));
		Assert.assertTrue(users.getJob().equals(userResObj.getJob()));
		
		System.out.println(userResObj.getId());
		System.out.println(userResObj.getCreatedAt());
	}
}