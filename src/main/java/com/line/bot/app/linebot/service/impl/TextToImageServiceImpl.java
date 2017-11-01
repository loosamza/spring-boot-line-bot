package com.line.bot.app.linebot.service.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.springframework.stereotype.Service;

import com.line.bot.app.linebot.DemoFonts;
import com.line.bot.app.linebot.service.interfaces.TextToImageService;
@Service
public class TextToImageServiceImpl implements TextToImageService {

	@Override
	public byte[] textToimage(String key) throws IOException {
		
		int height = 400;
		int width = 400;
		BufferedImage bufferedImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
		Graphics2D  graphics = (Graphics2D) bufferedImage.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, height, width);
		graphics.setColor(Color.BLACK);	
		graphics.setFont(DemoFonts.getFont("SanamDeklen_chaya.ttf").deriveFont(30f));
		FontMetrics metrics = graphics.getFontMetrics(DemoFonts.getFont("SanamDeklen_chaya.ttf").deriveFont(30f));
		int x = (height - metrics.stringWidth(key)) / 2;
		int y = ((width - metrics.getHeight()) / 2) + metrics.getAscent();
		graphics.drawString(key, x, y);
		graphics.setColor(Color.CYAN);
		graphics.fillRect(0, 0 ,width ,50 );
		graphics.setColor(Color.CYAN);
		graphics.fillRect(0, 350 ,width ,50 );
		Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("png");
		ImageWriter writer = writers.next();

		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		byte[] imageBytes = null;
		
		try {
			ImageOutputStream imageOut = ImageIO.createImageOutputStream(bytes);
			writer.setOutput(imageOut);
			writer.write(bufferedImage);
			imageOut.close();
			imageBytes = bytes.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		ImageIO.write(bufferedImage, "png", ios);
		System.out.println("Image Created a" + imageBytes.length);
		return imageBytes;
	}

	@Override
	public void byteToFile(byte[] byteArray) throws IOException {
		File outputfile = new File("C:\\Users\\piyapat.p\\Desktop\\image.png");
		BufferedImage img = ImageIO.read(new ByteArrayInputStream(byteArray));
		ImageIO.write(img, "png", outputfile);
		
	}
	
	

}
