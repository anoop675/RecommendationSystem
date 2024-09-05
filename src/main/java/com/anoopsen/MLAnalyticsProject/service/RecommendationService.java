/*
 * Alternating Least Squares (ALS) algorithm is a matrix factorization algorithm used in recommendation systems 
 * to estimate user-item interaction matrices.
 */
package com.anoopsen.MLAnalyticsProject.service;

import org.apache.spark.ml.recommendation.ALSModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.spark.ml.recommendation.ALS;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.anoopsen.MLAnalyticsProject.config.SparkConfig;
import jakarta.annotation.PostConstruct;

@Service
public class RecommendationService {
	
	@Autowired
	private SparkSession sparkSession;
	
	private ALSModel asl_model; //the ML model that uses Alternating Least Squares Algorithm

	@PostConstruct
	public void trainModel() {
		Dataset<Row> ratings = loadRatingsData();
		asl_model = trainALSModel(ratings);
	}
	
	private Dataset<Row> loadRatingsData(){
		String path = "src/main/resources/static/ratings.csv";       //path in package directory to "rating.csv"
		Dataset<Row> ratingsData = sparkSession.read().format("csv")
									.option("header", "true")
									.option("inferSchema", "true")
									.load(path);
		return ratingsData;
	}
	
	private ALSModel trainALSModel(Dataset<Row> ratings) {
		ALS als = new ALS().setMaxIter(10)
					.setRegParam(0.1)
					.setUserCol("userId")
					.setItemCol("movieId")
					.setRatingCol("rating");
		
		ALSModel model = als.fit(ratings);
		model.setColdStartStrategy("drop");
		
		return model;
	}
	
	public List<String> getRecommendations(int userId){
		Dataset<Row> users = sparkSession.createDataFrame(
				Collections.singletonList(userId), Integer.class)
				.toDF("userId");
		
		Dataset<Row> recommendations = asl_model.recommendForUserSubset(users, 10);
		List<Row> rows = recommendations.collectAsList();
		
		if(rows.isEmpty()) {
			Collections.emptyList();
		}
		
		Row row = rows.get(0);
		List<Row> recs = row.getList(1);
		List<String> movieIds = new ArrayList<>();
		
		for(Row rec : recs) {
			movieIds.add(rec.getAs(0).toString());
		}
		
		return movieIds;
	}
}
