package user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.apache.http.HttpStatus.*;

public class UpdateUnauthorizedUserTest {
    String fakeToken = "fakeToken";
    private UserRequests userRequests;
    private User user = new User().generateUserEmail();
    @Before
    public void setUp() {
        userRequests = new UserRequests();
    }
    @Test
    @DisplayName("Update unauthorized user name")
    @Description("Send post request to /api/auth/user, expected status code 401 unauthorized")
    public void changeNameForUnauthorizedUser() {
        ValidatableResponse response = userRequests.updateUserInfo(fakeToken, user.setName("newName"));
        assertFalse(response.extract().path("success"));
        assertEquals(SC_UNAUTHORIZED, response.extract().statusCode());
    }
    @Test
    @DisplayName("Update unauthorized user email")
    @Description("Send post request to /api/auth/user, expected status code 401 unauthorized")
    public void changeEmailForUnauthorizedUser() {
        ValidatableResponse response = userRequests.updateUserInfo(fakeToken, user.setEmail("research_java00@mail.ru"));
        assertFalse(response.extract().path("success"));
        assertEquals(SC_UNAUTHORIZED, response.extract().statusCode());
    }
    @After
    public void tearDown() {

    }
}