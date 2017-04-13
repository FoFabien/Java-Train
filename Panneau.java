import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
 
public class Panneau extends JPanel {
 
        private int position_trainX = 0;
        private int position_trainY = 0;
        private int position_tableau=0;
        private boolean crashed = false;
        ArrayList<Integer> deplacement_train_x=new ArrayList<Integer>();
        ArrayList<Integer> deplacement_train_y=new ArrayList<Integer>();
        Parcours le_parcours;
        private String type_parcours="parcours_ferme";
        ReseauFerroviere res;
        
        public void paintComponent(Graphics g){
                //On decide un rectangle pour le fond
                g.setColor(Color.white);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
               
                this.tracerRails(g);

                //on place la/les gare(s)
                try {
                    // position gare
                    ImageIcon img = new ImageIcon(getClass().getResource("img/gare.png"));
                    g.drawImage(img.getImage(), le_parcours.getDernierePositionX(), le_parcours.getDernierePositionY(), this);
                    g.drawImage(img.getImage(), le_parcours.getPremierePositionX(), le_parcours.getPremierePositionY(), this);
                    } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();               
                    }
                    // on affiche le passage du train    
                    g.setColor(Color.red);
                    g.fillOval(position_trainX, position_trainY, 15,15);
                    
                    if(crashed == true)
                    {
                        g.drawImage(new ImageIcon(getClass().getResource("img/crash.png")).getImage(), position_trainX + 5, position_trainY - 25, this);
                    }
        }
        
        public void tracerRails(Graphics graph)
        {
            if(le_parcours != null)
                for(int i=0;i<le_parcours.getNombrePointAtracer();i++)
                {
            	// On trace les points
        		graph.setColor(Color.black);
        		graph.fillOval(le_parcours.getPointXaTracer(i),le_parcours.getPointYaTracer(i), 5,5);                  
                }
  
        }
        
        public int getPosTrainX() {
                return position_trainX;
        }
 
        public void setPosTrainX(int posX) {
                this.position_trainX = posX;
        }
        
        public int getPosTrainY() {
                return position_trainY;
        }
 
        public void setPosTrainY(int posY) {
                this.position_trainY = posY;
        }
        
        public void setParcours(String parcours)
        {   
            position_tableau=0;
            type_parcours=parcours;
            le_parcours=new Parcours(235,50,type_parcours);
            this.refreshCoordonnees();
        }
        
        
        public String getParcours()
        {
        	return type_parcours;
        }
        
        public void refreshCoordonnees()
        {
        	this.deplacement_train_x.clear();
            this.deplacement_train_y.clear();
            this.deplacement_train_x=le_parcours.getCoordonneesParcoursX();
            this.deplacement_train_y=le_parcours.getCoordonneesParcoursY();	
        }
        
        public void constructionRail(String type_rail,String sens)
        {
        	if(this.getParcours()=="parcours_perso")
        	{
        	le_parcours.add_rail(type_rail,sens);
        	}
        	
        }
        
        public void destructionRail()
        {
        	if(this.getParcours()=="parcours_perso")
        	{
        	le_parcours.delete_rail();
        	}
        }

        
        public String getTypeDernierRail()
        {
        	return le_parcours.getTypeDernierRail();
        }
        
        public void testLoop()
        {
        if(le_parcours.testerLoop())
        {
        le_parcours.setTypeBoucle("ferme");
        }
        else
        le_parcours.setTypeBoucle("ouvert");
        }
        
        public boolean switchAiguillage(int indice_aiguillage,int indice_chemin)
        {
        	boolean reponse=false;
        	if((position_tableau<le_parcours.getPositionAiguillageX(indice_aiguillage))&&(this.getPosTrainY()<le_parcours.getPositionAiguillageY(indice_aiguillage)))
        	{
        	le_parcours.changerAiguillage(indice_aiguillage,indice_chemin);
        	reponse=true;
        	}
        	return reponse;
        }

        public boolean runTrain(String action)
        {
        boolean bout_de_parcours=false;
        	if(action=="run_positif")
        	{
        		if(position_tableau==deplacement_train_x.size())
        		{
        			if(le_parcours.isAloop())
        			{
        			// si le chemin est fermé, on reboucle le trajet du train sur la premiere case du tableau
        			position_tableau=0;
        			}
        			else
        			{
        			// sinon le train est en bout de course, donc on le stop (ou il y a crash)
        			position_tableau=deplacement_train_x.size()-1;
                                bout_de_parcours=true;
                                res.boutDePiste();
        			}
        		}
        		
        	setPosTrainX(deplacement_train_x.get(position_tableau));
        	setPosTrainY(deplacement_train_y.get(position_tableau));
        	position_tableau++;
        	}
        	else if(action=="run_negatif")
        	{
        		if((position_tableau==0))
        		{
        			if(le_parcours.isAloop())
        			position_tableau=deplacement_train_x.size()-1;
        			else
        			{
        			position_tableau=0;
                                bout_de_parcours=true;
                                res.boutDePiste();
        			}
        		}
        	setPosTrainX(deplacement_train_x.get(position_tableau));
        	setPosTrainY(deplacement_train_y.get(position_tableau));
        	position_tableau--;
        	}
        	else if(action=="stop")
        	{
        	
        		if(position_tableau==deplacement_train_x.size())
        		position_tableau--;
        		else if(position_tableau==-1)
        		position_tableau++;
        	//on ne bouge pas le train
        	setPosTrainX(deplacement_train_x.get(position_tableau));
                setPosTrainY(deplacement_train_y.get(position_tableau));
            
        }	
        return bout_de_parcours;
    }
        
    public void setReseauPointeur(ReseauFerroviere r)
    {
        this.res = r;
    }
    
    public void isCrashed(boolean bo)
    {
        crashed = bo;
    }
}
