package messagerie;


import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;


public class UserLogin extends JPanel{

	
	private static final long serialVersionUID = 1L;
	JPanel addMailPan;
	JPanel panMail;
	JPanel pan;
	JScrollPane scrollPan;
	JButton addButton;
	JFrame window, window2;
	JButton newButton;
	JButton mail,exit,actualiser;
	JOptionPane jop1;
	String parts[];
	JLabel user, label,label1;
	String pseudo;
	ArrayList tab = null;
	int i;
	Client new3;
	
	public UserLogin(String pseudo){
		
		
		this.pseudo=pseudo;
		

		this.new3= new Client(messagerie.Client.arg[0]);
    	this.new3.pseudo=pseudo;
		tab = this.new3.RecupererMessage(this.new3);
		
		this.window = new JFrame(); //New window
		this.window.setTitle("Mail"); //Window Title
		//this.window.setLayout(new GridLayout(1,3)); //Set the Grid 6rows 2columns
		this.window.setSize(200, 200); // setSize 400*400
		this.window.setLocationRelativeTo(null);
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.window.setLayout(new BorderLayout());

		panMail = new JPanel(); //Set a buttonPanel
		panMail.setLayout(new BoxLayout(panMail, BoxLayout.PAGE_AXIS));
		scrollPan = new JScrollPane(panMail); //Scroll panel
		scrollPan.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//scrollPan.setPreferredSize(new Dimension(800, 60));
		//this.window.add(scrollPan);
		label1=new JLabel("LIST OF EMAILS");
		panMail.add(label1);
		
		int j=0;
		String tab1[]=new String[7];
		if (tab!= null)
		{
		for (i=0;i<tab.size();i++){
			
			tab1=(String[])tab.get(i);
			if (tab1[1].equals(pseudo))
			{
				
			//TEST
				JPanel panMe = new JPanel();
			panMe.setLayout(new GridLayout(2,2));
			if (j==0)
			{
				panMe.setBackground(Color.WHITE);
				j=1;
			}
			else j=0;
			
			JLabel l1 = new JLabel("From : "+tab1[0]);
			JLabel l2 = new JLabel("Obj : "+tab1[2]);
			JLabel l3 = new JLabel("");
			
			JButton newButton = new JButton(+(i+1) +"/ "  + "Read");
			newButton.addActionListener(new AddButtonListener());
			panMe.add(l1);
			panMe.add(l3);
			panMe.add(l2);
			panMe.add(newButton);
			panMail.add(panMe);
			
			panMail.updateUI();
		}}}
		
		pan = new JPanel(); 
		pan.setLayout(new GridLayout(2,3));
		
		mail=new JButton("Write a new Message"); 	 
		exit=new JButton("Exit"); 
		actualiser=new JButton("Refresh");
		user=new JLabel(pseudo);
		label=new JLabel("  Compte de messagerie de : ");
		JLabel vide =new JLabel("");
		pan.add(label);
		pan.add(user);
		pan.add(vide);
		pan.add(actualiser);
		pan.add(mail);
		pan.add(exit);
		
		JSplitPane split2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,pan,scrollPan);
		this.window.getContentPane().add(split2,BorderLayout.CENTER);
		
		//Display the window.
				this.window.pack();
				this.window.setVisible(true);
		
		exit.addActionListener(new ActionListener(){ // Run the function below
			public void actionPerformed(ActionEvent ae)
			{
				System.exit(0); // Close the program
			}
		});

		
		
		// Read the fields
		mail.addActionListener(new ActionListener(){ // Run the function below
			public void actionPerformed(ActionEvent ae)
			{
				String pse = user.getText();
            	messagerie.Client new2= new messagerie.Client(messagerie.Client.arg[0]);
            	new2.pseudo=pse;
                messagerie.NewMessage message = new messagerie.NewMessage(new2);
                
			}
		});
		
		// Actualiser
		actualiser.addActionListener(new ActionListener(){ // Run the function below
			public void actionPerformed(ActionEvent ae)
			{
				
				window.dispose();
				UserLogin neww=new UserLogin(user.getText() );
			}
		});

		
		
	}

	class AddButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			String label = e.getActionCommand();
			String parts[] = label.split("/");
			i = Integer.parseInt(parts[0]);
			i=i-1;
			
			String tab1[]=new String[7];
			tab1=(String[])tab.get(i);
			
			String str;
			str = "From : " + tab1[0] + "\n";
			str += "To : " + tab1[1] + "\n";
			str += "Object :" + tab1[2]+ "\n";
			str += "Date :" + tab1[5] + "\n";
			str += "\n" + tab1[3];
			jop1 = new JOptionPane();
			jop1.showMessageDialog(null, str , "E-mail", JOptionPane.PLAIN_MESSAGE);
			
}
}}


