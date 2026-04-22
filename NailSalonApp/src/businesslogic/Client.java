package businesslogic;

/**
 * A client of the business.
 * 
 * @author Blue Ridge Consulting
 * @version 1.0
 */
public class Client extends User {

	public Client(String userId, String password, String firstName, String lastName, String email, String phoneNumber) {
		super(userId, password, firstName, lastName, email, phoneNumber);
	}
	
    @Override
    public String toString() {
        return String.format("Client,%s,%s,%s,%s,%s,%s", 
        		getUserId(), getPassword(), getFirstName(),
        		getLastName(), getEmail(), getPhoneNumber());
    }
    
    public static Client parseClientString(String clientString)
    {
    	if (!clientString.contains("Client"))
    	{
    		return null;
    	}
    	String[] splitLine = clientString.split(",");
    	return new Client(splitLine[0], splitLine[1], splitLine[2], splitLine[3], splitLine[4], splitLine[5]);
    }
	
}
