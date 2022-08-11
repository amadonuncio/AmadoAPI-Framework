package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class ReadOneProduct {
//	TEST CASE:
//	given: all input details(baseURI,Headers,Payload/Body,Authorization,QueryParameters)
//	when:  submit api requests(Http method,Endpoint/Resource)
//	then:  validate response(status code, Headers, responseTime, Payload/Body)

//	AUTOMATING:
//	02. ReadOneProduct
//	HTTP Method: GET
//	EndpointURL: https://techfios.com/api-prod/api/product/read_one.php
//	Authorization: Basic Auth/ Bearer Token
//	Header: "Content-Type" : "application/json"
//	QueryParam: "id" : "value"
//	Status Code: 200

	@Test
	public void readOneProduct() {
		
		Response response = 

//		given:
		given()
//			.log().all() //If I want 
			.baseUri("https://techfios.com/api-prod/api/product")
			.header("Content-Type", "application/json")
			.header("Authorization", "Bearer Authorization")
			.queryParam("id", "4837")
			.relaxedHTTPSValidation().
			
//		when:
		when()
			.get("/read_one.php").
			
//		then:
		then()
			.extract().response();
		
//		Status Code
		int actualStatusCode = response.getStatusCode();
		Assert.assertEquals(actualStatusCode, 200);  //Validation
		
//		Header
		String actualHeader = response.getHeader("Content-Type");
		Assert.assertEquals(actualHeader, "application/json"); //Validation
	
//		Response Body
		String actualResponseBody = response.getBody().asString();
		System.out.println("Actual Response Body:" + actualResponseBody);
		
		JsonPath jp = new JsonPath(actualResponseBody);
		
//		Validations 
		String productID = jp.get("id");
		System.out.println("Product ID:" + productID);
		Assert.assertEquals(productID, "4837");
		
		String productName = jp.get("name");
		System.out.println("Product Name:" + productName);
		Assert.assertEquals(productName, "Video Game 3 By Amado Nuncio");
		
		String productDesc = jp.get("description");
		System.out.println("Product Description:" + productDesc);
		Assert.assertEquals(productDesc, "The best video game for amazing programmers.");
		
		String productPrice = jp.get("price");
		System.out.println("Product Price:" + productPrice);
		Assert.assertEquals(productPrice, "70");		
	}
	
}
