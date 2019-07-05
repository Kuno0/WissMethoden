package WBM_TestPackage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

public class Primaer extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 6930173412792515849L;
	
    ArrayList<Pfad> Sammlung= new ArrayList<>();
	JButton buttonSetStart;
	JButton buttonSetEnde;
	JButton buttonGetWay;
	@SuppressWarnings("rawtypes")
	JComboBox comboBoxAttributwahl;
	JTextField textFStart;
	JTextField textFZiel;
	public prolog pclass;
	
	public static int spalte_NamePStart=0;
	public static int spalte_NamePEnde=1;
	public static int spalte_StartPX=3;
	public static int spalte_StartPY=4;
	public static int spalte_EndePX=5;
	public static int spalte_EndePY=6;
	public static int spalte_MerkmalBarrierefrei=8;
	public static String csvDatenbasis = ".\\DatenBasis.CSV";
	private Boolean gitter=false;
	
	public static Primaer p;
	JPopupMenu popup;
	
	public Integer MenuXPos;
	public Integer MenuYPos;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Primaer()
	{
		this.setTitle("Navigationskarte");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
// JLabel mit Hintergrundbild erzeugen
		ImageIcon verwendetesBild = new ImageIcon(".\\Karte.png");
		JLabel hintergrundbildLabel = new JLabel(verwendetesBild, JLabel.LEFT);
		hintergrundbildLabel.setOpaque(true);
		
		popup = new JPopupMenu();
		ActionListener menuListener = new ActionListener()
	    {
	      public void actionPerformed(ActionEvent event)
	      {
	 		 if(event.getActionCommand()=="Punkt als Start festlegen")
			 {
	 			 //textFStart.setText("N�chster: "+ naechstenPunktErmitteln(MenuXPos,MenuYPos) +"; X: "+ MenuXPos +"; Y: "+ MenuYPos);
	 			 textFStart.setText(naechstenPunktErmitteln(MenuXPos,MenuYPos));
			 }
	 		 else if (event.getActionCommand()=="Punkt als Ziel festlegen")
	 		 {
	 			//textFZiel.setText("N�chster: "+ naechstenPunktErmitteln(MenuXPos,MenuYPos) +"; X: "+ MenuXPos +"; Y: "+ MenuYPos);
	 			textFZiel.setText(naechstenPunktErmitteln(MenuXPos,MenuYPos));
	 		 }
	      }
	    };
	    
	    JMenuItem itemStart;
	    JMenuItem itemZiel;
	    popup.add(itemStart = new JMenuItem("Punkt als Start festlegen"));
	    itemStart.addActionListener(menuListener);
	    popup.add(itemZiel = new JMenuItem("Punkt als Ziel festlegen"));
	    itemZiel.addActionListener(menuListener);
	    popup.setLightWeightPopupEnabled(false);
		
		
// Buttons erstellen
	    
		JPanel buttonPanel = new JPanel();
		buttonSetStart = new JButton("Clear");
		buttonSetEnde
 = new JButton("Karte");
		buttonGetWay = new JButton("Strecke ermitteln");
		
		String[] attributauswahl = { "Ohne", "Barrierefrei" };
		comboBoxAttributwahl = new JComboBox(attributauswahl);
		
		textFStart = new JTextField("Test Start");
		textFZiel = new JTextField("Test Ziel");
		
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));	
		buttonPanel.add(buttonSetStart);
		buttonSetStart.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.add(Box.createRigidArea(new Dimension(0,25)));
		buttonPanel.add(buttonSetEnde
);
		buttonSetEnde
.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.add(Box.createRigidArea(new Dimension(0,25)));
		buttonPanel.add(buttonGetWay);
		buttonGetWay.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.add(Box.createRigidArea(new Dimension(0,25)));
		buttonPanel.add(comboBoxAttributwahl);
		comboBoxAttributwahl.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.add(Box.createRigidArea(new Dimension(0,25)));
		buttonPanel.add(textFStart);
		textFStart.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.add(Box.createRigidArea(new Dimension(0,25)));
		buttonPanel.add(textFZiel);
		textFZiel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		buttonSetStart.addActionListener(this);
		buttonSetEnde
.addActionListener(this);
		buttonGetWay.addActionListener(this);
		
		//addMouseListener(new ClickListener());
		
// Flowlayout erstellen
		FlowLayout LayoutManagement = new FlowLayout();
		LayoutManagement.setHgap(0);
		LayoutManagement.setVgap(0);
		LayoutManagement.setAlignment(FlowLayout.LEFT);
		
// Container vorbereiten		
		Container contentpaneKarte = this.getContentPane();
		contentpaneKarte.setLayout(LayoutManagement);
		contentpaneKarte.add(hintergrundbildLabel);
		contentpaneKarte.add(buttonPanel);
		hintergrundbildLabel.addMouseListener(new ClickListener());
		
		this.setLocationRelativeTo(null);
		//this.setSize(1700, 908);
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setLocation(120,60);
	}

	public static void main(String[] args)
	{	
		// Array mit Pfaden erstellen
		//berechneterpfadladen();
		//pfadObjekteLaden("Ohne");
		p = new Primaer();
		p.neuaufbau();
	}
	
	public void neuaufbau()
	{
// Drawing als JComponent mit Strichen und Punkten erstellen und als GlassPane festlegen
		if(this.gitter==true) {
			
			Drawing linienUndPunkte = new Drawing();
			linienUndPunkte.WertUebergebenB(Sammlung);
			this.setGlassPane(linienUndPunkte);
			linienUndPunkte.setVisible(true);
	// Fenster ausf�hren
			this.setVisible(true);
		}else {
			
			Drawing linienUndPunkte = new Drawing();
			linienUndPunkte.WertUebergeben(Sammlung);
			this.setGlassPane(linienUndPunkte);
			linienUndPunkte.setVisible(true);
	// Fenster ausf�hren
			this.setVisible(true);
			
		}

	}
	
	public void actionPerformed(ActionEvent e) 
	{
		 if(e.getSource() == this.buttonSetStart)
		 {
			 Sammlung.clear();
			 this.neuaufbau();
		 }
		 else if(e.getSource() == this.buttonSetEnde
    )
		 {
			 Sammlung.clear();
			 berechneBackground();
			 this.neuaufbau();
		 }
		 else if (e.getSource() == this.buttonGetWay)
		 {
			 Sammlung.clear();
			 //berechneterpfadladen();
			 try {
				prologberechnung();
			} catch (Exception e1) {
	
				e1.printStackTrace();
			}			 
			 //pfadObjekteLaden(comboBoxAttributwahl.getSelectedItem().toString());
			 this.neuaufbau();
		 }
	}
	public void prologberechnung()throws Exception 
	{
		this.pclass=new prolog();
		if(comboBoxAttributwahl.getSelectedItem().toString()=="Barrierefrei"){
			this.pclass.setMerkmal(1);
		}else 
		{
			this.pclass.setMerkmal(0);
		}
		this.pclass.setEingang(Integer.parseInt(textFStart.getText()));
		this.pclass.setAusgang(Integer.parseInt(textFZiel.getText()));
		berechneterpfadladen(this.pclass.berechneRoute());
	}
	public void berechneBackground()
	{
		    
			BufferedReader br = null;
	        String line = "";
	        this.gitter=true;
	        	try
	        {
	            br = new BufferedReader(new FileReader(".\\DatenBasis.CSV"));
	            br.readLine();
	            while ((line = br.readLine()) != null)
	            {
	                String[] eintragGelesen = line.split(";");
	                this.Sammlung.add(new Pfad(Integer.parseInt(eintragGelesen[spalte_StartPX]),Integer.parseInt(eintragGelesen[spalte_StartPY]),Integer.parseInt(eintragGelesen[spalte_EndePX]),Integer.parseInt(eintragGelesen[spalte_EndePY])));
	 	                //br.readLine();
	            }
	        } catch (FileNotFoundException e)
	        {
	            e.printStackTrace();
	        } catch (IOException e)
	        {
	            e.printStackTrace();
	        } finally
	        {
	            if (br != null)
	            {
	                try
	                {
	                    br.close();
	                } catch (IOException e)
	                {
	                    e.printStackTrace();
	                }
	            }
	        	}
	}
	public void berechneterpfadladen(String[] pfad)
	{
			BufferedReader br = null;
	        String line = "";
	        this.gitter=false;

	        for(int g=0; g<pfad.length-2; g++) { 
	        	try
	        {
	            br = new BufferedReader(new FileReader(".\\DatenBasis.CSV"));
	            br.readLine();
	            while ((line = br.readLine()) != null)
	            {
	                String[] eintragGelesen = line.split(";");
	                if ((Integer.parseInt(eintragGelesen[1])==Integer.parseInt(pfad[g+1])&&Integer.parseInt(eintragGelesen[0])==Integer.parseInt(pfad[g]))||(Integer.parseInt(eintragGelesen[1])==Integer.parseInt(pfad[g])&&Integer.parseInt(eintragGelesen[0])==Integer.parseInt(pfad[g+1])))
	                {
	                	this.Sammlung.add(new Pfad(Integer.parseInt(eintragGelesen[spalte_StartPX]),Integer.parseInt(eintragGelesen[spalte_StartPY]),Integer.parseInt(eintragGelesen[spalte_EndePX]),Integer.parseInt(eintragGelesen[spalte_EndePY])));

	                }	                
	                //br.readLine();
	            }

	        } catch (FileNotFoundException e)
	        {
	            e.printStackTrace();
	        } catch (IOException e)
	        {
	            e.printStackTrace();
	        } finally
	        {
	            if (br != null)
	            {
	                try
	                {
	                    br.close();
	                } catch (IOException e)
	                {
	                    e.printStackTrace();
	                }
	            }
	        	
	        }
	        	}
	       
	}
	
/*	public static void pfadObjekteLaden(String aMerkmal)
	{
/*
F�llt das Array "Sammlung" mit Pfad-Objekten des gew�hlten Merkmals
Quelle ist die angegebene CSV Datei
 
		//Sammlung=null;
		
		
		
		int aktivesMerkmal= 0;
		
		
		if (aMerkmal=="Barrierefrei")
		{
			aktivesMerkmal=spalte_MerkmalBarrierefrei;
		}
		else if (aMerkmal=="Ohne")
		{
			aktivesMerkmal=0;
		}
		
		BufferedReader br = null;
        String line = "";
        int Zaehler = 0;
        //Sammlung = new Pfad[1];
        
        try
        {
            br = new BufferedReader(new FileReader(csvDatenbasis));
            br.readLine();
            while ((line = br.readLine()) != null)
            {
                String[] eintragGelesen = line.split(";");
                //System.out.println(eintragGelesen[aktivesMerkmal]);
                if ((aktivesMerkmal==0)||(Integer.parseInt(eintragGelesen[aktivesMerkmal])==1))
                {
                	//Sammlung = Arrays.copyOf(Sammlung, 1+Zaehler);
                	//Sammlung[Zaehler] = new Pfad(Integer.parseInt(eintragGelesen[spalte_StartPX]),Integer.parseInt(eintragGelesen[spalte_StartPY]),Integer.parseInt(eintragGelesen[spalte_EndePX]),Integer.parseInt(eintragGelesen[spalte_EndePY]));
            		//System.out.println(Zaehler);
                	Zaehler++;
                }
                
                //br.readLine();
            }

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            if (br != null)
            {
                try
                {
                    br.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }		
	}*/
	
	public String naechstenPunktErmitteln(int xSuche, int ySuche)
	{
		BufferedReader br = null;
        String line = "";
        //int Zaehler=0;
        double minDist = 100000;
        String bestYet = "";
        try
        {
            br = new BufferedReader(new FileReader(csvDatenbasis));
            br.readLine();
            while ((line = br.readLine()) != null)
            {
                String[] eintragGelesen = line.split(";");
                //System.out.println(eintragGelesen[aktivesMerkmal]);
                if (Math.sqrt(Math.pow((Integer.parseInt(eintragGelesen[spalte_StartPX])-xSuche),2)+Math.pow((Integer.parseInt(eintragGelesen[spalte_StartPY])-ySuche),2))<minDist)
                {
                	
                	minDist=Math.sqrt(Math.pow((Integer.parseInt(eintragGelesen[spalte_StartPX])-xSuche),2)+Math.pow((Integer.parseInt(eintragGelesen[spalte_StartPY])-ySuche),2));
                	bestYet=eintragGelesen[spalte_NamePStart];
                }
                if (Math.sqrt(Math.pow((Integer.parseInt(eintragGelesen[spalte_EndePX])-xSuche),2)+Math.pow((Integer.parseInt(eintragGelesen[spalte_EndePY])-ySuche),2))<minDist)
                {
                	minDist=Math.sqrt(Math.pow((Integer.parseInt(eintragGelesen[spalte_EndePX])-xSuche),2)+Math.pow((Integer.parseInt(eintragGelesen[spalte_EndePY])-ySuche),2));
                	bestYet=eintragGelesen[spalte_NamePEnde];
                }
            }

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            if (br != null)
            {
                try
                {
                    br.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    return bestYet;
	}
	
	class ClickListener extends MouseAdapter
	{	
		public void mouseClicked(MouseEvent e)
		{
			maybeShowPopup(e);
		}
		public void mouseEntered(MouseEvent e)
		{
			//maybeShowPopup(e);
		}
		public void mouseExited(MouseEvent e)
		{
			//maybeShowPopup(e);
		}
		public void mousePressed(MouseEvent e)
		{
			//maybeShowPopup(e);
		}
		public void mouseReleased(MouseEvent e)
		{
			maybeShowPopup(e);
		}
		public void maybeShowPopup(MouseEvent e)
		{
			if (e.isPopupTrigger())
			{
				popup.show(Primaer.this, e.getX(), e.getY());
				MenuXPos=e.getX();
				MenuYPos=e.getY();
				//System.out.println("XPos: "+MenuXPos+"  YPos: "+MenuYPos);
			}
		}
	}

}