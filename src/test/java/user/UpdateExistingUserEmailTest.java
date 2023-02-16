package user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class UpdateExistingUserEmailTest {
    String accessToken;
    String accessToken2;
    private UserRequests userRequests = new UserRequests();

    @Test
    @DisplayName("Update existing user email")
    @Description("Send post request to /api/auth/user, expected status code  403 forbidden")
    public void changeExistingUserEmail() {
        User user = new User().generateUserEmail();
        accessToken = userRequests.create(user).extract().path("accessToken");
        User userSecond = new User().generateUserEmail();
        accessToken2 = userRequests.create(userSecond).extract().path("accessToken");

        ValidatableResponse response1 = userRequests.updateEmail(accessToken, userSecond);
        assertFalse(response1.extract().path("success"));
        assertEquals(SC_FORBIDDEN, response1.extract().statusCode());

        userRequests.delete(accessToken);
        userRequests.delete(accessToken2);
    }

}
