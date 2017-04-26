package com.home.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.home.app.entities.ModeEntity;

public interface ModeRepoImpl extends MongoRepository<ModeEntity,String>{

}
