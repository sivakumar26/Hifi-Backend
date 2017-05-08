package com.hifi.neo4j;

import static org.neo4j.driver.v1.Values.parameters;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

public class FindIdentities {

	public List<String> findFriends(String id){
		List<String> friends = new ArrayList<String>();
		Driver driver = SingletonDriver.getDriver();
		Session session = driver.session();
		StatementResult result = session.run(
				"MATCH (a:User)-[:FRIENDS]->(b:User) WHERE a.id={id} RETURN b.name as name,b.id as id",
				parameters("id", id));

		while (result.hasNext()) {
			Record record = result.next();
			friends.add(record.get("name").asString());
		}

		session.close();
		return friends;
	}
	
	public boolean isUser(String id){
		Driver driver = SingletonDriver.getDriver();
		Session session = driver.session();
		
		StatementResult result = session.run("MATCH (a:User) WHERE a.id={id} RETURN a.id as id",
				parameters("id", id));
		
		if (result.hasNext()){
			return true;
		}return false;
		
		
	}
}

