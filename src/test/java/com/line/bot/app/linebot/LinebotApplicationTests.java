package com.line.bot.app.linebot;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.line.bot.app.linebot.service.interfaces.TextToImageService;
import com.line.bot.app.linebot.service.interfaces.UploadImageToCloudService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LinebotApplicationTests {
	
	@Autowired
	private TextToImageService textToImageService;
	@Autowired
	private UploadImageToCloudService uploadImageToCloudService;
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void testTextToImageService() throws IOException{
//		uploadImageToCloudService.uploadImage(textToImageService.textToimage());
//		textToImageService.byteToFile(textToImageService.textToimage("สวัสดีครับ"));
		
	}

}
