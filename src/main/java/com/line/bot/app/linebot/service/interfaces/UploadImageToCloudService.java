package com.line.bot.app.linebot.service.interfaces;

import java.util.Map;

public interface UploadImageToCloudService {

	public Map uploadImage(byte[] bytes);

	public boolean deleteImage(String public_id);

}
