import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface DataValidator {
	
	public static boolean checkType(String type) { 
		
		if(type.equalsIgnoreCase("task") || 
		   type.equalsIgnoreCase("meeting")) { 
			return true;
		}
				
		else { 
			return false; 
		}
	}
	
	public static boolean checkDate(String dayOfMonth) { 
		
		final String DATE_PATTERN = "^[0-9.]{5}$"; 
		
		Pattern pattern = Pattern.compile(DATE_PATTERN);
		Matcher matcher = pattern.matcher(dayOfMonth);
		
		if(!matcher.matches()) { 
			return false; 
		}
		
		if(dayOfMonth.startsWith(".") || dayOfMonth.endsWith(".")) { 
			return false;
		}
		
		String[] split = dayOfMonth.split("\\."); 
		
		try { 
			int day = Integer.parseInt(split[0]); 
			int month = Integer.parseInt(split[1]); 
			
			if( (day < 1) || (day > 31) ) { 
				return false; 
			}
			
			if( (month < 1) || (month > 12) ) { 
				return false; 
			}
		}
		catch(NumberFormatException e) { 
			return false; 
		}
			
		return true;
	}
	
	
	public static boolean checkTime(String time) { 
		
		final String TIME_PATTERN = "^[0-9:]{5}$"; 
		
		Pattern pattern = Pattern.compile(TIME_PATTERN);
		Matcher matcher = pattern.matcher(time);
		
		if(!matcher.matches()) { 
			return false;
		}
		
		if(time.startsWith(":") || time.endsWith(":")) { 
			return false;
		}
		
		String[] split = time.split(":"); 
				
		try {
			int hour = Integer.parseInt(split[0]); 
			int minutes = Integer.parseInt(split[1]);
			
			if( (hour < 0) || (hour > 23) ) { 
				return false; 
			}
			
			if( (minutes < 0) || (minutes > 59) ) { 
				return false;
			}
		}
		catch(NumberFormatException e) { 
			return false; 
		}

		return true; 
				
	}
	
	public static boolean checkMarker(String marker) { 
		
		if(marker.equalsIgnoreCase("public") || 
	       marker.equalsIgnoreCase("confidential") || 
	       marker.equalsIgnoreCase("personal")) { 
			return true;
		}
		
		else { 
			return false; 
		}
	}

	public static boolean checkMonth(String month) { 
		
		final String MONTH_PATTERN = "^[0-9]{2}$"; 
		
		Pattern pattern = Pattern.compile(MONTH_PATTERN);
		Matcher matcher = pattern.matcher(month);
		
		if(!matcher.matches()) { 
			return false;
		}
		
		int monthInt = Integer.parseInt(month); 
		
		if( (monthInt < 1) || (monthInt > 12) ) { 
			return false;
		}
		
		return true; 
		
	}
}
