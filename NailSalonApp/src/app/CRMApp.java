package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import businesslogic.Availability;
import businesslogic.User;
import businesslogic.Client;
import businesslogic.NailTech;

/**
 * Primary runner of nail salon CRM app.
 * 
 * @author Blue Ridge Consulting
 * @version 1.0
 * Used:
 * - https://www.w3schools.com/java/java_files_read.asp
 * - https://www.w3schools.com/java/java_fileinputstream.asp
 * - https://www.w3schools.com/java/java_bufferedreader.asp
 * - https://www.w3schools.com/java/java_bufferedwriter.asp
 * - https://www.geeksforgeeks.org/java/filewriter-class-in-java/
 * - https://stackoverflow.com/questions/50586627/java-lang-nullpointexception-using-filewriter
 * - https://forums.oracle.com/ords/apexds/post/why-does-this-filewriter-create-a-null-pointer-help-5290
 */
public class CRMApp {
	
	private String clientFileName;
	private String techFileName;
	private ArrayList<Client> loadedClients;
	private ArrayList<NailTech> loadedTechs;
	private Scanner in;
	private int maxUserID;
	private User currentUser;
	
	public CRMApp(String clientFileName, String techFileName)
	{
		this.clientFileName = clientFileName;
		this.techFileName = techFileName;
		this.in = new Scanner(System.in);
		this.maxUserID = 0;
		loadedClients = new ArrayList<>();
		loadedTechs = new ArrayList<>();
		try {
			BufferedReader clientReader = new BufferedReader(new FileReader(clientFileName));
			String currentReaderLine = clientReader.readLine();
			while (currentReaderLine != null)
			{
				loadedClients.add(Client.parseClientString(currentReaderLine));
				currentReaderLine = clientReader.readLine();
			}
			clientReader.close();

		} catch (IOException e) {
			System.out.println("Couldn't read client data file!\n");
		}
		try {
			BufferedReader techReader = new BufferedReader(new FileReader(techFileName));
			String currentReaderLine = techReader.readLine();
			while (currentReaderLine != null)
			{
				loadedTechs.add(NailTech.parseNailTechString(currentReaderLine));
				currentReaderLine = techReader.readLine();
			}
			techReader.close();
		} catch (IOException e) {
			System.out.println("Couldn't read nail tech data file!\n");
		}
		System.out.printf("Loaded %d clients and %d nail techs!\n\n", loadedClients.size(), loadedTechs.size());
		for (Client client : this.loadedClients)
		{
			if (client.getUserId() >= maxUserID)
			{
				maxUserID = client.getUserId() + 1;
			}
		}
		for (NailTech tech : this.loadedTechs)
		{
			if (tech.getUserId() >= maxUserID)
			{
				maxUserID = tech.getUserId() + 1;
			}
		}
		loginFlow();
		in.close();
	}
	
	public void loginFlow()
	{
		if (loadedClients.size() == 0 && loadedTechs.size() == 0)
		{
			System.out.println("No users found! Please create a user...\n");
			addUserFlow();
		}
		boolean successfulLogin = false;
		User foundUser = null;
		while (!successfulLogin)
		{
			Integer userID = null;
			while (userID == null)
			{
				System.out.println("\nPlease enter a User ID:");
				try
				{
					userID = Integer.parseInt(this.in.next());
				}
				catch (NumberFormatException e)
				{
					System.out.println("\nInvalid user ID format!");
				}
			}
			for (Client client : this.loadedClients)
			{
				if (client.getUserId() == userID)
				{
					foundUser = client;
				}
			}
			for (NailTech tech : this.loadedTechs)
			{
				if (tech.getUserId() == userID)
				{
					foundUser = tech;
				}
			}
			if (foundUser != null)
			{
				System.out.println("\nPlease enter password for user:");
				String password = this.in.next();
				if (password.equals(foundUser.getPassword()))
				{
					System.out.println("\nUser authenticated successfully.");
					successfulLogin = true;
				}
				else
				{
					System.out.println("\nInvalid password.");
					foundUser = null;
				}
			}
			else
			{
				System.out.printf("\nNo user with ID %d found! Please try again.", userID);
			}
		}
		System.out.printf("\nWelcome, %s %s.\n\n", foundUser.getFirstName(), foundUser.getLastName());
		this.currentUser = foundUser;
		if (this.currentUser.getClass().equals(Client.class))
		{
			clientsideMenu();
		}
		else
		{
			techsideMenu();
		}
	}
	
	
	public void clientsideMenu()
	{
		System.out.println("What would you like to do?\n");
		System.out.println("Options are:\n"
				+ "1) Book/Manage Appointments\n"
				+ "2) View Upcoming Appointments\n"
				+ "3) Update User Info\n"
				+ "4) Quit\n");
		Integer selection = null;
		while (selection == null || (!selection.equals(1) && !selection.equals(2) 
				&& !selection.equals(3) && !selection.equals(4)))
		{
			System.out.println("Please enter your selection...");
			try
			{
				selection = Integer.parseInt(this.in.next());
			} catch (NumberFormatException e)
			{
				
			}
		}
		switch(selection) {
		case(1):
			bookManageAppointmentsClient();
			clientsideMenu();
			break;
		
		case(2):
			viewUpcomingAppointments();
			clientsideMenu();
			break;
		
		case(3):
			updateUserInfo();
			clientsideMenu();
			break;
		
		default:
			saveSystemState();
		}
			
	}
	
	public void techsideMenu()
	{
		
	}
	
	public void bookManageAppointmentsClient()
	{
		
	}
	
	public void manageAppointmentsTech()
	{
		
	}
	
	public void viewUpcomingAppointments()
	{
		
	}
	
	public void updateUserInfo()
	{
		System.out.println("\nWhat user information would you like to update?");
		System.out.println("Options are:\n"
				+ "1) Update First Name\n"
				+ "2) Update Last Name\n"
				+ "3) Update Email\n"
				+ "4) Update Phone Number\n"
				+ "5) Update Password\n"
				+ "6) Go Back\n");
		Integer selection = null;
		while (selection == null 
				|| (!selection.equals(1) && !selection.equals(2) 
						&& !selection.equals(3) && !selection.equals(4)
						&& !selection.equals(5) && !selection.equals(6)))
		{
			System.out.println("Please enter a selection...");
			try
			{
				selection = Integer.parseInt(this.in.next());
			}
			catch (NumberFormatException e)
			{
			}
		}
		switch(selection) {
		case(1):
			System.out.println("Please enter a new first name:");
			String newFirst = this.in.next();
			String oldFirst = this.currentUser.getFirstName();
			this.currentUser.setFirstName(newFirst);
			System.out.printf("First name changed from %s to %s.\n", oldFirst, newFirst);
			break;
			
		case(2):
			System.out.println("Please enter a new last name:");
			String newLast = this.in.next();
			String oldLast = this.currentUser.getLastName();
			this.currentUser.setLastName(newLast);
			System.out.printf("Last name changed from %s to %s.\n", oldLast, newLast);
			break;
			
		case(3):
			System.out.println("Please enter a new email:");
			String newEmail = this.in.next();
			String oldEmail = this.currentUser.getEmail();
			this.currentUser.setEmail(newEmail);
			System.out.printf("Email changed from %s to %s.\n", oldEmail, newEmail);
			break;
			
		case(4):
			System.out.println("Please enter a new phone number:");
			String newPhone = this.in.next();
			String oldPhone = this.currentUser.getPhoneNumber();
			this.currentUser.setPhoneNumber(newPhone);
			System.out.printf("Phone number changed from %s to %s.\n", oldPhone, newPhone);
			break;
			
		case(5):
			System.out.println("Please enter a new password:");
			String newPassword = this.in.next();
			String oldPassword = this.currentUser.getPassword();
			this.currentUser.setPassword(newPassword);
			System.out.printf("Password changed from %s to %s.\n", oldPassword, newPassword);
			break;
			
		default:
			break;
		}
	}
	
	public void saveSystemState()
	{
		try {
			File clientFile = new File(this.clientFileName);
			BufferedWriter clientWriter = new BufferedWriter(new FileWriter(clientFile));
			for (Client client : this.loadedClients)
			{
				clientWriter.write(client.toString() + "\n");
			}
			clientWriter.close();
			System.out.printf("\nWrote %d clients out to %s.\n", this.loadedClients.size(), this.clientFileName);
		} catch (IOException | NullPointerException e) {
			System.out.println("\nUnable to save client information! Invalid save location!");
		}
		try {
			File techFile = new File(this.techFileName);
			BufferedWriter techWriter = new BufferedWriter(new FileWriter(techFile));
			for (NailTech tech : this.loadedTechs)
			{
				techWriter.write(tech.toString() + "\n");
			}
			techWriter.close();
			System.out.printf("Wrote %d nail techs out to %s.\n", this.loadedTechs.size(), this.techFileName);
		} catch (IOException | NullPointerException e) {
			System.out.println("Unable to save nail tech information! Invalid save location!");
		}
		System.out.println("Goodbye!");
		
	}
	
	public void addUserFlow()
	{
		System.out.println("Creating a new user...");
		Integer userSelection = null;
		while (userSelection == null)
		{
			System.out.println("Please enter type of user:\n1) Client\n2) Employee\n");
			try {
				userSelection = Integer.parseInt(this.in.next());
			} 
			catch (NumberFormatException e)
			{
			}
			if (userSelection == null || (userSelection != 1 && userSelection != 2))
			{
				System.out.println("Invalid selection, please try again.\n");
				userSelection = null;
			}
		}
		System.out.println("Please enter the user's first name:");
		String firstName = this.in.next();
		System.out.println("\nPlease enter the user's last name:");
		String lastName = this.in.next();
		System.out.println("\nPlease enter the user's email address:");
		String email = this.in.next();
		System.out.println("\nPlease enter the user's phone number:");
		String phoneNum = this.in.next();
		System.out.println("\nPlease enter a password that the user will use to log in:");
		String password = this.in.next();
		if (userSelection == 1)
		{
			Client newClient = new Client(maxUserID++, password, firstName, lastName, email, phoneNum);
			this.loadedClients.add(newClient);
		}
		else
		{
			NailTech newTech = new NailTech(maxUserID++, password, firstName, lastName, email, phoneNum);
			this.loadedTechs.add(newTech);
		}
		System.out.println(String.format("\nCreated new user %s %s, with email %s and phone number %s.\n", 
				firstName, lastName, email, phoneNum));
		System.out.printf("Your user ID is %d!\nRemember it, because you'll need it and your password to log in!\n\n", maxUserID - 1);
	}
}
