package businesslogic;

import java.time.LocalDateTime;

/**
 * A class representing a user's availability for appointments.
 * 
 * @author Blue Ridge Consulting
 * @version 1.0
 * Used:
 * - https://www.w3schools.com/java/java_date.asp
 */
public class Availability {
	
	private int userID;
	private LocalDateTime[] availabilityPerDay;
	
	/**
	 * Create the availability object.
	 * @param userID - the user to associate this availability window with.
	 */
	public Availability(int userID)
	{
		this.userID = userID;
		this.availabilityPerDay = new LocalDateTime[7];
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
	 * @return the user's availability for that day, or null if not available.
	 */
	public LocalDateTime getAvailabilityPerDay(int day)
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
	public void setAvailabilityPerDay(int day, LocalDateTime newAvailability)
	{
		if (0 <= day && day <= 6)
		{
			this.availabilityPerDay[day] = newAvailability;
		}
	}

}
