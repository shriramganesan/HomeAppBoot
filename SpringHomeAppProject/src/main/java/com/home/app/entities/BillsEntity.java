package com.home.app.entities;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class BillsEntity extends AbstractAuditEntity {

	@Id
	private String id;	
	private CategoryEntity categoryId;
	private SmsEntity smsId;
	private ItemsEntity itemsId;
	private Double amount;
	private ModeEntity modeOfPayment;
	private String shopName;
	private String location;
	private Date billDate; 
	private LoginEntity loginEntity;
	private String description;
	private String billImage;
	
	public LoginEntity getLoginEntity() {
		return loginEntity;
	}
	public void setLoginEntity(LoginEntity loginEntity) {
		this.loginEntity = loginEntity;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public CategoryEntity getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(CategoryEntity categoryId) {
		this.categoryId = categoryId;
	}
	public SmsEntity getSmsId() {
		return smsId;
	}
	public void setSmsId(SmsEntity smsId) {
		this.smsId = smsId;
	}
	public ItemsEntity getItemsId() {
		return itemsId;
	}
	public void setItemsId(ItemsEntity itemsId) {
		this.itemsId = itemsId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public ModeEntity getModeOfPayment() {
		return modeOfPayment;
	}
	public void setModeOfPayment(ModeEntity modeOfPayment) {
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
	public Date getBillDate() {
		return billDate;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
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
	
	
	
}
