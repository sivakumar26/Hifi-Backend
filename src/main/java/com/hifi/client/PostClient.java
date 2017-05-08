package com.hifi.client;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.hifi.model.Post;

public class PostClient {

	private Client client;
	
	public PostClient()
	{
		client=ClientBuilder.newClient();
	}
	
	public Post get(String postId)
	{
		WebTarget target=client.target("http://localhost:8080/hifi-services/webapi/");
		
	    Response response=target.path("posts/"+postId).request(MediaType.APPLICATION_JSON).get(Response.class);
	    
	    if(response.getStatus()!=200)
	    {
	    	throw new RuntimeException(response.getStatus()+"there was an error on server");
	    }
	    return response.readEntity(Post.class);
	}
	
	public List<Post> get()
	{
        WebTarget target=client.target("http://localhost:8080/hifi-services/webapi/");
		
	    List<Post> response=target.path("posts").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Post>>() {});
	    
	    return response;
	    
	}

	public Post create(Post post)
	{
		
		WebTarget target=client.target("http://localhost:8080/hifi-services/webapi/");
		
        Response response=target.path("posts/post").request(MediaType.APPLICATION_JSON).
        		post(Entity.entity(post,MediaType.APPLICATION_JSON));
	    
	    if(response.getStatus()!=200)
	    {
	    	throw new RuntimeException(response.getStatus()+"there was an error on server");
	    }
	    
	    return response.readEntity(Post.class);
	}

	public Post update(Post post) 
	{
		
		WebTarget target=client.target("http://localhost:8080/hifi-services/webapi/");
		Response response=target.path("posts/"+post.getPostId()).request(MediaType.APPLICATION_JSON).
        		put(Entity.entity(post,MediaType.APPLICATION_JSON));
	    
	    if(response.getStatus()!=200)
	    {
	    	throw new RuntimeException(response.getStatus()+"there was an error on server");
	    }
		
	    return response.readEntity(Post.class);
		
	}
	
	public void delete(String postId)
	{
		WebTarget target=client.target("http://localhost:8080/hifi-services/webapi/");
		Response response=target.path("posts/"+postId).request(MediaType.APPLICATION_JSON).
        		delete();
		
		if(response.getStatus()!=200)
	    {
	    	throw new RuntimeException(response.getStatus()+"there was an error on server");
	    }
	}
}
