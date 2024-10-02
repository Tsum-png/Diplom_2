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

public class CreateOrderTest {

    private UserSteps userSteps = new UserSteps();
    private OrderSteps orderSteps = new OrderSteps();
    private String accessToken;
    String email = (RandomStringUtils.randomAlphabetic(7) + "@" + "yandex.ru");
    String password = RandomStringUtils.randomAlphabetic(6);
    String productHesh = "61c0c5a71d1f82001bdaaa6d";
    String wrongHesh = "6059711abdacab1896a733c6";

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void testCreateOrderWithIngredientWithLogin() {
        CreateUser user = new CreateUser(email, password, "Сглыпа");
        ValidatableResponse response = userSteps.createUser(user, 200, true);
        accessToken = response.extract().path("accessToken");
        CreateOrder order = new CreateOrder(List.of(productHesh));
        orderSteps.createOrder(order, accessToken, 200, true);
    }

    @Test
    public void testCreateOrderWithoutIngredientWithLogin() {
        CreateUser user = new CreateUser(email, password, "Сглыпа");
        ValidatableResponse response = userSteps.createUser(user, 200, true);
        accessToken = response.extract().path("accessToken");
        CreateOrder order = new CreateOrder(List.of(""));
        orderSteps.createOrder(order, accessToken, 400, false);
    }

    @Test
    public void testCreateOrderWithIngredientWithoutLogin() {
        CreateUser user = new CreateUser(email, password, "Сглыпа");
        ValidatableResponse response = userSteps.createUser(user, 200, true);
        accessToken = response.extract().path("accessToken");
        CreateOrder order = new CreateOrder(List.of(productHesh));
        orderSteps.createOrderWithoutLogin(order, 500);
    }

    @Test
    public void testCreateOrderWithoutIngredientWithoutLogin() {
        CreateUser user = new CreateUser(email, password, "Сглыпа");
        ValidatableResponse response = userSteps.createUser(user, 200, true);
        accessToken = response.extract().path("accessToken");
        CreateOrder order = new CreateOrder(List.of(""));
        orderSteps.createOrderWithoutLogin(order,500);
    }

    @Test
    public void testCreateOrderWithWrongHeshWithLogin() {
        CreateUser user = new CreateUser(email, password, "Сглыпа");
        ValidatableResponse response = userSteps.createUser(user, 200, true);
        accessToken = response.extract().path("accessToken");
        CreateOrder order = new CreateOrder(List.of(wrongHesh));
        orderSteps.createOrder(order, accessToken, 400, false);
    }

    @Test
    public void testCreateOrderWithWrongHeshWithoutLogin() {
        CreateUser user = new CreateUser(email, password, "Сглыпа");
        ValidatableResponse response = userSteps.createUser(user, 200, true);
        accessToken = response.extract().path("accessToken");
        CreateOrder order = new CreateOrder(List.of(wrongHesh));
        orderSteps.createOrderWithoutLogin(order, 500);
    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            userSteps.deleteUser(accessToken);
        }
    }
}
