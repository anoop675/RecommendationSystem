package com.anoopsen.MLAnalyticsProject.config;

//import org.apache.spark.ml.recommendation.ALSModel;
import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConfig {

	//Configuring Apache Spark within the Spring boot application
	@Bean
    public static SparkSession getSparkSession() {
        SparkSession session = SparkSession.builder()
					            .appName("MLAnalyticsProject")
					            .config("spark.executor.extraJavaOptions", "--add-opens java.base/sun.nio.ch=ALL-UNNAMED")
					            .config("spark.driver.extraJavaOptions", "--add-opens java.base/sun.nio.ch=ALL-UNNAMED")
					            .master("local[*]") // Use this for local mode, change for cluster mode
					            //.config("spark.master", "local")
					            .getOrCreate();
        
        System.out.println("Spark session varsion: "+session.version());
        //session.stop();
        return session;
    }
}
