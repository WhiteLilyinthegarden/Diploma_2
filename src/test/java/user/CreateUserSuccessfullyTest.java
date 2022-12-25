package user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;


public class CreateUserSuccessfullyTest {
    private UserRequests userRequests;
    @Before
    public void setUp() {
        userRequests = new UserRequests();
    }

    @Test
    @DisplayName("Create user account")
    @Description("Send post request tp /api/auth/register, expected status code 200 ok")
    public void userAccountCreateSuccessfully() {
        CreateUser createUser = new CreateUser("yellow_ferra@mail.ru", "P@ssword", "Lily");
        ValidatableResponse response = userRequests.create(createUser);
        String accessToken = response.extract().body().path("accessToken");

        boolean actual = response.extract().body().path("success");
        Assert.assertTrue(actual);

        int statusCode = response.extract().statusCode();
        Assert.assertEquals(SC_OK, statusCode);
        userRequests.delete(accessToken);
    }
}