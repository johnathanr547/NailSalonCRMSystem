package businesslogic;

/**
 * A nail tech at the business.
 * 
 * @author Blue Ridge Consulting
 * @version 1.0
 */
public class NailTech extends User {
	private Availability techAvailability;
	
	
	public NailTech(int userId, String password, String firstName, String lastName, String email,
			String phoneNumber, Availability availability) {
		super(userId, password, firstName, lastName, email, phoneNumber);
		techAvailability = availability;
		
	}
	
	public Availability getAvailability() {
		return techAvailability;
	}
	
    @Override
    public String toString() {
        return String.format("NailTech,%s,%s,%s,%s,%s,%s", getUserId(), getPassword(), getFirstName(), getLastName(), getEmail(), getPhoneNumber());
    }
    
    public static NailTech parseNailTechString(String techString)
    {
    	if (!techString.contains("NailTech"))
    	{
    		return null;
    	}
    	String[] splitLine = techString.split(",");
    	return new NailTech(splitLine[0], splitLine[1], splitLine[2], splitLine[3], splitLine[4], splitLine[5]);
    }

}
