package com.hifi.model;

import java.util.List;

import org.jose4j.jwk.JsonWebKey;

public class JsonWebKeys {

	static List<JsonWebKey> jwkList = null;

	public static List<JsonWebKey> getJwkList() {
		return jwkList;
	}

	public static void setJwkList(List<JsonWebKey> jwkList) {
		JsonWebKeys.jwkList = jwkList;
	}
}
