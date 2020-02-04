package io.techhublisbon.api.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import io.techhublisbon.api.model.Operator;
import io.techhublisbon.api.service.OperatorService;

@WebMvcTest(OperatorController.class)
public class OperatorControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	private OperatorService operatorService;

	@BeforeEach
	public void setUp() {
		final List<Operator> operators = List.of(new Operator("SL"), new Operator("RD"));
		Mockito.when(operatorService.getByTimeframeBetween(Mockito.any(LocalDate.class), Mockito.any(LocalDate.class)))
				.thenReturn(operators);
	}

	@Test
	public void shouldReturnListOfOperatorsByTimeframeRange() throws Exception {
		mockMvc.perform(get("/operators").queryParam("startTime", "2012-11-05").queryParam("endTime", "2012-11-06")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].id", is("SL")))
				.andExpect(jsonPath("$[1].id", is("RD")));
	}

	@Test
	public void shouldSendBadRequestWhenNoStartTimeIsProvided() throws Exception {
		mockMvc.perform(get("/operators").queryParam("endTime", "2012-11-06").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error", is("startTime query parameter is required")));
	}

	@Test
	public void shouldSendBadRequestWhenNoEndTimeIsProvided() throws Exception {
		mockMvc.perform(get("/operators").queryParam("startTime", "2012-11-05").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error", is("endTime query parameter is required")));
	}

	@Test
	public void shouldSendBadRequestWhenStartTimeIsInWrongFormat() throws Exception {
		mockMvc.perform(get("/operators").queryParam("startTime", "05-11-2012").queryParam("endTime", "2012-11-06")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error", is("startTime must be a date in the format yyyy-MM-dd")));
	}

	@Test
	public void shouldSendBadRequestWhenEndTimeIsInWrongFormat() throws Exception {
		mockMvc.perform(get("/operators").queryParam("startTime", "2012-11-05").queryParam("endTime", "06-11-2012")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error", is("endTime must be a date in the format yyyy-MM-dd")));
	}
}
