package com.hifi.repository.User;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

import com.hifi.model.User;

public class UserDAOImpl extends BasicDAO<User,ObjectId> implements UserDAO {

	public UserDAOImpl(Class<User> entityClass, Datastore ds) {
		super(entityClass, ds);
		// TODO Auto-generated constructor stub
	}

	@Override
	public User getUser() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
