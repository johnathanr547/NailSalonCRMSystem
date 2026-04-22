package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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
	
	public CRMApp(String clientFileName, String techFileName)
	{
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
			
			
	}
}
