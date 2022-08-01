package spec;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;

public class Specification {

    public static RequestSpecification requestUsers = with()
            .baseUri("https://reqres.in")
            .basePath("/api")
            .log().all()
            .log().body()
            .contentType(JSON);

    public static ResponseSpecification responseUsers = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(LogDetail.BODY)
            .log(LogDetail.STATUS)
            .build();

    public static ResponseSpecification responseCreate = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(LogDetail.BODY)
            .log(LogDetail.STATUS)
            .build();
}
