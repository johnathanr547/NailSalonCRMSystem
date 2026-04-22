package businesslogic;

/**
 * A class representing a user of the system.
 * 
 * @author Blue Ridge Consulting
 * @version 1.0
 */
public class User {

    private int userId;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
  
    public User(int userId, String password, String firstName, String lastName,
                String email, String phoneNumber) {
        this.userId = userId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return String.format("User,%s,%s,%s,%s,%s,%s", getUserId(), getPassword(), getFirstName(), getLastName(), getEmail(), getPhoneNumber());
    }
}