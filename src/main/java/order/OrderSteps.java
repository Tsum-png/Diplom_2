package order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static endpoints.ApiEndpoints.CREATE_ORDER;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class OrderSteps {
    @Step("Создание заказа ")
    public ValidatableResponse createOrder(CreateOrder order, String accessToken, int expectedStatusCode, boolean isSuccessExpected) {
        return given()
                .contentType("application/json")
                .header("Authorization", accessToken)
                .header("Accept", "*/*")
                .body(order)
                .when()
                .post(CREATE_ORDER)
                .then()
                .statusCode(expectedStatusCode)
                .assertThat().body("success", is(isSuccessExpected));
    }

    @Step("Создание заказа без авторизации ")
    public ValidatableResponse createOrderWithoutLogin(CreateOrder order, int expectedStatusCode) {
        return given()
                .contentType("application/json")
                .body(order)
                .when()
                .post(CREATE_ORDER)
                .then()
                .statusCode(expectedStatusCode);
    }

    @Step("Получение заказа пользователя c авторизацией")
    public ValidatableResponse getOrder(String accessToken, int expectedStatusCode, boolean isSuccessExpected) {
        return given()
                .contentType("application/json")
                .header("Authorization", accessToken)
                .header("Accept", "*/*")
                .when()
                .get(CREATE_ORDER)
                .then()
                .statusCode(expectedStatusCode)
                .assertThat().body("success", is(isSuccessExpected));
    }
    @Step("Получение заказа пользователя c авторизацией")
    public ValidatableResponse getOrderWithoutLogIn(int expectedStatusCode, boolean isSuccessExpected) {
        return given()
                .contentType("application/json")
                .when()
                .get(CREATE_ORDER)
                .then()
                .statusCode(expectedStatusCode)
                .assertThat().body("success", is(isSuccessExpected));
    }
}
