/*
 * To change current version of java in Eclipse: Window > Preferences > Java > Compiler > change the version 
 * (NOTE: Do not tick the 'Use --release' option
 */
package com.anoopsen.MLAnalyticsProject;

import java.io.File;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.anoopsen.MLAnalyticsProject.service.RecommendationService;

@SpringBootApplication
public class MLAnalyticsProjectApplication implements ApplicationRunner {
	
	private static Logger logger = LoggerFactory.getLogger(MLAnalyticsProjectApplication.class);
	
	@Autowired
	private RecommendationService recommendationService;

	public static void main(String[] args) throws URISyntaxException {
		logger.info("Java version: "+System.getProperty("java.version"));
		logger.info("Class path: "+getClassPath());
		
		SpringApplication.run(MLAnalyticsProjectApplication.class, args);
	}
	
	public static String getClassPath() throws URISyntaxException {
		File path = new File(MLAnalyticsProjectApplication.class.getProtectionDomain()
						.getCodeSource().getLocation().toURI());
		
		return path.getPath();
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception{
		recommendationService.trainModel();
	}
}
