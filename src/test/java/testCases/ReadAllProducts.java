package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import java.util.concurrent.TimeUnit;

public class ReadAllProducts {
//	TEST CASE:
//	given: all input details(baseURI,Headers,Payload/Body,QueryParameters,Authorization)
//	when:  submit api requests(Http method,Endpoint/Resource)
//	then:  validate response(status code, Headers, responseTime, Payload/Body)

//	AUTOMATING:
//	01.	ReadAllProducts
//	HTTP Method: GET
//	EndpointUrl: https://techfios.com/api-prod/api/product /read.php
//	Authorization: Basic Auth/ Bearer Token
//	Header: "Content-Type" : "application/json; charset=UTF-8"
//	Status Code: 200

	@Test
	public void readAllProducts() {
		
		Response response = 

//		given:
		given()
//			.log().all() //(If I want to use it)
			.baseUri("https://techfios.com/api-prod/api/product")
			.header("Content-Type", "application/json; charset=UTF-8")
			.auth().preemptive().basic("demo@techfios.com", "abc123") //Basic Auth (if I do this one)
			.relaxedHTTPSValidation().
			
//		when:
		when()
			.get("/read.php").
			
//		then:
		then()
			.extract().response();
		
//		Status Code
		int actualStatusCode = response.getStatusCode();
		Assert.assertEquals(actualStatusCode, 200); //Validating
		
//		Header
		String actualHeader = response.getHeader("Content-Type");
		Assert.assertEquals(actualHeader, "application/json; charset=UTF-8"); //Validating
		
//		Response Time
		long actualResponseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
		System.out.println("Actual Response Time: " + actualResponseTime); 
		if (actualResponseTime <= 2000) {						// Validating
			System.out.println("Response time is within rage.");
		}else {
			System.out.println("Response time is out of range!!!");
		}
		
//		Payload/Body
		
//		This may be a bug in Eclipse but it doesn't matter 
		String actualResponseBody = response.getBody().asString();
//		response.prettyPrint(); //To just see it printed
		System.out.println("Actual Response Body:" + actualResponseBody); //Validation
		JsonPath jp = new JsonPath(actualResponseBody); //Validation 2 (Converting the string request to JSON path, it is in the dependency)
		String firstProductID = jp.get("records[0].id"); //Since the string request is JSON now, I can use the JSON XPATH
		System.out.println("First Product ID:" + firstProductID);
		if (firstProductID != null) {
			System.out.println("First Product ID is not null.");
		}else {
			System.out.println("First Product ID is null!!!");
		}
		
		
	}
	
}
