package tests;

import io.qameta.allure.Description;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import java.util.Map;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class LoginTest extends TestBase {
    @Test
    @Description("Login to the site using only UI")
    void loginWithUITest() {
        //authorise
        // rxzx6tdtgg@harakirimail.com !QAZ2wsx
        open("/login");
        $("#Email").val("rxzx6tdtgg@harakirimail.com");
        $("#Password").val("!QAZ2wsx").pressEnter();
        //verified
        $(".account").shouldHave(text("rxzx6tdtgg@harakirimail.com"));

    }

    @Test
    @Description("Login to the site using only cookie")
    void loginWithCookieTest() {
        Map<String, String> cookiesMap =
                given()
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        //.contentType(ContentType.URLENC) тоже самое что и строка выше
                        .formParam("Email", "rxzx6tdtgg@harakirimail.com")
                        .formParam("Password", "!QAZ2wsx")
                        .when()
                        .post("/login")
                        .then()
                        .statusCode(302)
                        .log().body()
                //        .body("success", is(true))
                        .extract().cookies();
        //verified

        open("http://demowebshop.tricentis.com/Themes/DefaultClean/Content/images/logo.png"); //нужно предзагружать сайт чтобы подложить куки, для этого можно использовать любую картинку с сайта
        getWebDriver().manage().addCookie(new Cookie("Nop.customer", cookiesMap.get("Nop.customer")));
        getWebDriver().manage().addCookie(new Cookie("NOPCOMMERCE.AUTH", cookiesMap.get("NOPCOMMERCE.AUTH")));
        getWebDriver().manage().addCookie(new Cookie("ARRAffinity", cookiesMap.get("ARRAffinity")));

        open("");
        $(".account").shouldHave(text("rxzx6tdtgg@harakirimail.com"));

    }

}
