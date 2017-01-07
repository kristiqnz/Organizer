import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EventFile {

	private File file; 
	private boolean isEmpty;
	
	public EventFile() throws IOException { 
		this.file = openEventFile(); 
		this.isEmpty = checkFile();		
	}
	
	
	public boolean isEmpty() {
		return isEmpty;
	}
	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	
	public File openEventFile() throws IOException {  // CREATES OR OPENS AN EVENT TXT FILE
		
		try {
			File file = new File("D:\\III semestur\\PIK III\\Organizer\\events.txt"); 
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
			System.out.println(e.getMessage());
			return null; 
		}
	}
	
	public boolean emptyEventFile() throws IOException { // EMPTIES THE EVENT FILE
		
		BufferedWriter writer = null;

		
		try { 
			writer = new BufferedWriter(new FileWriter(this.getFile()));
			writer.write("");
		}
		catch(IOException e) { 
			System.out.println("IOException occured while emptying event file!"); 
			System.out.println(e.getMessage());
			return false; 
		}	
		finally { 
			if(writer != null) { 
				writer.close();
			}
		}
		
		return true; 
	}
	
	public boolean checkFile() {  //CHECKS IF FILE IS EMPTY
		
		return (this.file.length() == 0);
	}
	
	
	
	
}
