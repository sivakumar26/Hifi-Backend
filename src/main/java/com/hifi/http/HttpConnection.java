package com.hifi.http;

public interface HttpConnection {

	int sendGet(String url) throws Exception;
	int sendPost(String url) throws Exception;
	int sendPost(String url, String postData) throws Exception;
	int sendPut(String url, String postBody) throws Exception;
}
