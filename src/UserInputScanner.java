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
				
				if(! (checked = DataValidator.checkType(type)) ) { 
					throw new InvalidInputException(type);
				}
				
			}
			catch(InvalidInputException e) { 
				e.getMessege();
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
				
				if(! (checked = DataValidator.checkMarker(marker)) ) { 
					throw new InvalidInputException(marker);
				}					
			}
			catch(InvalidInputException e) { 
				e.getMessege();
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
				
				if(! (checked = DataValidator.checkDate(date)) ) { 
					throw new InvalidInputException(date);
				}
				
			}
			catch(InvalidInputException e) { 
				e.getMessege();
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
				
				if(! (checked = DataValidator.checkTime(time)) ) { 
					throw new InvalidInputException(time);
				}
				
			}
			catch(InvalidInputException e) { 
				e.getMessege();
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
				
				if(! (checked = DataValidator.checkMonth(month)) ) { 
					throw new InvalidInputException(month);
				}
				
			}
			catch(InvalidInputException e) { 
				e.getMessege();
				continue;
			}
		}
		
		return month;
	}

	public String inputMenuChoice() { 
		
		System.out.println("\t***ORGANIZER MENU***"); 
		System.out.println("____________________________________");
		System.out.println("1.Print event list.................."); 
		System.out.println("2.Add new event....................."); 
		System.out.println("3...................................");
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
