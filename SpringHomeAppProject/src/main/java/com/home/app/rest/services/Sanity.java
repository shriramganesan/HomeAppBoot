package com.home.app.rest.services;

import org.springframework.stereotype.Component;

@Component
public class Sanity {
	
	private boolean success;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
