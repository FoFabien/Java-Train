import java.awt.Color;
import java.util.ArrayList;

public class Rail {
	private String orientation;
	private String sens; // negatif : bas ecran vers haut ecran / positif : haut vers bas 
	private ArrayList<Integer> tableau_coordonneesX=new  ArrayList<Integer> ();
	private ArrayList<Integer> tableau_coordonneesY=new ArrayList<Integer>();
	private int incremente=1;
	
	Rail(int point_depart_x,int point_depart_y,String orientation_rail,String sens_rail)
	{
	this.setSens(sens_rail);
	this.setOrientation(orientation_rail);
	if(sens=="down")
	incremente=-1;
	// on definit le type de rail : virage, horizontal et vertical
	// on remplit le tableau de points (x,y) en fct du type
	if(this.getOrientation()=="Horizontal")
	{
	tableau_coordonneesX.add(point_depart_x+incremente);
	tableau_coordonneesY.add(point_depart_y);
	for(int i=1;i<50;i++)
		{
		tableau_coordonneesX.add(tableau_coordonneesX.get(i-1)+(incremente));
		tableau_coordonneesY.add(tableau_coordonneesY.get(i-1));
		}
	}
	if(this.getOrientation()=="Vertical")
	{
		tableau_coordonneesX.add(point_depart_x);
		tableau_coordonneesY.add(point_depart_y+(incremente));
		for(int i=1;i<50;i++)
			{
			tableau_coordonneesX.add(tableau_coordonneesX.get(i-1));
			tableau_coordonneesY.add(tableau_coordonneesY.get(i-1)+(incremente));
			}
	}
	else if(this.getOrientation()=="Virage_right_up")
	{
		int centre_cercle_x=0;
		int centre_cercle_y=0;
		
		if(sens=="up")
		{
		centre_cercle_x=point_depart_x-50;
		centre_cercle_y=point_depart_y;
		for(int i=0;i<=50;i++)
			{
			tableau_coordonneesX.add((int) (centre_cercle_x+50*(Math.cos(((Math.PI)/100)*i))));
			tableau_coordonneesY.add((int) (centre_cercle_y+50*(Math.sin(((Math.PI)/100)*i))));
			}	
		}
		else if(sens=="down")
		{
		centre_cercle_x=point_depart_x;
		centre_cercle_y=point_depart_y-50;
		for(int i=50;i>=0;i--)
			{
			tableau_coordonneesX.add((int) (centre_cercle_x+50*(Math.cos(((Math.PI)/100)*i))));
			tableau_coordonneesY.add((int) (centre_cercle_y+50*(Math.sin(((Math.PI)/100)*i))));
			}
		}
		else
		System.out.println("Probleme de sens de rail");
	}
	else if(this.getOrientation()=="Virage_right_down")
	{
		int centre_cercle_x=0;
		int centre_cercle_y=0;
		
		if(sens=="up")
		{
		centre_cercle_x=point_depart_x;
		centre_cercle_y=point_depart_y+50;
		for(int i=150;i<=200;i++)
			{
			tableau_coordonneesX.add((int) (centre_cercle_x+50*(Math.cos(((Math.PI)/100)*i))));
			tableau_coordonneesY.add((int) (centre_cercle_y+50*(Math.sin(((Math.PI)/100)*i))));
			}	
		}
		else if(sens=="down")
		{
		centre_cercle_x=point_depart_x-50;
		centre_cercle_y=point_depart_y;
		for(int i=0;i<=50;i++)
			{
			tableau_coordonneesX.add((int) (centre_cercle_x+50*(Math.cos(((Math.PI)/100)*-i))));
			tableau_coordonneesY.add((int) (centre_cercle_y+50*(Math.sin(((Math.PI)/100)*-i))));
			}
		}
		else
		System.out.println("Probleme de sens de rail");
			
	}
	else if(this.getOrientation()=="Virage_left_up")
	{
		int centre_cercle_x=0;
		int centre_cercle_y=0;
		
		
		if(sens=="up")
		{
		centre_cercle_x=point_depart_x+50;
		centre_cercle_y=point_depart_y;
		for(int i=100;i>=50;i--)
			{
			tableau_coordonneesX.add((int) (centre_cercle_x+50*(Math.cos(((Math.PI)/100)*i))));
			tableau_coordonneesY.add((int) (centre_cercle_y+50*(Math.sin(((Math.PI)/100)*i))));
			}	
		}
		else if(sens=="down")
		{
		centre_cercle_x=point_depart_x;
		centre_cercle_y=point_depart_y-50;
		for(int i=50;i<=100;i++)
			{
			tableau_coordonneesX.add((int) (centre_cercle_x+50*(Math.cos(((Math.PI)/100)*i))));
			tableau_coordonneesY.add((int) (centre_cercle_y+50*(Math.sin(((Math.PI)/100)*i))));
			}
		}
		else
		System.out.println("Probleme de sens de rail");
			
	}
	else if(this.getOrientation()=="Virage_left_down")
	{
		int centre_cercle_x=0;
		int centre_cercle_y=0;
		
		
		if(sens=="up")
		{
		centre_cercle_x=point_depart_x;
		centre_cercle_y=point_depart_y+50;
		for(int i=50;i<=100;i++)
			{
			tableau_coordonneesX.add((int) (centre_cercle_x+50*(Math.cos(((Math.PI)/100)*-i))));
			tableau_coordonneesY.add((int) (centre_cercle_y+50*(Math.sin(((Math.PI)/100)*-i))));	
			}
		}
		else if(sens=="down")
		{
		centre_cercle_x=point_depart_x+50;
		centre_cercle_y=point_depart_y;
		for(int i=100;i<=150;i++)
			{
			tableau_coordonneesX.add((int) (centre_cercle_x+50*(Math.cos(((Math.PI)/100)*i))));
			tableau_coordonneesY.add((int) (centre_cercle_y+50*(Math.sin(((Math.PI)/100)*i))));
			}
		}
		else 
		System.out.println("Probleme de sens de rail");
	}
	

	
	}
	
	public String getOrientation()
	{
	return orientation;
	}
	
	public void setOrientation(String new_orientation)
	{
	orientation=new_orientation;
	}
	
	public String getSens()
	{
	return sens;
	}
	
	public void setSens(String sens_rail)
	{
	sens=sens_rail;	
	}
	
	public ArrayList<Integer> getTabCoordX()
	{
	return tableau_coordonneesX;
	}
	

	public  ArrayList<Integer>  getTabCoordY()
	{
	return tableau_coordonneesY;
	}
	
	public int getDerniereCoordX()
	{
	return tableau_coordonneesX.get(tableau_coordonneesX.size()-1);	
	}
	

	public int getDerniereCoordY()
	{
		return tableau_coordonneesY.get(tableau_coordonneesY.size()-1);	
	}
	
	
} 
