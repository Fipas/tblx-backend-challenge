package io.techhublisbon.api.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.techhublisbon.api.model.Operator;
import io.techhublisbon.api.model.Vehicle;
import io.techhublisbon.api.service.VehicleService;

@RestController
@RequestMapping("/vehicles")
@Validated
public class VehicleController {

	@Autowired
	private VehicleService vehicleService;

	@GetMapping
	public List<Vehicle> list(
			@RequestParam(name = "startTime", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
			@RequestParam(name = "endTime", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endTime,
			@RequestParam(name = "operator", required = true) @Valid Operator operator,
			@RequestParam(name = "atStop", required = false) @AssertTrue Boolean atStop) {
		
		if (atStop != null && atStop) {
			return vehicleService.getByTimeframeBetweenAndOperator(startTime, endTime, operator, true);
		} else {
			return vehicleService.getByTimeframeBetweenAndOperator(startTime, endTime, operator, false);
		}
	}

}