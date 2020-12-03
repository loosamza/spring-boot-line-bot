package com.line.bot.app.linebot.service.impl;

import com.google.gson.Gson;
import com.line.bot.app.linebot.bean.Data;
import com.line.bot.app.linebot.service.interfaces.LineWSService;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LineWSServiceImpl implements LineWSService {

    @Value("${line.access_token}")
    private String access_token;

    private MediaType mediaType = MediaType.parse("application/json; charset=utf-8");


    @Override
    public Response callLineWSReply(Data req) {
        OkHttpClient client = new OkHttpClient();
        Response response;
        Gson g = new Gson();
        try {
            String json = g.toJson(req);
            RequestBody body = RequestBody.create(mediaType, json);
            Request request = new Request.Builder().url("https://api.line.me/v2/bot/message/reply").post(body)
                    .addHeader("content-type", "application/json").addHeader("cache-control", "no-cache")
                    .addHeader("postman-token", "ec35ea6c-381c-12ad-4725-6821a25890ae")
                    .addHeader("Authorization", "Bearer " + access_token).build();


            response = client.newCall(request).execute();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }
}
