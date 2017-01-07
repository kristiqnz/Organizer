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
			
			if("1".equals(choice)) { 
				this.editor.printEventList(); 
				continue; 
			}
			
			if("2".equals(choice)) { 
				this.editor.addEvent();
				continue;
			}
			
			if("3".equals(choice)) { 
				this.editor.editEvent();
				continue;
			}
			
			if("4".equals(choice)) { 
				this.editor.deleteEvent();
			}
			
			if("5".equals(choice)) {
				this.editor.printEventsForADay(); 
				continue;
			}
			
			if("6".equals(choice)) { 
				this.editor.printEventForAMonth();
				continue; 
			}
			
			if("7".equals(choice) || "exit".equals(choice)) { 
				exit = true; 
			}
			
			else { 
				continue; 
			}
			
		} 
				
	}

	
}
