import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONException;
import org.json.JSONObject;

public class EventEditor {
	
	private UserInputScanner scan;
	private EventFile file;
		
	public EventEditor() throws IOException { 
		this.scan = new UserInputScanner();
		this.file = new EventFile();
	}
	
	public void addEvent() throws IOException, JSONException, InvalidInputException {
		
		System.out.println("CREATING EVENT: "); 
		System.out.println("____________________");
		
		String type = null; 
		String marker = null; 
		String date= null; 
		String time = null;
		String description = null; 
		Event event = null;
		
		while(type == null) { 
			type = scan.inputType();
		}
		
		while(marker == null) { 
			marker = scan.inputMarker();
		}
		
		
		while(date == null) { 
			date = scan.inputDate();
		}	
		
		while(time == null) { 
			time = scan.inputTime();
		}		
		
		description = scan.inputDescription(); 
		
		if(description.length() != 0) {
			event = new Event(type, marker, date, time, description); 
		}
		
		else { 
			event = new Event(type, marker, date, time);
		}
		
		if(event.writeInFile(this.file.getFile()))  { 
		
			System.out.println("\nEvent added successfully!");
			event.printInfo();
			
			if(file.isEmpty()) { 
				file.setEmpty(false);
			}
		}
		
		else { 
			System.out.println("\nCouldn't write event in file: ");
			event.printInfo();
		}
		
		return; 
				
	}
	
	public void printEventList() throws JSONException, InvalidInputException, IOException {  
				
		System.out.println("EVENT LIST: "); 
		System.out.println("____________________");
		
		BufferedReader reader = null; 
		String line = null; 
		
		try { 
			
			if(this.file.isEmpty()) { 
				System.out.println("No events yet!\n");
				return; 
			}
			
			reader = new BufferedReader(new FileReader(this.file.getFile())); 
						
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
					System.out.println(e.getMessage()); 
					continue;
				}

			}
						
		}
		catch(IOException e) {
			System.out.println("IOException occured while printing event list!");
			System.out.println(e.getMessage()); 			
			return;
		}
		
		finally { 

			if(reader != null) {
				reader.close();
			}
			
		}

	}
	
	public void printEventsForADay() throws InvalidInputException, IOException { 
		
		System.out.println("EVENTS FOR A DAY: "); 
		System.out.println("____________________");
		
		if(this.file.isEmpty()) { 
			System.out.println("No events yet!\n");
			return; 
		}
		
		ArrayList<Event> eventList = new ArrayList<Event>();
		String date = scan.inputDate(); 
		StringBuffer eventsForDay = new StringBuffer(); 
		BufferedReader reader = null; 
		String line = null; 
		
		try { 
			reader = new BufferedReader(new FileReader(this.file.getFile())); 
			
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
					System.out.println(e.getMessage()); 
					continue;
				}

			}
			
		}
		catch(IOException e) {
			System.out.println("IOException occured while printing event for " + date);
			System.out.println(e.getMessage()); 
			return;
		}	
		
		finally { 
			if(reader != null) { 
				reader.close();
			}
		}
						
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
	
	public void printEventForAMonth() throws InvalidInputException, IOException { 
		
		System.out.println("EVENTS FOR A MONTH: "); 
		System.out.println("____________________");
		
		if(this.file.isEmpty()) { 
			System.out.println("No events yet!\n");
			return; 
		}
		
		String month = null;
		BufferedReader reader = null; 
		String line = null; 
		ArrayList<Event> eventList = new ArrayList<Event>();
		ArrayList<String> dateList = new ArrayList<String>();
		int meetings = 0; 
		int tasks = 0;
		
		while(month == null) { 
			
			month = this.scan.inputMonth();		
		}
				
		try { 
			reader = new BufferedReader(new FileReader(this.file.getFile())); 
			
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
					System.out.println(e.getMessage()); 
					continue;
				}

			}
			
		}
		
		catch(IOException e ) { 
			System.out.println("IOException occured while printing event for  " + month + "(month)");
			System.out.println(e.getMessage()); 

			return; 
		}
		
		finally { 
			if(reader != null) { 
				reader.close();
			}
		}
			
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
	
	public void deleteEvent() throws JSONException, InvalidInputException, IOException {
		
		System.out.println("DELETING EVENT: "); 
		System.out.println("____________________");
		
		if(this.file.isEmpty()) { 
			System.out.println("No events yet!\n");
			return; 
		}
		
		JSONObject toDelete = searchEvent(); 
		
		if(toDelete == null) { 
			return; 
		}
		

		Event event = toEvent(toDelete);
		System.out.println("Event to delete: ");
		event.printInfo();

		String toDeleteDate = toDelete.get("date").toString();
		String toDeleteTime = toDelete.get("time").toString();
		BufferedReader reader = null;
		ArrayList<Event> eventList = new ArrayList<Event>(); 
		String line = null;
		boolean match = false;
		
		
		try {
			
			reader = new BufferedReader(new FileReader(this.file.getFile()));
			
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
					System.out.println(e.getMessage());
					continue; 
				}
				
			}
			
		}
		
		catch(IOException e ){
			System.out.println("IOException occured while deleting event!");
			System.out.println(e.getMessage()); 
			return;
		}
		
		finally { 
			if(reader != null) { 
				reader.close();
			}
		}
									
		this.file.emptyEventFile();
		if(eventList.size() == 0) {  //IF THE LAST EVENT IN THE FILE IS BEING DELETED
			this.file.setEmpty(true);
			System.out.println("Event deleted successfully!\n");
			return; 
		}
			
			
			for(Event currentEvent : eventList) { 
				if(!currentEvent.writeInFile(this.file.getFile())) { 
					System.out.println("Couldn't write event in file:");
					currentEvent.printInfo();
				}
			}
			
			eventList.clear(); 
			eventList = null; 
			
			System.out.println("Event deleted successfully!\n");
			return; 


			
	}

	public void editEvent() throws IOException, InvalidInputException, JSONException { 
		
		System.out.println("EDITING EVENT: "); 
		System.out.println("____________________");
				
		if(this.file.isEmpty()) { 
			System.out.println("No events yet!\n");
			return; 
		}
				
		JSONObject toEdit = searchEvent();
		
		if(toEdit == null) { 
			return; 
		}
		
		Event eventToEdit = toEvent(toEdit);
		
		ArrayList<Event> allEvents = getAllEvents();
		
		this.file.emptyEventFile();
				
		allEvents.remove(eventToEdit);
		
		eventToEdit = scan.editEventData(eventToEdit);
		
		allEvents.add(eventToEdit);
		
		for(Event e : allEvents) { 
			e.writeInFile(this.file.getFile());
		}
		
		allEvents.clear();
		
	}

	//#########################################################################//
	
	public Event toEvent(JSONObject obj) throws JSONException, InvalidInputException {  //CREATES AN EVENT FROM A JSON OBJECT
		
		try {
			
			String type = obj.get("type").toString();
			DataValidator.checkType(type);
			
			String marker = obj.get("marker").toString(); 
			DataValidator.checkMarker(marker);
			
			String dayOfMonth = obj.get("date").toString();
			DataValidator.checkDate(dayOfMonth);
			
			String time = obj.get("time").toString(); 
			DataValidator.checkTime(time);
			
			String description = obj.get("description").toString(); 
			
			Event event = new Event(type, marker, dayOfMonth, time, description);
			
			return event; 
		}
		catch(InvalidInputException e) { 
			System.out.println("Couldn't read event from file!");
			return null;

		}
		catch(JSONException e) { 
			System.out.println("JSON exception occured while creating event!");
			System.out.println(e.getMessage());
			return null;
		}
	}
		
	public JSONObject searchEvent() throws IOException, InvalidInputException {  //SEARCHES AN EVENT BY DATE AND TIME
			
		if(this.file.isEmpty()) { 
			System.out.println("No events yet!\n");
			return null; 
		}
		
		String date = null; 
		String time = null;
		BufferedReader reader = null; 
		String line = null; 
		
		while(date == null) { 
			date = scan.inputDate();
		}	
		
		while(time == null) { 
			time = scan.inputTime();
		}
				
		try { 
			
			reader = new BufferedReader(new FileReader(this.file.getFile())); 
						
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
					System.out.println(e.getMessage());
					continue;
				}
			}
		}
		
		catch(IOException e) { 
			System.out.println("IOException occured while searching for event!");
			System.out.println(e.getMessage());
			return null;
		}
		
		finally { 
			if(reader != null) { 
				reader.close();
			}
		}
			
		System.out.println("No event found on " + date + " at " + time + "\n");
		return null;
	}
		
	public ArrayList<Event> getAllEvents() throws IOException, InvalidInputException { 
		
		BufferedReader reader = null; 
		String line = null; 
		ArrayList<Event> allEvents = new ArrayList<Event>();
		
		try { 
						
			reader = new BufferedReader(new FileReader(this.file.getFile())); 
						
			while( (line = reader.readLine()) != null ) { 
				
				try {
					
					JSONObject read = new JSONObject(line);
					
					Event event = toEvent(read);
					if(event == null) { 
						continue;
					}
					
					allEvents.add(event);
				}
				catch(JSONException e) { 
					System.out.println("JSON exception occured while printing event list!");
					System.out.println(e.getMessage()); 
					continue;
				}

			}
						
		}
		catch(IOException e) {
			System.out.println("IOException occured while printing event list!");
			System.out.println(e.getMessage()); 			
			return null;
		}
		
		finally { 

			if(reader != null) {
				reader.close();
			}	
		}
		
		return allEvents;

	}

}