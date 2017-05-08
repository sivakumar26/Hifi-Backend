package com.hifi.database;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hifi.model.Post;
import com.hifi.model.User;
import com.hifi.util.Constants;
import com.mongodb.MongoClient;

public class MongoDBSingleton {

	private static MongoDBSingleton mDbSingleton;

	Logger logger = LoggerFactory.getLogger(MongoDBSingleton.class);

	private Morphia morphia;

	private Datastore datastore;

	private MongoDBSingleton() {

		try {

			morphia = new Morphia();

			morphia.mapPackage("com.hifi.model");

			// create the Datastore connecting to the default port on the local
			// host
			datastore = morphia.createDatastore(new MongoClient(), Constants.MONGO_DB);
			
			
			datastore.ensureIndexes();
		} catch (Exception u) {
			logger.info("Unable to connect to MongoDB");
		}

	};

	public Datastore getDataStore() {
		return this.datastore;
	}

	public static MongoDBSingleton getInstance() {
		if (mDbSingleton == null) {
			mDbSingleton = new MongoDBSingleton();
		}
		return mDbSingleton;
	}
}