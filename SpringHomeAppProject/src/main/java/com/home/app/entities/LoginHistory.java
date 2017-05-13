package com.home.app.entities;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

public class LoginHistory extends AbstractAuditEntity{

	@Id
	private String id;	
	private LoginEntity loginEntity;
	private String idToken;        
	private String serverAuthCode;	
	@NotNull
	private Date lastLoginDate;
	private Date loggedOffDate;
	private String deviceId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public LoginEntity getLoginEntity() {
		return loginEntity;
	}
	public void setLoginEntity(LoginEntity loginEntity) {
		this.loginEntity = loginEntity;
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
	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public Date getLoggedOffDate() {
		return loggedOffDate;
	}
	public void setLoggedOffDate(Date loggedOffDate) {
		this.loggedOffDate = loggedOffDate;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	
}
