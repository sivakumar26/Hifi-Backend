package com.hifi.repository.User;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

import com.hifi.database.MongoDBSingleton;
import com.hifi.firebase.crud.UserOperations;
import com.hifi.model.User;

public class UserRepositoryStub implements UserRepository {

	public static List<User> posts = new ArrayList<User>();
	private MongoDBSingleton dbInstance;
	private Datastore datastore;
	private UserDAO userDao;
	private UserOperations userOps;

	@Override
	public User findUser(ObjectId userId) {
		return null;
	}

	@Override
	public boolean createUser(User user) {
		dbInstance = MongoDBSingleton.getInstance();
		datastore = dbInstance.getDataStore();
		this.userDao = new UserDAOImpl(User.class, datastore);
		userDao.save(user);

		/* Saving in firebase */
		userOps = new UserOperations(user);

		try {
			userOps.createUserProfile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean updateUser(User user) {
		return true;
	}

	@Override
	public void deleteUser(User user) {

	}

	@Override
	public boolean sendMessage(ObjectId userId, String msg) {
		return true;
	}

}
