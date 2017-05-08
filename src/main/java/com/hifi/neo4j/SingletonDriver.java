package com.hifi.neo4j;

import org.neo4j.driver.v1.*;

public class SingletonDriver {
	private static Driver driver;
	protected SingletonDriver(){}
	public static Driver getDriver(){
		if(driver==null){
			synchronized (SingletonDriver.class) {
				if(driver==null){
					driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "123" ) );
				}
			}
		}
		return driver;
	}
}
