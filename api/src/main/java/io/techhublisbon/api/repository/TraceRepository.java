package io.techhublisbon.api.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import io.techhublisbon.api.model.Trace;

public interface TraceRepository extends ElasticsearchRepository<Trace, String> {

	Page<Trace> findByTimeframeBetweenAndVehicleIdOrderByTimestamp(LocalDate startTime, LocalDate endTime, Long vehicleId,
			Pageable page);
}
