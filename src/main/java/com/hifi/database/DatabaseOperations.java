package com.hifi.database;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

import com.hifi.model.Post;
import com.hifi.repository.Post.PostDAO;
import com.hifi.repository.Post.PostDAOImpl;
import com.hifi.repository.User.UserDAO;
import com.mongodb.DBCollection;

public class DatabaseOperations {
	
	private static MongoDBSingleton dbInstance;
	private static Datastore datastore;
	private static UserDAO userDao;
	private static PostDAO postDao;

	public static Post getFriendsPost(ObjectId postId) {

		dbInstance = MongoDBSingleton.getInstance();
		datastore = dbInstance.getDataStore();

		postDao = new PostDAOImpl(Post.class, datastore);

		DBCollection collection = datastore.getCollection(Post.class);
        Post results = postDao.getPost(postId);

		return results;

	}

}
