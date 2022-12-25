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
    private UserRequests userRequests;
    @Before
    public void setUp() {
        userRequests = new UserRequests();
        CreateUser createUser = new CreateUser("yellow_ferra@mail.ru", "P@ssword", "Lily");
        userRequests.create(createUser);
    }
    @Test
    @DisplayName("Update unauthorized user name")
    @Description("Send post request to /api/auth/user, expected status code 401 unauthorized")
    public void changeNameForUnauthorizedUser() {

        UpdateUserName updateUserName = new UpdateUserName("Lilu");
        ValidatableResponse response = userRequests.updateNameWithoutToken(updateUserName);
        boolean result = response.extract().path("success");
        assertFalse(result);
        int statusCode = response.extract().statusCode();
        assertEquals(SC_UNAUTHORIZED, statusCode);
    }
    @Test
    @DisplayName("Update unauthorized user email")
    @Description("Send post request to /api/auth/user, expected status code 401 unauthorized")
    public void changeEmailForUnauthorizedUser() {

        UpdateUserEmail updateUserEmail = new UpdateUserEmail("research_java00@mail.ru");
        ValidatableResponse response = userRequests.updateEmailWithoutToken(updateUserEmail);
        boolean result = response.extract().path("success");
        assertFalse(result);
        int statusCode = response.extract().statusCode();
        assertEquals(SC_UNAUTHORIZED, statusCode);
    }
    @After
    public void tearDown() {
        LoginUser loginUser = new LoginUser("yellow_ferra@mail.ru", "P@ssword");
        ValidatableResponse response = userRequests.login(loginUser);
        String accessToken = response.extract().path("accessToken");
        userRequests.delete(accessToken);
    }
}