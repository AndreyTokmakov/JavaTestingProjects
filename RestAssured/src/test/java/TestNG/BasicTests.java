/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* BasicTests.java class
*
* @name    : BasicTests.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Dec 7, 2020
****************************************************************************/

package TestNG;

import static io.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import HttpServers.HTTPServer;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class BasicTests {
	/** Server host: **/
	private static final String host = "0.0.0.0"; 
	
	/** Server port: **/
	private static final int port = 52525; 
	
	/** Test server dummy: **/
	private HTTPServer server = null;
	
	private String serviceAddress() {
		return new StringBuilder("http://")
				.append(host).append(":").append(port).toString();
	}
	
	public String getJsonFromFile(String filePath) {
		try {
			FileReader reader = new FileReader(filePath);
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(reader);
			return jsonObject.toJSONString();
		}
		catch (IOException | ParseException exc) {
			return "";
		}
	}
	
	@BeforeClass
	public void setUp() throws IOException {
		System.out.println("***** STARTING SERVER on " + host + ":" + port + " ****");
		server = new HTTPServer(host, port);
		server.addHandler("gray", "<html><body bgcolor = \"gray\"><h1>Hello</h1></body></html>");
		server.addHandler("red", "<html><body bgcolor = \"red\"><h1>Hello</h1></body></html>");
		
		String albums = getJsonFromFile("src/test/resources/albums.json");
		server.addHandler("albums", albums);
		
		server.start();
		
		// RestAssured.baseURI = BasicTests.host;
        // RestAssured.port = BasicTests.port;
	}

	@AfterClass
	public void tearDown() {
		System.out.println("***** STOPPING SERVER ****");
		server.stop();
	}

	@Test (enabled = false)
	public void SimpleTest() throws IOException {
        given().when().get(this.serviceAddress() + "/red")
					  .then()
					  .assertThat()
        			  .log().ifValidationFails()
					  .statusCode(200);
        System.out.println("Done. OK!");
	}
	
	@Test //(enabled = false)
	public void GetResponse_JsonPath() throws IOException {
		Response response = given().when().get(this.serviceAddress() + "/albums").then().extract().response();
		

		String albums = response.jsonPath().getString("albums");
		System.out.println(albums);
	}
}
