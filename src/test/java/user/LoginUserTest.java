package user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.*;

import static org.junit.Assert.*;
import static org.apache.http.HttpStatus.*;

public class LoginUserTest {
    private static UserRequests userRequests;

    @BeforeClass
    public static void setUp() {
        userRequests = new UserRequests();
        CreateUser createUser = new CreateUser ("Hardkones@yandex.ru", "123456", "Senya");
        userRequests.create(createUser);
    }
    @Test
    @DisplayName("Successfully user authorization")
    @Description("Send post request to /api/auth/login and returns 200 ok")
    public void successfullyUserAuthorization() {
        LoginUser loginUser = new LoginUser("Hardkones@yandex.ru", "123456");
        ValidatableResponse response = userRequests.login(loginUser);
        boolean actual = response.extract().path("success");
        assertTrue(actual);
        int statusCode = response.extract().statusCode();
        assertEquals(SC_OK, statusCode);
    }
    @Test
    @DisplayName("Not enough data for user authorization")
    @Description("Send post request to /api/auth/login and returns 401 unauthorized")
    public void notEnoughDataForUserAuthorization() {
        LoginUser loginUser = new LoginUser("Hardkones@yandex.ru", "");
        ValidatableResponse response = userRequests.login(loginUser);
        int statusCode = response.extract().statusCode();
        assertEquals(SC_UNAUTHORIZED, statusCode);
        String message = response.extract().path("message");
        assertEquals("email or password are incorrect", message);
    }
    @Test
    @DisplayName("Wrong data for user authorization")
    @Description("Send post request to /api/auth/login and returns 401 unauthorized")
    public void wrongDataForUserAuthorization() {
        LoginUser loginUser = new LoginUser("Hardkones@yandex.ru", "123");
        ValidatableResponse response = userRequests.login(loginUser);
        int statusCode = response.extract().statusCode();
        assertEquals(SC_UNAUTHORIZED, statusCode);
        String message = response.extract().path("message");
        assertEquals("email or password are incorrect", message);
    }
    @AfterClass
    public static void tearDown() {
       LoginUser loginUser = new LoginUser("Hardkones@yandex.ru", "123456");
        ValidatableResponse response = userRequests.login(loginUser);
        String accessToken = response.extract().path("accessToken");
        userRequests.delete(accessToken);
    }
}
