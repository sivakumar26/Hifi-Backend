package com.hifi.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

public class RedisSingleton {

	private static RedisSingleton redisSingleton;
	Logger logger = LoggerFactory.getLogger(RedisSingleton.class);
	private Jedis jedis;

	private RedisSingleton() {
		try {

			jedis = new Jedis("localhost");
			logger.info("Connection to server sucessfully");
			// check whether server is running or not
			logger.info("Server is running: " + jedis.ping());

		} catch (Exception u) {
			logger.info("Unable to connect to Redis Cache");
		}
	}

	public Jedis getJedis() {
		return this.jedis;
	}

	public static RedisSingleton getInstance() {
		if (redisSingleton == null) {
			redisSingleton = new RedisSingleton();
		}
		return redisSingleton;
	}
}
