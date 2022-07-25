import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegressInTests {
/* 1. make GET request to https://reqres.in/api/unknown/2

   2. get response - { "data": { "id": 2, "name": "fuchsia rose", "year": 2001, "color": "#C74375", "pantone_value": "17-2031" },
    "support": { "url": "https://reqres.in/#support-heading", "text": "To keep ReqRes free, contributions towards server costs are appreciated!" } }
 */

    @Test
    void getUsersName() {
        String expectedName = "fuchsia rose";
        String actualName = given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .when()
                .get("https://reqres.in/api/unknown/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract()
                .path("data.name");

        assertEquals(expectedName, actualName);
    }

    @Test
    void checkUsersYear() {
        Integer expectedYear = 2001;
        Integer actualYear = given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .when()
                .get("https://reqres.in/api/unknown/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract()
                .path("data.year");

        assertEquals(expectedYear, actualYear);
    }
    @Test
    void checkUsersColor() {
        String expectedColor = "#C74375";
        String actualColor = given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .when()
                .get("https://reqres.in/api/unknown/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract()
                .path("data.color");

        assertEquals(expectedColor, actualColor);
    }



    @Test
    void checkListUsers() {
        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("page", is(2));
    }

    @Test
    void checkSingleUser() {
        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("support.url", is("https://reqres.in/#support-heading"));
    }
}
