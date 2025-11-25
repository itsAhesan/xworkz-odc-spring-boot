package com.xworkz.orderprocessing;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.internal.MongoClientImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class OrderProcessingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderProcessingServiceApplication.class, args);
	}




	@Bean
	public CommandLineRunner testMongoPool(MongoClient mongoClient) {
		return args -> {

			MongoClientSettings settings = ((MongoClientImpl) mongoClient).getSettings();
			var pool = settings.getConnectionPoolSettings();

			System.out.println("==== MongoDB Connection Pool Settings ====");
			System.out.println("Max Pool Size: " + pool.getMaxSize());
			System.out.println("Min Pool Size: " + pool.getMinSize());
			System.out.println("Max Wait Time: " + pool.getMaxWaitTime(TimeUnit.MILLISECONDS));
			System.out.println("Max Connection Idle Time: " + pool.getMaxConnectionIdleTime(TimeUnit.MILLISECONDS));
			System.out.println("===========================================");
		};
	}



}
