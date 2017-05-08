package com.hifi.repository.User;

import org.bson.types.ObjectId;

import com.hifi.model.User;

public interface UserRepository {

	User findUser(ObjectId userId);
	boolean createUser(User user) ;
	boolean updateUser(User user);
	void deleteUser(User user);
	boolean sendMessage(ObjectId userId,String msg);
	
	
	
}
