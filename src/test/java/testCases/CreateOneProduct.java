package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
//import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CreateOneProduct {
//	TEST CASE:
//	given: all input details(baseURI,Authorization,Headers,Payload/Body,QueryParameters)
//	when:  submit api requests(Http method,Endpoint/Resource)
//	then:  validate response(status code, Headers, responseTime, Payload/Body)/extract response 

//	AUTOMATING:
//	03. CreateOneProduct
//	HTTP Method: POST
//	EndpointUrl: https://techfios.com/api-prod/api/product  /create.php
//	Authorization: Basic Auth/ Bearer Token
//	Header: "Content-Type" : "application/json; charset=UTF-8"
//	Status Code: 201
//	Payload/Body: 
//	{
//	    "name" : "Video Game 3 By Amado Nuncio",
//	    "price" : "70",
//	    "description" : "The best video game for amazing programmers.",
//	    "category_id" : "2",
//		"category_name": "Electronics"
//	}
	
	HashMap<String, String> createPayload; // Saving time by not converting it to a JSON Path and having to do other things. Converting the CreateProductPayload.json to map
	String firstProductID;
	
	
	public Map<String, String> createPayLoadMap(){    //It's in its own method because it needs to be accessed by other methods for comparisons
		
		createPayload = new HashMap<String, String>();
		
		createPayload.put("name", "Video Game 3 By Amado Nuncio");
		createPayload.put("price", "70");
		createPayload.put("description", "The best video game for amazing programmers.");
		createPayload.put("category_id", "2");
		createPayload.put("category_name", "Electronics");
		
		return createPayload;
	}
	
	@Test(priority=1) 
	public void createOneProduct() {
		
		Response response =

//		given:
		given()
			.baseUri("https://techfios.com/api-prod/api/product")
			.header("Content-Type", "application/json; charset=UTF-8")
			.header("Authorization", "Bearer Authorization")
			.body(createPayLoadMap()) //One way to do the body 
//			.body(new File ("src\\main\\resources\\data\\CreateProductPayload.json")) //Another way to do the body
			.relaxedHTTPSValidation().

//		when:
		when()
			.post("/create.php").

//		then:
		then()
			.extract()
			.response();

//		Status Code
		int actualStatusCode = response.getStatusCode();
		Assert.assertEquals(actualStatusCode, 201);

//		Header
		String actualHeader = response.getHeader("Content-Type");
		Assert.assertEquals(actualHeader, "application/json; charset=UTF-8");

//		Response Body
		String actualResponseBody = response.getBody().asString();
		System.out.println("Actual Response Body:" + actualResponseBody);

		JsonPath jp = new JsonPath(actualResponseBody);

		String productMessage = jp.get("message");
		System.out.println("Product Message:" + productMessage);
		Assert.assertEquals(productMessage, "Product was created.");


	}

	@Test(priority=2) 
	public void getFirstProductID() {
	
	Response response = 

//	given:
	given()
//		.log().all() //If I want to use it
		.baseUri("https://techfios.com/api-prod/api/product")
		.header("Content-Type", "application/json; charset=UTF-8")
		.auth().preemptive().basic("demo@techfios.com", "abc123") //Basic Auth (if I do this one)
		.relaxedHTTPSValidation().
		
//	when:
	when()
		.get("/read.php").
		
//	then:
	then()
		.extract().response();
	
//	Status Code
	int actualStatusCode = response.getStatusCode();
	Assert.assertEquals(actualStatusCode, 200);
	
//	Response Body
	String actualResponseBody = response.getBody().asString();
	System.out.println("Actual Response Body:" + actualResponseBody);
	
//	Getting The First Product ID By Using Response Body
	JsonPath jp = new JsonPath(actualResponseBody);
	firstProductID = jp.get("records[0].id");
	System.out.println("First Product ID:" + firstProductID);
	
	}

	@Test(priority=3)
	public void validateProductDetails() {
		
	Response response = 
	
	//given:
	given()
//		.log().all() //If I want to use it
		.baseUri("https://techfios.com/api-prod/api/product")
		.header("Content-Type", "application/json; charset=UTF-8")
		.queryParam("id", firstProductID)
		.relaxedHTTPSValidation().
		
	//when:
	when()
		.get("/read_one.php").
		
	//then:
	then()
		.extract().response();
	
//	Status Code
	int actualStatusCode = response.getStatusCode();
	Assert.assertEquals(actualStatusCode, 200); //Validation
	
//	Response Body (All The Created Product Details Being Validated)
	String actualResponseBody = response.getBody().asString();
	System.out.println("Actual Response Body:" + actualResponseBody); //Validation All The Way Down
	
	JsonPath jp = new JsonPath(actualResponseBody);

	String expectedProductName = createPayLoadMap().get("name");
	String expectedProductPrice = createPayLoadMap().get("price");
	String expectedProductDesc = createPayLoadMap().get("description");
	
	String actualProductName = jp.get("name");
	System.out.println("Actual Product Name:" + actualProductName);
	Assert.assertEquals(actualProductName, expectedProductName);
	
	String actualProductPrice = jp.get("price");
	System.out.println("Actual Product Price:" + actualProductPrice);
	Assert.assertEquals(actualProductPrice, expectedProductPrice);
	
	String actualProductDesc = jp.get("description");
	System.out.println("Actual Product Description:" + actualProductDesc);
	Assert.assertEquals(actualProductDesc, expectedProductDesc);
	
	}
	
}
