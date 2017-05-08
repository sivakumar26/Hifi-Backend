package com.hifi.client;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import com.hifi.database.MongoDBSingleton;

import com.hifi.model.Post;
import com.hifi.model.User;
import com.hifi.util.Constants;
import com.mongodb.MongoClient;

public class PostClientTest {

	MongoDBSingleton mSingleton;
	Datastore datastore;

	@Test
	public void testDelete() {
		PostClient postClient = new PostClient();
		postClient.delete("45672");
	}

	@Test
	public void testPut() {
		PostClient postClient = new PostClient();
		Post post = new Post();
		post.setPostDescription("Get Tomatoes and Onions 2lb each ");
		post.setPostId(new ObjectId());
		post.setPostTimeStamp(System.currentTimeMillis());
		post.setPostTitle("Grocery");

		User user = new User();
		user.setName("Siva Kumar");
		user.setUserId(new ObjectId());
		post.setUser(user);

		post = postClient.update(post);
		assertNotNull(post);

	}

	@Test
	public void testCreate() {
		PostClient postClient = new PostClient();

		Post post = new Post();
		post.setPostDescription("Get Juices and Cheese");
		post.setPostId(new ObjectId());
		post.setPostTimeStamp(System.currentTimeMillis());
		post.setPostTitle("Grocery");

		mSingleton = MongoDBSingleton.getInstance();
		datastore = mSingleton.getDataStore();

		Query<User> userQuery = datastore.createQuery(User.class);
		userQuery.field("facebookId").equal("nikhil123@fb.com");

		User user = userQuery.get();

		post.setUser(user);

		post = postClient.create(post);
		assertNotNull(post);

	}

	@Test
	public void testGet() {
		PostClient postClient = new PostClient();

		Post post = postClient.get("1234");

		assertNotNull(post);
	}

	@Test
	public void testGetList() {
		PostClient postClient = new PostClient();

		List<Post> posts = postClient.get();

		assertNotNull(posts);

	}

	@Test(expected = RuntimeException.class)

	public void testGetWithBadRequest() {
		PostClient postClient = new PostClient();
		postClient.get("12");
	}

}
