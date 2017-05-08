package com.hifi.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.utils.IndexType;

@XmlRootElement
@Indexes(@Index(fields = @Field(value = "$**", type = IndexType.TEXT)))

@Entity("location")
public class Location {

	@Override
	public String toString() {
		return "{id=" + id + ", locationName=" + locationName + ", locationId=" + locationId + "}";
	}
	@Id
	private ObjectId id;
	private String locationName;
	
	private String locationId;
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	
	
}
