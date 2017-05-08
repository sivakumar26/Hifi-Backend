package com.hifi.repository.Cache;

import java.util.Iterator;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hifi.database.RedisSingleton;
import com.hifi.neo4j.Neo4j;
import com.hifi.neo4j.Neo4jOperations;

import redis.clients.jedis.Jedis;

public class CacheOperations implements CacheRepository {

	private RedisSingleton redisSingleton;
	private Jedis jedis;
	ObjectMapper mapper = new ObjectMapper();
	Neo4j neo4j = new Neo4jOperations();

	@Override
	public void updateCache(String postId, String userFacebookId) {

		redisSingleton = RedisSingleton.getInstance();
		jedis = redisSingleton.getJedis();

		Set<String> users = jedis.keys("*");

		Iterator<String> it = users.iterator();
		while (it.hasNext()) {
			jedis.lrem(it.next(),0, postId);
		}
		

	}

}
