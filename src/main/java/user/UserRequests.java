package user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class UserRequests extends RestClient {

    public static final String USER_REGISTER =  "/api/auth/register";
    public static final String USER_LOGIN =  "/api/auth/login";
    public static final String USER_DELETE_AND_PATCH = "/api/auth/user";
    @Step("Create user")
    public ValidatableResponse create(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(USER_REGISTER).prettyPeek()
                .then();
    }
    @Step("User login")
    public ValidatableResponse login(User User) {
        return given()
                .spec(getBaseSpec())
                .body(User)
                .post(USER_LOGIN).prettyPeek()
                .then();
    }
    @Step("Delete user")
    public ValidatableResponse delete(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .when()
                .delete(USER_DELETE_AND_PATCH)
                .then();
    }
    @Step("Update user email")
    public ValidatableResponse updateEmail(String accessToken, User updateUserEmail22) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .body(updateUserEmail22)
                .when()
                .patch(USER_DELETE_AND_PATCH).prettyPeek()
                .then();
    }
    @Step("Update user name")
    public ValidatableResponse updateUserInfo(String accessToken, User user) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch(USER_DELETE_AND_PATCH)
                .then();
    }
}

