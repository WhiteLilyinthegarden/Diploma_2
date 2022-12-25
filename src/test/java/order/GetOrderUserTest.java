package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import user.CreateUser;
import user.LoginUser;
import user.UserRequests;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.apache.http.HttpStatus.*;

public class GetOrderUserTest {
    private static UserRequests userRequests;

    @BeforeClass
    public static void setUp() {
        userRequests = new UserRequests();
        CreateUser createUser = new CreateUser("yellow_ferra@mail.ru", "P@ssword", "Lily");
        userRequests.create(createUser);
    }
    @Test
    @DisplayName("Get order of authorized user")
    @Description("Sent get request to /api/orders, expected status code 200 ok")
    public void getOrderListWithAuth() {
        LoginUser loginUser = new LoginUser("yellow_ferra@mail.ru", "P@ssword");
        ValidatableResponse response = userRequests.login(loginUser);
        String accessToken = response.extract().path("accessToken");
        OrderRequests orderRequests = new OrderRequests();

        ValidatableResponse response1 = orderRequests.getIngredients();
        String ingredientHash = response1.extract().path("data[0]._id");
        IngredientsJson ingredientsJson = new IngredientsJson(ingredientHash);
        orderRequests.createOrder(accessToken, ingredientsJson);

        ValidatableResponse responseOrder = orderRequests.getOrderWithLogin(accessToken);

        ArrayList<String> orderBody = responseOrder.extract().path("orders");
        assertNotEquals(Collections.EMPTY_LIST, orderBody);
        int statusCode = responseOrder.extract().statusCode();
        assertEquals(SC_OK, statusCode);
    }
    @Test
    @DisplayName("Get order of unauthorized user")
    @Description("Send get request to /api/orders, expected status code 401 unauthorized")
    public void getOrderListWithoutLogin() {
        OrderRequests orderRequests = new OrderRequests();
        ValidatableResponse responseOrder = orderRequests.getOrderWithoutLogin();
        int statusCode = responseOrder.extract().statusCode();
        assertEquals(SC_UNAUTHORIZED, statusCode);
        String message = responseOrder.extract().path("message");
        assertEquals("You should be authorised", message);

    }
    @AfterClass
    public static void tearDown() {
        LoginUser loginUser = new LoginUser("yellow_ferra@mail.ru", "P@ssword");
        ValidatableResponse response = userRequests.login(loginUser);
        String accessToken = response.extract().path("accessToken");
        userRequests.delete(accessToken);
    }
}