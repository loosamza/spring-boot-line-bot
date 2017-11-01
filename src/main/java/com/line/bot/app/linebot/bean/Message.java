package com.line.bot.app.linebot.bean;

public class Message {
	private String id;
	private String type;
	private String text;
	private String originalContentUrl;
	private String previewImageUrl;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getOriginalContentUrl() {
		return originalContentUrl;
	}

	public void setOriginalContentUrl(String originalContentUrl) {
		this.originalContentUrl = originalContentUrl;
	}

	public String getPreviewImageUrl() {
		return previewImageUrl;
	}

	public void setPreviewImageUrl(String previewImageUrl) {
		this.previewImageUrl = previewImageUrl;
	}

}
