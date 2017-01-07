import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface DataValidator {
	
	public static boolean checkType(String type) throws InvalidInputException { 
		
		if(! ("task".equalsIgnoreCase(type) || "meeting".equalsIgnoreCase(type)) ) { 
			throw new InvalidInputException(type);
		}
		
		else { 
			return true;
		}
		
	}
	
	public static boolean checkDate(String dayOfMonth) throws InvalidInputException { 
		
		final String DATE_PATTERN = "^[0-9.]{5}$"; 
		
		Pattern pattern = Pattern.compile(DATE_PATTERN);
		Matcher matcher = pattern.matcher(dayOfMonth);
		
		if(!matcher.matches()) { 
			throw new InvalidInputException(dayOfMonth); 
		}
		
		if(dayOfMonth.startsWith(".") || dayOfMonth.endsWith(".")) { 
			throw new InvalidInputException(dayOfMonth); 
		}
		
		String[] split = dayOfMonth.split("\\."); 
		
		try { 
			int day = Integer.parseInt(split[0]); 
			int month = Integer.parseInt(split[1]); 
			
			if( (day < 1) || (day > 31) ) { 
				throw new InvalidInputException(dayOfMonth); 
			}
			
			if( (month < 1) || (month > 12) ) { 
				throw new InvalidInputException(dayOfMonth); 
			}
		}
		catch(NumberFormatException e) { 
			System.out.println("Number format exception!");
			System.out.println(e.getMessage());
			return false; 
		}
			
		return true;
	}
	
	
	public static boolean checkTime(String time) throws InvalidInputException { 
		
		final String TIME_PATTERN = "^[0-9:]{5}$"; 
		
		Pattern pattern = Pattern.compile(TIME_PATTERN);
		Matcher matcher = pattern.matcher(time);
		
		if(!matcher.matches()) { 
			throw new InvalidInputException(time); 
		}
		
		if(time.startsWith(":") || time.endsWith(":")) { 
			throw new InvalidInputException(time);
			}
		
		String[] split = time.split(":"); 
				
		try {
			int hour = Integer.parseInt(split[0]); 
			int minutes = Integer.parseInt(split[1]);
			
			if( (hour < 0) || (hour > 23) ) { 
				throw new InvalidInputException(time);
			}
			
			if( (minutes < 0) || (minutes > 59) ) { 
				throw new InvalidInputException(time);
			}
		}
		catch(NumberFormatException e) { 
			System.out.println("Number format exception!");
			System.out.println(e.getMessage());
			return false; 
		}

		return true; 
				
	}
	
	public static boolean checkMarker(String marker) throws InvalidInputException { 
		
		if("public".equalsIgnoreCase(marker) || 
		   "confidential".equalsIgnoreCase(marker) || 
		   "personal".equalsIgnoreCase(marker)) { 
			return true;
		}
		
		else { 
			throw new InvalidInputException(marker);
		}
	}

	public static boolean checkMonth(String month) throws InvalidInputException { 
		
		final String MONTH_PATTERN = "^[0-9]{2}$"; 
		
		Pattern pattern = Pattern.compile(MONTH_PATTERN);
		Matcher matcher = pattern.matcher(month);
		
		if(!matcher.matches()) { 
			throw new InvalidInputException(month);
		}
		
		int monthInt = Integer.parseInt(month); 
		
		if( (monthInt < 1) || (monthInt > 12) ) { 
			throw new InvalidInputException(month);
		}
		
		return true; 
		
	}
}
