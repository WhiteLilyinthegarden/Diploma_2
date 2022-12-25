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


import static org.junit.Assert.*;
import static org.apache.http.HttpStatus.*;

public class CreateOrderUnauthorizedUserTest {
    private static UserRequests userRequests;

    @BeforeClass
    public static void setUp() {
        userRequests = new UserRequests();
        CreateUser createUser = new CreateUser("yellow_ferra@mail.ru", "P@ssword", "Lily");
        userRequests.create(createUser);
    }
    @Test
    @DisplayName("Create order without user login")
    @Description("Send post request to /api/orders, expected status code  200 ok")
    public void createOrderWithoutAuthorization() {
        OrderRequests orderRequests = new OrderRequests();
        ValidatableResponse response1 = orderRequests.getIngredients();

        String ingredientHash = response1.extract().path("data[0]._id");

        IngredientsJson ingredientsJson = new IngredientsJson(ingredientHash);

        orderRequests.createOrderWithoutLogin(ingredientsJson);

        LoginUser loginUser = new LoginUser("yellow_ferra@mail.ru", "P@ssword");
        ValidatableResponse response = userRequests.login(loginUser);
        String accessToken = response.extract().path("accessToken");
        ValidatableResponse response2 = orderRequests.createOrder(accessToken, ingredientsJson);
        boolean result = response2.extract().path("success");
        assertTrue(result);
        int statusCode = response2.extract().statusCode();
        assertEquals(SC_OK, statusCode);
    }
    @Test
    @DisplayName("Create order without user login and without ingredients")
    @Description("Send post request to /api/orders, expected status code  400 bad request")
    public void createOrderWithoutIngredientsAndLogin() {
        OrderRequests orderRequests = new OrderRequests();
        IngredientsJson ingredientsJson = new IngredientsJson();

        orderRequests.createOrderWithoutIngredient();

        LoginUser loginUser = new LoginUser("yellow_ferra@mail.ru", "P@ssword");
        ValidatableResponse response = userRequests.login(loginUser);
        String accessToken = response.extract().path("accessToken");

        ValidatableResponse response1 = orderRequests.createOrder(accessToken, ingredientsJson);

        boolean result = response1.extract().path("success");
        assertFalse(result);
        int statusCode = response1.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);
    }
    @AfterClass
    public static void tearDown() {
        LoginUser loginUser = new LoginUser("yellow_ferra@mail.ru", "P@ssword");
        ValidatableResponse response = userRequests.login(loginUser);
        String accessToken = response.extract().path("accessToken");
        userRequests.delete(accessToken);
    }
}
