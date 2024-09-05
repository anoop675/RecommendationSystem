package com.anoopsen.MLAnalyticsProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anoopsen.MLAnalyticsProject.service.RecommendationService;

@RestController
@RequestMapping(value="/recommendations")
public class RecommendationController {
	
	@Autowired
	private RecommendationService recommendationService;
	
	@GetMapping(value="/{userId}")
	public ResponseEntity<List<String>> getRecommendations(@PathVariable int userId){
		List<String> recommendations = recommendationService.getRecommendations(userId);
		return ResponseEntity.ok(recommendations);
	}
}
