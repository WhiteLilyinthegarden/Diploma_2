package user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;


public class CreateUserSuccessfullyTest {
    private UserRequests userRequests;

    @Test
    @DisplayName("Create user account")
    @Description("Send post request tp /api/auth/register, expected status code 200 ok")
    public void userAccountCreateSuccessfully() {
        userRequests = new UserRequests();
        ValidatableResponse response = userRequests.create(new User().generateUserEmail());
        String accessToken = response.extract().body().path("accessToken");
        Assert.assertTrue(response.extract().body().path("success"));
        Assert.assertEquals(SC_OK, response.extract().statusCode());
        userRequests.delete(accessToken);
    }

    @Test
    @DisplayName("Create user account")
    @Description("Send post request tp /api/auth/register, expected status code 200 ok")
    public void userAccountCreateSuccessfully1() {
        userRequests = new UserRequests();
        ValidatableResponse response = userRequests.create(new User().generateUserEmail());
        String accessToken = response.extract().body().path("accessToken");

        boolean actual = response.extract().body().path("success");
        Assert.assertTrue(actual);

        int statusCode = response.extract().statusCode();
        Assert.assertEquals(SC_OK, statusCode);
        userRequests.delete(accessToken);
    }
}