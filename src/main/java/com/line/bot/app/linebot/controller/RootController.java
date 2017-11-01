package com.line.bot.app.linebot.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.line.bot.app.linebot.bean.Data;
import com.line.bot.app.linebot.bean.Events;
import com.line.bot.app.linebot.bean.Message;
import com.line.bot.app.linebot.bean.WebHookBean;
import com.line.bot.app.linebot.service.interfaces.TextToImageService;
import com.line.bot.app.linebot.service.interfaces.UploadImageToCloudService;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@RestController
public class RootController {

	@Value("${line.access_token}")
	private String access_token;

	@Autowired
	private TextToImageService textToImageService;
	@Autowired
	private UploadImageToCloudService uploadImageToCloudService;

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
	String bot(@org.springframework.web.bind.annotation.RequestBody WebHookBean events) throws IOException {
		String txt = "";
		String replyToken = "";
		String result = "";
		List<Message> replyMsgList = new ArrayList<Message>();
		Message msg1 = new Message();
		Message msg = new Message();
		Data data = new Data();
		for (Events ev : events.getEvents()) {
			if (ev.getMessage().getType().equals("text")) {
				String rMsg = ev.getMessage().getText();
				if (rMsg.contains(" ")) {
					String splitMsg[] = rMsg.split(" ");

					if (splitMsg[0].equalsIgnoreCase("-image") || splitMsg[0].equalsIgnoreCase("-รูป")) {
						msg1.setText("โย่วววววว นี่คือการตอบกลับอัตโนมือ xD อยากได้รูปหราาาา จัดปายยย");
						msg1.setType("text");
						replyMsgList.add(msg1);
						msg.setType("image");
						rMsg = rMsg.replace("-รูป", "").replace("-image", "");
						Map resultMap = new HashMap<String, String>();
						if (rMsg.trim().length() == 0) {
							byte[] bytes = textToImageService.textToimage("ไหนละข้อความ ??");
							resultMap = uploadImageToCloudService.uploadImage(bytes);
						} else {
							byte[] bytes = textToImageService
									.textToimage(rMsg.substring(0, (rMsg.length() < 10) ? rMsg.length() : 10));
							resultMap = uploadImageToCloudService.uploadImage(bytes);
						}

						String imgURL = (String) resultMap.get("secure_url");
						msg.setOriginalContentUrl(imgURL);
						msg.setPreviewImageUrl(imgURL);
						replyMsgList.add(msg);
						data.setMessages(replyMsgList);
						data.setReplyToken(ev.getReplyToken());
					} else {
						msg1.setText("โย่วววววว นี่คือการตอบกลับอัตโนมือ xD");
						msg1.setType("text");
						replyMsgList.add(msg1);
						msg.setText(ev.getMessage().getText());
						msg.setType("text");
						replyMsgList.add(msg);
						data.setMessages(replyMsgList);
						data.setReplyToken(ev.getReplyToken());
					}
				} else {
					msg1.setText("โย่วววววว นี่คือการตอบกลับอัตโนมือ xD");
					msg1.setType("text");
					replyMsgList.add(msg1);
					msg.setText(ev.getMessage().getText());
					msg.setType("text");
					replyMsgList.add(msg);
					data.setMessages(replyMsgList);
					data.setReplyToken(ev.getReplyToken());
				}

				OkHttpClient client = new OkHttpClient();
				MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
				Gson g = new Gson();
				String json = g.toJson(data);
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
