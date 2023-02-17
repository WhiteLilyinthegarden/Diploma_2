package user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.*;
import static org.junit.Assert.*;
import static org.apache.http.HttpStatus.*;

public class LoginUserTest {
    private static UserRequests userRequests = new UserRequests();
    private User user = new User().generateUserEmail();


    @Test
    @DisplayName("Successfully user authorization")
    @Description("Send post request to /api/auth/login and returns 200 ok")
    public void successfullyUserAuthorization() {

        userRequests.create(user);

        ValidatableResponse response = userRequests.login(user);
        assertTrue(response.extract().path("success"));
        assertEquals(SC_OK, response.extract().statusCode());
        String accessToken = response.extract().path("accessToken");
        userRequests.delete(accessToken);
    }

    @Test
    @DisplayName("Not enough data for user authorization")
    @Description("Send post request to /api/auth/login and returns 401 unauthorized")
    public void notEnoughDataForUserAuthorization() {
        ValidatableResponse response = userRequests.login(new User().setPassword(" ").setEmail(user.getEmail()));
        int statusCode = response.extract().statusCode();
        assertEquals(SC_UNAUTHORIZED, statusCode);
        String message = response.extract().path("message");
        assertEquals("email or password are incorrect", message);
    }

    @Test
    @DisplayName("Wrong data for user authorization")
    @Description("Send post request to /api/auth/login and returns 401 unauthorized")
    public void wrongDataForUserAuthorization() {
        ValidatableResponse response = userRequests.login(new User().setPassword("123").setEmail(user.getEmail()));
        int statusCode = response.extract().statusCode();
        assertEquals(SC_UNAUTHORIZED, statusCode);
        String message = response.extract().path("message");
        assertEquals("email or password are incorrect", message);
    }
}