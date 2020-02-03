package io.techhublisbon.api.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.techhublisbon.api.model.Trace;
import io.techhublisbon.api.service.TraceService;

@RestController
@RequestMapping("/traces")
public class TraceController {

	@Autowired
	private TraceService traceService;

	@GetMapping
	public List<Trace> list(
			@RequestParam(name = "startTime", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
			@RequestParam(name = "endTime", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endTime,
			@RequestParam(name = "vehicleId", required = true) Long vehicleId) {

		return traceService.getByTimeframeBetweenAndVehicleId(startTime, endTime, vehicleId);
	}

}
