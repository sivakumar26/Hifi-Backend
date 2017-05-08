package com.hifi.http;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

public class HttpConnectionImpl implements HttpConnection {

	private final String USER_AGENT = "Mozilla/5.0";

	public HttpConnectionImpl() {

	}

	@Override
	public int sendGet(String url) throws Exception {

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();

		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		/*
		 * BufferedReader in = new BufferedReader( new
		 * InputStreamReader(con.getInputStream())); String inputLine;
		 * StringBuffer response = new StringBuffer();
		 * 
		 * while ((inputLine = in.readLine()) != null) {
		 * response.append(inputLine); } in.close();
		 */

		// print result

		return responseCode;

	}

	// Send post request
	@Override
	public int sendPost(String url, String postBody) throws Exception {

		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		byte[] postData = postBody.getBytes(StandardCharsets.UTF_8);
		// add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		con.setRequestProperty("Authorization", "key=AIzaSyAWmUcHDpzQZ4vT4bTqcwTc9qqMN8Fy4xs");
		con.setDoOutput(true);
		try {
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.write(postData);
			wr.flush();
			wr.close();
		} catch (Exception e) {

		}

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);

		System.out.println("Response Code : " + responseCode);

		// print result
		return responseCode;

	}

	@Override
	public int sendPost(String url) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int sendPut(String url, String postBody) throws Exception {

		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		byte[] postData = postBody.getBytes(StandardCharsets.UTF_8);
		// add reuqest header
		con.setRequestMethod("PUT");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		con.setRequestProperty("Authorization", "key=AIzaSyAWmUcHDpzQZ4vT4bTqcwTc9qqMN8Fy4xs");
		con.setDoOutput(true);
		try {
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.write(postData);
			wr.flush();
			wr.close();
		} catch (Exception e) {

		}

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);

		System.out.println("Response Code : " + responseCode);

		return responseCode;

	}

}
