package app;

/**
 * A runner for the Nail Tech CRM application.
 * 
 * @author Blue Ridge Consulting
 * @version 1.0
 */
public class CRMDriver {
	
	public static void main(String[] args)
	{
		CRMApp app = new CRMApp("clientList.txt", "techList.txt", "appointments.txt", "availability.txt");
	}
	
}
