package com.hifi.repository.Post;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;

import com.hifi.Exception.PostNotFoundException;
import com.hifi.model.Post;

public class PostCartImpl implements PostCart {

	private ObjectId userId;
	private Map<ObjectId, Post> postsTaskMap;

	public PostCartImpl(ObjectId userId) {
		this.userId = userId;
		postsTaskMap = new HashMap<ObjectId, Post>();
	}

	@Override
	public boolean addPost(Post post) {

		ObjectId postId = post.getPostId();
		if (!postsTaskMap.containsKey(postId)) {
			postsTaskMap.put(postId, post);
			return true;
		}
		return false;
	}

	@Override
	public boolean removePost(Post post) throws PostNotFoundException {
		ObjectId postId = post.getPostId();
		if (postsTaskMap.containsKey(postId)) {
			postsTaskMap.remove(postId);
			return true;
		} else
			throw new PostNotFoundException("Post Not Present in the Cart");

	}

	@Override
	public Collection getPostCartDetails() {

		return postsTaskMap.values();
	}

	@Override
	public Post getPostFromPostCart(ObjectId postId) throws PostNotFoundException {
		if (postsTaskMap.containsKey(postId)) {
			Post post = postsTaskMap.get(postId);
			return post;
		} else
			throw new PostNotFoundException("Post Not Present in the Cart");

	}

	@Override
	public int postsTotal() {

		return postsTaskMap.size();
	}

}
