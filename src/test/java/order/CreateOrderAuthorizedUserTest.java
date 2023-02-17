package order;


import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import user.User;
import user.UserRequests;


import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class CreateOrderAuthorizedUserTest {
    private static UserRequests userRequests;
    static User user = new User().generateUserEmail();
    @BeforeClass
    public static void setUp() {
        userRequests = new UserRequests();
        userRequests.create(user);
    }
    @Test
    @DisplayName("Create order with login")
    @Description("Send post request to /api/orders, expected status code 200 ok")
    public void createOrderWithAuthorizedUser() {


        ValidatableResponse response = userRequests.login(user);
        String accessToken = response.extract().path("accessToken");

        OrderRequests orderRequests = new OrderRequests();
        ValidatableResponse response1 = orderRequests.getIngredients();
        String ingredientHash = response1.extract().path("data[0]._id");
        IngredientsJson ingredientsJson = new IngredientsJson(ingredientHash);

        ValidatableResponse response2 = orderRequests.createOrder(accessToken, ingredientsJson);
        boolean result = response2.extract().path("success");
        assertTrue(result);
        int statusCode = response2.extract().statusCode();
        assertEquals(SC_OK, statusCode);
    }
    @Test
    @DisplayName("Create order without ingredients")
    @Description("Send post request to /api/orders, expected status code 400 bad request")
    public void createOrderWithoutIngredients() {
        ValidatableResponse response = userRequests.login(user);
        String accessToken = response.extract().path("accessToken");

        OrderRequests orderRequests = new OrderRequests();
        IngredientsJson ingredientsJson = new IngredientsJson();

        ValidatableResponse response2 = orderRequests.createOrder(accessToken, ingredientsJson);
        boolean result = response2.extract().path("success");
        assertFalse(result);
        int statusCode = response2.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);
    }
    @Test
    @DisplayName("Create order with invalid ingredient hash")
    @Description("Send post request to /api/orders, expected status code 500 internal server error")
    public void createOrderWithInvalidIngredientHash() {
        ValidatableResponse response = userRequests.login(user);
        String accessToken = response.extract().path("accessToken");

        OrderRequests orderRequests = new OrderRequests();
        String ingredientHash = "60d3b41abdacab0026";
        IngredientsJson ingredientsJson = new IngredientsJson(ingredientHash);

        ValidatableResponse response2 = orderRequests.createOrder(accessToken, ingredientsJson);
        int statusCode = response2.extract().statusCode();
        assertEquals(SC_INTERNAL_SERVER_ERROR, statusCode);
    }
    @AfterClass
    public static void tearDown() {
        ValidatableResponse response = userRequests.login(user);
        String accessToken = response.extract().path("accessToken");
        userRequests.delete(accessToken);
    }
}
