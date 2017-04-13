import java.io.*;
/**********************************************************************/
// definir chaque action à faire suivant le bouton appuye
abstract class Action{
	protected ReseauFerroviere reseauFerroviere;
	public Action(ReseauFerroviere m){
		reseauFerroviere=m;
	}
	abstract void act();	
}



/**************************************************************/


//definition class automate

public class Automate{

	//Definition des etats
	int nbEtats=Etat.nbEtats;
	int nbEvent=Etat.nbEvent;
	
	int etat_0=Etat.etat_0;
	int etat_1=Etat.etat_1;
	int etat_2=Etat.etat_2;
	int etat_3=Etat.etat_3;
	int etat_4=Etat.etat_4;
	int etat_5=Etat.etat_5;
	int etat_6=Etat.etat_6;
	
	int event_0=Etat.event_0;
	int event_1=Etat.event_1;
	int event_2=Etat.event_2;
	int event_3=Etat.event_3;
	int event_4=Etat.event_4;
	int event_5=Etat.event_5;
	int event_6=Etat.event_6;
	

	//definition des matrices	
	int transit [][]=new int [nbEtats][nbEvent];
	Action script [][]=new Action [nbEtats][nbEvent];
	
	int etat_initial= etat_0;
	int etat_courant;
	
	ReseauFerroviere leReseauFerroviere=null;
	
	public Automate(ReseauFerroviere m){
		leReseauFerroviere=m;
		m.donnerAdresseAutomate(this);
		
		//////////////////////////////////////////////////////////
		for(int i=0;i<nbEtats;i++)
		{
			for(int j=0;j<nbEvent;j++)
			{
				transit[i][j]=-1; //etats impossibles
			}
		}
		
		//Transitions possibles
		
/*
		  MATRICE DE TRANSITION
		  |	 | Event0 | Event1 | Event2 | Event3 | Event4 |  EventAuto |  EventFinDeParcours  |
		  |Etat0 | Etat1  |   -1   |   -1   |   -1   |  Etat4 |      -1    |         Etat0        |
		  |Etat1 |   -1   |   -1   |   -1   |   -1   |   -1   |    Etat5   |         Etat0        |        
		  |Etat2 |   -1   |   -1   |   -1   |   -1   |   -1   |    Etat5   |         Etat6        |
		  |Etat3 |   -1   |   -1   |   -1   |   -1   |   -1   |    Etat5   |         Etat6        |
		  |Etat4 |   -1   |   -1   |   -1   |   -1   |   -1   |    Etat0   |         Etat0        |
		  |Etat5 |   -1   | Etat0  | Etat2  | Etat3  |   -1   |    Etat5   |         Etat6        |
		  |Etat6 | Etat6  | Etat6  | Etat6  | Etat6  |  Etat6 |    Etat6   |         Etat6        |
		  
		  	 
		  
		  
		 */
		
		//ligne par ligne                
                transit[etat_0][event_0]=etat_1;
		transit[etat_0][event_4]=etat_4;
		transit[etat_0][event_6]=etat_0;
				
		transit[etat_1][event_5]=etat_5;
		
		transit[etat_2][event_5]=etat_5;
		
		transit[etat_3][event_5]=etat_5;
		
		transit[etat_4][event_5]=etat_0;
		
		transit[etat_5][event_1]=etat_0;
		transit[etat_5][event_2]=etat_2;
		transit[etat_5][event_3]=etat_3;
		transit[etat_5][event_5]=etat_5;
		transit[etat_5][event_6]=etat_6;
		
		transit[etat_6][event_5]=etat_6;
                
                // ajout
                transit[etat_1][event_6]=etat_0;
                transit[etat_2][event_6]=etat_6;
                transit[etat_3][event_6]=etat_6;
                transit[etat_4][event_6]=etat_0;
                
                transit[etat_6][event_0]=etat_6;
                transit[etat_6][event_1]=etat_6;
                transit[etat_6][event_2]=etat_6;
                transit[etat_6][event_3]=etat_6;
                transit[etat_6][event_4]=etat_6;
                transit[etat_6][event_6]=etat_6;
		
		
		///////////////////////////////////////////////////////////
		for(int i=0;i<nbEtats;i++)
		{
			for(int j=0;j<nbEvent;j++)
			{
				script[i][j]=null; //etats impossibles
			}
		}
		
		script[etat_0][event_0]=new ActionDemarrer(leReseauFerroviere);
		script[etat_0][event_4]=new ActionChangerDeSens(leReseauFerroviere);
		script[etat_0][event_6]=new ActionStopper(leReseauFerroviere);

		
		script[etat_1][event_5]=new ActionMarche(leReseauFerroviere);
		script[etat_2][event_5]=new ActionMarche(leReseauFerroviere);
		script[etat_3][event_5]=new ActionMarche(leReseauFerroviere);
		script[etat_4][event_5]=new ActionStopper(leReseauFerroviere);
		script[etat_6][event_5]=new ActionStopper(leReseauFerroviere);
		
		script[etat_5][event_5]=new ActionMarche(leReseauFerroviere);
		script[etat_5][event_1]=new ActionStopper(leReseauFerroviere);
		script[etat_5][event_2]=new ActionAccelerer(leReseauFerroviere);
		script[etat_5][event_3]=new ActionRalentir(leReseauFerroviere);
		script[etat_5][event_6]=new ActionCrash(leReseauFerroviere);
		
		etat_courant=etat_initial;
		
	}//Automate
	
	void envoyer (int ev){
		int etatSuivant;
		Action action;
		//System.out.println("Etat courant avant changement etat: "+etat_courant);
		//System.out.println("Ev : "+ ev);
		etatSuivant=transit[etat_courant][ev];
		//System.out.println("Etat Suivant: "+etatSuivant);
		if (etatSuivant!=-1){
			//leReseauFerroviere.afficherEtat(etat[etatSuivant]);
			action=script[etat_courant][ev];
			if (action!=null)
				leReseauFerroviere.traiterAction(action);
			etat_courant=etatSuivant;
		}
		else 
			System.out.println("\n/#################################/\n WARNING OPERATION NON PERMISE\n/#################################/\n");
		if(etat_courant != etat_6) System.out.println("Etat Courant: "+etat_courant+"\n"); // le if est pour empecher que le terminal soit noyé sous les println en cas e crash		
		
	}//envoyer*/
        
        void reset(){ // remet à l'état initial la machine d'état
               etat_initial = etat_0;
               etat_courant = etat_0;
        }
	
}//class Automate



class ActionDemarrer extends Action{
	public ActionDemarrer (ReseauFerroviere m)
	{
		super(m);
	}
	void act(){
		System.out.println("Automate : <Demande de Demarrage>");
		reseauFerroviere.trainADemarrer();
	}
}//Action demarrer

class ActionMarche extends Action{
	public ActionMarche (ReseauFerroviere m)
	{
		super(m);
	}
	void act(){
		System.out.println("Automate : <Demande de mise en Marche>");
		reseauFerroviere.trainMarche();
	}
}//AcctionMarche

class ActionStopper extends Action{
	public ActionStopper(ReseauFerroviere m)
	{
		super(m);
	}
	void act(){
		System.out.println("Automate : <Demande d'Arret>");
		reseauFerroviere.trainAStopper();
	}
}//ActionStopper

class ActionChangerDeSens extends Action{
	public ActionChangerDeSens (ReseauFerroviere m)
	{
		super(m);
	}
	void act(){
		System.out.println("Automate : <Demande de changement de Sens>");
		reseauFerroviere.trainAChangerDeSens();
	}
}//ActionChanger de sens

class ActionAccelerer extends Action{
	public ActionAccelerer (ReseauFerroviere m)
	{
		super(m);
	}
	void act(){
		System.out.println("Automate : <Demande d'Acceleration>");
		reseauFerroviere.trainAAccelerer();
	}
}//Action Accelerer

class ActionRalentir extends Action{
	public ActionRalentir(ReseauFerroviere m)
	{
		super(m);
	}
	void act(){
		System.out.println("Automate : <Demande de Freinage>");
		reseauFerroviere.trainARalentir();
	}
}//Action Ralentir

class ActionCrash extends Action{
	public ActionCrash(ReseauFerroviere m)
	{
		super(m);
	}
	void act(){
		System.out.println("Automate : <Demande de Crash>");
		reseauFerroviere.trainACrasher();
	}
}//Acction crash