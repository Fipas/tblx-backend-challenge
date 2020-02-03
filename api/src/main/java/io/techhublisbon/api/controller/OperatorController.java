package io.techhublisbon.api.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.techhublisbon.api.model.Operator;
import io.techhublisbon.api.service.OperatorService;

@RestController
@RequestMapping("/operators")
public class OperatorController {

	@Autowired
	private OperatorService operatorService;

	@GetMapping
	public List<Operator> list(
			@RequestParam(name = "startTime", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
			@RequestParam(name = "endTime", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endTime) {

		return operatorService.getByTimeframeBetween(startTime, endTime);
	}

}
