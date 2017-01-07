import java.util.Scanner;

public class UserInputScanner {

	private Scanner scan; 
	
	public UserInputScanner() { 
		this.scan = new Scanner(System.in);
	}
	
	public String inputType() { 
	
		boolean checked = false; 
		String type = null;
		while(!checked) {  		
			
			try { 
				System.out.print("Event type (Meeting / Task): "); 
				type = this.scan.nextLine();
				
				checked = DataValidator.checkType(type); 

			}
			catch(InvalidInputException e) { 
				System.out.println(e.getMessage());
				continue;
			}
		}
		
		return type.toLowerCase(); 
	}
	
	public String inputMarker() { 
		
		boolean checked = false; 
		String marker = null; 
		
		while(!checked) {  		
			
			try { 
				System.out.print("Marker (Public / Confidential / Personal): "); 
				marker = this.scan.nextLine();
				
				checked = DataValidator.checkMarker(marker); 
			}
		
			catch(InvalidInputException e) { 
				System.out.println(e.getMessage());
				continue;
			}
		}
		
		return marker.toLowerCase();

	}
	
	public String inputDate() { 

		
		boolean checked = false; 
		String date = null; 
		
		while(!checked) {  		
			
			try { 
				System.out.print("Date (DD.MM): "); 
				date = this.scan.nextLine();
				
				checked = DataValidator.checkDate(date);
				
			}
			catch(InvalidInputException e) { 
				System.out.println(e.getMessage());
				continue;
			}
		}
		
		return date; 
	}
	
	public String inputTime() { 
		
		boolean checked = false;
		String time = null; 
		
		while(!checked) {  		
			
			try { 
				System.out.print("Time (HH:MM): "); 
				time = this.scan.nextLine();
				
				checked = DataValidator.checkTime(time);
				
			}
			catch(InvalidInputException e) { 
				System.out.println(e.getMessage());
				continue; 
			}
		}
		
		return time; 
	}

	public String inputDescription() { 
		
		System.out.println("Description (press Enter to skip): "); 
		String description = scan.nextLine(); 
		
		return description;		
	}

	public String inputMonth() { 
		boolean checked = false; 
		String month = null; 
		
		while(!checked) {  		
			
			try { 
				System.out.print("Month (MM): "); 
				month = this.scan.nextLine();
				
				checked = DataValidator.checkMonth(month);
				
			}
			catch(InvalidInputException e) { 
				System.out.println(e.getMessage());
				continue;
			}
		}
		
		return month;
	}
	
	public boolean edit(String toChange) { 
		
		String choice = null; 
		
		while(choice == null) { 
			System.out.println("\nDo you want to change the current " + toChange + "?");
			System.out.print("Choice (y/n): ");
			choice = this.scan.nextLine();
		
			if("y".equals(choice)) { 
				return true;
			}
			
			if("n".equals(choice)) { 
				return false;
			}
			
		}
		
		return false;
		
	}
	
	public Event editEventData(Event event) { 
		
		event.printInfo();
				
		if(edit("type" + "(" + event.getType() + ")")) { 
			event.setType(inputType());
		}
				
		if(edit("marker" + "(" + event.getMarker() + ")")) { 
			event.setMarker(inputMarker());
		}
				
		if(edit("date" + "(" + event.getDayOfMonth() + ")")) { 
			event.setDayOfMonth(inputDate());
		}
				
		if(edit("time" + "(" + event.getTime() + ")")) { 
			event.setTime(inputTime());
		}
				
		if(edit("descrption" + "(" + event.getDescription() + ")")) { 
			event.setDescription(inputDescription());
		}
		
		System.out.println("Event edited successfully!\n");
		event.printInfo();
		
		return event;
		
	}

	public String inputMenuChoice() { 
		
		System.out.println("\t***ORGANIZER MENU***"); 
		System.out.println("____________________________________");
		System.out.println("1.Print event list.................."); 
		System.out.println("2.Add new event....................."); 
		System.out.println("3.Edit event........................");
		System.out.println("4.Delete event......................");
		System.out.println("5.Print events for a specific day...");
		System.out.println("6.Print events for a specific month.");
		System.out.println("7.Exit..............................");
		System.out.println("____________________________________");
		
		System.out.print("\n-> ");
		String choice = this.scan.nextLine();
		
		return choice;
	}
}
