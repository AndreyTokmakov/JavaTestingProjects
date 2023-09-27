/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* REST Assured Java library testing project
*
* @name    : Rest_Testing.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : November 06, 2020
****************************************************************************/ 

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.get;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONObject;

class REST_Tester {
	
	final static String guru_url = "http://demo.guru99.com/V4/sinkministatement.php?CUSTOMER_ID=68195&PASSWORD=1234!&Account_No=1";

	void GetResponse_Simple() {
        Response response = get("http://restcountries.eu/rest/v1/name/russia");
        System.out.println(response.asString());
        
        JSONArray jsonResponse = new JSONArray(response.asString());
        
        String capital = jsonResponse.getJSONObject(0).getString("capital");
        
        System.out.println(capital);
        // Assert.assertEquals(capital, "Moscow");
	}
	
	void WebService_Test_Get() {
        Response response = get("http://restcountries.eu/rest/v1/name/russia");
        System.out.println(response.asString());
        
        JSONArray jsonResponse = new JSONArray(response.asString());
        
        String capital = jsonResponse.getJSONObject(0).getString("capital");
        
        System.out.println(capital);
        // Assert.assertEquals(capital, "Moscow");
	}
	
	public void Base_Get_Request(){
		given().when().get(guru_url).then().log().all();
	}
	
	public void Base_Get_Request_Query() {
		given().queryParam("CUSTOMER_ID","68195")
		       .queryParam("PASSWORD","1234!")
		       .queryParam("Account_No","1")
		       .when().get("http://demo.guru99.com/V4/sinkministatement.php")
		       .then().log().body();
	}
	
	public void request_Post() {
		String someRandomString = String.format("%1$TH%1$TM%1$TS", new Date());
		
		JSONObject requestBody = new JSONObject();
		requestBody.put("FirstName", someRandomString);
		requestBody.put("LastName",  someRandomString);
		requestBody.put("UserName",  someRandomString);
		requestBody.put("Password",  someRandomString);
		requestBody.put("Email",     someRandomString + "@gmail.com");

		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(requestBody.toString());
		Response response = request.post("http://restapi.demoqa.com/customer/register");

		int statusCode = response.getStatusCode();
		// Assert.assertEquals(statusCode, 201);
		System.out.println(statusCode);
		
		String successCode = response.jsonPath().get("SuccessCode");
		// Assert.assertEquals(successCode, "OPERATION_SUCCESS");
		System.out.println(successCode);
		
		System.out.println(response.getBody().asString());
	}
};

public class Tests {
	private final static REST_Tester restTester = new REST_Tester();
	
	public static void main(String[] args) {
		// restTester.GetResponse_Simple();
		// restTester.request_Post();
		
		// restTester.Base_Get_Request();
		// restTester.Base_Get_Request_Query();
		
		
		
	}
}
