package tests;

import api.Auth;
import config.ConfigHelper;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import java.util.Map;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;


public class WishlistTests extends TestBase{

    @Test
    @DisplayName("Add item to the Wishlist as guest")
    void addedToWishlistAsGuest() {
        String Email = ConfigHelper.getEmailUsername();
        String Password = ConfigHelper.getEmailPassword();


        Map<String, String> cookiesMap = new Auth().login(Email, Password);
        Response response =
                given()
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        .cookies(cookiesMap)
                        .body("product_attribute_5_7_1=1&addtocart_5.EnteredQuantity=1")
                        .when()
                        .post("/addproducttocart/details/5/2")
                        .then()
                        .statusCode(200)
                        .log().body()
                        .body("success", is(true))
                        .extract().response();

        open("http://demowebshop.tricentis.com/Themes/DefaultClean/Content/images/logo.png"); //нужно предзагружать сайт чтобы подложить куки, для этого можно использовать любую картинку с сайта
        getWebDriver().manage().addCookie(new Cookie("Nop.customer", cookiesMap.get("Nop.customer")));
        getWebDriver().manage().addCookie(new Cookie("NOPCOMMERCE.AUTH", cookiesMap.get("NOPCOMMERCE.AUTH")));
        getWebDriver().manage().addCookie(new Cookie("ARRAffinity", cookiesMap.get("ARRAffinity")));

        open("");
        $(".account").shouldHave(text("rxzx6tdtgg@harakirimail.com"));
    }

    @Test
    @DisplayName("Add item to the Wishlist using cookie as guest")
    void addedToWishlistWithCookie() throws ParseException {
        String Remail = ConfigHelper.getEmailUsername();
        String Rpassword = ConfigHelper.getEmailPassword();

        Map<String, String> cookiesMap = new Auth().login(Remail, Rpassword);
        open("http://demowebshop.tricentis.com/Themes/DefaultClean/Content/images/logo.png"); //нужно предзагружать сайт чтобы подложить куки, для этого можно использовать любую картинку с сайта
        getWebDriver().manage().addCookie(new Cookie("Nop.customer", cookiesMap.get("Nop.customer")));
        getWebDriver().manage().addCookie(new Cookie("NOPCOMMERCE.AUTH", cookiesMap.get("NOPCOMMERCE.AUTH")));
        getWebDriver().manage().addCookie(new Cookie("ARRAffinity", cookiesMap.get("ARRAffinity")));

        open("");
        $(".account").shouldHave(text("rxzx6tdtgg@harakirimail.com"));

        String wishListValue = new TestBase().getWishlistCount();
        Integer wishListQty = Integer.parseInt(wishListValue.substring(1, wishListValue.length() - 1));

        String response =
                given()
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        .cookies(cookiesMap)
                        .body("product_attribute_5_7_1=1&addtocart_5.EnteredQuantity=1")
                .when()
                        .post("/addproducttocart/details/5/2")
                .then()
                        .statusCode(200)
                        .log().body()
                        .body("success", is(true))
                        .body("updatetopwishlistsectionhtml", is(("(" + (wishListQty + 1) + ")")))
                        .extract().response().asString();

        JSONParser parser = new JSONParser();
        JSONObject JSONResponse = (JSONObject) parser.parse(response);
        String wishListResult = (String) JSONResponse.get("updatetopwishlistsectionhtml");

        open("");
        $(".wishlist-qty").shouldHave(text((wishListResult)));

//        $(".wishlist-qty").shouldHave(text("rxzx6tdtgg@harakirimail.com"));
    }


}
