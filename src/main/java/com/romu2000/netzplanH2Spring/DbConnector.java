package com.romu2000.netzplanH2Spring;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
public class DbConnector{


	public static void main(String[] args) {
		SpringApplication.run(DbConnector.class, args);

	}
}
