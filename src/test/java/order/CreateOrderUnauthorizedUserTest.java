package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import user.User;
import user.UserRequests;


import static org.junit.Assert.*;
import static org.apache.http.HttpStatus.*;

public class CreateOrderUnauthorizedUserTest {

    private static UserRequests userRequests;
    static User user = new User().generateUserEmail();
    String fakeToken = "fakeToken";

    @BeforeClass
    public static void setUp() {
        userRequests = new UserRequests();
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


        ValidatableResponse response2 = orderRequests.createOrder(fakeToken, ingredientsJson);
        boolean result = response2.extract().path("success");
        assertTrue(result);
        int statusCode = response2.extract().statusCode();
        assertEquals(SC_OK, statusCode);
    }
    @AfterClass
    public static void tearDown() {

    }
}
