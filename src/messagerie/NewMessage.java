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

public class NewMessage extends JFrame implements ActionListener {
	
	
	Client newc;
	private static final long serialVersionUID = 1L;
	JFrame window;
	JTextField recipient, object;
	JTextArea mail;
	JLabel labelRecipient, labelObject, labelSender, sender;
	JButton send;
	
    private ObjectOutputStream os = null;
    private ObjectInputStream is = null;
	
	NewMessage(Client client)
	{
		this.newc=client;
		/* Création de la fenetre */	
		this.window = new JFrame();
		//Définit un titre pour notre fenêtre
		this.window.setTitle("Mail");
		//Définit sa taille : 400 pixels de large et 100 pixels de haut
		this.window.setSize(800, 200);
		//Nous demandons maintenant à notre objet de se positionner au centre
		this.window.setLocationRelativeTo(null);
		//Termine le processus lorsqu'on clique sur la croix rouge
		//this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Et enfin, la rendre visible        
		
		/* Interface écrire un nouveau message */
	
		JPanel b2 = new JPanel();
	    this.sender = new JLabel(client.pseudo);
		this.labelSender = new JLabel("Sender :     ");
		b2.setLayout(new BoxLayout(b2, BoxLayout.LINE_AXIS));
		b2.add(this.labelSender); 
		b2.add(this.sender); 
		
		
		JPanel b1 = new JPanel();
		this.recipient = new JTextField();
		this.labelRecipient = new JLabel("Recipient : ");
		b1.setLayout(new BoxLayout(b1, BoxLayout.LINE_AXIS));
		b1.add(this.labelRecipient);  
	    b1.add(this.recipient);  
	    

	  
	    JPanel b3 = new JPanel();
	    this.object = new JTextField();
		this.labelObject = new JLabel("Object :      ");
		b3.setLayout(new BoxLayout(b3, BoxLayout.LINE_AXIS));
		b3.add(this.labelObject); 
		b3.add(this.object); 
		
		JPanel b5 = new JPanel();
	    this.mail = new JTextArea();		
		b5.setLayout(new BoxLayout(b5, BoxLayout.LINE_AXIS));
		b5.add(this.mail); 
	    
		JPanel b6 = new JPanel();
	    this.send = new JButton("send");		
		b6.setLayout(new BoxLayout(b6, BoxLayout.LINE_AXIS));
		b6.add(this.send); 
		
		this.send.addActionListener(this);
		
		
	    JPanel b4 = new JPanel();
	    //On positionne maintenant ces trois lignes en colonne
	    b4.setLayout(new BoxLayout(b4, BoxLayout.PAGE_AXIS));
	    b4.add(b2);
	    b4.add(b1);
	    b4.add(b3);
	    b4.add(b5);
	    b4.add(b6);
		
	    this.window.getContentPane().add(b4);
		this.window.setVisible(true);
		
		
	}
	 
	 
		public void actionPerformed(ActionEvent event)
	{
 	
 	//exp,dest,obj,mess
 	String tab[]=new String[5];
 	tab[0]="remplirTabNewMess";
 	tab[1]=recipient.getText(); //Recuperer texte dest
 	tab[2]=sender.getText(); //
 	tab[3]=mail.getText(); //Recup message
 	tab[4]=object.getText();
 	
 	
 	ArrayList reponse=null;
 	
 	System.out.println("01. -> Sending Command (" + tab + ") to the server...");
 	 this.send(tab, this.newc);
 	 
      try{
              reponse = (ArrayList) receive(this.newc);
              System.out.println("05. <- The Server responded with: ");
              System.out.println("    <- " + reponse);
              
              String tab1[]=new String[7];
              tab1=(String[]) reponse.get(0);
              
              System.out.println(tab1[0]);
              System.out.println(tab1[1]);
              System.out.println(tab1[2]);
              System.out.println(tab1[3]);
              System.out.println(tab1[4]);
              System.out.println(tab1[5]);
              System.out.println(tab1[6]);
              
             
              System.out.println("Close window");
              this.window.dispose();
              
              
      }
      catch (Exception e){
              System.out.println("XX. There was an invalid object sent back from the server");
      }
      System.out.println("06. -- Disconnected from Server.");
 	
 }
		
		
		// method to send a generic object.
	    private void send(Object o, Client client) {
	                try {
	                    System.out.println("02. -> Sending an object...");
	                    client.os.writeObject(o);
	                    client.os.flush();
	                } 
	            catch (Exception e) {
	                    System.out.println("XX. Exception Occurred on Sending:" +  e.toString());
	                }
	    }

	    // method to receive a generic object.
	    private Object receive(Client client) 
	    {
	                Object o = null;
	                try {
	                        System.out.println("03. -- About to receive an object...");
	                    o = client.is.readObject();
	                    System.out.println("04. <- Object received...");
	                } 
	            catch (Exception e) {
	                    System.out.println("XX. Exception Occurred on Receiving:" + e.toString());
	                }
	                return o;
	    }
	 
	 }