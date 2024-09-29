package UserTest;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.CreateUser;
import io.restassured.response.ValidatableResponse;

import java.util.Collections;

import static endpoints.ApiEndpoints.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class CreateUserTest {

    private String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void createUserSuccessfully() {
        CreateUser user = new CreateUser("qei123@mail.ru", "123416", "Сглыпа");
        ValidatableResponse response = given()
                .contentType("application/json")
                .body(user)
                .when()
                .post(CREATE_USER)
                .then()
                .statusCode(200)
                .assertThat().body("success", is(true));
        accessToken = response.extract().path("accessToken");
    }

    @Test
    public void createExistingUser() {
        CreateUser user = new CreateUser("wei123@mail.ru", "123416", "Сглыпа");
        ValidatableResponse response = given()
                .contentType("application/json")
                .body(user)
                .when()
                .post(CREATE_USER)
                .then()
                .statusCode(200)
                .assertThat().body("success", is(true));
        accessToken = response.extract().path("accessToken");
        given()
                .contentType("application/json")
                .body(user)
                .when()
                .post(CREATE_USER)
                .then()
                .statusCode(403)
                .assertThat().body("success", is(false));
    }

   // @After
    //public void deleteUser() {
      //  if (accessToken != null){
      //  given()
         //       .contentType("application/json")
         //       .header("authorization", accessToken)
            //    .when()
            //    .post(DELETE_USER);
  //  }
       // }
}
