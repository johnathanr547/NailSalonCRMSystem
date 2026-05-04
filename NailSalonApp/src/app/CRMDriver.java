package app;

/**
 * A runner for the Nail Tech CRM application.
 * 
 * @author Blue Ridge Consulting
 * @version 1.0
 * GenAI statement: Generative AI was used in the creation of this app.
 */
public class CRMDriver {
	
	public static void main(String[] args)
	{
		CRMApp app = new CRMApp("clientList.txt", "techList.txt", "appointments.txt", "availability.txt");
	}
	
}
