package com.home.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.home.app.entities.BillsEntity;

public interface BillsRepoImpl extends MongoRepository<BillsEntity,String>{

}
