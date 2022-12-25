package user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class UpdateExistingUserEmailTest {
    private UserRequests userRequests;
    @Before
    public void setUp() {
        userRequests = new UserRequests();
        CreateUser createUser = new CreateUser("yellow_ferra@mail.ru", "P@ssword", "Lily");
        userRequests.create(createUser);
        CreateUser createUserSecond = new CreateUser("research_java00@mail.ru", "P@ssword", "Lily");
        userRequests.create(createUserSecond);
    }
    @Test
    @DisplayName("Update existing user email")
    @Description("Send post request to /api/auth/user, expected status code  403 forbidden")
    public void changeExistingUserEmail() {

        LoginUser loginUser = new LoginUser("yellow_ferra@mail.ru", "P@ssword");
        ValidatableResponse response = userRequests.login(loginUser);
        String accessToken = response.extract().path("accessToken");

        UpdateUserEmail updateUserEmail = new UpdateUserEmail("research_java00@mail.ru");
        ValidatableResponse response1 = userRequests.updateEmail(accessToken, updateUserEmail);

        boolean result = response1.extract().path("success");
        assertFalse(result);
        int statusCode = response1.extract().statusCode();
        assertEquals(SC_FORBIDDEN, statusCode);
    }

    @After
    public void tearDown() {
        LoginUser loginUser= new LoginUser("yellow_ferra@mail.ru", "P@ssword");
        ValidatableResponse response = userRequests.login(loginUser);
        String accessToken = response.extract().path("accessToken");
        userRequests.delete(accessToken);

        LoginUser loginUserSecond = new LoginUser("research_java00@mail.ru", "P@ssword");
        ValidatableResponse response1 = userRequests.login(loginUserSecond);
        String accessToken1 = response1.extract().path("accessToken");
        userRequests.delete(accessToken1);
    }
}
