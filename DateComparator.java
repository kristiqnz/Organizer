import java.util.Comparator;

public class DateComparator implements Comparator<Event> {

	@Override
	public int compare(Event e1, Event e2) {
		
		String date1 = e1.getDayOfMonth(); 
		String date2 = e2.getDayOfMonth();
		
		return date1.compareTo(date2);
	}

}
