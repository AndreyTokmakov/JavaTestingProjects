import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONObject;

// import org.json.simple.JSONArray;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

class WebService_Test {
	final static String local_web_servive_url = "http://127.0.0.1:2020/wss/time?wsdl&getAvailableTimeZones";
	final static String local_service = "http://127.0.0.1:8080/payment";	

	void GetResponse_Simple() {
        Response response = get(WebService_Test.local_service);
        System.out.println(response.asString());
        
        
        JSONObject jsonResponse = new JSONObject(response.asString());

        // JSONArray jsonResponse = new JSONArray(response.asString());  
        String status = jsonResponse.getString("status");
        
        System.out.println(status);
	}
	
	void Test1() {
		ExtractableResponse<Response> response = when().get(local_service).then().extract();
		System.out.println(response);
	}
	
	void POST_Request() {
		JSONObject requestBody = new JSONObject();
        requestBody.put("key", "123");
        //requestBody.put("userId", "11233");
        
        System.out.println(requestBody.toString());
        
        RequestSpecification request = RestAssured.given();
        //request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        
        Response response = request.post(local_service + "/pay");
        System.out.println(response.asString());
	}
	
	void POST_2() {
		 Response response  = given()
				.contentType(ContentType.JSON).accept(ContentType.JSON)
				.body("{\"key\": \"key\"}")
				.when()
				.post(local_service + "/pay");
		 System.out.println(response.asString());
	}
	
	void Test_GIVEN() {
		String response = given().queryParam("key","key").post(local_service + "/pay").then().extract().body().asString();
		System.out.println(response);
	}
};

public class TestLocal_WebService {
	private static final WebService_Test tests = new WebService_Test();
	
	public static void main(String[] args) {
		// tests.GetResponse_Simple();
		// tests.Test1();
		// tests.Test_GIVEN();
		
		// tests.POST_Request();
		tests.POST_2();
	}
}
