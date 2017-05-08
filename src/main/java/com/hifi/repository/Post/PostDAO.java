package com.hifi.repository.Post;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.dao.DAO;

import com.hifi.model.Post;

public interface PostDAO extends DAO<Post, ObjectId> {

	//List<Post> getPost();

	List<Post> getPosts(String searchQuery);
	
	Post getPost(ObjectId postId);

}
