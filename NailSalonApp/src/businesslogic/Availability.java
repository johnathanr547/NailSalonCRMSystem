package businesslogic;

import java.time.LocalDateTime;

/**
 * A class representing a user's availability for appointments.
 * 
 * @author Blue Ridge Consulting
 * @version 1.0
 * Used:
 * - https://www.w3schools.com/java/java_date.asp
 * - https://docs.oracle.com/javase/8/docs/api/java/sql/Time.html
 * - https://docs.oracle.com/javase/8/docs/api/java/time/LocalDateTime.html
 * GenAI statement: Generative AI was used in the creation of this app.
 */
public class Availability {
	
	private int userID;
	private LocalDateTime[][] availabilityPerDay;
	
	/**
	 * Create the availability object.
	 * @param userID - the user to associate this availability window with.
	 */
	public Availability(int userID)
	{
		this.userID = userID;
		this.availabilityPerDay = new LocalDateTime[7][];
		for (int i = 0; i < 7; i++)
		{
			this.availabilityPerDay[i] = new LocalDateTime[2];
		}
	}
	
	/**
	 * Get the user ID associated with this object.
	 * @return the user ID associated with this object.
	 */
	public int getUserID()
	{
		return this.userID;
	}
	
	/**
	 * Set the user ID associated with this object.
	 * @param userID - the new user to associate this object with.
	 */
	public void setUserID(int userID)
	{
		this.userID = userID;
	}
	
	/**
	 * Get a user's availability on a given day.
	 * Days of the week are 0-6, with Sunday as 0 and Saturday as 6.
	 * @param day - the day to get a user's availability for.
	 * Format is start of availability, end of availability.
	 * @return the user's availability for that day, or null if not available.
	 */
	public LocalDateTime[] getAvailabilityPerDay(int day)
	{
		if (day < 0 || day > 6)
		{
			return null;
		}
		else
		{
			return this.availabilityPerDay[day];
		}
	}
	
	/**
	 * Set the user's availability in a given day.
	 * @param day - the day to set the user's availability for.
	 * @param newAvailability - the new availability for the user on that day.
	 */
	public boolean setAvailabilityPerDay(int day, LocalDateTime newAvailabilityStart, LocalDateTime newAvailabilityEnd)
	{
		if (0 <= day && day <= 6)
		{
			// Only start times before the end times.
			if (newAvailabilityStart.isBefore(newAvailabilityEnd))
			{
				this.availabilityPerDay[day][0] = newAvailabilityStart;
				this.availabilityPerDay[day][1] = newAvailabilityEnd;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Parse availability information from a string.
	 * @param availabilityString - the availability string to parse.
	 * @return
	 */
	public static Availability parseAvailabilityString(String availabilityString)
	{
		if (!availabilityString.contains("Availability"))
		{
			return null;
		}
		String[] splitLine = availabilityString.split(",");
		try
		{
			Integer userID = Integer.parseInt(splitLine[1]);
			Availability newAvailability = new Availability(userID);
			int dayCounter = 0;
			for (int i = 2; i < 30; i = i + 4)
			{
				String time1String = splitLine[i];
				String time2String = splitLine[i + 1];
				String time3String = splitLine[i + 2];
				String time4String = splitLine[i + 3];
				if (!time1String.equals("") && !time2String.equals("") && !time3String.equals("") && !time4String.equals(""))
				{
				    Integer hrs1 = Integer.parseInt(time1String);
				    Integer mins1 = Integer.parseInt(time2String);
				    Integer hrs2 = Integer.parseInt(time3String);
				    Integer mins2 = Integer.parseInt(time4String);
				    Integer time1 = hrs1 * 100 + mins1;
				    LocalDateTime dt1= LocalDateTime.of(2026, 1, 1, time1 / 100, time1 % 100);
				    Integer time2 = hrs2 * 100 + mins2;
				    LocalDateTime dt2 = LocalDateTime.of(2026, 1, 1, time2 / 100, time2 % 100);
					newAvailability.setAvailabilityPerDay(dayCounter, dt1, dt2);
				}
				dayCounter++;
			}
			return newAvailability;
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	@Override
	public String toString()
	{
		String returnString = "Availability,";
		returnString += this.getUserID() + ",";
		for (int i = 0; i < 6; i++)
		{
			LocalDateTime start = this.availabilityPerDay[i][0];
			LocalDateTime end = this.availabilityPerDay[i][1];
			if (start != null)
			{
				returnString = returnString + String.format("%d,%d,", start.getHour(), start.getMinute());
			}
			else
			{
				returnString += ",,";
			}
			if (end != null)
			{
				returnString = returnString + String.format("%d,%d,", end.getHour(), end.getMinute());
			}
			else
			{
				returnString += ",,";
			}
		}
		LocalDateTime start = this.availabilityPerDay[6][0];
		LocalDateTime end = this.availabilityPerDay[6][1];
		if (start != null)
		{
			returnString = returnString + String.format("%d,%d,", start.getHour(), start.getMinute());
		}
		else
		{
			returnString += ",,";
		}
		if (end != null)
		{
			returnString = returnString + String.format("%d,%d", end.getHour(), end.getMinute());
		}
		else
		{
			returnString += ",,";
		}
		return returnString + ";";
	}
}
