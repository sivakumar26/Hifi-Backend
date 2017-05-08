package com.hifi;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.hifi.firebase.crud.AssigneeOperations;
import com.hifi.model.VerifyAuthentication;
import com.hifi.repository.Cache.CacheOperations;
import com.hifi.repository.Cache.CacheRepository;
import com.hifi.repository.Post.PostRepository;
import com.hifi.repository.Post.PostRepositoryStub;

@Path("/taskCompletion")
public class TaskCompleteResource {

	private PostRepository postRepository = new PostRepositoryStub();
	private CacheRepository cacheOp;
	Response response;
	AssigneeOperations assigneeOps;

	@POST
	@Path("{postId}/{userFacebookId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })

	public Response taskCompleted(@HeaderParam("token") String token, @PathParam("postId") String postId,
			@PathParam("userFacebookId") String userFacebookId) throws FileNotFoundException, URISyntaxException {

		// Authenticate token first

		response = VerifyAuthentication.verify(token);

		if (response.getStatus() == 200) {
			// System.out.println("Mapping post with post id" + postId + "to
			// user " + userFacebookId);

			// Call to show the post mapped to user and mark this post as
			// invalid
			// for all other users

			// We need to set the active flag in post collection to false

			if (postRepository.update(postId,"COMPLETED")) {

				 //Mark the post as completed in database

				System.out.println("Updated Mongo Sucessfully");
				cacheOp = new CacheOperations();
				cacheOp.updateCache(postId, userFacebookId);
				// Map in firebase in assignee tree
				assigneeOps = new AssigneeOperations();
				assigneeOps.removeAssginee(userFacebookId, postId);
				response = Response.ok().build();

			}

			response = Response.ok().build();
		}

		return response;

	}

}
