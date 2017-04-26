package com.home.app.rest.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class BillsRequest {

	private String id;	
	private String category;
	private SmsRequest sms;
	private String amount;
	private String modeOfPayment;
	private String shopName;
	private String location;
	private String billDateTime; 
	private String description;
	private String billImage;
	private String email;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public SmsRequest getSms() {
		return sms;
	}
	public void setSms(SmsRequest sms) {
		this.sms = sms;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getModeOfPayment() {
		return modeOfPayment;
	}
	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getBillDateTime() {
		return billDateTime;
	}
	public void setBillDateTime(String billDateTime) {
		this.billDateTime = billDateTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBillImage() {
		return billImage;
	}
	public void setBillImage(String billImage) {
		this.billImage = billImage;
	}
	@Override
	public String toString() {
		return "BillsRequest [id=" + id + ", category=" + category + ", sms=" + sms + ", amount=" + amount
				+ ", modeOfPayment=" + modeOfPayment + ", shopName=" + shopName + ", location=" + location
				+ ", billDateTime=" + billDateTime + ", description=" + description + ", billImage=" + billImage + "]";
	}
	public BillsRequest() {
	}
	
	
	
}
