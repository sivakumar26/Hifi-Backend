package com.hifi.neo4j;

import static org.neo4j.driver.v1.Values.parameters;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

public class Neo4jOperations implements Neo4j {

	public List<String> getUserFriends(String userName) {

		List<String> userFriendsList = new ArrayList<String>();
		Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "123"));
		Session session = driver.session();
		StatementResult result = session.run(
				"MATCH (a:Friend)-[:FriendOf]->(b:Friend) WHERE a.name={name} RETURN b.name as name,b.age as age",
				parameters("name", userName));

		while (result.hasNext()) {
			Record record = result.next();
			userFriendsList.add(record.get("name").asString());
		}

		// Close the session and connection
		session.close();
		driver.close();
		return userFriendsList;
	}

}
