package com.home.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.home.app.entities.BillsEntity;
import com.home.app.entities.LoginEntity;

public interface BillsRepoImpl extends MongoRepository<BillsEntity,String>{
	public List<BillsEntity> findByLoginEntity(LoginEntity loginEntity);

	public BillsEntity findById(String id);
}
