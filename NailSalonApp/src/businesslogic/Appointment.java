package businesslogic;

import java.time.LocalDateTime;

/**
 * The appointment between a client and a technician.
 * 
 * @author Dorian Crawford
 * @version 1.0
 */

public class Appointment {
	private int nailTechID;
	private int clientID;
	private LocalDateTime apptDate;
	private String apptName;
	
	public Appointment(int ntID, int cID, LocalDateTime apptDt, String apptName) {
		nailTechID = ntID;
		clientID = cID;
		apptDate = apptDt;
		this.apptName = apptName;
	}
	
	public int getClientID() {
		return clientID;
	}
	
	public int getNailTechID() {
		return nailTechID;
	}
	
	public LocalDateTime getApptDate() {
		return apptDate;
	}
	
	public String getApptName() {
		return apptName;
	}
	
	@Override
    public String toString() {
		// This can be better but it's fine for now - DC
		// Each appointment time is listed by the name of the day, the full numerical date, and the name of the booked service.
		LocalDateTime apptDt = getApptDate();
        return "Appointment on the " + apptDt.getDayOfMonth() + "th of " + apptDt.getMonth() + " named " + getApptName();
    }
}