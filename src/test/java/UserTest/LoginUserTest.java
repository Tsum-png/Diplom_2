package UserTest;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.CreateUser;
import user.LoginUser;
import user.UserSteps;

import static endpoints.ApiEndpoints.*;

public class LoginUserTest {

    private UserSteps userSteps = new UserSteps();
    private String accessToken;
    String email = (RandomStringUtils.randomAlphabetic(7) + "@" + "yandex.ru");
    String password = RandomStringUtils.randomAlphabetic(6);
    String incorrectEmail = (RandomStringUtils.randomAlphabetic(6) + "@" + "yandex.ru");
    String incorrectPassword = RandomStringUtils.randomAlphabetic(6);

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }
    @Test
    public void testCorrectLogin() {
        CreateUser user = new CreateUser(email, password, "Сглыпа");
        ValidatableResponse response = userSteps.createUser(user, 200, true);
        accessToken = response.extract().path("accessToken");
        LoginUser login = new LoginUser(email, password);
        userSteps.loginUser(login, 200, true);
    }

    @Test
    public void testIncorrectEmail() {
        CreateUser user = new CreateUser(email, password, "Сглыпа");
        ValidatableResponse response = userSteps.createUser(user, 200, true);
        accessToken = response.extract().path("accessToken");
        LoginUser login = new LoginUser(incorrectEmail, password);
        userSteps.loginUser(login, 401, false);
    }

    @Test
    public void testIncorrectPassword() {
        CreateUser user = new CreateUser(email, password, "Сглыпа");
        ValidatableResponse response = userSteps.createUser(user, 200, true);
        accessToken = response.extract().path("accessToken");
        LoginUser login = new LoginUser(email, incorrectPassword);
        userSteps.loginUser(login, 401, false);
    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            userSteps.deleteUser(accessToken);
        }
    }
}
