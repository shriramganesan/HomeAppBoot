package com.home.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.home.app.entities.LoginEntity;
import com.home.app.entities.LoginHistory;

public interface LoginHistoryRepoImpl extends MongoRepository<LoginHistory,String>{
	public List<LoginHistory> findByLoginEntity(LoginEntity loginEntity);
}
