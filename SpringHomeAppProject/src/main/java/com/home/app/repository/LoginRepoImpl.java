package com.home.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.home.app.entities.LoginEntity;

public interface LoginRepoImpl  extends MongoRepository<LoginEntity,String>{
	public LoginEntity findByEmail(String email);
}
