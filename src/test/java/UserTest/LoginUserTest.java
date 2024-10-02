package UserTest;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.CreateUser;
import user.LoginUser;

import static endpoints.ApiEndpoints.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class LoginUserTest {

    private String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Before
    public void createUser() {
        CreateUser user = new CreateUser("quil123@mail.ru", "123416", "Сглыпа");
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
    public void testCorrectLogin() {
        LoginUser login = new LoginUser("quil123@mail.ru", "123416");
        given()
                .contentType("application/json")
                .body(login)
                .when()
                .post(LOGIN_USER)
                .then()
                .statusCode(200)
                .assertThat().body("success", is(true));
    }

    @Test
    public void testIncorrectEmail() {
        LoginUser login = new LoginUser("pup@mail.ru", "123416");
        given()
                .contentType("application/json")
                .body(login)
                .when()
                .post(LOGIN_USER)
                .then()
                .statusCode(401)
                .assertThat().body("success", is(false));
    }

    @Test
    public void testIncorrectPassword() {
        LoginUser login = new LoginUser("quil123@mail.ru", "jhgfd");
        given()
                .contentType("application/json")
                .body(login)
                .when()
                .post(LOGIN_USER)
                .then()
                .statusCode(401)
                .assertThat().body("success", is(false));
    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            given()
                    .contentType("application/json")
                    .header("Authorization", accessToken)
                    .header("Accept", "*/*")
                    .when()
                    .delete(DELETE_USER)
                    .then()
                    .statusCode(202);
        }
    }
}
