package com.line.bot.app.linebot.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.line.bot.app.linebot.service.interfaces.UploadImageToCloudService;

@Service
public class UploadImageToCloudServiceImpl implements UploadImageToCloudService {

	@Autowired
	@Qualifier("iCloudinary")
	private Cloudinary cloudinary;
	
	@Override
	public Map uploadImage(byte[] bytes) {
		
		String encoded = Base64.getEncoder().encodeToString(bytes);
		StringBuffer upload = new StringBuffer("data:image/png;base64,");
		upload.append(encoded);
		System.out.println("encoded : " + encoded);
		
		Map uploadResult = new HashMap<String, String>();
		try {
			 uploadResult = cloudinary.uploader().upload(upload.toString(), ObjectUtils.emptyMap());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(uploadResult.get("secure_url").toString());
		return uploadResult;
	}

	@Override
	public boolean deleteImage(String public_id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
