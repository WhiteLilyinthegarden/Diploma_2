package order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import user.RestClient;

import static io.restassured.RestAssured.given;
public class OrderRequests extends RestClient {
    public static final String POST_AND_GET_ORDER = "/api/orders";

    @Step("Get order of authorized user")
    public ValidatableResponse getOrderWithLogin(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .when()
                .get(POST_AND_GET_ORDER)
                .then();
    }
    @Step("Get order without authorized user")
    public ValidatableResponse getOrderWithoutLogin() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(POST_AND_GET_ORDER)
                .then();
    }


    @Step("Create order without user authorization")
    public ValidatableResponse createOrderWithoutLogin(IngredientsJson ingredientsJson) {
        return given()
                .spec(getBaseSpec())
                .body(ingredientsJson)
                .when()
                .post(POST_AND_GET_ORDER)
                .then();
    }

    @Step("Create order with user authorization and with ingredient")
    public ValidatableResponse createOrder(String accessToken, IngredientsJson ingredientsJson) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .body(ingredientsJson)
                .when()
                .post("/api/orders").prettyPeek()
                .then();
    }

    @Step("Create order without ingredient")
    public ValidatableResponse createOrderWithoutIngredient() {
        return given()
                .spec(getBaseSpec())
                .when()
                .post("/api/orders")
                .then();
    }

    @Step("Get list of ingredient for getting hash")
    public ValidatableResponse getIngredients() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get("/api/ingredients")
                .then();
    }

}