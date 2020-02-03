package io.techhublisbon.api.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import io.techhublisbon.api.model.Operator;
import io.techhublisbon.api.model.Vehicle;

@Service
public class VehicleService {

	@Autowired
	private ElasticsearchOperations elasticsearchOperations;

	public List<Vehicle> getByTimeframeBetweenAndOperator(LocalDate startTime, LocalDate endTime, Operator operator,
			Boolean atStop) {

		final TermsAggregationBuilder aggregation = AggregationBuilders.terms("vehicles").field("vehicleId")
				.size(10000);

		final QueryBuilder timeframeQuery = QueryBuilders.rangeQuery("timeframe").from(startTime).to(endTime);
		final QueryBuilder operatorQuery = QueryBuilders.termQuery("operator.keyword", operator.getId());
		final QueryBuilder atStopQuery = QueryBuilders.termQuery("atStop", true);
		final QueryBuilder boolQuery;

		if (atStop) {
			boolQuery = QueryBuilders.boolQuery().filter(timeframeQuery).filter(operatorQuery).filter(atStopQuery);
		} else {
			boolQuery = QueryBuilders.boolQuery().filter(timeframeQuery).filter(operatorQuery);
		}

		final SearchQuery query = new NativeSearchQueryBuilder().withQuery(boolQuery).addAggregation(aggregation)
				.build();

		return elasticsearchOperations.query(query, new ResultsExtractor<List<Vehicle>>() {

			@Override
			public List<Vehicle> extract(SearchResponse response) {
				List<Vehicle> results = new ArrayList<>();
				Terms operators = response.getAggregations().get("vehicles");

				for (Terms.Bucket bucket : operators.getBuckets()) {
					results.add(new Vehicle(bucket.getKeyAsNumber().longValue()));
				}

				return results;
			}

		});
	}

}
