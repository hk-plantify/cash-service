package com.plantify.cash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@SpringBootApplication
@EnableFeignClients
public class CashApplication {

	public static void main(String[] args) {
		SpringApplication.run(CashApplication.class, args);
	}

	@Bean
	public RecordMessageConverter messageConverter() {
		return new StringJsonMessageConverter();
	}

}
