/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* TESTS.java class
*
* @name    : TESTS.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Dec 7, 2020
****************************************************************************/

package VariousTests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import HttpServers.HTTPServer;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.get;

class REST_Test implements AutoCloseable  {
	/** Server host: **/
	private static final String host = "0.0.0.0"; 
	/** Server port: **/
	private static final int port = 52525; 
	
	/** Test server dummy: **/
	private HTTPServer server = null;
	
	public REST_Test() throws IOException {
		System.out.println("***** STARTING SERVER on " + host + ":" + port + " ****");
		server = new HTTPServer(host, port);
		server.addHandler("gray", "<html><body bgcolor = \"gray\"><h1>Hello</h1></body></html>");
		server.addHandler("red", "<html><body bgcolor = \"red\"><h1>Hello</h1></body></html>");
		server.start();
	}
	
	private String serviceAddress() {
		return new StringBuilder("http://")
				.append(host).append(":").append(port).toString();
	}
	
	public void AfterTests() {
		System.out.println("***** STOPPING SERVER ****");
		server.stop();
	}
	
	@Override
	public void close() throws Exception {
		AfterTests();
	}
	
	//-----------------------------------------------------------------------//
	
	public void SimpleTest() throws IOException {
        given().when().get(this.serviceAddress() + "/red")
					  .then()
					  .assertThat()
        			  .log().ifValidationFails()
					  .statusCode(200);
        System.out.println("Done. OK!");
	}
	
	public void GetResponse() {
		Response response = given().when().get(this.serviceAddress() + "/red").then().extract().response();
		System.out.println(response.getStatusLine());
	}
	
	public void GetResponse_RequestSpecification() 
	{
		RequestSpecification request = RestAssured.given();
		request.baseUri(this.serviceAddress());
		request.basePath("/red");
 
		Response response = request.get();
		System.out.println(response.asString());
	}
	
	public void GetResponse_RequestSpecification_404() 
	{
		RequestSpecification request = RestAssured.given();
		request.baseUri(this.serviceAddress());
		request.basePath("/22222");
		
 
		Response response = request.get();
		System.out.println(response.asString());
		
		response.then().assertThat().log().ifValidationFails().statusCode(222);
	}
}

public class TESTS {
	public static void main(String[] args) throws IOException 
	{
		try (REST_Test tests = new REST_Test()) 
		{
			// tests.SimpleTest();
			 tests.GetResponse();
			
			// tests.GetResponse_RequestSpecification();
			// tests.GetResponse_RequestSpecification_404();
		}
		catch (final Exception exc) {
			// System.err.println(exc);
		}
		catch (final AssertionError exc) {
			System.err.println(exc);
		}
		finally {
			// 
		}
	}
}
