package io.techhublisbon.api.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TraceControllerTest {


	@Test
	public void shouldListOperatorsByTimeframeRange() {
		given().queryParam("startTime", "2012-11-05").queryParam("endTime", "2012-11-06").expect().statusCode(200)
				.when().get("/operators").then().body("$.id", hasItems("SL", "RD"));
	}
}
