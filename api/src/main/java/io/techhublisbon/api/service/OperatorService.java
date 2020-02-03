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

@Service
public class OperatorService {

	@Autowired
	private ElasticsearchOperations elasticsearchOperations;

	public List<Operator> getByTimeframeBetween(LocalDate startTime, LocalDate endTime) {
		final TermsAggregationBuilder aggregation = AggregationBuilders.terms("operators").field("operator.keyword")
				.size(10000);

		final QueryBuilder timeframeQuery = QueryBuilders.rangeQuery("timeframe").from(startTime).to(endTime);
		final SearchQuery query = new NativeSearchQueryBuilder().withQuery(timeframeQuery).addAggregation(aggregation)
				.build();

		return elasticsearchOperations.query(query, new ResultsExtractor<List<Operator>>() {

			@Override
			public List<Operator> extract(SearchResponse response) {
				List<Operator> results = new ArrayList<>();
				Terms operators = response.getAggregations().get("operators");

				for (Terms.Bucket bucket : operators.getBuckets()) {
					results.add(new Operator(bucket.getKeyAsString()));
				}
				return results;
			}

		});
	}

}
