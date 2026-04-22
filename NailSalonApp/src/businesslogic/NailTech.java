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
        return "User{" +
                "userId='" + getUserId() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", phoneNumber='" + getPhoneNumber() + '\'' +
                '}';
    }

}
