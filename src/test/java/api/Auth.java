package api;

import config.ConfigHelper;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class Auth {
    public Map<String, String> login(String login, String password) {
        String Remail = ConfigHelper.getEmailUsername();
        String Rpassword = ConfigHelper.getEmailPassword();
        return
                given()
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        //.contentType(ContentType.URLENC) тоже самое что и строка выше
                        .formParam("Email", Remail)
                        .formParam("Password", Rpassword)
                        .when()
                        .post("/login")
                        .then()
                        .statusCode(302)
                        .log().body()
                        //        .body("success", is(true))
                        .extract().cookies();
    }
}
