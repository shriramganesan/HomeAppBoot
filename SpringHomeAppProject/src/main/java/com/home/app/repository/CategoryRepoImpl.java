package com.home.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.home.app.entities.CategoryEntity;

@Repository
public interface CategoryRepoImpl extends MongoRepository<CategoryEntity,String>{

	public CategoryEntity findByCategoryName(String categoryName);
	public Page<CategoryEntity> findAll(Pageable pageable);
}
