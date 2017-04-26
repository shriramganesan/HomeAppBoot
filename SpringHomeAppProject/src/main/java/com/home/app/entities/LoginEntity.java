package com.home.app.entities;

import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

public class LoginEntity extends AbstractAuditEntity{
	
	@Id
	private String id;
	
	@NotEmpty
    @Size(min = 1, max = 50)
	private String email; // 'eddyverbruggen@gmail.com'
	@NotEmpty
	private String userId; // user id
	@NotEmpty
	private String displayName; // 'Eddy Verbruggen'
	private String familyName; // 'Verbruggen'
	@NotEmpty
	private String givenName; // 'Eddy'
	private String imageUrl; // 'http://link-to-my-profilepic.google.com'
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	
}
