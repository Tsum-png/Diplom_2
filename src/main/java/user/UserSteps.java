package user;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static endpoints.ApiEndpoints.*;

public class UserSteps {

    @Step("Создание пользователя {user}")
    public ValidatableResponse createUser(CreateUser user, int expectedStatusCode, boolean isSuccessExpected) {
        ValidatableResponse response = given()
                .contentType("application/json")
                .body(user)
                .when()
                .post(CREATE_USER)
                .then()
                .statusCode(expectedStatusCode)
                .assertThat().body("success", is(isSuccessExpected));
        return response;
    }

    @Step("Удаление пользователя с accessToken")
    public void deleteUser(String accessToken) {
        given()
                .contentType("application/json")
                .header("Authorization", accessToken)
                .header("Accept", "*/*")
                .when()
                .delete(DELETE_USER)
                .then()
                .statusCode(202);
    }

    @Step("Логин пользователя ")
    public ValidatableResponse loginUser(LoginUser loginUser, int expectedStatusCode, boolean isSuccessExpected) {
        ValidatableResponse response = given()
                .contentType("application/json")
                .body(loginUser)
                .when()
                .post(LOGIN_USER)
                .then()
                .statusCode(expectedStatusCode)
                .assertThat().body("success", is(isSuccessExpected));
        return response;
    }

    @Step("Изменение пользователя ")
    public ValidatableResponse changeUser(ChangeUser changeUser, String accessToken, int expectedStatusCode, boolean isSuccessExpected) {
        return given()
                .contentType("application/json")
                .header("Authorization", accessToken)
                .header("Accept", "*/*")
                .body(changeUser)
                .when()
                .patch(CHANGE_USER)
                .then()
                .statusCode(expectedStatusCode)
                .assertThat().body("success", is(isSuccessExpected));
    }
    @Step("Изменение пользователя без авторизации {user}")
    public ValidatableResponse changeUserWithoutLogin(ChangeUser changeUser, int expectedStatusCode, boolean isSuccessExpected) {
        return given()
                .contentType("application/json")
                .body(changeUser)
                .when()
                .patch(CHANGE_USER)
                .then()
                .statusCode(expectedStatusCode)
                .assertThat().body("success", is(isSuccessExpected));
    }
}

