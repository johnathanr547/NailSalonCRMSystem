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
	
	private String userID;
	private LocalDateTime[] availabilityPerDay;
	
	public Availability(String userID)
	{
		this.userID = userID;
		this.availabilityPerDay = new LocalDateTime[7];
	}
	
	public String getUserID()
	{
		return this.userID;
	}
	
	public void setUserID(String userID)
	{
		this.userID = userID;
	}
	
	// Days of week are 0-6, with Sunday as 0 and Saturday as 6.
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
	
	public void setAvailabilityPerDay(int day, LocalDateTime newAvailability)
	{
		if (0 <= day && day <= 6)
		{
			this.availabilityPerDay[day] = newAvailability;
		}
	}

}
