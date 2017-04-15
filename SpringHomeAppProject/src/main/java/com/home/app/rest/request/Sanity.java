package com.home.app.rest.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Sanity {
	
	private boolean success;

	@Autowired
	public Sanity(@Value("false") Boolean b) {
		this.success=b;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
