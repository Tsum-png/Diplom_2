package UserTest;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.ChangeUser;
import user.CreateUser;
import user.UserSteps;

import static endpoints.ApiEndpoints.*;

public class ChangeUserTest {

    private UserSteps userSteps = new UserSteps();
    private String accessToken;
    String email = (RandomStringUtils.randomAlphabetic(7) + "@" + "yandex.ru");
    String newEmail = (RandomStringUtils.randomAlphabetic(7) + "@" + "yandex.ru");
    String password = RandomStringUtils.randomAlphabetic(6);
    String newName = RandomStringUtils.randomAlphabetic(6);

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }


    @Test
    public void testChangeNameWithLogin() {
        CreateUser user = new CreateUser(email, password, "Сглыпа");
        ValidatableResponse response = userSteps.createUser(user, 200, true);
        accessToken = response.extract().path("accessToken");
        ChangeUser change = new ChangeUser(email, newName);
        userSteps.changeUser(change, accessToken, 200, true);
    }

    @Test
    public void testChangeEmailWithLogin() {
        CreateUser user = new CreateUser(email, password, "Сглыпа");
        ValidatableResponse response = userSteps.createUser(user, 200, true);
        accessToken = response.extract().path("accessToken");
        ChangeUser change = new ChangeUser(newEmail, "Сглыпа");
        userSteps.changeUser(change, accessToken, 200, true);
    }

    @Test
    public void testChangeNameWithoutLogin() {
        CreateUser user = new CreateUser(email, password, "Сглыпа");
        ValidatableResponse response = userSteps.createUser(user, 200, true);
        accessToken = response.extract().path("accessToken");
        ChangeUser change = new ChangeUser(email, newName);
        userSteps.changeUserWithoutLogin(change, 401, false);
    }

    @Test
    public void testChangeEmailWithoutLogin() {
        CreateUser user = new CreateUser(email, password, "Сглыпа");
        ValidatableResponse response = userSteps.createUser(user, 200, true);
        accessToken = response.extract().path("accessToken");
        ChangeUser change = new ChangeUser(newEmail, "Сглыпа");
        userSteps.changeUserWithoutLogin(change, 401, false);
    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            userSteps.deleteUser(accessToken);
        }
    }
}
