package messagerie;


import java.net.*;
import java.util.ArrayList;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client extends JFrame implements ActionListener {
        
    private static int portNumber = 5050;
     private Socket socket = null;
    ObjectOutputStream os = null;
    ObjectInputStream is = null;   
    public String pseudo =null;
    public static String[] arg =null;
    ArrayList mess = null;
    JFrame window;
	JTextField mail, pass;
	JLabel labelMail, labelPass; 
	JButton connexion;
	private String info[]=new String[2];
    private String reponse=null;
    
	
	//INTERFACE
	public void window()
	{
		//Creation of window
		this.window = new JFrame();
		//Window title
		this.window.setTitle("Mail");
		//Size of window
		this.window.setSize(800, 200);
		//window in the center
		this.window.setLocationRelativeTo(null);
		//End of Application if red cross
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
		
		/* Connection Interface */
		this.window.setLayout(new GridLayout(3,2));
		
		this.mail = new JTextField();
		this.labelMail = new JLabel("Identifiant");
		  
		this.pass = new JTextField();
		this.labelPass = new JLabel("Password");
		  

		this.connexion = new JButton("Connexion");
		this.connexion.addActionListener(this); //Action of button
		  
		this.window.getContentPane().add(this.labelMail);
		this.window.getContentPane().add(this.mail);
		this.window.getContentPane().add(this.labelPass);
		this.window.getContentPane().add(this.pass);
		this.window.getContentPane().add(this.connexion);
		
		//Make the window visible
		this.window.setVisible(true);
	}
	
    

	public void actionPerformed(ActionEvent e)
	{
		
		this.info[1]=this.pass.getText();	
		this.info[0]=this.mail.getText(); 
		
	}
		

    // the constructor expects the IP address of the server - the port is fixed
    public Client(String serverIP) {
            if (!connectToServer(serverIP)) {
                    System.out.println("XX. Failed to open socket connection to: " + serverIP);            
            }
    }

    
    private boolean connectToServer(String serverIP) {
            try { // open a new socket to the server 
                    this.socket = new Socket(serverIP,portNumber);
                    this.os = new ObjectOutputStream(this.socket.getOutputStream());
                    this.is = new ObjectInputStream(this.socket.getInputStream());
                    System.out.println("00. -> Connected to Server:" + this.socket.getInetAddress() 
                                    + " on port: " + this.socket.getPort());
                    System.out.println("    -> from local address: " + this.socket.getLocalAddress() 
                                    + " and port: " + this.socket.getLocalPort());
            } 
        catch (Exception e) {
                System.out.println("XX. Failed to Connect to the Server at port: " + portNumber);
                System.out.println("    Exception: " + e.toString());        
                return false;
        }
                return true;
    }

    
  
    private void connectionClient(){
    	String tab[]=new String[3];
    	tab[0]="connexion";
    	tab[1]=this.info[0];
    	tab[2]=this.info[1];
    	
    	System.out.println("01. -> Sending Command (" + tab + ") to the server...");
    	this.send(tab);
    	 
         try{
                 this.reponse = (String) receive();
                 System.out.println("05. <- The Server responded with: ");
                 System.out.println("    <- " + this.reponse); 
         }
         catch (Exception e){
                 System.out.println("XX. There was an invalid object sent back from the server");
         }
         System.out.println("06. -- Disconnected from Server.");
        
    }
    
    
        
    // method to send a generic object.
    private void send(Object o) {
                try {
                    System.out.println("02. -> Sending an object...");
                    os.writeObject(o);
                    os.flush();
                } 
            catch (Exception e) {
                    System.out.println("XX. Exception Occurred on Sending:" +  e.toString());
                }
    }

    // method to receive a generic object.
    private Object receive() 
    {
                Object o = null;
                try {
                        System.out.println("03. -- About to receive an object...");
                   
                    try {
                    	 o = is.readObject();
                    } catch (EOFException e)
                    {
                    	
                    }
                    System.out.println("04. <- Object received...");
                } 
            catch (Exception e) {
                    System.out.println("XX. Exception Occurred on Receiving:" + e.toString());
                }
                return o;
    }

    
    public static void main(String args[]) 
    {
           
            arg = args;
            
    		String rep=null, pseu=null;
    		System.out.println("** ASSIGNEMENT 2 : Mail Box **");
            if(args.length==1){
                    Client theApp = new Client(args[0]);
                    theApp.window();
                 
                   while(theApp.info[0]==null)
                    {
                    	System.out.print("");
                    }
                   System.out.print(theApp.info[0]);
                    
                    
                theApp.connectionClient();
                pseu=theApp.pseudo;
                rep=theApp.reponse;
               
               // GO WINDOW 
               if (rep.equals("oui"))
                {
            	   theApp.window.dispose();
            	
            	UserLogin neww = new UserLogin(theApp.info[0] );
                }
                }
            else
            {
                    System.out.println("Error: you must provide the address of the server");
                    System.out.println("Usage is:  java Client x.x.x.x  (e.g. java Client 192.168.7.2)");
                    System.out.println("      or:  java Client hostname (e.g. java Client localhost)");
            }    
            
            System.out.println("**. End of Application.");
    }



    private Object receive2(Client client) 
    {
                Object o = null;
                try {
                        System.out.println("03. -- About to receive an object...");
                        try {
                        	 o = client.is.readObject();
                        } catch (EOFException e)
                        {

                        }
                   
                    System.out.println("04. <- Object received...");
                } 
            catch (Exception e) {
                    System.out.println("XX. Exception Occurred on Receiving:" + e.toString());
                }
                return o;
    }





ArrayList RecupererMessage(Client client)
{
   	String[] commande = new String[]{"RecupArray","0"};
	
	
	System.out.println("01. -> Sending Command (" + commande + ") to the server...");
	 this.send(commande);
	
	 
     try{
             client.mess = (ArrayList) receive2(client);
             System.out.println("05. <- The Server responded with: ");
             System.out.println("    <- " + client.mess);
             
        
             
     }
     catch (Exception e){
             System.out.println("XX. There was an invalid object sent back from the server");
     }
     System.out.println("06. -- Disconnected from Server.");
     
     return this.mess;
}



}