package com.hifi.repository.Post;

import java.util.List;

import org.mongodb.morphia.Datastore;

import com.hifi.database.MongoDBSingleton;
import com.hifi.model.Post;
import com.hifi.repository.User.UserDAO;
import com.mongodb.DBCollection;

public class FullTextSearch {

	private MongoDBSingleton dbInstance;
	private Datastore datastore;
	private UserDAO userDao;
	private PostDAO postDao;

	public List<Post> fullTextSearch(String searchQuery) {

		dbInstance = MongoDBSingleton.getInstance();
		datastore = dbInstance.getDataStore();

		this.postDao = new PostDAOImpl(Post.class, datastore);

		DBCollection collection = datastore.getCollection(Post.class);

		List<Post> results = postDao.getPosts(searchQuery);

		return results;

	}

}
