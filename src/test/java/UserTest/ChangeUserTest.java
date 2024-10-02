package UserTest;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.ChangeUser;
import user.CreateUser;

import static endpoints.ApiEndpoints.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class ChangeUserTest {
    private String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Before
    public void createUser() {
        CreateUser user = new CreateUser("qiu123@mail.ru", "123416", "Сглыпа");
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
    public void testChangeNameWithLogin() {

        ChangeUser change = new ChangeUser("qiu123@mail.ru", "Сглыпа");
        given()
                .contentType("application/json")
                .header("Authorization", accessToken)
                .header("Accept", "*/*")
                .and()
                .body(change)
                .when()
                .patch(CHANGE_USER)
                .then()
                .statusCode(200)
                .assertThat().body("success", is(true));

    }

    @Test
    public void testChangeEmailWithLogin() {
        ChangeUser change = new ChangeUser("123@mail.ru", "Сглыпа");
        given()
                .contentType("application/json")
                .header("Authorization", accessToken)
                .header("Accept", "*/*")
                .and()
                .body(change)
                .when()
                .patch(CHANGE_USER)
                .then()
                .statusCode(200)
                .assertThat().body("success", is(true));
    }

    @Test
    public void testChangeNameWithoutLogin() {
        ChangeUser change = new ChangeUser("qiu123@mail.ru", "Сглыыыыпа");
        given()
                .contentType("application/json")
                .body(change)
                .when()
                .patch(CHANGE_USER)
                .then()
                .statusCode(401)
                .assertThat().body("success", is(false));
    }

    @Test
    public void testChangeEmailWithoutLogin() {
        ChangeUser change = new ChangeUser("qi12u123@mail.ru", "Сглыпа");
        given()
                .body(change)
                .when()
                .patch(CHANGE_USER)
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
