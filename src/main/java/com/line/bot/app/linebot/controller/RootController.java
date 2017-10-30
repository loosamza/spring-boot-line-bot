package com.line.bot.app.linebot.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.line.bot.app.linebot.bean.Events;


import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@RestController
public class RootController {

	@Value("${line.access_token}")
	private String access_token;

	@RequestMapping("/")
	String home() {
		return "Hello World!";
	}

	@RequestMapping(value = "/bot", method = RequestMethod.POST, consumes = "text/plain")
	@ResponseBody
	String bot(@org.springframework.web.bind.annotation.RequestBody List<Events> events) {
		System.out.println("Message ===> " + events.get(0).getMessage().getText());
		return events.get(0).getMessage().getText();
	}

	@RequestMapping("/verify")
	@ResponseBody
	String verify() {
		OkHttpClient client = new OkHttpClient();
		String result = "";

		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		Request request = new Request.Builder().url("https://api.line.me/v1/oauth/verify").get()
				.addHeader("content-type", "application/x-www-form-urlencoded").addHeader("cache-control", "no-cache")
				.addHeader("postman-token", "ec35ea6c-381c-12ad-4725-6821a25890ae")
				.addHeader("Authorization", "Bearer " + access_token).build();

		try {
			Response response = client.newCall(request).execute();
			result = response.body().string().toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return result;
	}

}
