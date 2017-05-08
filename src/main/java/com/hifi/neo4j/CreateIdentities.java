package com.hifi.neo4j;

import org.neo4j.driver.v1.*;
import static org.neo4j.driver.v1.Values.parameters;


public class CreateIdentities {
	
	public void createUser(String name, String id){
		Driver driver = SingletonDriver.getDriver();
		Session session = driver.session();
		session.run( "CREATE (a:User {name: {name}, id: {id}})",
		        parameters( "name", name, "id", id ) );
		session.close();
	} 
	
	public void createRelationship(String id1, String id2){
		Driver driver = SingletonDriver.getDriver();
		Session session = driver.session();
		session.run("MATCH (a:User),(b:User) WHERE a.id = {node1} AND b.id = {node2} CREATE (a)-[r:FRIENDS]->(b)",
		        parameters( "node1", id1, "node2", id2 ) );
		session.run("MATCH (a:User),(b:User) WHERE a.id = {node1} AND b.id = {node2} CREATE (a)-[r:FRIENDS]->(b)",
		        parameters( "node1", id2, "node2", id1 ) );
		session.close();	
	}
}
