package com.hifi.ObjectMapper;

import org.bson.types.ObjectId;

import com.hifi.model.Post;

public class PostMapJson extends ObjectMapper<Post> {

	Post post;
	UserMapJson userMapJson;

	public PostMapJson(Post p) {

		this.post = p;
	}

	@Override
	public Post getObject() {
		// TODO Auto-generated method stub
		userMapJson=new UserMapJson();
		post.setPostId(new ObjectId());
		post.setPostTimeStamp(System.currentTimeMillis());
		post.setUser(userMapJson.getObjectWithId(post.getUserFacebookId()));

		return post;
	}

}
