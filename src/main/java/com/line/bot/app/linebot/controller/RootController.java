package com.line.bot.app.linebot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {
	
	@RequestMapping("/")
	String home() {
		return "Hello World!";
	}

}
