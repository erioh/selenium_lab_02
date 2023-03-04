package ua.com.rozetka.scenarios.apis;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ua.com.rozetka.dtos.Pet;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class RestAssuredTest {
    private static final String NEW_PET_ID_KEY = "newPetId";
    private static final String PET = "pet";
    private static final String PET_BY_STATUS = PET + "/findByStatus";
    private static final String PET_BY_ID = PET + "/{id}";

    private final Map<String, Object> cachedResults = new ConcurrentHashMap<>();

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .build();
    }

    @DataProvider
    public Object[][] should_return_pets_by_status() {
        return new Object[][]{
                {"available"},
                {"pending"},
                {"sold"}
        };
    }

    @Test(dataProvider = "should_return_pets_by_status")
    public void should_return_pets_by_status(String status) {
        RestAssured.given()
                .queryParam("status", status)
                .get(PET_BY_STATUS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("$.size()", greaterThanOrEqualTo(0));
    }

    @Test
    public void should_add_new_pet() {
        var pet = new Pet();
        pet.setName("Demenkov [%s]".formatted(Faker.instance().funnyName().name()));
        pet.setStatus("available");
        var response = RestAssured.given()
                .body(pet)
                .post(PET)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .body()
                .as(Pet.class);
        assertThat(response)
                .usingRecursiveComparison()
                .comparingOnlyFields("name", "status")
                .isEqualTo(pet);
        cachedResults.put("newPetId", response.getId());
    }

    @Test(dependsOnMethods = "should_add_new_pet")
    public void should_return_pet_by_id() {
        var newPetId = (Long) cachedResults.get(NEW_PET_ID_KEY);
        var pet = RestAssured.given()
                .get(PET_BY_ID, newPetId)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .body()
                .as(Pet.class);
        assertThat(pet).isNotNull();

    }

    @Test(dependsOnMethods = "should_add_new_pet")
    public void should_update_pet_by_id() {
        var newPetId = (Long) cachedResults.get(NEW_PET_ID_KEY);
        var pet = new Pet();
        pet.setId(newPetId);
        pet.setName("Demenkov [%s]".formatted(Faker.instance().funnyName().name()));
        pet.setStatus("available");
        var response = RestAssured.given()
                .body(pet)
                .put(PET)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .body()
                .as(Pet.class);
        assertThat(response).isNotNull();
    }

    @Test(dependsOnMethods = {"should_update_pet_by_id", "should_return_pet_by_id"})
    public void should_delete_pet() {
        var newPetId = (Long) cachedResults.get(NEW_PET_ID_KEY);
        RestAssured.given()
                .delete(PET_BY_ID, newPetId)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}
