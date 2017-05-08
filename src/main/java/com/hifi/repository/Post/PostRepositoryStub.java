	package com.hifi.repository.Post;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateResults;

import com.hifi.database.MongoDBSingleton;
import com.hifi.firebase.crud.CreatorOperations;
import com.hifi.firebase.crud.PostOperations;
import com.hifi.model.Post;
import com.hifi.model.User;

public class PostRepositoryStub implements PostRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hifi.repository.PostRepository#findAllPosts()
	 */
	public static List<Post> posts = new ArrayList<Post>();
	private MongoDBSingleton dbInstance;
	private Datastore datastore;
	private PostDAO postDao;
	private PostOperations postOps;
	private CreatorOperations creatorOps;

	@Override
	public List<Post> findAllPosts() {

		Post post1 = new Post();

		// post1.setPostId(1);
		post1.setPostTimeStamp(1213321);
		post1.setPostTitle("Grocercies");
		post1.setPostDescription("Get 2 Gallons of Milk from the store and deliver");
		// post1.setUserId(123);

		User user = new User();
		user.setName("Pratik");
		// user.setUserId("5678");

		post1.setUser(user);
		posts.add(post1);
		return posts;
	}

	@Override
	public Post findPost(ObjectId postId) {

		dbInstance = MongoDBSingleton.getInstance();
		datastore = dbInstance.getDataStore();
		Query<Post> query = datastore.createQuery(Post.class).field("postId").equal(postId);
		
		return query.get();
	}

	@Override
	public void create(Post post) {
		// Store the post to Posts Collection in Database

		dbInstance = MongoDBSingleton.getInstance();

		datastore = dbInstance.getDataStore();
		// this.postDao = new PostDAOImpl(Post.class, datastore);
		// postDao.save(post);
		datastore.save(post);

		/* Saving in Firebase */
		postOps = new PostOperations(post);
		creatorOps = new CreatorOperations();
		try {
			postOps.createFirebasePost();
			creatorOps.buildCreator(post.getUser().getFacebookId(), post.getPostId().toString());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean update(String userPostId,String updateStatus) {
		// search the database if there is a post with given post id

		// Select * from posts where id=?
		// if rs size==0
		// insert the post in Database
		// else update the database
		
		System.out.println("Printing postId"+userPostId);

		
		dbInstance = MongoDBSingleton.getInstance();
		datastore = dbInstance.getDataStore();
		Query<Post> query = datastore.createQuery(Post.class).field("_id").equal(new ObjectId(userPostId));
		final UpdateResults results = datastore.update(query,
				datastore.createUpdateOperations(Post.class).set("postStatus", updateStatus));
		System.out.println("Count"+results.getUpdatedCount());

		if (!((results.getUpdatedCount()) >= 1))
		{
			System.out.println("Its false----------------");
			return false;
		}
			

		return true;

	}

	@Override
	public boolean update(Post post) {

		// Make changes to existing task

		// Used save since exisiting postId is sent morphia takes care of
		// updating

		dbInstance = MongoDBSingleton.getInstance();
		datastore = dbInstance.getDataStore();

		if(datastore.save(post) != null)
		{
			return true;
		}
		return false;

		

	}

	@Override
	public boolean delete(ObjectId postId) {
		// delete from Posts where id=?

		dbInstance = MongoDBSingleton.getInstance();
		datastore = dbInstance.getDataStore();
		Query<Post> query = datastore.createQuery(Post.class).field("postId").equal(postId);
		
		if(datastore.delete(query)!=null)
		{
			return true;
		}
		return false;

	}
}
