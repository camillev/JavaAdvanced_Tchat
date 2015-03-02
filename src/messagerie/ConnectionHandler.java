package messagerie;

import java.net.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList; 
import java.util.Calendar;
import java.io.*;

public class ConnectionHandler
{
	private Socket clientSocket = null;                         // Client socket object
	private ObjectInputStream is = null;                        // Input stream
	private ObjectOutputStream os = null;                        // Output stream
	private DateTimeService theDateService;

	private static String compte[][]=new String[][]{{"pseudo1","pseudo2"},{"pass1","pass2"}};

	private String tab[]=new String[3]; //Y REVENIR

	public static final String DATE_FORMAT_NOW = "dd/MM/yyyy HH:mm:ss";


	private static ArrayList<String[]> messages = new ArrayList<String[]>();


	// The constructor for the connection handler
	public ConnectionHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
		//Set up a service object to get the current date and time
		theDateService = new DateTimeService();
	}


	// Will eventually be the thread execution method - can't pass the exception back
	public void init() {
		try {
			this.is = new ObjectInputStream(clientSocket.getInputStream());
			this.os = new ObjectOutputStream(clientSocket.getOutputStream());
			while (this.readCommand()) {}
		} 
		catch (IOException e) 
		{
			System.out.println("XX. There was a problem with the Input/Output Communication:");
			e.printStackTrace();
		}
	}

	// Receive and process incoming string commands from client socket 
	private boolean readCommand() {

		try {
			tab =  (String[]) is.readObject();
		} 
		catch (Exception e){    // catch a general exception
			this.closeSocket();
			return false;
		}
		System.out.println("01. <- Received a String object from the client (" + tab + ").");

		// At this point there is a valid String object
		// invoke the appropriate function based on the command 

		String s=tab[0];

		// Connexion of the Client to the Server
		if (s.equalsIgnoreCase("connexion")){ 
			this.connexion(); 
		} 

		// Case of Send New Message
		if (s.equalsIgnoreCase("remplirTabNewMess")){
			this.remplirTabNewMess();
		}

		// Recuperation of all messages
		if (s.equalsIgnoreCase("RecupArray")){
			this.RecupArray();
		}


		return true;
	}


	private void RecupArray(){

		this.send(messages);
		messagerie.Server.con.closeSocket();

	}



	private void remplirTabNewMess(){

		//Add to the Array of the messages the new mail

		//Get hour and date
		Calendar date = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);

		String passage[]= new String[7];

		passage[0]=tab[2]; //sender
		passage[1]=tab[1]; //recep
		passage[2]=tab[4]; //obj
		passage[3]=tab[3]; //txt
		passage[4]="0";    //importance
		passage[5]=sdf.format(date.getTime()); //date
		passage[6]="1"; //non lu

		messages.add((String[])passage);
		this.send(messages);
		messagerie.Server.con.closeSocket();

	}



	private void connexion(){

		String pseudo=this.tab[1];
		String pass=this.tab[2];
		String reponse=null;
		int i=0;


		for (i=0;i<this.compte[0].length;i++)
		{

			if (pseudo.equals(this.compte[0][i]))
			{
				//Pseudo does exist
				System.out.println("The pseudo does exist");
				System.out.println("We want for password : " + this.compte[1][i]);

				if (pass.equals(this.compte[1][i]))
				{

					System.out.println("Succeed in connexion !");
					reponse="oui";
				}
				else System.out.println("Erreur of password");
			}
		}

		this.send(reponse);
		messagerie.Server.con.closeSocket();

	}

	
	// Send a generic object back to the client 
	private void send(Object o) {
		try {
			System.out.println("02. -> Sending (" + o +") to the client.");
			this.os.writeObject(o);
			this.os.flush();
		} 
		catch (Exception e) {
			System.out.println("XX." + e.getStackTrace());
		}
	}

	// Send a pre-formatted error message to the client 
	public void sendError(String message) { 
		this.send("Error:" + message);        //remember a String IS-A Object!
	}

	// Close the client socket 
	public void closeSocket() { //gracefully close the socket connection
		try {
			this.os.close();
			this.is.close();
			this.clientSocket.close();
		} 
		catch (Exception e) {
			System.out.println("XX. " + e.getStackTrace());
		}
	}
}