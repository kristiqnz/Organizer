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
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dayOfMonth == null) ? 0 : dayOfMonth.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((marker == null) ? 0 : marker.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (dayOfMonth == null) {
			if (other.dayOfMonth != null)
				return false;
		} else if (!dayOfMonth.equals(other.dayOfMonth))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (marker == null) {
			if (other.marker != null)
				return false;
		} else if (!marker.equals(other.marker))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
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
		
		BufferedWriter writer = null;
		
		try {
			writer = new BufferedWriter(new FileWriter(eventFile, true)); //APENDS TO FILE
			
			String JSONString = this.toJSONString();
			
			writer.write(JSONString);
			writer.newLine();
			
		}
		catch(IOException e) { 
			System.out.println("IOException occured while adding event in file!");
			System.out.println(e.getMessage());
		}
		
		catch(JSONException e) { 
			System.out.println("JSON exception occured while adding event in file!");
			System.out.println(e.getMessage());
		}
		
		finally { 
			if(writer != null) { 
				writer.close();
			}
		}
		
		return true; 
	}
	
	
}
