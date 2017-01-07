import java.util.Comparator;

public class TimeComparator implements Comparator<Event> {


	public int compare(Event e1, Event e2) {
				
		String time1 = e1.getTime();  
		String time2 = e2.getTime();
		
		return time1.compareTo(time2);
		
	}

}
