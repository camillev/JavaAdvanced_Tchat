package messagerie;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Window extends JFrame implements ActionListener{

	JFrame window;
	JTextField mail, pass;
	JLabel labelMail, labelPass;
	JButton connexion;
	
	public Window()
	{
		/* Création de la fenetre */	
		this.window = new JFrame();
		//Définit un titre pour notre fenêtre
		this.window.setTitle("Mail");
		//Définit sa taille : 400 pixels de large et 100 pixels de haut
		this.window.setSize(800, 200);
		//Nous demandons maintenant à notre objet de se positionner au centre
		this.window.setLocationRelativeTo(null);
		//Termine le processus lorsqu'on clique sur la croix rouge
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Et enfin, la rendre visible        
		
		/* Interface de connexion */
		this.window.setLayout(new GridLayout(3,2));
		
		this.mail = new JTextField();
		this.labelMail = new JLabel("Identifiant");
		  
		this.pass = new JTextField();
		this.labelPass = new JLabel("Password");
		  

		this.connexion = new JButton("Connexion");
		this.connexion.addActionListener(this); //Action du bouton
		  
		this.window.getContentPane().add(this.labelMail);
		this.window.getContentPane().add(this.mail);
		this.window.getContentPane().add(this.labelPass);
		this.window.getContentPane().add(this.pass);
		this.window.getContentPane().add(this.connexion);
		
		this.window.setVisible(true);
	}
	
	
	public void actionPerformed(ActionEvent e)
	{
		// //Action quand on appui sur le bouton // // CA MARCHE
		
		//this.mail.getText() <- identifiant
		//this.pass.getText() <- mot de passe
		
		//System.out.println(this.mail.getText());
		
		// // Lire un fichier // // CA MARCHE
		Scanner scanner = null;
		try {
			scanner = new Scanner(new FileReader("connexion.txt"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String str = null;
		String mot = null;
		while (scanner.hasNext()){
			//On récupère les infos mot a mot
			mot = scanner.next();
			if (mot.equals(this.mail.getText()))
			{
				//Le pseudo existe
				mot = scanner.next();
				System.out.println("pseudo existe");
				if (mot.equals(this.pass.getText()))
				{
					// // //Passer a la page SUIVANTE // // //
					System.out.println("Connexion réussi!!");
					
				}
				else System.out.println("Erreur de mot de passe");
			}
			else mot = scanner.next();
			
			//System.out.println(mot);
		}
		
		/*// // Ecrire dans un fichier // // CA MARCHE
		try {
			ecrireFichier("test.txt",this.mail.getText());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		
	}
	


	public void ecrireFichier(String nomFichier, String text) throws IOException
	{
		text= text + " ";
		FileWriter fw = new FileWriter(nomFichier, true);
		BufferedWriter output = new BufferedWriter(fw);
		output.write(text);
		output.flush();output.close();
		System.out.println("fichier crée");
		
		
	}
	
	
}
