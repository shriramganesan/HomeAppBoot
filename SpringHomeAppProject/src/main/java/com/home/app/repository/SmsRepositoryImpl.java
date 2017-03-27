package com.home.app.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.home.app.entities.SmsEntity;
import com.mongodb.WriteResult;

@org.springframework.stereotype.Repository
public class SmsRepositoryImpl implements Repository<SmsEntity>{

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public List<SmsEntity> getAllObjects() {
		return mongoTemplate.findAll(SmsEntity.class);
	}

	@Override
	public void saveObject(SmsEntity object) {
		mongoTemplate.insert(object);
		
	}

	@Override
	public SmsEntity getObject(String id) {
		return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)),
				SmsEntity.class);
	}

	@Override
	public WriteResult updateObject(String id, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteObject(String id) {
		mongoTemplate
		.remove(new Query(Criteria.where("id").is(id)), SmsEntity	.class);
		
	}

	@Override
	public void createCollection() {
		if (!mongoTemplate.collectionExists(SmsEntity.class)) {
			mongoTemplate.createCollection(SmsEntity.class);
		}
		
	}

	@Override
	public void dropCollection() {
		if (mongoTemplate.collectionExists(SmsEntity.class)) {
			mongoTemplate.dropCollection(SmsEntity.class);
		}
		
	}

}
