package businesslogic;

/**
 * A nail tech at the business.
 * 
 * @author Blue Ridge Consulting
 * @version 1.0
 */
public class NailTech extends User {

	public NailTech(String userId, String password, String firstName, String lastName, String email,
			String phoneNumber) {
		super(userId, password, firstName, lastName, email, phoneNumber);
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
