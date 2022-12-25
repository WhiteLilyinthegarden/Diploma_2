package user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserRequests extends RestClient {
    @Step("Create user")
    public ValidatableResponse create(CreateUser createUser) {
        return given()
                .spec(getBaseSpec())
                .body(createUser)
                .when()
                .post("/api/auth/register")
                .then();

    }
    @Step("User login")
    public ValidatableResponse login(LoginUser loginUser) {
        return given()
                .spec(getBaseSpec())
                .body(loginUser)
                .post("/api/auth/login")
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
    public ValidatableResponse updateEmail(String accessToken, UpdateUserEmail updateUserEmail) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .body(updateUserEmail)
                .when()
                .patch("/api/auth/user")
                .then();
    }
    @Step("Update user name")
    public ValidatableResponse updateName(String accessToken, UpdateUserName updateUserName) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .body(updateUserName)
                .when()
                .patch("/api/auth/user")
                .then();
    }
    @Step("Update unauthorized user name")
    public ValidatableResponse updateNameWithoutToken(UpdateUserName updateUserName) {
        return given()
                .spec(getBaseSpec())
                .body(updateUserName)
                .when()
                .patch("/api/auth/user")
                .then();
    }
    @Step("Update unauthorized user email")
    public ValidatableResponse updateEmailWithoutToken(UpdateUserEmail updateUserEmail) {
        return given()
                .spec(getBaseSpec())
                .body(updateUserEmail)
                .when()
                .patch("/api/auth/user")
                .then();
    }
}

