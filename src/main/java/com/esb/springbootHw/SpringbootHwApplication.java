package com.esb.springbootHw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SpringbootHwApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootHwApplication.class, args);
	}
}
