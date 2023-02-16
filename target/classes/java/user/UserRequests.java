package user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserRequests extends RestClient {
    @Step("Create user")
    public ValidatableResponse create(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post("/api/auth/register").prettyPeek()
                .then();

    }
    @Step("User login")
    public ValidatableResponse login(User User) {
        return given()
                .spec(getBaseSpec())
                .body(User)
                .post("/api/auth/login").prettyPeek()
                .then();
    }
    @Step("Delete user")
    public ValidatableResponse delete(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .when()
                .delete("/api/auth/user")
                .then();
    }
    @Step("Update user email")
    public ValidatableResponse updateEmail(String accessToken, User updateUserEmail22) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .body(updateUserEmail22)
                .when()
                .patch("/api/auth/user").prettyPeek()
                .then();
    }
    @Step("Update user name")
    public ValidatableResponse updateUserInfo(String accessToken, User user) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch("/api/auth/user")
                .then();
    }
//    @Step("Update unauthorized user name")
//    public ValidatableResponse updateNameWithoutToken(String accessToken, UpdateUserName updateUserName) {
//        return given()
//                .spec(getBaseSpec())
//                .header("Authorization", accessToken)
//                .body(updateUserName)
//                .when()
//                .patch("/api/auth/user")
//                .then();
//    }
//    @Step("Update unauthorized user email")
//    public ValidatableResponse updateEmailWithoutToken(UpdateUserEmail updateUserEmail) {
//        return given()
//                .spec(getBaseSpec())
//                .body(updateUserEmail)
//                .when()
//                .patch("/api/auth/user")
//                .then();
//    }
}

