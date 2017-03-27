package com.home.app.repository;

import java.util.List;

import com.mongodb.WriteResult;

public interface Repository<T> {

	public List<T> getAllObjects();

	public void saveObject(T object);

	public T getObject(String id);

	public WriteResult updateObject(String id, String name);

	public void deleteObject(String id);

	public void createCollection();

	public void dropCollection();
}
