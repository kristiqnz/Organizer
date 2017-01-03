
public class InvalidInputException extends Throwable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String invalidInput; 
	
	public InvalidInputException(String invalidInput) { 
		this.invalidInput = invalidInput; 
	}

	public void getMessege() { 
		System.out.println("\nInvalid input: " + this.invalidInput + "\n");
	}
	
}
