package com.home.app.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class SmsEntity {
	@Id
	private String id;
	private String address;
	private String dateTime;
	private String message;
	
	@Override
	public String toString() {
		return "SmsEntity [id=" + id + ", address=" + address + ", dateTime=" + dateTime + ", message=" + message + "]";
	}
	public SmsEntity(String id, String address, String dateTime, String message) {
		this.id = id;
		this.address = address;
		this.dateTime = dateTime;
		this.message = message;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	


}
