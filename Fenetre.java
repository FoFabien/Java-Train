import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
 
 
public class Fenetre extends JFrame{
		
        private Panneau pan = new Panneau();
        private ReseauFerroviere leReseauFerroviere;
      //menu et items
        private static final int pas_speed=1;
        private JMenuBar menuBar = new JMenuBar();
        private JMenu menu1 = new JMenu("Choix du parcours");
        private JMenu menu2 = new JMenu("Aiguillage");
    	private JMenuItem menu1_item1 = new JMenuItem("Chemin de fer fermé");
    	private JMenuItem menu1_item2 = new JMenuItem("Chemin de fer ouvert");
    	private JMenuItem menu1_item3 = new JMenuItem("Votre chemin de fer");
    	private JMenuItem menu1_item4 = new JMenuItem("Chemin de fer avec aiguillages");
    	private JMenuItem menu2_item1 = new JMenuItem("Switch aiguillage 1");
    	//barre d'outil et boutons
        private JToolBar toolBar = new JToolBar();
        private JButton  del_rail = new JButton(new ImageIcon(getClass().getResource("img/supprimer.png"))),
        				 bouton_ok = new JButton(new ImageIcon(getClass().getResource("img/ok.png"))),
        				 rail_hor_up = new JButton(new ImageIcon(getClass().getResource("img/horizont_up.png"))),
        				 rail_hor_down = new JButton(new ImageIcon(getClass().getResource("img/horizont_down.png"))),
        				 rail_vert_up = new JButton(new ImageIcon(getClass().getResource("img/verticale_up.png"))),
        				 rail_vert_down = new JButton(new ImageIcon(getClass().getResource("img/verticale_down.png"))),
        				 vir_right_up_up = new JButton(new ImageIcon(getClass().getResource("img/virage_right_up_up.png"))),
        				 vir_right_up_down = new JButton(new ImageIcon(getClass().getResource("img/virage_right_up_down.png"))),
        				 vir_right_down_up = new JButton(new ImageIcon(getClass().getResource("img/virage_right_down_up.png"))),
        				 vir_right_down_down = new JButton(new ImageIcon(getClass().getResource("img/virage_right_down_down.png"))),
        				 vir_left_up_up = new JButton(new ImageIcon(getClass().getResource("img/virage_left_up_up.png"))),
        				 vir_left_up_down = new JButton(new ImageIcon(getClass().getResource("img/virage_left_up_down.png"))),
        				 vir_left_down_up = new JButton(new ImageIcon(getClass().getResource("img/virage_left_down_up.png"))),
        				 vir_left_down_down = new JButton(new ImageIcon(getClass().getResource("img/virage_left_down_down.png")));
        private Color fondBouton = Color.white;
        private BarreOutilListener btListener=new BarreOutilListener();
        //
    	private int taille_fenetre_x=600;
    	private int taille_fenetre_y=700;
    	private boolean sortir=false;
    	private String mouvement_train_precedent="run_positif";
    	private String mouvement_train_en_cours="stop";
    	private int speed=pas_speed;
    	private String type_parcours_encours="parcours_ferme";
    	private String etat_parcours_perso="unfinished";
    	private boolean demande_chgt_aiguillage0=false;
    	
        public Fenetre(ReseauFerroviere ReseauFerroviere){
                this.leReseauFerroviere=ReseauFerroviere;
                this.leReseauFerroviere.ajusterVitesse(pas_speed);
                leReseauFerroviere.donnerAdresseFenetre(this);
                pan.setReseauPointeur(leReseauFerroviere);
                this.setTitle("Animation du train");
                this.setSize(taille_fenetre_x, taille_fenetre_y);
                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.setLocationRelativeTo(null);
                this.setContentPane(pan);
                this.init_menu();
                this.init_toolBar();
                this.setVisible(true);
                pan.setParcours(type_parcours_encours);
                setVitesse(speed);
                go();
        }
        
        private void init_menu()
        {
        	
        // on ajoute des items dans notre menu avec des actionListener sur ces items pour detecter le click
        	   menu1_item1.addActionListener(new ActionListener(){
       			public void actionPerformed(ActionEvent arg0) {
       				pan.setParcours("parcours_ferme");
       				type_parcours_encours="parcours_ferme";
       				leReseauFerroviere.getAutomate().reset();
                                pan.isCrashed(false);
                                mouvement_train_precedent="run_positif";
                                mouvement_train_en_cours="stop";
       				DesactivationBoutonToolBar();
       				menu2.setEnabled(false);
       			}				
       		});
               this.menu1.add(menu1_item1);
               menu1_item2.addActionListener(new ActionListener(){
       			public void actionPerformed(ActionEvent arg0) {
       				pan.setParcours("parcours_ouvert");
       				type_parcours_encours="parcours_ouvert";
       				leReseauFerroviere.getAutomate().reset();
                                mouvement_train_precedent="run_positif";
                                mouvement_train_en_cours="stop";
                                pan.isCrashed(false);
       				DesactivationBoutonToolBar();
       				menu2.setEnabled(false);
       			}				
       		});
       		this.menu1.add(menu1_item2);
       		menu1_item3.addActionListener(new ActionListener(){
       			public void actionPerformed(ActionEvent arg0) {
       				pan.setParcours("parcours_perso");
       				type_parcours_encours="parcours_perso";
       				leReseauFerroviere.getAutomate().reset();
                                mouvement_train_precedent="run_positif";
                                mouvement_train_en_cours="stop";
                                pan.isCrashed(false);
       				etat_parcours_perso="unfinished";
       				ActivationBoutonToolBar();
       				menu2.setEnabled(false);
       			}				
       		});
       		this.menu1.add(menu1_item3);
       		
       		menu1_item4.addActionListener(new ActionListener(){
       			public void actionPerformed(ActionEvent arg0) {
       				pan.setParcours("parcours_avec_aiguillage");
       				type_parcours_encours="parcours_avec_aiguillage";
       				leReseauFerroviere.getAutomate().reset();
                                mouvement_train_precedent="run_positif";
                                mouvement_train_en_cours="stop";
                                pan.isCrashed(false);
       				DesactivationBoutonToolBar();
       				menu2.setEnabled(true);
       			}				
       		});
       		this.menu1.add(menu1_item4);
       		
       		menu2_item1.addActionListener(new ActionListener(){
       			public void actionPerformed(ActionEvent arg0) {
       				demande_chgt_aiguillage0=true;
       			}				
       		});
       		this.menu2.add(menu2_item1);
       		this.menuBar.add(menu1);
       		this.menuBar.add(menu2);
       		menu2.setEnabled(false);
   			this.setJMenuBar(menuBar);
        }
        
   
        private void init_toolBar()
        {	
        	this.del_rail.addActionListener(btListener);
        	this.del_rail.setBackground(fondBouton);
        	this.toolBar.add(del_rail);
        	
        	this.bouton_ok.addActionListener(btListener);
        	this.bouton_ok.setBackground(fondBouton);
        	this.toolBar.add(bouton_ok);
        	
        	this.rail_hor_up.addActionListener(btListener);
        	this.rail_hor_up .setBackground(fondBouton);
        	this.toolBar.add(rail_hor_up );
        	
        	this.rail_hor_down.addActionListener(btListener);
        	this.rail_hor_down.setBackground(fondBouton);
        	this.toolBar.add(rail_hor_down);
        	
        	this.rail_vert_up.addActionListener(btListener);
        	this.rail_vert_up.setBackground(fondBouton);
        	this.toolBar.add(rail_vert_up);
        	
        	this.rail_vert_down.addActionListener(btListener);
        	this.rail_vert_down.setBackground(fondBouton);
        	this.toolBar.add(rail_vert_down);
        	
        	this.vir_right_up_up.addActionListener(btListener);
        	this.vir_right_up_up.setBackground(fondBouton);
        	this.toolBar.add(vir_right_up_up);
        	
        	this.vir_right_up_down.addActionListener(btListener);
        	this.vir_right_up_down.setBackground(fondBouton);
        	this.toolBar.add(vir_right_up_down);
        	
          	this.vir_right_down_up.addActionListener(btListener);
        	this.vir_right_down_up.setBackground(fondBouton);
        	this.toolBar.add(vir_right_down_up);
        	
        	this.vir_right_down_down.addActionListener(btListener);
        	this.vir_right_down_down.setBackground(fondBouton);
        	this.toolBar.add(vir_right_down_down);
       
         	this.vir_left_up_up.addActionListener(btListener); 	
        	this.vir_left_up_up.setBackground(fondBouton);
        	this.toolBar.add(vir_left_up_up);
       
        	this.vir_left_up_down.addActionListener(btListener); 	
        	this.vir_left_up_down.setBackground(fondBouton);
        	this.toolBar.add(vir_left_up_down);
       
          	this.vir_left_down_up.addActionListener(btListener);	
        	this.vir_left_down_up.setBackground(fondBouton);
        	this.toolBar.add(vir_left_down_up);
        
        	this.vir_left_down_down.addActionListener(btListener);
        	this.vir_left_down_down.setBackground(fondBouton);
        	this.toolBar.add(vir_left_down_down);
        	
        	this.DesactivationBoutonToolBar();
        	this.add(toolBar, BorderLayout.NORTH);
        }
        
        void DesactivationBoutonToolBar()
    	{
    		this.del_rail.setEnabled(false);
    		this.bouton_ok.setEnabled(false);
    		this.rail_hor_up .setEnabled(false);
    		this.rail_hor_down.setEnabled(false);
    		this.rail_vert_up.setEnabled(false);
    		this.rail_vert_down.setEnabled(false);
    		this.vir_right_up_up.setEnabled(false);
    		this.vir_right_up_down.setEnabled(false);
    		this.vir_left_down_down.setEnabled(false);
    		this.vir_right_down_up.setEnabled(false);
    		this.vir_left_down_up.setEnabled(false);
    		this.vir_right_down_down.setEnabled(false);
    		this.vir_left_up_up.setEnabled(false);
    		this.vir_left_up_down.setEnabled(false);
    	}
        
        void ActivationBoutonToolBar()
    	{
			String type_rail=new String(pan.getTypeDernierRail());
			DesactivationBoutonToolBar();
			if(type_parcours_encours=="parcours_perso"&&etat_parcours_perso=="unfinished")
			{
				this.del_rail.setEnabled(true);
				this.bouton_ok.setEnabled(true);
				
				if((type_rail.compareTo("Horizontal up")==0)||(type_rail.compareTo("Virage_left_down down")==0)||(type_rail.compareTo("Virage_left_up up")==0))
				{
				this.vir_right_down_up.setEnabled(true);
				this.vir_right_up_down.setEnabled(true);
				this.rail_hor_up .setEnabled(true);
				}
				else if((type_rail.compareTo("Horizontal down")==0)||(type_rail.compareTo("Virage_right_up up")==0)||(type_rail.compareTo("Virage_right_down down")==0))
				{
				this.vir_left_down_up.setEnabled(true);
				this.vir_left_up_down.setEnabled(true);
				this.rail_hor_down .setEnabled(true);	
				}
				else if((type_rail.compareTo("Vertical up")==0)||(type_rail.compareTo("Virage_right_down up")==0)||(type_rail.compareTo("Virage_left_down up")==0))
				{
				this.vir_right_up_up.setEnabled(true);
				this.vir_left_up_up.setEnabled(true);
				this.rail_vert_up .setEnabled(true);	
				}
				else if((type_rail.compareTo("Vertical down")==0)||(type_rail.compareTo("Virage_right_up down")==0)||(type_rail.compareTo("Virage_left_up down")==0))
				{
				this.vir_right_down_down.setEnabled(true);
				this.vir_left_down_down.setEnabled(true);
				this.rail_vert_down .setEnabled(true);
				}	
			}
        }
        	
        
        private void go(){
            
            while(!sortir)
            {		
            		if((type_parcours_encours=="parcours_perso")&&etat_parcours_perso=="unfinished")
            		stopper();
            	
            		leReseauFerroviere.refreshAffichageVitesse();
      				pan.testLoop();
    
      				if(pan.runTrain(mouvement_train_en_cours))
                    {
                   	//envoie message automate stop ou crash
      					leReseauFerroviere.boutDePiste();
                    }
      				
      				if(demande_chgt_aiguillage0)
      				{
      					if(pan.switchAiguillage(0,0))
      					demande_chgt_aiguillage0=false;
      				}
      				
      				
      				pan.repaint();  
                    try {
                            Thread.sleep((17*pas_speed)-speed);
                    
                    } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                    }
            }        
    }
   
        public void demarrer()
        {
        	if(getSens()=="stop")
        	{
        	setSens(mouvement_train_precedent);
        	this.accelerer();
        	menu1.setEnabled(false);
        	}
        }
        
        public void stopper()
        {
        	if(getSens()!="stop")
        	{
        	setSens("stop");
                setVitesse(0);
                menu1.setEnabled(true);
        	}
        }
        
        public void accelerer()
        {   
        	if(speed<(16*pas_speed))
        	{	
        	speed+=pas_speed; 
        	}
        }
   
        public void ralentir()
        {   
        	if(speed>(pas_speed))
        	{	
            speed-=pas_speed; 
            System.out.println("speed ralentir " + speed);
            } 	   
        }
        
        public void crasher()
        {
            // reset des paramètres (similaire à un stop)
            mouvement_train_en_cours="stop";
            menu1.setEnabled(true);
            setVitesse(0);
            pan.isCrashed(true); // on dit à panneau d'afficher la petite image "Crash"
        }
        
        class BarreOutilListener implements ActionListener{
    		public void actionPerformed(ActionEvent e) {
    			
    				if(e.getSource() == del_rail){
    				pan.destructionRail();	
    				ActivationBoutonToolBar();
    				}
    				else if(e.getSource() == bouton_ok){
    				del_rail.setEnabled(false);	
    				etat_parcours_perso="finished";
    				ActivationBoutonToolBar();
    				}
    				else if(e.getSource() == rail_hor_up){
    				pan.constructionRail("Horizontal","up");
    				ActivationBoutonToolBar();			
    				}
    				else if(e.getSource() == rail_hor_down){
    				pan.constructionRail("Horizontal","down");
    				ActivationBoutonToolBar();
    				}
    				else if(e.getSource() == rail_vert_up){
    				pan.constructionRail("Vertical","up");
    				ActivationBoutonToolBar();
    				}
    				else if(e.getSource() == rail_vert_down){
    				pan.constructionRail("Vertical","down");
    				ActivationBoutonToolBar();
    				}
    				else if(e.getSource()==vir_right_up_up){
    				pan.constructionRail("Virage_right_up","up");
    				ActivationBoutonToolBar();
    				}
    				else if(e.getSource() ==vir_right_up_down){
        			pan.constructionRail("Virage_right_up","down");
        			ActivationBoutonToolBar();
    				}
    				else if(e.getSource() ==vir_right_down_down){
        			pan.constructionRail("Virage_right_down","down");
        			ActivationBoutonToolBar();
    				}
    				else if(e.getSource() ==vir_right_down_up){
    				pan.constructionRail("Virage_right_down","up");
    				ActivationBoutonToolBar();
    				}
    				else if(e.getSource() ==vir_left_down_up){
    				pan.constructionRail("Virage_left_down","up");
    				ActivationBoutonToolBar();
    				}
    				else if(e.getSource() ==vir_left_down_down){
    				pan.constructionRail("Virage_left_down","down");
    				ActivationBoutonToolBar();
    				}
    	    		else if(e.getSource() ==vir_left_up_up){
    	    		pan.constructionRail("Virage_left_up","up");
    	    		ActivationBoutonToolBar();
    	    		}
    	    		else if(e.getSource() ==vir_left_up_down){
    	    		pan.constructionRail("Virage_left_up","down");
    	    		ActivationBoutonToolBar();
    	    				}
    			}
    		}	
        
        public String getSens()
        {
        return mouvement_train_en_cours;
        }
        
        public void setSens(String sens)
        {
        	
        	if(sens=="run_positif"||sens=="run_negatif"||sens=="stop")
        	{	
        		this.setSensPrecedent(mouvement_train_en_cours);
        		mouvement_train_en_cours=sens;
        	}

        }
        
        public String getSensPrecedent()
        {
        return mouvement_train_precedent;
        }
        
        public void setSensPrecedent(String sens)
        {
        if(sens=="run_positif"||sens=="run_negatif"||sens=="stop")
        {
        mouvement_train_precedent=sens;
        }
        }
        
        public int getVitesse()
        {
        	return speed;
        }
        
        public void setVitesse(int vitesse)
        {
        	if(this.getSens()=="stop")
        	speed=0;
        	else
        	speed=vitesse;
        }
        
        public int getVitesseKMH()
        {
        	return (this.getVitesse()*10);
        }
 
        public int getPas()
        {
        	return pas_speed;
        }
}
