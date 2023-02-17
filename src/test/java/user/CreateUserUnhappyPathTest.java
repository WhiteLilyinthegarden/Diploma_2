package user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.apache.http.HttpStatus.*;

public class CreateUserUnhappyPathTest {
    private static UserRequests userRequests;

    @BeforeClass
    public static void setUp() {
        userRequests = new UserRequests();
    }

    @Test
    @DisplayName("Create existing user account")
    @Description("Send post request to /api/auth/register, expected status code 403 forbidden")
    public void userAccountAlreadyExists() {
        User user = new User().generateUserEmail();
        String accessToken = userRequests.create(user).extract().path("accessToken");
        ValidatableResponse response = userRequests.create(user);
        userRequests.delete(accessToken);
        int statusCode = response.extract().statusCode();
        assertEquals(SC_FORBIDDEN, statusCode);
        String message = response.extract().path("message");
        assertEquals("User already exists", message);
    }

    @Test
    @DisplayName("Create user account without filling required fields")
    @Description("Send post request to /api/auth/register, expected status code  403 forbidden")
    public void checkFillingRequiredFieldsForCreateUserAccount() {
        ValidatableResponse response = userRequests.create(new User().generateUserEmail().setPassword(""));
        int statusCode = response.extract().statusCode();
        assertEquals(SC_FORBIDDEN, statusCode);
        String message = response.extract().path("message");
        assertEquals("Email, password and name are required fields", message);
    }

    @AfterClass
    public static void tearDown() {
    }
}