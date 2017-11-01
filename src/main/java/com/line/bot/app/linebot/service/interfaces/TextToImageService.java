package com.line.bot.app.linebot.service.interfaces;

import java.io.IOException;

public interface TextToImageService {
	
	public byte[] textToimage(String key) throws IOException ;
	public void byteToFile(byte[] byteArray) throws IOException;
	
}
