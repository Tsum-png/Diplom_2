package OrderTest;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import order.CreateOrder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.CreateUser;

import java.util.List;

import static endpoints.ApiEndpoints.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class GetUserOrderTest {
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

    @Test
    public void testGetOneOrder() {
        CreateOrder order = new CreateOrder(List.of("60d3b41abdacab0026a733c6"));
        given()
                .contentType("application/json")
                .header("Authorization", accessToken)
                .header("Accept", "*/*")
                .and()
                .body(order)
                .when()
                .post(CREATE_ORDER)
                .then()
                .statusCode(200)
                .assertThat().body("success", is(true));

        given()
                .contentType("application/json")
                .header("Authorization", accessToken)
                .header("Accept", "*/*")
                .and()
    }
}
