package user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.apache.http.HttpStatus.*;
public class UpdateUserNameTest {
    private UserRequests userRequests;
    @Before
    public void setUp() {
        userRequests = new UserRequests();
        CreateUser createUser = new CreateUser("yellow_ferra@mail.ru", "P@ssword", "Lily");
        userRequests.create(createUser);
    }
    @Test
    @DisplayName("Update user name")
    @Description("Send post request to /api/auth/user, expected status code  200 ok")
    public void changeUserName() {
        LoginUser loginUser = new LoginUser("yellow_ferra@mail.ru", "P@ssword");
        ValidatableResponse response = userRequests.login(loginUser);
        String accessToken = response.extract().path("accessToken");

        UpdateUserName updateUserName = new UpdateUserName("Lilu");
        ValidatableResponse response1 = userRequests.updateName(accessToken, updateUserName);
        boolean result = response1.extract().path("success");
        assertTrue(result);
        int statusCode = response1.extract().statusCode();
        assertEquals(SC_OK, statusCode);
    }

    @After
    public void tearDown() {
        LoginUser loginUser = new LoginUser("yellow_ferra@mail.ru", "P@ssword");
        ValidatableResponse response = userRequests.login(loginUser);
        String accessToken = response.extract().path("accessToken");
        userRequests.delete(accessToken);
    }
}