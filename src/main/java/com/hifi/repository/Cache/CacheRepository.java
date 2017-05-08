package com.hifi.repository.Cache;

import org.bson.types.ObjectId;

public interface CacheRepository {
	
	void updateCache(String postId,String userFacebookId);
	

}
