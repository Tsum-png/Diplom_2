package OrderTest;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import order.CreateOrder;
import order.OrderSteps;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.CreateUser;
import user.UserSteps;

import java.util.List;

import static endpoints.ApiEndpoints.*;

public class GetUserOrderTest {
    private UserSteps userSteps = new UserSteps();
    private String accessToken;
    private OrderSteps orderSteps = new OrderSteps();
    String email = (RandomStringUtils.randomAlphabetic(7) + "@" + "yandex.ru");
    String password = RandomStringUtils.randomAlphabetic(6);
    String productHesh = "61c0c5a71d1f82001bdaaa6d";

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            userSteps.deleteUser(accessToken);
        }
    }

    @Test
    public void testGetOrderWithLogin() {
        CreateUser user = new CreateUser(email, password, "Сглыпа");
        ValidatableResponse response = userSteps.createUser(user, 200, true);
        accessToken = response.extract().path("accessToken");
        CreateOrder order = new CreateOrder(List.of(productHesh));
        orderSteps.createOrder(order, accessToken, 200, true);
        orderSteps.getOrder(accessToken, 200, true);
    }

    @Test
    public void testGetOrderWithoutLogin() {
        CreateUser user = new CreateUser(email, password, "Сглыпа");
        ValidatableResponse response = userSteps.createUser(user, 200, true);
        accessToken = response.extract().path("accessToken");
        CreateOrder order = new CreateOrder(List.of(productHesh));
        orderSteps.createOrder(order, accessToken, 200, true);
        orderSteps.getOrderWithoutLogIn(401, false);
    }
}
