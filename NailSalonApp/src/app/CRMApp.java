package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
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
 */
public class CRMApp {
	
	private ArrayList<Client> loadedClients;
	private ArrayList<NailTech> loadedTechs;
	private Scanner in;
	private int maxUserID;
	
	public CRMApp(String clientFileName, String techFileName)
	{
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
			System.out.println("Couldn't read client data file!");
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
			System.out.println("Couldn't read nail tech data file!");
		}
		System.out.printf("Loaded %d clients and %d nail techs!\n", loadedClients.size(), loadedTechs.size());
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
	}
	
	public void loginFlow()
	{
		if (loadedClients.size() == 0 && loadedTechs.size() == 0)
		{
			System.out.println("No users found! Please create a user...");
			addUserFlow();
		}
		System.out.println("Please enter a username:");
		String userName = this.in.next();
		System.out.println(userName);
		in.close();
	}
	
	public void addUserFlow()
	{
		System.out.println("Creating a new user...");
		Integer userSelection = null;
		while (userSelection == null)
		{
			System.out.println("Please enter user type:\n1) Client\n2) Employee");
			try {
				userSelection = Integer.parseInt(this.in.next());
			} 
			catch (NumberFormatException e)
			{
			}
			if (userSelection == null || (userSelection != 1 && userSelection != 2))
			{
				System.out.println("Invalid selection, please try again.");
				userSelection = null;
			}
		}
		System.out.println("Please enter the user's first name:");
		String firstName = this.in.next();
		System.out.println("Please enter the user's last name:");
		String lastName = this.in.next();
		System.out.println("Please enter the user's email address:");
		String email = this.in.next();
		System.out.println("Please enter the user's phone number:");
		String phoneNum = this.in.next();
		System.out.println("Please enter a password that the user will use to log in:");
		String password = this.in.next();
		if (userSelection == 1)
		{
			Client newClient = new Client(maxUserID++, password, firstName, lastName, email, phoneNum);
		}
		else
		{
			NailTech newTech = new NailTech(maxUserID++, password, firstName, lastName, email, phoneNum);
		}
		System.out.println(String.format("Created new user %s %s, with email %s and phone number %s.", 
				firstName, lastName, email, phoneNum));
		System.out.printf("Your user ID is %d!\nRemember it, because you'll need it and your password to log in!\n", maxUserID - 1);
	}
}
