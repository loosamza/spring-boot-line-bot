package com.line.bot.app.linebot.service.interfaces;

import com.line.bot.app.linebot.bean.Data;
import okhttp3.Response;

public interface LineWSService {
    Response callLineWSReply(Data req);
}
