package com.hifi;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hifi.model.Post;
import com.hifi.repository.Post.FullTextSearch;

@Path("/searchResults")
public class FullTextSearchResource {

	@GET
	@Path("{searchQuery}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })

	public Response getSearchResults(@PathParam("searchQuery") String searchQuery) throws JsonProcessingException {

		FullTextSearch fts = new FullTextSearch();
		List<Post> results = fts.fullTextSearch(searchQuery);
		ObjectMapper objectMapper = new ObjectMapper();
		String arrayToJson = objectMapper.writeValueAsString(results);
		return Response.ok().entity(arrayToJson).build();

	}

}
