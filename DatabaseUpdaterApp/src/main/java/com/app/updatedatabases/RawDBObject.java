package com.app.updatedatabases;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/*
 * Author: Kimberly Wolak
 * 
 * RawDBObject  Class
 * This class helps massage the data from the updater file so that it can be sorted and duplicates can be deleted
 * 
 */

public class RawDBObject {
	String name;
	String date;
	String time;
	String filePath;
	
	public RawDBObject(String name, String date, String time, String filePath) {
		this.name = name;
		this.date = date;
		this.time = time;
		this.filePath = filePath;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getfilePath() {
		return this.filePath;
	}
	
	public LocalDate getDate() {
		LocalDate date = LocalDate.parse(this.date);
		return date;
	}
	
	public LocalTime getTime() {
	    LocalTime time = LocalTime.parse(this.time, DateTimeFormatter.ofPattern("HH:mm:ss"));
		return time;
	}

}

