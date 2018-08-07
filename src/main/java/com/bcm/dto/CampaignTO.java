package com.bcm.dto;

public class CampaignTO {

	private long id;

	private String notification;

	private String type;

	private String url;

	public CampaignTO() {
	}

	public CampaignTO(long id, String notification, String url, String type) {
		this.id = id;
		this.notification = notification;
		this.type = type;
		this.url = url;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNotification() {
		return notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
