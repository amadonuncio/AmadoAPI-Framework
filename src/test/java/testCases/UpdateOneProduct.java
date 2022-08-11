package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import java.util.HashMap;
import java.util.Map;

public class UpdateOneProduct {
//	TEST CASE:
//	given: all input details(baseURI,Authorization,Headers,Payload/Body,QueryParameters)
//	when:  submit api requests(Http method,Endpoint/Resource)
//	then:  validate response(status code, Headers, responseTime, Payload/Body)

//	AUTOMATING:
//	04. UpdateOneProduct
//	HTTP Method: PUT
//	EndpointUrl: https://techfios.com/api-prod/api/product /update.php
//	Authorization: Basic Auth/ Bearer Token
//	Header: "Content-Type" : "application/json; charset=UTF-8"
//	Status Code: 200
//	Payload/Body: 
//	{
//		"id": "",
//	    "name" : "Video Game 4 By Amado Nuncio",
//	    "price" : "89",
//	    "description" : "The best video game for amazing programmers.",
//	    "category_id" : "2",
//		"category_name" : "Electronics"
//	} 
	
	HashMap<String, String> updatePayload;
	String firstProductID;
	
	
	public Map<String, String> updatePayLoadMap(){
		
		updatePayload = new HashMap<String, String>();
		
//		I Don't Need To Put All These Key/Values For The Product, Only The Key/Values I Want To Update
		updatePayload.put("id", "4849");
		updatePayload.put("name", "Video Game 4 By Amado Nuncio");
		updatePayload.put("price", "99");
		updatePayload.put("description", "The best video game for amazing programmers.");
		updatePayload.put("category_id", "2");
		updatePayload.put("category_name", "Electronics");
		
		return updatePayload;
	}
	
	@Test(priority=1) 
	public void updateOneProduct() {
		
		Response response =

//		given:
		given()
			.baseUri("https://techfios.com/api-prod/api/product")
			.header("Content-Type", "application/json; charset=UTF-8")
			.header("Authorization", "Bearer Authorization")
			.body(updatePayLoadMap())
			.relaxedHTTPSValidation().

//		when:
		when()
			.put("/update.php").

//		then:
		then()
			.extract()
			.response();

//		Ways To Assert 
		int actualStatusCode = response.getStatusCode();
		Assert.assertEquals(actualStatusCode, 200);

		String actualHeader = response.getHeader("Content-Type");
		Assert.assertEquals(actualHeader, "application/json; charset=UTF-8");

		String actualResponseBody = response.getBody().asString();
		System.out.println("Actual Response Body:" + actualResponseBody);

		JsonPath jp = new JsonPath(actualResponseBody);

		String productMessage = jp.get("message");
		System.out.println("Product Message:" + productMessage);
		Assert.assertEquals(productMessage, "Product was updated.");


	}


	@Test(priority=2)
	public void validateProductUpdateDetails() {
		
	Response response = 
	
	//given:
	given()
//		.log().all() //I can do this if I like
		.baseUri("https://techfios.com/api-prod/api/product")
		.header("Content-Type", "application/json; charset=UTF-8")
		.queryParam("id", updatePayLoadMap().get("id"))
		.relaxedHTTPSValidation().
		
	//when:
	when()
		.get("/read_one.php").
		
	//then:
	then()
		.extract().response();
	
	//Ways To Assert 
	int actualStatusCode = response.getStatusCode();
	Assert.assertEquals(actualStatusCode, 200);
	
	String actualResponseBody = response.getBody().asString();
	System.out.println("Actual Response Body:" + actualResponseBody);
	
	JsonPath jp = new JsonPath(actualResponseBody);

	String expectedProductName = updatePayLoadMap().get("name");
	String expectedProductPrice = updatePayLoadMap().get("price");
	
	String actualProductName = jp.get("name");
	System.out.println("Actual Product Name:" + actualProductName);
	Assert.assertEquals(actualProductName, expectedProductName);
	
	String actualProductPrice = jp.get("price");
	System.out.println("Actual Product Price:" + actualProductPrice);
	Assert.assertEquals(actualProductPrice, expectedProductPrice);		
	}
	
	
}
