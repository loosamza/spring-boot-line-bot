package com.line.bot.app.linebot.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.line.bot.app.linebot.bean.Data;
import com.line.bot.app.linebot.bean.Events;
import com.line.bot.app.linebot.bean.Message;
import com.line.bot.app.linebot.bean.WebHookBean;
import com.line.bot.app.linebot.service.interfaces.LineWSService;
import com.line.bot.app.linebot.service.interfaces.TextToImageService;
import com.line.bot.app.linebot.service.interfaces.UploadImageToCloudService;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

@RestController
public class RootController {

    @Autowired
    private TextToImageService textToImageService;
    @Autowired
    private UploadImageToCloudService uploadImageToCloudService;
    @Autowired
    private LineWSService lineWSService;

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
    void bot(@org.springframework.web.bind.annotation.RequestBody WebHookBean events) throws IOException {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String txt = "";
        String replyToken = "";
        String result = "";
        List<Message> replyMsgList = new ArrayList<Message>();
        Message msg1 = new Message();
        Message msg = new Message();
        Data data = new Data();
        System.out.println("Request : " + gson.toJson(events));
        List<String> packageList = Arrays.asList("11537:52002738", "11537:51626494");


        for (Events ev : events.getEvents()) {
            if (ev.getMessage().getType().equals("text")) {
                String rMsg = ev.getMessage().getText();
                System.out.println("Request : " + Arrays.asList("-image", "-รูป").contains(rMsg));
                if (Arrays.asList("-image", "-รูป").contains(rMsg)) {
                    String splitMsg[] = rMsg.split(" ");
                    if (splitMsg.length < 2) return;

                    msg1.setText("โย่วววววว นี่คือการตอบกลับอัตโนมือ xD อยากได้รูปหราาาา จัดปายยย");
                    msg1.setType("text");
                    replyMsgList.add(msg1);
                    msg.setType("image");
                    rMsg = rMsg.replace("-รูป", "").replaceAll("(?i)-image", "");
                    Map resultMap;
                    if (rMsg.trim().length() == 0) {
                        byte[] bytes = textToImageService.textToimage("ไหนละข้อความ ??");
                        resultMap = uploadImageToCloudService.uploadImage(bytes);
                    } else {
                        byte[] bytes = textToImageService
                                .textToimage(rMsg.substring(0, (rMsg.length() < 28) ? rMsg.length() : 28));
                        resultMap = uploadImageToCloudService.uploadImage(bytes);
                    }

                    String imgURL = (String) resultMap.get("secure_url");
                    msg.setOriginalContentUrl(imgURL);
                    msg.setPreviewImageUrl(imgURL);
                    replyMsgList.add(msg);
                    data.setMessages(replyMsgList);
                    data.setReplyToken(ev.getReplyToken());
                    Response response = lineWSService.callLineWSReply(data);
                    return;

                }
                System.out.println("Request : " + Arrays.asList("!b Hello", "!b สวัสดี").contains(rMsg));

                if (Arrays.asList("!b Hello", "!b สวัสดี").contains(rMsg)) {
                    int rand = new Random().nextInt(packageList.size());
                    msg1.setType("sticker");
                    msg1.setPackageId(Long.parseLong(packageList.get(rand).split(":")[0]));
                    msg1.setStickerId(Long.parseLong(packageList.get(rand).split(":")[1]));
                    replyMsgList.add(msg1);
                    data.setMessages(replyMsgList);
                    data.setReplyToken(ev.getReplyToken());
                    lineWSService.callLineWSReply(data);
                    return;
                }
            }
        }


    }
}
