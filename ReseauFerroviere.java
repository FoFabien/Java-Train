import java.util.*;
import java.io.*;


public class ReseauFerroviere {
	private Ihm ihm;
	private Automate aut;
	private	Fenetre afficher_reseau;
	
	int demarrer = Etat.event_0;
	int stopper = Etat.event_1;
	int accelerer = Etat.event_2;
	int ralentir = Etat.event_3;
	int changerDeSens = Etat.event_4;
	int Auto = Etat.event_5;
	int crash = Etat.event_6;
	
	private int VitesseLimiteDarret =0;
	private int VitesseMinimale = 0;
	private int VitesseMaximale= 0;

	//int mode=Etat.event_0;
	
	public ReseauFerroviere(){
		System.out.println("Reseau : <Start>");
	}//ReseauFerroviere
	
	public void donnerAdresseIhm(Ihm ihm){
		this.ihm=ihm;
	}//donnerAdresseIhm
	
	public void donnerAdresseAutomate(Automate aut){
		this.aut=aut;
	}//donnerAdresseAutomate
        
        public Automate getAutomate(){
            return this.aut;
        }
	
	public void donnerAdresseFenetre(Fenetre fen){
		this.afficher_reseau=fen;
	}//donnerAdresseFenetre
	
	
	
	public void ajusterVitesse(int pas_speed)
	{
		VitesseMaximale=16*pas_speed;
		VitesseMinimale=pas_speed;
		VitesseLimiteDarret=pas_speed;
		
	}//ajusterVitesse
	

	public int getVitesseKMH(){
		return afficher_reseau.getVitesseKMH();
	}//getVitesseKMH
	
	public void refreshAffichageVitesse()
	{
		int vitesse=this.getVitesseKMH();
		ihm.changeAffichageVitesse(vitesse);
	}//refreshAffichageVitesse
	
	public void boutDePiste()
	{
		if(this.afficher_reseau.getVitesse()>VitesseLimiteDarret)
		{
			this.crash();
		}
		else
			this.stoppe();
	}//boutDePiste
	
	public void demarre(){
		aut.envoyer(demarrer);
	}//demarre
	
	public void accelere(){
		if(this.afficher_reseau.getVitesse()<=VitesseMaximale)
			aut.envoyer(accelerer);
		else
			System.out.println("Train : <Speed Limit>");
	}//accelere
	
	public void stoppe(){
		if(this.afficher_reseau.getVitesse()>VitesseLimiteDarret)
		{
			System.out.println("Train : <Too fast>");
			aut.envoyer(Auto);
		}
		else
			aut.envoyer(stopper);
	}//stoppe
	
	public void ralenti(){
		if(this.afficher_reseau.getVitesse()<=VitesseMinimale)
			System.out.println("Train : <Can't slow down");
		else
			aut.envoyer(ralentir);
	}//ralenti
	
	public void changeDeSens(){
		if(this.afficher_reseau.getSens()!="stop")
		{
			System.out.println("Train : <Can't change direction");
		}
		else 
		{
			aut.envoyer(changerDeSens);
		}
	}//changeDeSens
	
	public void auto(){
		aut.envoyer(Auto);
	}//auto
	
	public void crash(){
            aut.envoyer(crash);
	}//crash
	
	
	public void traiterAction(Action a)
	{
		a.act();
	}//traiterAction
	
	public void trainADemarrer()
	{
		System.out.println("Train : <Start>");
		this.afficher_reseau.demarrer();
		
	}//trainADemarrer
	
	public void trainAAccelerer()
	{
		System.out.println("Train : <Speed Up>");
		this.afficher_reseau.accelerer(); 
		
	}//trainAAccelerer
	
	public void trainARalentir()
	{
		System.out.println("Train : <Slow down>");
		this.afficher_reseau.ralentir(); 
		
	}//trainARalentir
	
	public void trainAStopper()
	{
		System.out.println("Train : <Stop>");
		this.afficher_reseau.stopper();
	}//trainAStopper
	
	public void trainAChangerDeSens()
	{
		System.out.println("Train : <Change direction>");
		if((this.afficher_reseau.getSens()=="stop"&&this.afficher_reseau.getSensPrecedent()=="run_positif"))
		{
			this.afficher_reseau.setSens("run_negatif");;
		}
		else if((this.afficher_reseau.getSens()=="stop"&&this.afficher_reseau.getSensPrecedent()=="run_negatif"))
		{
			this.afficher_reseau.setSens("run_positif");
		}
	}//trainAChangerDeSens
	
	public void trainMarche()
	{
		System.out.println("Train : <Speed Up>");
	}//trainMarche
	
	public void trainACrasher()
	{
                this.afficher_reseau.crasher();
		System.out.println("Train : <Crash>");
	}//trainACrasher
	
}//class ReseauFerroviere
