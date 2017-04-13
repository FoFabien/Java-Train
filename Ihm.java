import javax.swing.*;

import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.io.*;



public class Ihm extends JFrame{
	private static int vitesseMax=160;
	private static int vitesseArret=10;
			
	JPanel rowBouton=new JPanel();
	JPanel message=new JPanel();
	
	JLabel labelVitesseActuelle= new JLabel("Vitesse actuelle : 0 km/h");
	JLabel labelVitesseMaximaleDArret= new JLabel("Vitesse d'arret : " +vitesseArret);
	JLabel labelVitesseMaximale= new JLabel("Vitesse max : " +vitesseMax);
	
	private static JButton boutonDemarrer = new JButton("Demarrer");
	JButton boutonStopper = new JButton("Stopper");
	JButton boutonAccelerer = new JButton("Accelerer");
	JButton boutonRalentir = new JButton("Ralentir");
	JButton boutonChangerDeSens = new JButton("ChangerDeSens");
	

	private ReseauFerroviere leReseauFerroviere;

	
	public Ihm (ReseauFerroviere leReseauFerroviere)
	{
            super("IHM du Train");
            System.out.println("IHM : <Start>");
            this.leReseauFerroviere=leReseauFerroviere;
            leReseauFerroviere.donnerAdresseIhm(this);
            setSize(200,500);
            addWindowListener(new DelegueFenetre());
            GridLayout layout=new GridLayout(2,1,5,5);
            Container panel=getContentPane();
            panel.setLayout(layout);
            labelVitesseMaximaleDArret.setText("Vitesse d'arret : " +vitesseArret+ " km/h");
            labelVitesseMaximale.setText("Vitesse max : " +vitesseMax+" km/h");
            //Creation du layout des boutons et des listeners
            GridLayout layoutBouton=new GridLayout(5,1,5,5);
            rowBouton.setLayout(layoutBouton);
            rowBouton.add(boutonDemarrer);
            boutonDemarrer.addActionListener(new DelegueBoutonD());
            rowBouton.add(boutonStopper);
            boutonStopper.addActionListener(new DelegueBoutonS());
            rowBouton.add(boutonAccelerer);
            boutonAccelerer.addActionListener(new DelegueBoutonA());
            rowBouton.add(boutonRalentir);
            boutonRalentir.addActionListener(new DelegueBoutonR());
            rowBouton.add(boutonChangerDeSens);
            boutonChangerDeSens.addActionListener(new DelegueBoutonCDS());
            panel.add(rowBouton);
		
            // Creation affichage des infos sur le train
            Font police = new Font("Verdana", Font.BOLD,12);
            labelVitesseActuelle.setFont(police);
            labelVitesseMaximaleDArret.setFont(police);
            labelVitesseMaximale.setFont(police);
            labelVitesseActuelle.setHorizontalAlignment(JLabel.LEFT);
            labelVitesseMaximaleDArret.setHorizontalAlignment(JLabel.LEFT);
            labelVitesseMaximale.setHorizontalAlignment(JLabel.LEFT);
            message.add(labelVitesseActuelle);
            message.add(labelVitesseMaximaleDArret);
            message.add(labelVitesseMaximale);
            panel.add(message);
            setContentPane(panel);
            setVisible(true);	
	}//IHMResau
	
public void changeAffichageVitesse(int valeur)
{
	String affichage_vitesse=new String("Vitesse actuelle : "+valeur+" km/h");
	labelVitesseActuelle.setText(affichage_vitesse);
}//changeAffichageVitesse

	

class DelegueFenetre extends WindowAdapter{
	public void windowClosing(WindowEvent e){
		System.out.println("IHM : <Close>");
		System.exit(0);
	}
}//class DelegueFenetre

class DelegueBoutonA implements ActionListener{
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("IHM : <Accelerer>");
		leReseauFerroviere.accelere();
		leReseauFerroviere.auto();
	}
}//class DelegueBoutonA
class DelegueBoutonD implements ActionListener{
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("IHM : <Demarrer>");
		leReseauFerroviere.demarre();
		leReseauFerroviere.auto();
	}
}//class DelegueBoutonD
class DelegueBoutonS implements ActionListener{
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("IHM : <Stopper>");
		leReseauFerroviere.stoppe();		
	}
}//class DelegueBoutonS
class DelegueBoutonR implements ActionListener{
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("IHM : <Ralentir>");	
		leReseauFerroviere.ralenti();
		leReseauFerroviere.auto();
	}
}//class DelegueBoutonR
class DelegueBoutonCDS implements ActionListener{
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("IHM : <Changer de Sens>");	
		leReseauFerroviere.changeDeSens();
		leReseauFerroviere.auto();
	}
 }//class DelegueBoutonCDS
}//class ihm reseau ferroviere
