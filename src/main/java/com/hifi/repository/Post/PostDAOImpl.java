package com.hifi.repository.Post;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

import com.hifi.model.Post;
import com.hifi.model.User;

public class PostDAOImpl extends BasicDAO<Post, ObjectId> implements PostDAO {

	public PostDAOImpl(Class<Post> entityClass, Datastore ds) {
		super(entityClass, ds);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Post> getPosts(String searchQuery) {
		// TODO Auto-generated method stub
		List<Post> results = createQuery().search(searchQuery).order("-postTimeStamp").asList();

		// Post p=(Post) createQuery().field("postDescription").equal("Get
		// Yogurt and Cheese from Safeway");

		return results;
	}
	@Override
	public Post getPost(ObjectId postId){
		
		createQuery().field("postId").equal(postId);
		
		return createQuery().get();
	}

}
