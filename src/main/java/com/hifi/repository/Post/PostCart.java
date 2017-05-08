package com.hifi.repository.Post;

import java.util.Collection;

import org.bson.types.ObjectId;

import com.hifi.Exception.PostNotFoundException;
import com.hifi.model.Post;

public interface PostCart {

	boolean addPost(Post post);
	boolean removePost(Post post) throws PostNotFoundException;
	
	Collection getPostCartDetails();
	Post getPostFromPostCart(ObjectId postId) throws PostNotFoundException;
	int postsTotal();
}
