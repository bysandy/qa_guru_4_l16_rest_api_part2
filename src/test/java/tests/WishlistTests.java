package tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static utils.FileUtils.readStringFromFile;

public class WishlistTests extends TestBase{

    @Test
    @DisplayName("Add item to the Shopping List")
    void successItemAddedToShoppigListTest() {
        Response response =
        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie("Nop.customer=a8e7de35-5feb-4dfc-9064-d3195fcb8bbb; __utma=78382081.1069782919.1619552736.1619552736.1619635796.2; __utmz=78382081.1619552736.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); NopCommerce.RecentlyViewedProducts=RecentlyViewedProductIds=31; __atuvc=4%7C17; ARRAffinity=06e3c6706bb7098b5c9133287f2a8d510a64170f97e4ff5fa919999d67a34a46; __utmc=78382081; __atuvs=6089ae5373ee3642000; __utmb=78382081.1.10.1619635796; __utmt=1")
                .body("addtocart_31.EnteredQuantity=1")
                .when()
                .post("addproducttocart/details/31/1")
                .then()
                .statusCode(200)
                .log().body()
                .body("success", is(true))
                .extract().response();

        System.out.println(response);
    }
}
