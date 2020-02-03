package io.techhublisbon.api.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import io.techhublisbon.api.model.Operator;
import io.techhublisbon.api.service.OperatorService;

@SpringBootTest
public class OperatorControllerTest {
	
	@Mock
	private OperatorService operatorService;
	
	@InjectMocks
	private OperatorController operatorController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		final List<Operator> operators = List.of(new Operator("SL"), new Operator("RD"));
		when(operatorService.getByTimeframeBetween(Mockito.any(LocalDate.class), Mockito.any(LocalDate.class))).thenReturn(operators);
	}

	@Test
	public void shouldReturnListOfOperatorsByTimeframeRange() {
		given().queryParam("startTime", "2012-11-05").queryParam("endTime", "2012-11-06").expect().statusCode(200)
		.when().get("/operators").then().body("$.id", hasItems("SL", "RD"));
	}
}
