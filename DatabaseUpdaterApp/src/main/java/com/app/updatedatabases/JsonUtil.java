package com.app.updatedatabases;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
/*
 * Author: Kimberly Wolak
 * 
 * Utility Class
 * Due to the nature of this being a utility class the methods are static and aren't used to
 * modify the data being sent into the parameter
 * 
 */
public class JsonUtil {
	
	/*
	 *  Method: readJsonFile()
	 *  Input: Takes in a database identifier code
	 *  Description: This method reads in each database and saves it into a hash map for lookup later
	 */
	public static HashMap<String,JSONObject> readJsonFile(String databasePath) throws FileNotFoundException, IOException, ParseException {	
		HashMap<String,JSONObject> actualDBListMap = new HashMap<>();
		FileReader reader = new FileReader(databasePath); //database file
		JSONParser jsonParser = new JSONParser();				 
		JSONArray dbList = (JSONArray) jsonParser.parse(reader); //separate the objects into an array to search through
		
		//loop through the json objects and save them into a map
		for(int i = 0; i<dbList.size();i++) {
			JSONObject database = (JSONObject) dbList.get(i);
			String dbIdentifier =  (String) database.get("code");
			actualDBListMap.put(dbIdentifier.toLowerCase(), database);
		}		
		return actualDBListMap;
	}
	
	/*
	 *  Method: writeJsonFile()
	 *  Input: Takes in the updated database records
	 *  Description: This method writes the json file in a formated manor
	 */
	public static void writeJsonFile(JSONArray updateDBList,String downloadPath) throws IOException {		
		ObjectMapper objMapper = new ObjectMapper();
		FileWriter fileWriter = new FileWriter(downloadPath+ "/Updatedatabase.json");		
        fileWriter.write(objMapper.writerWithDefaultPrettyPrinter().writeValueAsString(updateDBList));         
        fileWriter.close();
	}
	
	/*
	 *  Method: lookUpDatabase()
	 *  Input: Takes in a database identifier code
	 *  Description: This method searches for the correct corresponding database
	 */
	public static JSONObject lookUpDatabase(String dbName,HashMap<String,JSONObject> actualDBList) throws IOException {
		dbName = dbName.toLowerCase();
		JSONObject targetDB = null; // the database we are looking for from the list
		
		if(actualDBList.get(dbName) != null) {
			targetDB = actualDBList.get(dbName);
		}
	
		return targetDB;		
	}
	
	/*
	 *  Method: extractJsonFileInfo()
	 *  Description: This takes in the file you want to update the database with and parses it
	 *  into a hash map in order to easily separate and find data
	 */
	public static HashMap<String, String> extractJsonFileInfo(String updaterPath) throws FileNotFoundException{
		File dbUpdaterFile = new File(updaterPath); //File you want to update the database with
		Scanner reader = new Scanner(dbUpdaterFile);
		HashMap<String,String> dbMap = new HashMap<>(); //going to hold the identifier codes and the corresponding file path to update with
		ArrayList<RawDBObject> databases = new ArrayList<>();	
		int postIndex = 1;
		
		while (reader.hasNextLine()) {
	        String tempData = reader.nextLine();
	        String[] data = tempData.split(" ");	       	        
	        String path = data[2];
       		String[] tempIdentifer = path.split("-");
       		
	        RawDBObject database = new RawDBObject(tempIdentifer[0].toLowerCase(),data[0],data[1],data[2]);
	        databases.add(database);		       
		}
		
		Comparator<RawDBObject> compareDatabases= Comparator
                .comparing(RawDBObject::getName)
                .thenComparing(RawDBObject::getDate)
                .thenComparing(RawDBObject::getTime);
		
		List<RawDBObject> sortedDatabases = databases.stream()
                .sorted(compareDatabases)
                .collect(Collectors.toList());
		
		for(int i = 0; i <sortedDatabases.size(); i++) {
			//should override with newer values by default since the list is sorted and all keys must be unique in a hashmap
			dbMap.put(sortedDatabases.get(i).getName(), sortedDatabases.get(i).getfilePath());
		}
		
		reader.close();
		return dbMap;
	}
	
	/*
	 *  Method: updateAndCreateJsonObject()
	 *  Input: Takes in the hash map created from reading in the updater file (identifier code, file path) and the actual list of the know databases
	 *  Description: This method looks up the databases that needs to be updated as directed by the updater file
	 *  and creates a new array of json objects with the new values. This then can be used to write the new json
	 *  file after
	 */
	public static JSONArray updateAndCreateJsonObject(HashMap<String,String> dbUpdaterList, HashMap<String,JSONObject> actualDBList) throws FileNotFoundException, IOException, ParseException{
		JsonDBObject dbObject;
		JSONArray updatedDBList = new JSONArray();
		
		//loop through the databases that need to be updated as indicated by the hash map derived from the updater file
		for (String key : dbUpdaterList.keySet()) {
			JSONObject targetDB = JsonUtil.lookUpDatabase(key, actualDBList); //map the key to the correct database object
			
			//create a json object shell (in case we want to add additional functionality in the future)
			if(targetDB != null) {
				dbObject = new JsonDBObject(
						(String) targetDB.get("code"),
						(String) targetDB.get("name"),
						"s3://some-special-bucket/production/publisher-data/" + dbUpdaterList.get(key),
						(String) targetDB.get("active"),
						(ArrayList<String>) targetDB.get("categories"));
				
				//create the actual json object
				JSONObject updatedDBJsonObj  = dbObject.createJsonObject();
				updatedDBList.add(updatedDBJsonObj);
			}
		}
		return updatedDBList;
	}	
	
}//end of class
