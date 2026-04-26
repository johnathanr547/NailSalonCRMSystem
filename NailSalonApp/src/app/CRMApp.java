package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import businesslogic.Availability;
import businesslogic.User;
import businesslogic.Client;
import businesslogic.NailTech;
import businesslogic.Appointment;

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
 * - https://stackoverflow.com/questions/19509647/scanner-doesnt-see-after-space
 * - https://stackoverflow.com/questions/35495663/java-bug-input-automatically-go-to-next-line-without-input-yet
 * - https://stackoverflow.com/questions/13102045/scanner-is-skipping-nextline-after-using-next-or-nextfoo
 */
public class CRMApp {
	
	private String clientFileName;
	private String techFileName;
	private ArrayList<Client> loadedClients;
	private ArrayList<NailTech> loadedTechs;
	private Scanner in;
	private int maxUserID;
	private User currentUser;
	private String apptFileName;
	private ArrayList<Appointment> appointments = new ArrayList<>();

	/**
	 * Construct the CRMApp "object"
	 * @param clientFileName - the file to load/store clients in.
	 * @param techFileName - the file to load/store techs in.
	 */
	public CRMApp(String clientFileName, String techFileName, String apptFileName)
	{
		this.clientFileName = clientFileName;
		this.techFileName = techFileName;
		this.apptFileName = apptFileName;
		this.in = new Scanner(System.in);
		this.maxUserID = 0;
		loadedClients = new ArrayList<>();
		loadedTechs = new ArrayList<>();
		
		// Read in client data
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
		
		// Read in user data
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
		
		// Update maxUserID by counting client ids
		for (Client client : this.loadedClients)
		{
			if (client.getUserId() >= maxUserID)
			{
				maxUserID = client.getUserId() + 1;
			}
		}
		
		// Update maxUserID by counting user ids
		for (NailTech tech : this.loadedTechs)
		{
			if (tech.getUserId() >= maxUserID)
			{
				maxUserID = tech.getUserId() + 1;
			}
		}
		
		// Load appointments
		try {
		    BufferedReader apptReader = new BufferedReader(new FileReader(apptFileName));
		    String line = apptReader.readLine();
		    while (line != null) {
		        String[] parts = line.split(",");
		        if (parts.length == 4) {
		            int clientID = Integer.parseInt(parts[0]);
		            int techID = Integer.parseInt(parts[1]);
		            LocalDateTime dt = LocalDateTime.parse(parts[2]);
		            String name = parts[3];
		            appointments.add(new Appointment(techID, clientID, dt, name));
		        }
		        line = apptReader.readLine();
		    }
		    apptReader.close();
		} catch (IOException e) {
		    System.out.println("No existing appointments file found. Starting fresh.");
		}

		loginFlow();
		in.close();
	}
	
	/**
	 * "Flow" of a user logging in.
	 */
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
	
	/**
	 * The menu that client's will see. Allows them to book, manage and view appointments or update their info.
	 */
	public void clientsideMenu()
	{
		System.out.println("What would you like to do?\n");
		System.out.println("Options are:\n"
				+ "1) Book Appointments\n"
				+ "2) Manage Appointments\n"
				+ "3) View Upcoming Appointments\n"
				+ "4) Update User Info\n"
				+ "5) Quit\n");
		Integer selection = null;
		while (selection == null || selection < 1 || selection > 5)
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
			// Book an appointment.
			bookAppointmentsClient();
			clientsideMenu();
			break;
		
		case(2):
			// Manage appointments.
			manageAppointmentsClient();
			clientsideMenu();
			break;
		
		case(3):
			// View appointments.
			viewAppointmentsByMonth();
			clientsideMenu();
			break;
		
		case(4):
			// Update client info.
			updateUserInfoClient();
			clientsideMenu();
			break;
		
		case(5):
			// Quitting.
			saveSystemState();
			return;
		
		default:
			saveSystemState();
		}
			
	}
	
	/**
	 * The menu that techs will see.
	 */
	public void techsideMenu()
	{
		System.out.println("What would you like to do?\n");
		System.out.println("Options are:\n"
				+ "1) Manage Appointments\n"
				+ "2) View Upcoming Appointments\n"
				+ "3) Update User Info\n"
				+ "4) Quit\n");
		Integer selection = null;
		while (selection == null || selection < 1 || selection > 4)
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
			// Manage a tech's appointment.
			manageAppointmentsTech();
			techsideMenu();
			break;
		
		case(2):
			// View appointments by month.
			viewAppointmentsByMonth();
			techsideMenu();
			break;
		
		case(3):
			// Update tech info.
			updateUserInfoTech();
			techsideMenu();
			break;
		
		default:
			// Quit.
			saveSystemState();
		}
	}
	
	/**
	 * Book an appointment as a client.
	 */
	public void bookAppointmentsClient() {
	    System.out.println("\nBook Appointment");
	    System.out.println("Enter q to quit.");
	    System.out.println("Enter month number (1–12):");

	    String sel = in.next();
	    if (sel.equalsIgnoreCase("q")) return; // exits program if q is entered

	    int month;
	    
	    // check user input
	    try {
	        month = Integer.parseInt(sel);
	        if (month < 1 || month > 12) {
	            System.out.println("Invalid month.");
	            return;
	        }
	    } catch (NumberFormatException e) {
	        System.out.println("Invalid input.");
	        return;
	    }

	    System.out.println("Enter day of month:");
	    int day = in.nextInt();

	    System.out.println("Enter start time (HHMM, i.e. 1245):");
	    int time = in.nextInt();

	    LocalDateTime dt;
	    // transform user input into a datetime object
	    try {
	        dt = LocalDateTime.of(2026, month, day, time / 100, time % 100);
	    } catch (Exception e) {
	        System.out.println("Invalid date/time.");
	        return;
	    }

	    // randomly assign a technician. Can (should) be changed later)
	    NailTech assigned = loadedTechs.get((int)(Math.random() * loadedTechs.size()));
	    if (assigned == null) {
	        System.out.println("No nail technicians available.");
	        return;
	    }

	    Appointment appt = new Appointment(
	        assigned.getUserId(),
	        currentUser.getUserId(),
	        dt,
	        "Nail Service with " + currentUser.getFirstName() + " " + currentUser.getLastName()
	    );

	    appointments.add(appt);

	    System.out.printf(
	        "Appointment booked on %s with tech %s %s.\n\n",
	        dt.toString(),
	        assigned.getFirstName(),
	        assigned.getLastName()
	    );
	}

	
	public void manageAppointmentsClient()
	{
		
		
	}
	
	public void manageAppointmentsTech()
	{
		
	}
	
	public void viewAppointmentsByMonth() {
	    while (true) {
	        System.out.println("\nView Appointments by Month");
	        System.out.println("Enter q to quit.");
	        System.out.println("Enter month number (1–12):");

	        String sel = in.next();
	        if (sel.equalsIgnoreCase("q")) return;

	        int month;
	        try {
	            month = Integer.parseInt(sel);
	            if (month < 1 || month > 12) {
	                System.out.println("Invalid month.");
	                continue;
	            }
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid input.");
	            continue;
	        }

	        ArrayList<Appointment> monthAppts = getAppointmentsForMonth(month);
	        System.out.printf("\nAppointments for %s:\n", java.time.Month.of(month));

	        // Count per day
	        int[] counts = new int[32];
	        for (Appointment a : monthAppts) {
	            int d = a.getApptDate().getDayOfMonth();
	            counts[d]++;
	        }

	        for (int d = 1; d <= 31; d++) {
	            if (counts[d] > 0) {
	                System.out.printf("Day %d: %d appointments\n", d, counts[d]);
	            }
	        }

	        while (true) {
	            System.out.println("\nEnter a day to view details, b to go back:");
	            String dsel = in.next();

	            if (dsel.equalsIgnoreCase("b")) break;

	            int day;
	            try {
	                day = Integer.parseInt(dsel);
	            } catch (NumberFormatException e) {
	                System.out.println("Invalid day.");
	                continue;
	            }

	            ArrayList<Appointment> dayAppts = getAppointmentsForDay(month, day);
	            if (dayAppts.isEmpty()) {
	                System.out.println("No appointments on that day.");
	                continue;
	            }

	            System.out.printf("\n=== Appointments on %s %d ===\n",
	                java.time.Month.of(month), day);

	            for (Appointment a : dayAppts) {
	                Client c = getClientById(a.getClientID());
	                NailTech t = getTechById(a.getNailTechID());

	                System.out.printf(
	                    "Client: %s %s | Tech: %s %s | Time: %02d:%02d | Status: Scheduled\n",
	                    c.getFirstName(), c.getLastName(),
	                    t.getFirstName(), t.getLastName(),
	                    a.getApptDate().getHour(),
	                    a.getApptDate().getMinute()
	                );
	            }
	        }
	    }
	}

	public void updateUserInfoClient()
	{
		System.out.println("\nWhat user information would you like to update?");
		System.out.println("Options are:\n"
				+ "1) Update First Name\n"
				+ "2) Update Last Name\n"
				+ "3) Update Email\n"
				+ "4) Update Phone Number\n"
				+ "5) Update Password\n"
				+ "6) Update Address\n"
				+ "7) Go Back\n");
		Integer selection = null;
		while (selection == null 
				|| (!selection.equals(1) && !selection.equals(2) 
						&& !selection.equals(3) && !selection.equals(4)
						&& !selection.equals(5) && !selection.equals(6)
						&& !selection.equals(7)))
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
			
		case(6):
			System.out.print("Please enter a new address:\n");
			this.in.nextLine();
			String newAddress = this.in.nextLine();
			((Client) this.currentUser).setAddress(newAddress);
			System.out.printf("Address changed to %s\n", newAddress);
			break;
		default:
			break;
		}
	}
	
	public void updateUserInfoTech()
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
		// Write client information
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
		
		// Write nail tech information
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
		
		// Save appointments
		try {
		    File apptFile = new File(this.apptFileName);
		    BufferedWriter apptWriter = new BufferedWriter(new FileWriter(apptFile));
		    for (Appointment a : this.appointments) {
		        apptWriter.write(
		            a.getClientID()            + "," +
		            a.getNailTechID()          + "," +
		            a.getApptDate().toString() + "," +
		            a.getApptName()            + "\n"
		        );
		    }
		    apptWriter.close();
		    System.out.printf("Wrote %d appointments to %s.\n", this.appointments.size(), this.apptFileName);
		} catch (IOException | NullPointerException e) {
		    System.out.println("Unable to save appointment information! Invalid save location!");
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
		System.out.println("\nOptionally, please enter an address for this user.\n"
				+ "An address is only required for clients that a technician must travel to.\n");
		String address = this.in.next();
		if (userSelection == 1)
		{
			Client newClient = new Client(maxUserID++, password, firstName, lastName, email, phoneNum, address);
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
	
	
	// ------------------------------------------------------------
	// Appointment lookup helpers
	// ------------------------------------------------------------

	private ArrayList<Appointment> getAppointmentsForMonth(int month) {
	    ArrayList<Appointment> result = new ArrayList<>();
	    for (Appointment a : appointments) {
	        if (a.getApptDate().getMonthValue() == month) {
	            result.add(a);
	        }
	    }
	    return result;
	}

	private ArrayList<Appointment> getAppointmentsForDay(int month, int day) {
	    ArrayList<Appointment> result = new ArrayList<>();
	    for (Appointment a : appointments) {
	        if (a.getApptDate().getMonthValue() == month &&
	            a.getApptDate().getDayOfMonth() == day) {
	            result.add(a);
	        }
	    }
	    return result;
	}

	// ------------------------------------------------------------
	// User lookup helpers
	// ------------------------------------------------------------

	private Client getClientById(int id) {
	    for (Client c : loadedClients) {
	        if (c.getUserId() == id) {
	            return c;
	        }
	    }
	    return null;
	}

	private NailTech getTechById(int id) {
	    for (NailTech t : loadedTechs) {
	        if (t.getUserId() == id) {
	            return t;
	        }
	    }
	    return null;
	}

}
