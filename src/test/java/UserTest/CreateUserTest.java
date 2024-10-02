package UserTest;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.CreateUser;
import user.UserSteps;

import static endpoints.ApiEndpoints.*;

public class CreateUserTest {

    private UserSteps userSteps = new UserSteps();
    private String accessToken;
    String email = (RandomStringUtils.randomAlphabetic(7) + "@" + "yandex.ru");
    String password = RandomStringUtils.randomAlphabetic(6);

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void testCreateUserSuccessfully() {
        CreateUser user = new CreateUser(email, password, "Сглыпа");
        ValidatableResponse response = userSteps.createUser(user, 200, true);
        accessToken = response.extract().path("accessToken");
    }

    @Test
    public void testCreateExistingUser() {
        CreateUser user = new CreateUser(email, password, "Сглыпа");
        ValidatableResponse response = userSteps.createUser(user, 200, true);
        accessToken = response.extract().path("accessToken");

        userSteps.createUser(user, 403, false);
    }

    @Test
    public void testCreateUserWithoutName() {
        CreateUser user = new CreateUser(email, password, "");
        userSteps.createUser(user, 403, false);
    }

    @Test
    public void testCreateUserWithoutEmail() {
        CreateUser user = new CreateUser("", password, "Сглыпа");
        userSteps.createUser(user, 403, false);
    }

    @Test
    public void testCreateUserWithoutPassword() {
        CreateUser user = new CreateUser(email, "", "Сглыпа");
        userSteps.createUser(user, 403, false);
    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            userSteps.deleteUser(accessToken);
        }
    }
}


