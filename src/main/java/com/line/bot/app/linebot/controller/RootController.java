package com.line.bot.app.linebot.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.line.bot.app.linebot.bean.Events;
import com.line.bot.app.linebot.bean.Message;
import com.line.bot.app.linebot.bean.WebHookBean;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@RestController
public class RootController {

	@Value("${line.access_token}")
	private String access_token;

	@RequestMapping("/")
	String home() {
		System.out.println("Hello, logs!");
		System.out.println("Hello, logs!");
		System.out.println("Hello, logs!");
		System.out.println("Hello, logs!");
		System.out.println("Hello, logs!");
		System.out.println("Hello, logs!");
		return "Hello World!";
	}

	@RequestMapping(value = "/bot", method = RequestMethod.POST)
	@ResponseBody
	String bot(@org.springframework.web.bind.annotation.RequestBody WebHookBean events) {
		String txt = "";
		String replyToken = "";
		String result = "";
		Message replyMsg = null;
		for (Events ev : events.getEvents()) {
			if (ev.getMessage().equals("text")) {
				replyMsg.setText(ev.getMessage().getText());
				replyMsg.setType("text");

				OkHttpClient client = new OkHttpClient();

				MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
				Gson g = new Gson();
				String json = g.toJson(replyMsg);
				RequestBody body = RequestBody.create(mediaType, json);
				Request request = new Request.Builder().url("https://api.line.me/v2/bot/message/reply").post(body)
						.addHeader("content-type", "application/json").addHeader("cache-control", "no-cache")
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

			}
		}
		System.out.println("Result ===> " + result);

		return result;
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
