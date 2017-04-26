package com.home.app.rest.request;

import org.hibernate.validator.constraints.NotEmpty;

public class LoginRequest {
	//@NotEmpty
	private String email; 
	private String userId; 
	private String displayName; 
	private String familyName; 
	private String givenName;
	private String imageUrl; 
	//@NotEmpty
	private String idToken;        
	private String serverAuthCode;	
	//@NotEmpty
	private String deviceId;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getFamilyName() {
		return familyName;
	}
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	public String getGivenName() {
		return givenName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getIdToken() {
		return idToken;
	}
	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}
	public String getServerAuthCode() {
		return serverAuthCode;
	}
	public void setServerAuthCode(String serverAuthCode) {
		this.serverAuthCode = serverAuthCode;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	
}
