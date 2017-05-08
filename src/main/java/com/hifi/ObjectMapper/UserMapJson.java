package com.hifi.ObjectMapper;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.hifi.database.MongoDBSingleton;
import com.hifi.model.User;

public class UserMapJson extends ObjectMapper<User> {
	
	User user;
	MongoDBSingleton mSingleton;
	Datastore datastore;

	public UserMapJson(User u)
	{
		this.user=u;
	}
	
	public UserMapJson() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public User getObject() {
		// TODO Auto-generated method stub
		
		user.setUserId(new ObjectId());
		user.getLocation().setId(new ObjectId());
		
		return user;
	}
	
	public User getObjectWithId(String facebookId)
	{
		mSingleton = MongoDBSingleton.getInstance();
		datastore = mSingleton.getDataStore();

		Query<User> userQuery = datastore.createQuery(User.class);
		userQuery.field("facebookId").equal(facebookId);

		User user = userQuery.get();
		return user;
	}

}
