import java.io.IOException;

import org.json.JSONException;

public class Organizer {
	
	private EventEditor editor;
	private UserInputScanner scan; 
	
	public Organizer() throws IOException { 
		this.editor = new EventEditor();
		this.scan = new UserInputScanner();
	}
	
	public void menu() throws IOException, JSONException, InvalidInputException { 
		
		boolean exit = false; 
		String choice;
		
		while(!exit) {
			
			choice = this.scan.inputMenuChoice();
			
			if(choice.equals("1")) { 
				this.editor.printEventList(); 
				continue; 
			}
			
			if(choice.equals("2")) { 
				this.editor.addEvent();
				continue;
			}
			
			if(choice.equals("4")) { 
				this.editor.deleteEvent();
			}
			
			if(choice.equals("5")) {
				this.editor.printEventsForADay(); 
				continue;
			}
			
			if(choice.equals("6")) { 
				this.editor.printEventForAMonth();
				continue; 
			}
			
			if(choice.equals("7") || choice.equals("exit")) { 
				exit = true; 
			}
			
			else { 
				continue; 
			}
			
		} //END OF WHILE
				
	} //END OF METHOD

	
}
