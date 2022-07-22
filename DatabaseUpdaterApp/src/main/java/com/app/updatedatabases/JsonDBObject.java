package com.app.updatedatabases;

import java.util.ArrayList;
import org.json.simple.JSONObject;

/*
 * Author: Kimberly Wolak
 * 
 * JsonDBObject Class
 * This class acts as a shell to create a json object. Right now we are not modifying the data, however
 * I created it this way in order for it to be expanded on later, if need be.
 * 
 */

public class JsonDBObject {

	String code;
	String name;
	String file;
	String active;
	ArrayList<String> categories = new ArrayList<>();
	
	public JsonDBObject(String code, String name, String file, String active, ArrayList<String> categories) {
		this.code = code;
		this.name = name;
		this.file = file;
		this.active = active;
		this.categories = categories;
	}
	
	public JSONObject createJsonObject() {
		JSONObject database = new JSONObject();
		
		if(this.code != null && this.name != null && this.file != null && this.active != null && this.categories!= null) {
			database.put("code", this.code);
			database.put("name", this.name);
			database.put("file", this.file);
			database.put("active", this.active);
			database.put("categories", this.categories);
		}				
		return database;
	}		
}
