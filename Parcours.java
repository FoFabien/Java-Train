import java.util.ArrayList;





class Parcours  {
// on stocke les parcours pour rails avec aiguillage
private ArrayList<Parcours> liste_parcours_aiguillage= new ArrayList<Parcours>(); 
// un parcours = succession de rails
private ArrayList<Rail> tableau_rail= new ArrayList<Rail>();
// le tableau précédent est traduit en coordonnées de points (x,y)
private ArrayList<Integer> coordonnees_parcoursX= new ArrayList<Integer>();
private ArrayList<Integer> coordonnees_parcoursY= new ArrayList<Integer>();
// un tableau d'aiguillage pour avoir "l'état" de 
private ArrayList<Aiguillage> tab_aiguillage= new ArrayList<Aiguillage>();

private int debut_x=0;
private int debut_y=0;
// si le parcours est ouvert/ferme
private String boucle_parcours="ferme";
private int nbr_rails;


Parcours(int x_depart,int y_depart,String type_parcours)
	{
	
	nbr_rails=0;
	debut_x=x_depart;
	debut_y=y_depart;
	
	if(type_parcours=="parcours_ouvert")
		{
                    this.makeCheminOuvert();
		}
	else if(type_parcours=="parcours_ferme")
		{
			this.makeCheminFerme();
		}
	else if(type_parcours=="parcours_perso")
		{
			setTypeBoucle("ouvert"); //par defaut le chemin à construire est ouvert
			this.add_rail("Horizontal","down");		
		}
	else if(type_parcours=="parcours_avec_aiguillage")
		{	
                    makeCheminAvecAiguillages();
                }
	else if(type_parcours=="parcours_aiguillage1_virage")
		{
                    makeParcoursAig1virage();
		}
	else if(type_parcours=="parcours_aiguillage1_droit")
		{
                    makeParcoursAig1droit();
		}

}

	public void add_rail(String type_rail,String sens)
	{
		if(nbr_rails==0)
		{
		Rail rail=new Rail(debut_x,debut_y,type_rail,sens);
		tableau_rail.add(rail);
		}
		else
		{
		// lors de l'ajout d'un nouveau rail, le suivant commence aux dernieres coordonnées du précédent 
		Rail rail=new Rail(tableau_rail.get(nbr_rails-1).getDerniereCoordX(),tableau_rail.get(nbr_rails-1).getDerniereCoordY(),type_rail,sens);
		tableau_rail.add(rail);
		}
		remplir_tableaux_coordonnees(this.tableau_rail,nbr_rails,"ajout");
		nbr_rails++;
	}
	
	public void delete_rail()
	{
		if(nbr_rails>1)
		{
		tableau_rail.remove(nbr_rails-1);
		nbr_rails--;
		remplir_tableaux_coordonnees(this.tableau_rail,nbr_rails,"suppression");
		}	
	}

	private void remplir_tableaux_coordonnees(ArrayList<Rail> from,int indice_rail,String type_remplissage)
	{
		if(type_remplissage=="ajout")
		{
		coordonnees_parcoursX.addAll(from.get(indice_rail).getTabCoordX());
		coordonnees_parcoursY.addAll(from.get(indice_rail).getTabCoordY());
		}
		else if(type_remplissage=="suppression")
		{
		int lastX=coordonnees_parcoursX.size();
		int lastY=coordonnees_parcoursY.size();
		this.coordonnees_parcoursX.subList(lastX-50,lastX).clear();
		this.coordonnees_parcoursY.subList(lastY-50,lastY).clear();
		}
	}
	
	public ArrayList<Rail> getTableauRails()
	{
	return tableau_rail;
	}
	
	public ArrayList<Integer> getCoordonneesParcoursX()
	{
	return coordonnees_parcoursX;
	}
	
	public ArrayList<Integer> getCoordonneesParcoursY()
	{
	return coordonnees_parcoursY;
	}
	
	public String getTypeDernierRail()
	{
		return (tableau_rail.get(nbr_rails-1).getOrientation()+" "+tableau_rail.get(nbr_rails-1).getSens());
	}
	public int getDernierePositionX()
	{
	return coordonnees_parcoursX.get(coordonnees_parcoursX.size()-1);
	}
	
	public int getDernierePositionY()
	{
	return coordonnees_parcoursY.get(coordonnees_parcoursY.size()-1);
	}
	
	public int getPremierePositionX()
	{
	return coordonnees_parcoursX.get(0);
	}
	
	public int getPremierePositionY()
	{
	return coordonnees_parcoursY.get(0);
	}
	
	public int getNombrePointAtracer()
	{
	// on trace tous les points, c'est à dire le parcours actuel et les parcours aiguillés
	int nbr_points=0;
	nbr_points=coordonnees_parcoursX.size();
	for(int i=0;i<liste_parcours_aiguillage.size();i++)
	{
	nbr_points+=liste_parcours_aiguillage.get(i).getNombrePointAtracer();
	}
	return nbr_points;
	}
	
	public int getPointXaTracer(int position)
	{
	int x;
	int taille=coordonnees_parcoursX.size();
		if(position<taille)
		{
		x=coordonnees_parcoursX.get(position);
		}
		else
		{	
			int i=0;
			int nouvelle_position=0;
			while((position>taille))
			{
			nouvelle_position=position-taille;
			taille+=this.liste_parcours_aiguillage.get(i).getNombrePointAtracer();
				if(position>=taille)
				{
				i++;
				nouvelle_position=position-taille;
				}
			}	
			x=liste_parcours_aiguillage.get(i).getPointXaTracer(nouvelle_position);
		}
	return x;
	}
	
	public int getPointYaTracer(int position)
	{
	int y;
	int taille=coordonnees_parcoursY.size();
		if(position<taille)
		{
		y=coordonnees_parcoursY.get(position);
		}
		else
		{	int i=0;
			int nouvelle_position=0;
			while((position>taille))
			{
			nouvelle_position=position-taille;
			taille+=this.liste_parcours_aiguillage.get(i).getNombrePointAtracer();
			
				if(position>=taille)
				{
				i++;
				nouvelle_position=position-taille;
				}
			}	
			y=this.liste_parcours_aiguillage.get(i).getPointYaTracer(nouvelle_position);
		}
	return y;
	}
	
	
	public boolean isAloop()
	{
	boolean reponse=false;
	if(boucle_parcours=="ferme")
	reponse=true;
	return reponse;
	}
	
	public void setTypeBoucle(String type_boucle)
	{
	boucle_parcours=type_boucle;	
	}
	
	public boolean testerLoop()
	{
	// test si le chemin est ferme ou ouvert
	boolean reponse=false;
	int first_x=this.getPremierePositionX();
	int first_y=this.getPremierePositionY();
	int last_x=this.getDernierePositionX();
	int last_y=this.getDernierePositionY();
	
	if((Math.abs((double)(first_x-last_x))<=1)&&(Math.abs(first_y-last_y)<=1))
	{
	reponse=true;
	}
	
	return reponse;	
	}
	
	public void changerAiguillage(int indice_rail,int indice_chemin)
	{
		coordonnees_parcoursX.clear();
		coordonnees_parcoursY.clear();
		for(int i=0;i<tableau_rail.size();i++)
		{
		remplir_tableaux_coordonnees(tableau_rail,i,"ajout");
		}
		if(indice_rail<(tab_aiguillage.size()))
		{
			tab_aiguillage.get(indice_rail).switchAiguillage();
			if(tab_aiguillage.get(indice_rail).getEtatAiguillage()=="droit")
			{
				for(int i=0;i<liste_parcours_aiguillage.get(indice_rail).getTableauRails().size();i++)
				{
				this.remplir_tableaux_coordonnees(liste_parcours_aiguillage.get(indice_rail).getTableauRails(),i,"ajout");
				}	
			}
			else if(tab_aiguillage.get(indice_rail).getEtatAiguillage()=="courbe")
			{
				for(int i=0;i<liste_parcours_aiguillage.get(indice_rail+1).getTableauRails().size();i++)
				{
					this.remplir_tableaux_coordonnees(liste_parcours_aiguillage.get(indice_rail+1).getTableauRails(),i,"ajout");
				}	
			}
		}
		else
		{
			liste_parcours_aiguillage.get(indice_chemin).changerAiguillage(indice_rail-1,indice_chemin);
		}
	}
	
	public void ajouterAiguillage(String chemin_aiguillage)
	{
		Aiguillage aig=new Aiguillage(this.coordonnees_parcoursX.size()-1,this.coordonnees_parcoursY.size()-1);
		tab_aiguillage.add(aig);
		Parcours parcours_aiguillage=new Parcours(this.getDernierePositionX(),this.getDernierePositionY(),chemin_aiguillage); // on fait un chemin pour le rail en arc de cercle
		this.liste_parcours_aiguillage.add(parcours_aiguillage);
	
	}
	
	public int getPositionAiguillageX(int indice_aiguillage)
	{
	// retourne la position dans le tableau de coordonnées au débute l'aiguillage 
	int position=0;
	position=tab_aiguillage.get(indice_aiguillage). getPositionParcoursX();
	return position;
	}
	
	public int getPositionAiguillageY(int indice_aiguillage)
	{
	int position=0;
	position=tab_aiguillage.get(indice_aiguillage). getPositionParcoursY();
	return position;
	}
	
	public void makeCheminFerme()
	{
		setTypeBoucle("ferme");
		this.add_rail("Horizontal","down");
		this.add_rail("Horizontal","down");
		this.add_rail("Virage_left_down","up");
		this.add_rail("Vertical","up");
		this.add_rail("Virage_left_up","up");
		this.add_rail("Horizontal","up");
		this.add_rail("Virage_right_down","up");
		this.add_rail("Vertical","up");
		this.add_rail("Vertical","up");
		this.add_rail("Virage_right_up","up");
		this.add_rail("Horizontal","down");
		this.add_rail("Virage_left_down","up");
		this.add_rail("Virage_left_up","up");
		this.add_rail("Horizontal","up");
		this.add_rail("Horizontal","up");
		this.add_rail("Virage_right_up","down");
		this.add_rail("Vertical","down");
		this.add_rail("Virage_left_down","down");
		this.add_rail("Horizontal","up");
		this.add_rail("Horizontal","up");
		this.add_rail("Horizontal","up");
		this.add_rail("Virage_right_up","down");
		this.add_rail("Vertical","down");
		this.add_rail("Vertical","down");
		this.add_rail("Virage_right_down","down");
		this.add_rail("Virage_left_up","down");
		this.add_rail("Virage_right_down","down");
		this.add_rail("Horizontal","down");
		this.add_rail("Horizontal","down");
		this.add_rail("Horizontal","down");
	}

	public void makeCheminOuvert()
	{
		setTypeBoucle("ouvert");
		this.add_rail("Horizontal","down");
		this.add_rail("Horizontal","down");
		this.add_rail("Virage_left_down","up");
		this.add_rail("Vertical","up");
		this.add_rail("Vertical","up");
		this.add_rail("Vertical","up");
		this.add_rail("Virage_left_up","up");
		this.add_rail("Horizontal","up");		
		this.add_rail("Virage_right_down","up");
		this.add_rail("Vertical","up");
		this.add_rail("Vertical","up");
		this.add_rail("Vertical","up");
		this.add_rail("Virage_left_up","up");
		this.add_rail("Horizontal","up");
		this.add_rail("Horizontal","up");
		this.add_rail("Horizontal","up");
		this.add_rail("Virage_right_up","down");
		this.add_rail("Vertical","down");
		this.add_rail("Virage_right_down","down");
		this.add_rail("Virage_left_down","up");
	}
	
	public void makeParcoursAig1virage()
	{
		this.add_rail("Virage_right_down","up");
		this.add_rail("Vertical","up");
		this.add_rail("Vertical","up");
		this.add_rail("Vertical","up");
		this.add_rail("Vertical","up");
		this.add_rail("Virage_left_up","up");
		this.add_rail("Horizontal","up");
	}
	
	public void makeParcoursAig1droit()
	{
		this.add_rail("Horizontal","up");
		this.add_rail("Horizontal","up");
		this.add_rail("Horizontal","up");
		this.add_rail("Virage_right_up","down");
		this.add_rail("Vertical","down");
		this.add_rail("Vertical","down");
		this.add_rail("Vertical","down");
		this.add_rail("Virage_right_down","down");
		this.add_rail("Horizontal","down");
	}
	
	public void makeCheminAvecAiguillages()
	{
		this.add_rail("Horizontal","down");
		this.add_rail("Horizontal","down");
		this.add_rail("Virage_left_down","up");
		this.add_rail("Vertical","up");
		this.add_rail("Vertical","up");
		this.add_rail("Vertical","up");
		this.add_rail("Virage_left_up","up");
		//on ajoute un rail avec aiguillage
		ajouterAiguillage("parcours_aiguillage1_droit");
		ajouterAiguillage("parcours_aiguillage1_virage");
		this.changerAiguillage(0,0);

	}
	
}



class Aiguillage {
	private int position_parcoursX=0;
	private int position_parcoursY=0;
	private String etat_aiguillage="courbe";
	
	Aiguillage(int positionX,int positionY)
	{
		position_parcoursX=positionX;
		position_parcoursY=positionY;
		etat_aiguillage="courbe";	
	}
	
	public void switchAiguillage()
	{
		if(this.etat_aiguillage=="droit")
		this.setEtatAiguillage("courbe");
		else
		this.setEtatAiguillage("droit");
	}
	
	public void setEtatAiguillage(String etat)
	{
		etat_aiguillage=etat;
	}
	
	public String getEtatAiguillage()
	{
	   return etat_aiguillage;
	}
	
	public void setPositionParcoursX(int position)
	{
		position_parcoursX=position;
	}
	
	public int getPositionParcoursX()
	{
		return position_parcoursX;
	}
	
	public void setPositionParcoursY(int position)
	{
		position_parcoursY=position;
	}
	
	public int getPositionParcoursY()
	{
		return position_parcoursY;
	}
}