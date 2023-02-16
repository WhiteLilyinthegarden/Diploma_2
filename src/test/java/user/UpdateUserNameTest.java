package user;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.apache.http.HttpStatus.*;
public class UpdateUserNameTest {
    private UserRequests userRequests;

    @Test
    @DisplayName("Update user name")
    @Description("Send post request to /api/auth/user, expected status code  200 ok")
    public void changeUserName() {
        userRequests = new UserRequests();
        User user = new User().generateUserEmail();
        userRequests.create(user);
        String newName = "newName";
        String accessToken = userRequests.login(user).extract().path("accessToken");
        ValidatableResponse response1 = userRequests.updateUserInfo(accessToken, user.setName(newName));
        boolean result = response1.extract().path("success");
        assertTrue(result);
        int statusCode = response1.extract().statusCode();
        assertEquals(SC_OK, statusCode);

        ValidatableResponse response2 = userRequests.login(user);
        String accessToken2 = response2.extract().path("accessToken");
        userRequests.delete(accessToken2);
    }
}