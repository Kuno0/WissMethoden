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
import java.util.Arrays;

import javax.swing.*;

public class Primaer extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 6930173412792515849L;
	
	static Pfad[] Sammlung;
	JButton buttonSetStart;
	JButton buttonSetEnd;
	JButton buttonGetWay;
	@SuppressWarnings("rawtypes")
	JComboBox comboBoxAttributwahl;
	JTextField textFStart;
	JTextField textFZiel;
	
	public static int spalte_NamePStart=0;
	public static int spalte_NamePEnd=1;
	public static int spalte_StartPX=3;
	public static int spalte_StartPY=4;
	public static int spalte_EndPX=5;
	public static int spalte_EndPY=6;
	public static int spalte_MerkmalBarrierefrei=8;
	public static String csvDatenbasis = ".\\DatenBasis.CSV";
	
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
	 			 textFStart.setText("Nächster: "+ naechstenPunktErmitteln(MenuXPos,MenuYPos) +"; X: "+ MenuXPos +"; Y: "+ MenuYPos);
			 }
	 		 else if (event.getActionCommand()=="Punkt als Ziel festlegen")
	 		 {
	 			textFZiel.setText("Nächster: "+ naechstenPunktErmitteln(MenuXPos,MenuYPos) +"; X: "+ MenuXPos +"; Y: "+ MenuYPos);
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
		buttonSetEnd = new JButton("XXXXXXXX");
		buttonGetWay = new JButton("Strecke ermitteln");
		
		String[] attributauswahl = { "Ohne", "Barrierefrei" };
		comboBoxAttributwahl = new JComboBox(attributauswahl);
		
		textFStart = new JTextField("Test Start");
		textFZiel = new JTextField("Test Ziel");
		
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));	
		buttonPanel.add(buttonSetStart);
		buttonSetStart.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.add(Box.createRigidArea(new Dimension(0,25)));
		buttonPanel.add(buttonSetEnd);
		buttonSetEnd.setAlignmentX(Component.CENTER_ALIGNMENT);
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
		buttonSetEnd.addActionListener(this);
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
		pfadObjekteLaden("Ohne");
		p = new Primaer();
		p.neuaufbau();
	}
	
	public void neuaufbau()
	{
// Drawing als JComponent mit Strichen und Punkten erstellen und als GlassPane festlegen
		Drawing linienUndPunkte = new Drawing();
		linienUndPunkte.WertUebergeben(Sammlung);
		this.setGlassPane(linienUndPunkte);
		linienUndPunkte.setVisible(true);
// Fenster ausführen
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		 if(e.getSource() == this.buttonSetStart)
		 {
			 Sammlung=new Pfad[0];
			 this.neuaufbau();
		 }
		 else if(e.getSource() == this.buttonSetEnd)
		 {
			 
		 }
		 else if (e.getSource() == this.buttonGetWay)
		 {
			 pfadObjekteLaden(comboBoxAttributwahl.getSelectedItem().toString());
			 this.neuaufbau();
		 }
	}
	
	public static void pfadObjekteLaden(String aMerkmal)
	{
/*
Füllt das Array "Sammlung" mit Pfad-Objekten des gewählten Merkmals
Quelle ist die angegebene CSV Datei
 */
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
        Sammlung = new Pfad[1];
        
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
                	Sammlung = Arrays.copyOf(Sammlung, 1+Zaehler);
                	Sammlung[Zaehler] = new Pfad(Integer.parseInt(eintragGelesen[spalte_StartPX]),Integer.parseInt(eintragGelesen[spalte_StartPY]),Integer.parseInt(eintragGelesen[spalte_EndPX]),Integer.parseInt(eintragGelesen[spalte_EndPY]));
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
	}
	
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
                if (Math.sqrt(Math.pow((Integer.parseInt(eintragGelesen[spalte_EndPX])-xSuche),2)+Math.pow((Integer.parseInt(eintragGelesen[spalte_EndPY])-ySuche),2))<minDist)
                {
                	minDist=Math.sqrt(Math.pow((Integer.parseInt(eintragGelesen[spalte_EndPX])-xSuche),2)+Math.pow((Integer.parseInt(eintragGelesen[spalte_EndPY])-ySuche),2));
                	bestYet=eintragGelesen[spalte_NamePEnd];
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