package com.line.bot.app.linebot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class LinebotApplication {

	public static void main(String[] args) {
		SpringApplication.run(LinebotApplication.class, args);
	}
	
}
