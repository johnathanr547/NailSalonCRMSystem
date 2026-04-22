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
        return "User{" +
                "userId='" + getUserId() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", phoneNumber='" + getPhoneNumber() + '\'' +
                '}';
    }

}
