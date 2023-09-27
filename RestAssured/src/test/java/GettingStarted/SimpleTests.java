/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* Test1.java class
*
* @name    : Test1.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Dec 7, 2020
****************************************************************************/

package GettingStarted;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.get;

public class SimpleTests {

	public static void checkEmployeeStatus() {
        given().when().get("http://dummy.restapiexample.com/api/v1/employees")
        			  .then()
        			  .assertThat()
        			  .statusCode(200);
    }
	
	public static void checkEmployeeStatus_LogIfFailed() {
        given().when().get("http://dummy.restapiexample.com/api/v1/employees")
        			  .then()
        			  .assertThat()
        			  .log().ifValidationFails()
        			  .statusCode(202); // HTTP OK will be returned actually
    }
	
	public static void checkEmployeeStatus_PrintBody() {
        given().when().get("http://dummy.restapiexample.com/api/v1/employees")
        			  .then()
        			  .assertThat()
        			  .statusCode(200).log().body();
    }

	public static void checkInvalidUrlReturns404() {
		given().when().get("http://dummy.restapiexample.com/api/v1/employees-invalid")
	        		  .then()
	        		  .assertThat()
	        		  .statusCode(404);
	}
	
	public static void getResponseBody_Params(){
		given().queryParam("CUSTOMER_ID","68195")
		       .queryParam("PASSWORD","1234!")
		       .queryParam("Account_No","1")
		       .when().get("http://demo.guru99.com/V4/sinkministatement.php").then().log()
		       .body();
	}
	
	public static void main(String[] args) {
		// checkEmployeeStatus();
		
		// checkEmployeeStatus_LogIfFailed();
		
		// checkEmployeeStatus_PrintBody();
		
		// checkInvalidUrlReturns404();
		
		getResponseBody_Params();
	}
}
