package io.techhublisbon.api.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import io.techhublisbon.api.model.Trace;
import io.techhublisbon.api.repository.TraceRepository;

@Service
public class TraceService {

	@Autowired
	private TraceRepository traceRepository;

	public List<Trace> getByTimeframeBetweenAndVehicleId(LocalDate startTime, LocalDate endTime, Long vehicleId) {
		final List<Trace> traces = new ArrayList<>();
		Page<Trace> response;
		Integer pageIndex = 0;
		Integer pageSize = 1000;

		do {
			response = traceRepository.findByTimeframeBetweenAndVehicleIdOrderByTimestamp(startTime, endTime, vehicleId,
					PageRequest.of(pageIndex, pageSize));
			traces.addAll(response.getContent());
			pageIndex++;
		} while (pageIndex < response.getTotalPages());

		return traces;
	}

}
