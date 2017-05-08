package com.hifi;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hifi.ObjectMapper.PostMapJson;
import com.hifi.model.Post;
import com.hifi.model.User;
import com.hifi.model.VerifyAuthentication;
import com.hifi.repository.Post.PostRepository;
import com.hifi.repository.Post.PostRepositoryStub;

@Path("/posts") /* http://localhost:8080/hifi-services/webapi/posts */
public class PostResource {

	private PostRepository postRepository = new PostRepositoryStub();
	static Logger logger = Logger.getLogger(PostResource.class);

	@DELETE
	@Path("{postId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })

	public Response delete(@HeaderParam("token") String token, @PathParam("postId") String postId) {
		Response response = VerifyAuthentication.verify(token);
		if (response.getStatus() == 200) {
			System.out.println("Deleting post " + postId);

			if (postRepository.delete(new ObjectId(postId))) {
				return Response.ok().build();

			}
			response = Response.serverError().build();
		}

		return response;

	}

	@PUT
	@Path("{postId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })

	public Response update(@HeaderParam("token") String token, Post post) {
		Response response = VerifyAuthentication.verify(token);

		if (response.getStatus() == 200) {
			if (postRepository.update(post)) {
				return Response.ok().entity(post).build();
			}
			response = Response.serverError().build();
		}
		return response;

	}

	@POST
	@Path("post")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response createPost(@HeaderParam("token") String token, Post post) throws JsonProcessingException {
		Response response = VerifyAuthentication.verify(token);

		if (response.getStatus() == 200) {
			ObjectMapper mapper = new ObjectMapper();

			PostMapJson mapjson = new PostMapJson(post);
			Post p = mapjson.getObject();
			postRepository.create(p);
			System.out.println("Printing now return object--------" + p.getPostId().toString());
			response = Response.ok().entity(mapper.writeValueAsString(p)).build();

		}

		return response;
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })

	public List<Post> getAllPosts() {
		return postRepository.findAllPosts();
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("{postId}") /*
						 * http://localhost:8080/hifi-services/webapi/posts/1234
						 */
	public Response getPost(@PathParam("postId") ObjectId postId) {
		if (postId == null || postId.toString().length() < 4) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		Post post = postRepository.findPost(postId);

		if (post == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		return Response.ok().entity(post).build();
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("{postId}/user") /*
							 * http://localhost:8080/hifi-services/webapi/posts/
							 * 1234/user
							 */
	public User getPostUser(@PathParam("postId") ObjectId postId) {
		return postRepository.findPost(postId).getUser();
	}

}
