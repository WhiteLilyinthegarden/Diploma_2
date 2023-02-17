package user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.apache.http.HttpStatus.*;

public class UpdateUserEmailSuccessfullyTest {
    private UserRequests userRequests;
    User user = new User().generateUserEmail();

    @Before
    public void setUp() {
        userRequests = new UserRequests();
        userRequests.create(user);

    }

    @Test
    @DisplayName("Update user email")
    @Description("Send post request to /api/auth/uer, expected status code 200 ok")
    public void changeUserEmail() {

        ValidatableResponse response = userRequests.login(user);
        String accessToken = response.extract().path("accessToken");

        ValidatableResponse response1 = userRequests.updateEmail(accessToken, user.generateUserEmail());
        boolean result = response1.extract().path("success");
        assertTrue(result);
        int statusCode = response1.extract().statusCode();
        assertEquals(SC_OK, statusCode);
    }

    @After
    public void tearDown() {
        ValidatableResponse response = userRequests.login(user);
        String accessToken = response.extract().path("accessToken");
        userRequests.delete(accessToken);
    }
}