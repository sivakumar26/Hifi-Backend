package com.hifi.repository.Post;

import java.util.List;

import org.bson.types.ObjectId;

import com.hifi.model.Post;

public interface PostRepository {

	List<Post> findAllPosts();

	Post findPost(ObjectId postId);

	void create(Post post);

	boolean update(Post post);

	boolean delete(ObjectId postId);

	boolean update(String userPostId, String updateStatus);

}