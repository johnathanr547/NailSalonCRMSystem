package businesslogic;

/**
 * A class representing a user of the system.
 * 
 * @author Blue Ridge Consulting
 * @version 1.0
 * GenAI statement: Generative AI was used in the creation of this app.
 */
public class User {

    private int userId;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
  
    /**
     * Create a user.
     * @param userId - the user's ID.
     * @param password - the user's password.
     * @param firstName - the user's first name.
     * @param lastName - the user's last name.
     * @param email - the user's email.
     * @param phoneNumber - the user's phone number.
     */
    public User(int userId, String password, String firstName, String lastName,
                String email, String phoneNumber) {
        this.userId = userId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Get the user's ID.
     * @return the user's ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Get the user's password.
     * @return the user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the user's password.
     * @param password - the user's new password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the user's first name.
     * @return the user's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the user's first name.
     * @param firstName - the user's new first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get the user's last name.
     * @return the user's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the user's last name.
     * @param lastName - the user's new last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get the user's email.
     * @return the user's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the user's email.
     * @param email - the user's new email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the user's phone number.
     * @return the user's phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Set the user's phone number.
     * @param phoneNumber - the user's new phone number.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return String.format("User,%s,%s,%s,%s,%s,%s",
        		getUserId(), getPassword(), getFirstName(), getLastName(), getEmail(), getPhoneNumber());
    }
}
