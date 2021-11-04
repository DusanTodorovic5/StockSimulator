package programPrikaz.grafik.vreme;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Vreme{
	public int seconds;
	public int min;
	public int hours;
	public int day;
	public int month;
	public int year;
	
	public Vreme() {
		
	}

	public Vreme(int seconds, int min, int hours, int day, int month, int year) {
		this.seconds = seconds;
		this.min = min;
		this.hours = hours;
		this.day = day;
		this.month = month;
		this.year = year;
	}
	public void set(int seconds, int min, int hours, int day, int month, int year) {
		this.seconds = seconds;
		this.min = min;
		this.hours = hours;
		this.day = day;
		this.month = month;
		this.year = year;
	}
	public String format() {
		StringBuilder sb = new StringBuilder();
		sb.append(seconds).append("s ");
		sb.append(min).append("min ");
		sb.append(hours).append("h:  ");
		sb.append(day).append(".");
		sb.append(month).append(".");
		sb.append(year);
		return sb.toString();
	}
	public String formatDate() {
		StringBuilder sb = new StringBuilder();
		sb.append(day).append(".");
		sb.append(month).append(".");
		sb.append(year);
		return sb.toString();
	}
	public boolean isEmpty() {
		return seconds == 0 && min == 0 && hours == 0 && day == 0 && month == 0 && year == 0;
	}
	@Override
	public String toString() {
		
		Calendar c = new GregorianCalendar();
		c.clear();
		c.set(year, month-1,day,hours,min,seconds);
		long unixTime = c.getTimeInMillis() / 1000;
		return "" + unixTime;
	}
}
