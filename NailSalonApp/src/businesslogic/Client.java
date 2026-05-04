package businesslogic;

/**
 * A client of the business.
 * 
 * @author Blue Ridge Consulting
 * @version 1.0
 * GenAI statement: Generative AI was used in the creation of this app.
 */
public class Client extends User {
	
	private String address;

	/**
	 * Create the client object.
	 * @param userId - the ID of the user.
	 * @param password - the client's password.
	 * @param firstName - the client's first name.
	 * @param lastName - the client's last name.
	 * @param email - the client's email.
	 * @param phoneNumber - the client's phone number.
	 * @param address - the client's address. Only necessary if techs need to travel to the client.
	 */
	public Client(int userId, String password, String firstName, 
			String lastName, String email, String phoneNumber, String address) {
		super(userId, password, firstName, lastName, email, phoneNumber);
		this.address = address;
	}
	
	/**
	 * Get the client's address.
	 * @return the client's address. Might be the empty string if no address is found.
	 */
	public String getAddress()
	{
		return this.address;
	}
	
	/**
	 * Set the client's address.
	 * @param address - the client's new address.
	 */
	public void setAddress(String address)
	{
		this.address = address;
	}
	
    /**
     * Parse/load a client from a string.
     * @param clientString - the string to load the client from.
     * @return a new client object, or null if the client couldn't be loaded.
     */
    public static Client parseClientString(String clientString)
    {
    	if (!clientString.contains("Client"))
    	{
    		return null;
    	}
    	String[] splitLine = clientString.split(",");
    	return new Client(Integer.parseInt(splitLine[1]), splitLine[2], 
    			splitLine[3], splitLine[4], splitLine[5], splitLine[6], splitLine[7]);
    }
	
    @Override
    public String toString() {
    	if (address.equals("") || address.equals(null))
    	{
    		this.address = " ";
    	}
        return String.format("Client,%d,%s,%s,%s,%s,%s,%s", 
        		getUserId(), getPassword(), getFirstName(),
        		getLastName(), getEmail(), getPhoneNumber(), getAddress());
    }
}
