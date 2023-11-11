package com.smt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication(exclude = MongoReactiveAutoConfiguration.class)
@EnableR2dbcRepositories
public class PostgresMultiTenancyApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostgresMultiTenancyApplication.class, args);
	}

}
