import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

import org.json.JSONException;

public class Event implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String type;
	private String marker; 
	private String dayOfMonth; 
	private String time; 
	private String description;
	
	public Event(String type, String marker, String dayOfMonth, String time, String description) {
		this.type = type;
		this.marker = marker;
		this.dayOfMonth = dayOfMonth;
		this.time = time;
		this.description = description;
	}
	
	public Event(String type, String marker, String dayOfMonth, String time) {
		this.type = type;
		this.marker = marker;
		this.dayOfMonth = dayOfMonth;
		this.time = time;
		this.description = "-";
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMarker() {
		return marker;
	}
	public void setMarker(String marker) {
		this.marker = marker;
	}
	public String getDayOfMonth() {
		return dayOfMonth;
	}
	public void setDayOfMonth(String dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	} 
	
	public void printInfo() { 
		System.out.println("\nTYPE: " + this.type + 
						   " \n -Marker: " + this.marker + 
						   " \n -Date: " + this.dayOfMonth + 
						   " \n -Time: " + this.time +
						   " \n -Description: " + this.description + "\n"); 
	}
	
	public String toJSONString() throws JSONException { 
		
			String JSONString = "{\"type\":" + "\"" + this.type + "\"," +
								"\"marker\":" + "\"" + this.marker + "\"," +
								"\"date\":" + "\"" + this.dayOfMonth + "\"," +
								"\"time\":" + "\"" + this.time + "\"," +
								"\"description\":" + "\"" + this.description + "\"}"; 
							
			return JSONString; 

	}
	
	public boolean writeInFile(File eventFile) throws IOException, JSONException { 
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(eventFile, true)); //APENDS TO FILE
			
			String JSONString = this.toJSONString();
			
			writer.write(JSONString);
			writer.newLine();
			writer.close();
			
		}
		catch(IOException e) { 
			System.out.println("IOException occured while adding event in file!");
			e.printStackTrace();
		}
		
		catch(JSONException e) { 
			System.out.println("JSON exception occured while adding event in file!");
		}
		
		return true; 
	}
	
	
}
