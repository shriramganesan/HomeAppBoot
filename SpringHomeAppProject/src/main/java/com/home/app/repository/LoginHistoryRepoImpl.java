package com.home.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.home.app.entities.LoginHistory;

public interface LoginHistoryRepoImpl extends MongoRepository<LoginHistory,String>{

}
