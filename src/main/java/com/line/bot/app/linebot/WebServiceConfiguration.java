package com.line.bot.app.linebot;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class WebServiceConfiguration {
	
	@Value("${cloudinary.name}")
	private String CLOUD_NAME;
	@Value("${cloudinary.api.key}")
	private String API_KEY;
	@Value("${cloudinary.api.secret}")
	private String API_SECRET;
	
	@Bean
	@Qualifier("iCloudinary")
	@Scope("prototype")
	public Cloudinary cloudinary(){
		Cloudinary cloudinary = new Cloudinary(
				ObjectUtils.asMap("cloud_name", CLOUD_NAME, "api_key", API_KEY, "api_secret", API_SECRET));
		return cloudinary;
	}
	
}
