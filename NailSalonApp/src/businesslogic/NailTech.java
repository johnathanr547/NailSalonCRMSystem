package businesslogic;

/**
 * A nail tech at the business.
 * 
 * @author Blue Ridge Consulting
 * @version 1.0
 * GenAI statement: Generative AI was used in the creation of this app.
 */
public class NailTech extends User {
	
	/**
	 * Construct the nail tech object.
	 * @param userId - the nail tech's user object.
	 * @param password - the nail tech's password.
	 * @param firstName - the first name of the nail tech.
	 * @param lastName - the last name of the nail tech.
	 * @param email - the nail tech's email.
	 * @param phoneNumber - the tech's phone number.
	 */
	public NailTech(int userId, String password, String firstName, String lastName, String email,
			String phoneNumber) {
		super(userId, password, firstName, lastName, email, phoneNumber);	
	}
	
    /**
     * Parse/load a nail tech from a string.
     * @param techString - the string to load the nail tech from.
     * @return a new nail tech object, or null if the nail tech couldn't be loaded.
     */
    public static NailTech parseNailTechString(String techString)
    {
    	if (!techString.contains("NailTech"))
    	{
    		return null;
    	}
    	String[] splitLine = techString.split(",");
    	return new NailTech(Integer.parseInt(splitLine[1]), splitLine[2], 
    			splitLine[3], splitLine[4], splitLine[5], splitLine[6]);
    }
	
    @Override
    public String toString() {
        return String.format("NailTech,%d,%s,%s,%s,%s,%s", getUserId(), 
        		getPassword(), getFirstName(), getLastName(), getEmail(), getPhoneNumber());
    }
}
