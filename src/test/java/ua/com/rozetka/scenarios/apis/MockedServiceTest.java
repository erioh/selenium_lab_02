package ua.com.rozetka.scenarios.apis;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class MockedServiceTest {
    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://07d4a799-03be-4c55-a754-6c6d292d405b.mock.pstmn.io";
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .build();
    }

    @Test
    public void should_return_OK_for_get() {
        RestAssured.given()
                .get("ownerName")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("name", equalTo("Serhii Demenkov"));
    }

    @Test
    public void should_return_FORBIDDEN_for_get() {
        RestAssured.given()
                .get("ownerName/unsuccess")
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .body("exception", equalTo("I won’t say my name"));
    }

    @Test
    public void should_return_OK_for_post() {
        RestAssured.given()
                .queryParam("permission", "yes")
                .body("{\"literally\": \"anything\"}")
                .post("createSomething")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("result", equalTo("Nothing was created"));
    }

    @Test
    public void should_return_INTERNAL_SERVER_ERROR_for_put() {
        RestAssured.given()
                .body("{\"name\": \"\", \"surename\": \"\"}")
                .put("updateMe")
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body("result", equalTo("Something went wrong"));
    }

    @Test
    public void should_return_GONE_for_delete() {
        RestAssured.given()
                .delete("deleteWorld")
                .then()
                .statusCode(HttpStatus.SC_GONE)
                .body("world", equalTo(0));
    }
}
