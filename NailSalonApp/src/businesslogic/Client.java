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
	
}
