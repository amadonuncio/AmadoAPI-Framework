package testCases;

//import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import java.util.HashMap;
import java.util.Map;


public class DeleteOneProduct {
//	TEST CASE:
//	given: all input details(baseURI,Authorization,Headers,Payload/Body,QueryParameters)
//	when:  submit api requests(Http method,Endpoint/Resource)
//	then:  validate response(status code, Headers, responseTime, Payload/Body)

//	AUTOMATING:
//	05. DeleteOneProduct
//	HTTP Method: DELETE
//	EndpointUrl: https://techfios.com/api-prod/api/product/delete.php
//	Authorization: Basic Auth/ Bearer Token
//	Header: "Content-Type" : "application/json; charset=UTF-8"
//	Status Code: 200
//	Payload/Body: 
//	{
//	    "id": ""   
//	}
	
	HashMap<String, String> deletePayload;
	
	SoftAssert softAssert = new SoftAssert();
	
	public DeleteOneProduct() {
		softAssert = new SoftAssert();
	}
	
	
	
	public Map<String, String> deletePayLoadMap(){
		
		deletePayload = new HashMap<String, String>();
		
		deletePayload.put("id", "4834");
		
		return deletePayload;
	}
	
	@Test(priority=1) 
	public void deleteOneProduct() {
		
		Response response =

//		given:
		given()
			.baseUri("https://techfios.com/api-prod/api/product")
			.header("Content-Type", "application/json; charset=UTF-8")
			.header("Authorization", "Bearer Authorization")
			.body(deletePayLoadMap())
			.relaxedHTTPSValidation().

//		when:
		when()
			.delete("/delete.php").

//		then:
		then()
			.extract()
			.response();

//		Status Code
		int actualStatusCode = response.getStatusCode();
		softAssert.assertEquals(actualStatusCode, 200, "Status codes do not match during the delete."); //Soft assert

//		Header
		String actualHeader = response.getHeader("Content-Type");
		softAssert.assertEquals(actualHeader, "application/json; charset=UTF-8");

//		Response Body
		String actualResponseBody = response.getBody().asString();
		System.out.println("Actual Response Body:" + actualResponseBody);
		

		JsonPath jp = new JsonPath(actualResponseBody);

		String productMessage = jp.get("message");
		System.out.println("Product Message:" + productMessage);
		softAssert.assertEquals(productMessage, "Product was deleted.");

		softAssert.assertAll(); // Hard assert
		
	}


	@Test(priority=2)
	public void validateProductDeleteDetails() {
		
	Response response = 
	
	//given:
	given()
//		.log().all() //If I want to use it
		.baseUri("https://techfios.com/api-prod/api/product")
		.header("Content-Type", "application/json; charset=UTF-8")
		.queryParam("id", deletePayLoadMap().get("id"))
		.relaxedHTTPSValidation().
		
	//when:
	when()
		.get("/read_one.php").
		
	//then:
	then()
		.extract().response();
	
//	Status Code
	int actualStatusCode = response.getStatusCode();
	softAssert.assertEquals(actualStatusCode, 404, "Status codes are not matching while validating delete.");
	
//	Response Body
	String actualResponseBody = response.getBody().asString();
	System.out.println("Actual Response Body:" + actualResponseBody);
	
	
	JsonPath jp = new JsonPath(actualResponseBody);
	
	String actualProductMessage = jp.get("message");
	System.out.println("Actual Product Message:" + actualProductMessage);
	softAssert.assertEquals(actualProductMessage, "Product does not exist.");
	
	softAssert.assertAll();
	
	}
	
	
}
