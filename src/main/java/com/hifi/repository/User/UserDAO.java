package com.hifi.repository.User;

import org.bson.types.ObjectId;
import org.mongodb.morphia.dao.DAO;

import com.hifi.model.User;

public interface UserDAO extends DAO<User,ObjectId> {

	public User getUser();
}
