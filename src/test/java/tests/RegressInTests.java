package tests;

import models.lombok.SingleUserDataLombokModel;
import models.lombok.User;
import models.lombok.UserCreateLombokModel;
import models.pojo.UserCreatePojoModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static spec.Specification.*;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegressInTests {

    /*  1. make GET request to https://reqres.in/api/unknown/2
        2. get response - { "data": { "id": 2, "name": "fuchsia rose", "year": 2001, "color": "#C74375", "pantone_value": "17-2031" },
    "support": { "url": "https://reqres.in/#support-heading", "text": "To keep ReqRes free, contributions towards server costs are appreciated!" } }
    */

    @Test
    void getUsersName() {

        given()
                .spec(requestUsers)
                .when()
                .get("/unknown/2")
                .then()
                .spec(responseUsers)
                .body("data.name", is("fuchsia rose"));
    }

    @Test
    void checkUsersYear() {
        Integer expectedYear = 2001;

        Integer actualYear = given()
                .spec(requestUsers)
                .when()
                .get("/unknown/2")
                .then()
                .spec(responseUsers)
                .extract()
                .path("data.year");

        assertEquals(expectedYear, actualYear);
    }

    @Test
    void checkUsersColor() {
        String expectedColor = "#C74375";
        String actualColor = given()
                .spec(requestUsers)
                .when()
                .get("/unknown/2")
                .then()
                .spec(responseUsers)
                .extract()
                .path("data.color");

        assertEquals(expectedColor, actualColor);
    }


    @Test
    void checkListUsers() {
        given()
                .spec(requestUsers)
                .when()
                .get("/users?page=2")
                .then()
                .spec(responseUsers)
                .body("page", is(2));
    }

    @Test
    void checkSingleUser() {
        given()
                .spec(requestUsers)
                .when()
                .get("/users/2")
                .then()
                .spec(responseUsers)
                .body("support.url", is("https://reqres.in/#support-heading"));
    }

    @Test
    @DisplayName("CREATE NEW USER")
    void createUserWithLombok() {
        UserCreateLombokModel body = new UserCreateLombokModel();
        body.setName("morpheus");
        body.setJob("leader");

        UserCreateLombokModel response = given()
                .spec(requestUsers)
                .when()
                .body(body)
                .post("/users")
                .then()
                .spec(responseCreate)
                .extract()
                .as(UserCreateLombokModel.class);

        assertThat(response.getName()).isEqualTo("morpheus");
    }

    @Test
    @DisplayName("LIST <RESOURCE>")
    void getListResource() {
        given()
                .spec(requestUsers)
                .when()
                .get("/unknown")
                .then()
                .spec(responseUsers)
                .body("data[0]", hasKey("id"))
                .body("data[0]", hasKey("name"))
                .body("data[0]", hasKey("year"))
                .body("data[0]", hasKey("color"))
                .body("data[0]", hasKey("pantone_value"));

    }

    @Test
    @DisplayName("SINGLE USER LOMBOK")
    public void getSingleUserWithLombok() {

        SingleUserDataLombokModel userdata = given()
                .spec(requestUsers)
                .when()
                .get("/users/2")
                .then()
                .spec(responseUsers)
                .extract()
                .as(SingleUserDataLombokModel.class);

        assertThat(userdata.getData().getId(), equalTo(2));
        assertThat(userdata.getData().getEmail(), equalTo("janet.weaver@reqres.in"));
        assertThat(userdata.getData().getFirstName(), equalTo("Janet"));
        assertThat(userdata.getData().getLastName(), equalTo("Weaver"));
        assertThat(userdata.getData().getAvatar(), equalTo("https://reqres.in/img/faces/2-image.jpg"));

    }

    @Test
    @DisplayName("CREATE NEW USER POJO")
    void createUserWithPoJo() {
        UserCreatePojoModel body = new UserCreatePojoModel();
        body.setName("morpheus");
        body.setJob("leader");

        UserCreatePojoModel response = given()
                .spec(requestUsers)
                .when()
                .body(body)
                .post("/users")
                .then()
                .spec(responseCreate)
                .extract()
                .as(UserCreatePojoModel.class);

        assertEquals("morpheus", response.getName());

       // assertThat(response.getName()).isEqualTo("morpheus");
    }

}
