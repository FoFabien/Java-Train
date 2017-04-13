import javax.swing.*;
import java.awt.event.*;
import javax.swing.JFrame;

public class MainClass extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MainClass(){
		addWindowListener (new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				dispose();
				System.out.println("Projet : <Close>");
				System.exit(0);
		}
			
	});
}
	public static void main(String args[]){
		System.out.println("Projet : <Start>");

		ReseauFerroviere leReseauFerroviere = new ReseauFerroviere();
		Automate lautomate = new Automate(leReseauFerroviere);
		Ihm ihm=new Ihm(leReseauFerroviere);	
                Fenetre fen=new Fenetre(leReseauFerroviere);
	}//main
	
}