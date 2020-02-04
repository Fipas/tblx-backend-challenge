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

import io.techhublisbon.api.model.Trace;
import io.techhublisbon.api.service.TraceService;

@WebMvcTest(TraceController.class)
public class TraceControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	private TraceService tarceService;

	@BeforeEach
	public void setUp() {
		final List<Trace> traces = List.of(new Trace(), new Trace());
		Mockito.when(tarceService.getByTimeframeBetweenAndVehicleId(Mockito.any(LocalDate.class),
				Mockito.any(LocalDate.class), Mockito.anyLong())).thenReturn(traces);
	}

	@Test
	public void shouldReturnListOfTracesByTimeframeRangeAndVehicleId() throws Exception {
		mockMvc.perform(get("/traces").queryParam("startTime", "2012-11-05").queryParam("endTime", "2012-11-06")
				.queryParam("vehicleId", "33210").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)));
	}

	@Test
	public void shouldSendBadRequestWhenNoStartTimeIsProvided() throws Exception {
		mockMvc.perform(get("/traces").queryParam("endTime", "2012-11-06").queryParam("vehicleId", "33210")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error", is("startTime query parameter is required")));
	}

	@Test
	public void shouldSendBadRequestWhenNoEndTimeIsProvided() throws Exception {
		mockMvc.perform(get("/traces").queryParam("startTime", "2012-11-05").queryParam("vehicleId", "33210")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error", is("endTime query parameter is required")));
	}

	@Test
	public void shouldSendBadRequestWhenNoVehicleIdIsProvided() throws Exception {
		mockMvc.perform(get("/traces").queryParam("startTime", "2012-11-05").queryParam("endTime", "2012-11-06")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error", is("vehicleId query parameter is required")));
	}

	@Test
	public void shouldSendBadRequestWhenStartTimeIsInWrongFormat() throws Exception {
		mockMvc.perform(get("/traces").queryParam("startTime", "05-11-2012").queryParam("endTime", "2012-11-06")
				.queryParam("vehicleId", "33210").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error", is("startTime must be a date in the format yyyy-MM-dd")));
	}

	@Test
	public void shouldSendBadRequestWhenEndTimeIsInWrongFormat() throws Exception {
		mockMvc.perform(get("/traces").queryParam("startTime", "2012-11-05").queryParam("endTime", "06-11-2012")
				.queryParam("vehicleId", "33210").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error", is("endTime must be a date in the format yyyy-MM-dd")));
	}

	@Test
	public void shouldSendBadRequestWhenVehicleIdIsInWrongFormat() throws Exception {
		mockMvc.perform(get("/traces").queryParam("startTime", "2012-11-05").queryParam("endTime", "2012-11-05")
				.queryParam("vehicleId", "33210A").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.error", is("vehicleId must be an integer")));
	}
}
