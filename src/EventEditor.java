import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONException;
import org.json.JSONObject;

public class EventEditor {
	
	private UserInputScanner scan;
	private File file;
		
	public EventEditor() throws IOException { 
		this.scan = new UserInputScanner();
		this.file = openEventFile();
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void addEvent() throws IOException, JSONException { //ADDS NEW EVENT IN EVENT FILE
		
		String type = null; 
		String marker = null; 
		String date= null; 
		String time = null;
		String description = null; 
		Event event = null;
				
		type = scan.inputType();
		
		marker = scan.inputMarker();
		
		date = scan.inputDate();
				
		time =  scan.inputTime();
		
		description = scan.inputDescription(); 
		
		if(description.length() != 0) {
			event = new Event(type, marker, date, time, description); 
		}
		
		else { 
			event = new Event(type, marker, date, time);
		}
		
		if(event.writeInFile(this.getFile()))  { 
		
			System.out.println("Event added successfully!");
			event.printInfo();
		}
		
		else { 
			System.out.println("Couldn't write event in file: ");
			event.printInfo();
		}
		
		return; 
				
	}
	
	public void printEventList() throws JSONException, InvalidInputException {  //PRINTS ALL EVENTS FROM EVENT FILE
		
		try { 
			
			if(this.file.length() == 0) { 
				System.out.println("No events yet!\n");
				return; 
			}
			
			BufferedReader reader = new BufferedReader(new FileReader(this.file)); 
			String line = ""; 
						
			while( (line = reader.readLine()) != null ) { 
				
				try {
					
					JSONObject read = new JSONObject(line);
					
					Event event = toEvent(read);
					if(event == null) { 
						continue;
					}
					
					event.printInfo();
				}
				catch(JSONException e) { 
					System.out.println("JSON exception occured while printing event list!");
					continue;
				}
			}
						
			reader.close();
						
		}
		catch(IOException e) {
			System.out.println("IOException occured while printing event list!");
			return;
		}

	}
	
	public void printEventsForADay() throws InvalidInputException { 
		
		if(this.file.length() == 0) { 
			System.out.println("No events yet!\n");
			return; 
		}
		
		ArrayList<Event> eventList = new ArrayList<Event>();
		
		String date = scan.inputDate(); 
		
		try { 
			BufferedReader reader = new BufferedReader(new FileReader(this.file)); 
			String line = null; 
			StringBuffer eventsForDay = new StringBuffer(); 
			
			while( (line = reader.readLine()) != null ) { 
				
				try { 
					
					JSONObject read = new JSONObject(line);
					
					if(read.get("date").toString().equals(date)) { 
						Event event = toEvent(read);
						
						if(event == null) { 
							System.out.println("Couldn't get event while printing events for " + date + "!");
							continue;
						}
						
						eventList.add(event);
						
					}				
				}
				catch(JSONException e) { 
					System.out.println("JSON exception occured while printing event list!");
					continue;
				}
			}
			
			reader.close();
			
			if(eventList.size() == 0) { 
				System.out.println("Events on " + date + ": no events\n");
				return; 
			}
		
			Collections.sort(eventList, new TimeComparator());
			
			for(Event e : eventList) { 
				eventsForDay.append("|" + e.getType() + " - " + e.getTime());
			}
			
			eventList = null; 
			System.out.print("Events on " + date + ": " + eventsForDay);
			System.out.println("|\n");
			
		}
		catch(IOException e) {
			System.out.println("IOException occured while printing event for " + date);
			return;
		}	
	}
	
	public void printEventForAMonth() throws InvalidInputException { 
		
		if(this.file.length() == 0) { 
			System.out.println("No events yet!\n");
			return; 
		}
		
		String month = scan.inputMonth();
				
		try { 
			BufferedReader reader = new BufferedReader(new FileReader(this.file)); 
			String line = null; 
			ArrayList<Event> eventList = new ArrayList<Event>();
			ArrayList<String> dateList = new ArrayList<String>();
			
			int meetings = 0; 
			int tasks = 0;
			
			while( (line = reader.readLine()) != null ) { 
				
				try { 
					
					JSONObject read = new JSONObject(line);
					String[] splitDate = read.get("date").toString().split("\\.");
					
					if(splitDate[1].equals(month)) { 
						Event e = toEvent(read); 
						if(e == null) {
							continue; 
						}
						eventList.add(e); //FILLS THE LIST WITH ALL EVENTS FOR THE CURRENT MONTH
					}						
				}
				
				catch(JSONException e) { 
					System.out.println("JSON exception occured while printing event list!");
					continue;
				}
			}
			
			reader.close();
			
			if(eventList.size() == 0) { 
				System.out.println("Events during month " + month + ": no events\n");
				return; 
			}
			
			Collections.sort(eventList, new DateComparator());
			
			System.out.println("Events for " + month + " month: ");
			
			for (Event e : eventList) { 	//FILLS THE LIST WITH ALL DATES FOR THE CURRENT MONTH
				if(!dateList.contains(e.getDayOfMonth())) { 
					dateList.add(e.getDayOfMonth());
				}
			}
			
			for(String date : dateList) {  //COUNTS EVENTS AND MEETING FOR EVERY DATE IN THE LIST
				
				for(Event e : eventList) {
				
					if(e.getDayOfMonth().equals(date)) { 
					
						if(e.getType().equalsIgnoreCase("meeting")) { 
							meetings++;
						}
					
						if(e.getType().equalsIgnoreCase("task")) { 
							tasks++;
						}
					}
			}
				System.out.println(" -" + date + ": " + meetings + " meeting(s), " + tasks + " task(s)");
				meetings = 0; 
				tasks = 0;
		}
			
			System.out.println("\n");
			dateList = null; 
			eventList = null; 
		
			
		}
			
		catch(IOException e ) { 
			System.out.println("IOException occured while printing event for  " + month + "(month)");
			return; 
		}
		
	}
	
	public boolean deleteEvent() throws JSONException, InvalidInputException {
		
		if(this.file.length() == 0) { 
			System.out.println("No events yet!\n");
			return false; 
		}
		
		JSONObject toDelete = searchEvent(); 
		
		if(toDelete == null) { 
			return false; 
		}
		
		Event event = toEvent(toDelete);
		System.out.println("Event to delete: ");
		event.printInfo();
			
		String toDeleteDate = toDelete.get("date").toString();
		String toDeleteTime = toDelete.get("time").toString();
		
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(this.file));
			ArrayList<Event> eventList = new ArrayList<Event>(); 
			String line;
			boolean match = false;
			
			while( (line = reader.readLine()) != null) { 
				
				try { 
					JSONObject read = new JSONObject(line); 
					match = (read.get("date").toString().equals(toDeleteDate)) && (read.get("time").toString().equals(toDeleteTime));
					
					if(match) { 
						continue; 
					}
					
					else { 
						eventList.add(toEvent(read)); 
					}
				}
				catch(JSONException e) { 
					System.out.println("JSONException occured while deleting event!");
					continue; 
				}
				
			}
						
			reader.close(); 
			
			emptyEventFile();
			
			if(eventList.size() == 0) {  //IF THE LAST EVENT IN THE FILE IS BEING DELETED
				return true; 
			}
			
			
			for(Event currentEvent : eventList) { 
				if(!currentEvent.writeInFile(this.getFile())) { 
					System.out.println("Couldn't write event in file:");
					currentEvent.printInfo();
				}
			}
			
			eventList.clear(); 
			eventList = null; 
			
			System.out.println("Event deleted successfully!\n");
			return true; 

		}
		
		catch(IOException e ){
			System.out.println("IOException occured while deleting event!"); 
			return false;
		}
			
	}

	//#########################################################################//
	
	public Event toEvent(JSONObject obj) throws JSONException, InvalidInputException {  //CREATES AN EVENT FROM A JSON OBJECT
		
		try {
			
			String type = obj.get("type").toString();
			if(!DataValidator.checkType(type)) { 
				throw new InvalidInputException(type);
			}
			
			String marker = obj.get("marker").toString(); 
			if(!DataValidator.checkMarker(marker)) { 
				throw new InvalidInputException(marker);
			}
			
			String dayOfMonth = obj.get("date").toString();
			if(!DataValidator.checkDate(dayOfMonth)) { 
				throw new InvalidInputException(dayOfMonth);
			}
			
			String time = obj.get("time").toString(); 
			if(!DataValidator.checkTime(time)) { 
				throw new InvalidInputException(time);
			}
			
			String description = obj.get("description").toString(); 
			
			Event event = new Event(type, marker, dayOfMonth, time, description);
			
			return event; 
		}
		catch(JSONException e) { 
			System.out.println("JSON exception occured while creating event!"); 
			return null;
		}
		catch(InvalidInputException e) { 
			e.getMessege();
			System.out.println("Couldn't read event from file!");
			return null;
		}
	}
	
	public File openEventFile() throws IOException {  // CREATES OR OPENS AN EVENT TXT FILE
		
		try {
			File file = new File("C:\\Users\\Kristiyan\\Desktop\\events.txt"); 
			boolean isCreated = file.createNewFile();
			
			if(isCreated) { 
				System.out.println("Event file created successfully!");
			}
			
			else { 
				System.out.println("Event file loaded successfully!\n"); 
			}
			
			return file;
		}
		catch(IOException e ) { 
			System.out.println("IOException occured while creating new event file!");
			return null; 
		}
	}
	
	public JSONObject searchEvent() {  //SEARCHES AN EVENT BY DATE AND TIME
			
	if(this.file.length() == 0) { 
		System.out.println("No events yet!\n");
		return null; 
	}
	
	String date = scan.inputDate();
	String time = scan.inputTime();
				
		try { 
			
			BufferedReader reader = new BufferedReader(new FileReader(this.file)); 
			String line = ""; 
						
			while( (line = reader.readLine()) != null ) { 
				
				try {
					JSONObject read = new JSONObject(line);
					String currentDate = read.get("date").toString(); 
					String currentTime = read.get("time").toString();
					
					if( (currentDate.equals(date)) && (currentTime.equals(time)) ) { 
						reader.close();
						return read; 
					}
					
					else {
						continue;
					}
						
				}
				catch(JSONException e) { 
					System.out.println("JSON exception occured while searching for event!");
					continue;
				}
			}
			reader.close();
		}
		
		catch(IOException e) { 
			System.out.println("IOException occured while searching for event!");
			return null;
		}
			
		System.out.println("No event found on " + date + " at " + time);
		return null;
	}
		
	public boolean emptyEventFile() throws IOException { // EMPTIES THE EVENT FILE
		
		try { 
			BufferedWriter writer = new BufferedWriter(new FileWriter(this.getFile()));
			writer.write("");
			writer.close();
		}
		catch(IOException e) { 
			System.out.println("IOException occured while emptying event file!"); 
			return false; 
		}
		
		return true; 
	}
	
}