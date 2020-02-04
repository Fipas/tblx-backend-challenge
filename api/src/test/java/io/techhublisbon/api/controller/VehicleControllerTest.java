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
import io.techhublisbon.api.model.Vehicle;
import io.techhublisbon.api.service.VehicleService;

@WebMvcTest(VehicleController.class)
public class VehicleControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	private VehicleService vehicleService;

	@BeforeEach
	public void setUp() {
		final List<Vehicle> vehicles = List.of(new Vehicle(33210L), new Vehicle(33494L));
		Mockito.when(vehicleService.getByTimeframeBetweenAndOperator(Mockito.any(LocalDate.class),
				Mockito.any(LocalDate.class), Mockito.any(Operator.class), Mockito.eq(false))).thenReturn(vehicles);
		Mockito.when(vehicleService.getByTimeframeBetweenAndOperator(Mockito.any(LocalDate.class),
				Mockito.any(LocalDate.class), Mockito.any(Operator.class), Mockito.eq(true)))
				.thenReturn(List.of(new Vehicle(33210L)));
	}

	@Test
	public void shouldReturnListOfVehiclesByTimeframeRangeAndOperatorId() throws Exception {
		mockMvc.perform(get("/vehicles").queryParam("startTime", "2012-11-05").queryParam("endTime", "2012-11-06")
				.queryParam("operator", "PO").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].id", is(33210)))
				.andExpect(jsonPath("$[1].id", is(33494)));
	}

	@Test
	public void shouldReturnListOfVehiclesByTimeframeRangeAndOperatorIdAndAtStop() throws Exception {
		mockMvc.perform(get("/vehicles").queryParam("startTime", "2012-11-05").queryParam("endTime", "2012-11-06")
				.queryParam("operator", "PO").queryParam("atStop", "true").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].id", is(33210)));
	}

	@Test
	public void shouldSendBadRequestWhenNoStartTimeIsProvided() throws Exception {
		mockMvc.perform(get("/vehicles").queryParam("endTime", "2012-11-06").queryParam("operator", "PO")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error", is("startTime query parameter is required")));
	}

	@Test
	public void shouldSendBadRequestWhenNoEndTimeIsProvided() throws Exception {
		mockMvc.perform(get("/vehicles").queryParam("startTime", "2012-11-05").queryParam("operator", "PO")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error", is("endTime query parameter is required")));
	}

	@Test
	public void shouldSendBadRequestWhenNoOperatorIsProvided() throws Exception {
		mockMvc.perform(get("/vehicles").queryParam("startTime", "2012-11-05").queryParam("endTime", "2012-11-06")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error", is("operator query parameter is required")));
	}

	@Test
	public void shouldSendBadRequestWhenStartTimeIsInWrongFormat() throws Exception {
		mockMvc.perform(get("/vehicles").queryParam("startTime", "05-11-2012").queryParam("endTime", "2012-11-06")
				.queryParam("operator", "PO").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error", is("startTime must be a date in the format yyyy-MM-dd")));
	}

	@Test
	public void shouldSendBadRequestWhenEndTimeIsInWrongFormat() throws Exception {
		mockMvc.perform(get("/vehicles").queryParam("startTime", "2012-11-05").queryParam("endTime", "06-11-2012")
				.queryParam("operator", "PO").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error", is("endTime must be a date in the format yyyy-MM-dd")));
	}

	@Test
	public void shouldSendBadRequestWhenOperatorIsInWrongFormat() throws Exception {
		mockMvc.perform(get("/vehicles").queryParam("startTime", "2012-11-05").queryParam("endTime", "2012-11-06")
				.queryParam("operator", "POA").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error", is("operator must be a string with 2 characters")));
	}

	@Test
	public void shouldSendBadRequestWhenAtStopIsInWrongFormat() throws Exception {
		mockMvc.perform(get("/vehicles").queryParam("startTime", "2012-11-05").queryParam("endTime", "2012-11-06")
				.queryParam("operator", "PO").queryParam("atStop", "false").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error", is("atStop, if present, must be true")));
	}
}
