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
	JButton buttonSetEnd;
	JButton buttonGetWay;
	@SuppressWarnings("rawtypes")
	JComboBox comboBoxAttributwahl;
	JTextField textFStart;
	JTextField textFZiel;
	JTextArea textfeedback;
	public prolog pclass;
	
	public static int spalte_NamePStart=0;
	public static int spalte_NamePEnd=1;
	public static int spalte_StartPX=3;
	public static int spalte_StartPY=4;
	public static int spalte_EndPX=5;
	public static int spalte_EndPY=6;
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
		JPanel buttonPanel = new JPanel();
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
	 			 //textFStart.setText("Nächster: "+ naechstenPunktErmitteln(MenuXPos,MenuYPos) +"; X: "+ MenuXPos +"; Y: "+ MenuYPos);
	 			 textFStart.setText(naechstenPunktErmitteln(MenuXPos,MenuYPos));
	 			//buttonPanel.updateUI();
			 }
	 		 else if (event.getActionCommand()=="Punkt als Ziel festlegen")
	 		 {
	 			//textFZiel.setText("Nächster: "+ naechstenPunktErmitteln(MenuXPos,MenuYPos) +"; X: "+ MenuXPos +"; Y: "+ MenuYPos);
	 			textFZiel.setText(naechstenPunktErmitteln(MenuXPos,MenuYPos));
	 			//buttonPanel.updateUI();
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
	    
		
		buttonSetStart = new JButton("Clear");
		buttonSetEnd = new JButton("Karte");
		buttonGetWay = new JButton("Strecke ermitteln");
		
		String[] attributauswahl = { "Ohne", "Barrierefrei" };
		comboBoxAttributwahl = new JComboBox(attributauswahl);
		
		textfeedback = new JTextArea(" Bitte geben Sie die Start und den Zielpunkt ein. \n                       \n                                ");
		textFStart = new JTextField("Test Start \t \t ");
		textFZiel = new JTextField("Test Ziel \t \t ");
		textfeedback.setBounds(100, 70, 30, 10);
		textfeedback.setOpaque(false);
		textfeedback.setEditable(false);
		textfeedback.setBorder(null);
		
		//buttonPanel.setBackground(Color.white);
		buttonPanel.setSize(800, 1000);
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
		//buttonPanel.add(textfeedback);
		buttonPanel.setSize(buttonPanel.getWidth(), buttonPanel.getHeight());
		//buttonPanel.setPreferredSize(new Dimension(640, 480));
		
		
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
		contentpaneKarte.add(textfeedback);
		hintergrundbildLabel.addMouseListener(new ClickListener());
		
		this.setLocationRelativeTo(null);
		//this.setSize(1700, 908);
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setLocation(120,60);
		this.setResizable(true);
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
	// Fenster ausführen
			this.setVisible(true);
		}else {
			
			Drawing linienUndPunkte = new Drawing();
			linienUndPunkte.WertUebergeben(Sammlung);
			this.setGlassPane(linienUndPunkte);
			linienUndPunkte.setVisible(true);
	// Fenster ausführen
			this.setVisible(true);
			
		}

	}
	
	public void actionPerformed(ActionEvent e) 
	{
		 if(e.getSource() == this.buttonSetStart)
		 {
			 textfeedback.setText(" Bitte geben Sie die Start und den Zielpunkt ein. \n Die Karte wurde geleert.\n                              ");
			 Sammlung.clear();
			 this.neuaufbau();
		 }
		 else if(e.getSource() == this.buttonSetEnd)
		 {
			 Sammlung.clear();
			 textfeedback.setText("Das Netz wurde generiert. \n Bitte geben Sie die Start und den Zielpunkt ein.                 \n                       ");
			 berechneBackground();
			 this.neuaufbau();
		 }
		 else if (e.getSource() == this.buttonGetWay)
		 {
			 Sammlung.clear();
			textfeedback.setText("---------------------------------------------------------------------- \n ----------------------------------------------------------------------  \n Berechne die kürzeste Route.");
			textfeedback.updateUI();
			textfeedback.revalidate();
			textfeedback.validate();
			textfeedback.update(textfeedback.getGraphics());
			 try {
				 
				prologberechnung();
			} catch (Exception e1) {
	
				e1.printStackTrace();
			}			 
			 //pfadObjekteLaden(comboBoxAttributwahl.getSelectedItem().toString());
			 textfeedback.setText("Die Route wurde berechnet und \n befindet sich sichtbar auf der Karte.    \n                            ");
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
		try {
		this.pclass.setEingang(Integer.parseInt(textFStart.getText().substring(0, 3)));
		this.pclass.setAusgang(Integer.parseInt(textFZiel.getText().substring(0, 3)));
		berechneterpfadladen(this.pclass.berechneRoute());
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Bitte geben Sie Start- und Endpunkt an.");
		}

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
	                this.Sammlung.add(new Pfad(Integer.parseInt(eintragGelesen[spalte_StartPX]),Integer.parseInt(eintragGelesen[spalte_StartPY]),Integer.parseInt(eintragGelesen[spalte_EndPX]),Integer.parseInt(eintragGelesen[spalte_EndPY])));
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
	                	this.Sammlung.add(new Pfad(Integer.parseInt(eintragGelesen[spalte_StartPX]),Integer.parseInt(eintragGelesen[spalte_StartPY]),Integer.parseInt(eintragGelesen[spalte_EndPX]),Integer.parseInt(eintragGelesen[spalte_EndPY])));

	                }	            
	                if ((Integer.parseInt(eintragGelesen[1])==Integer.parseInt(pfad[g+1])&&Integer.parseInt(eintragGelesen[0])==Integer.parseInt(pfad[g]))||(Integer.parseInt(eintragGelesen[1])==Integer.parseInt(pfad[g])&&Integer.parseInt(eintragGelesen[0])==Integer.parseInt(pfad[g+1])))
	                {
	                	this.Sammlung.add(new Pfad(Integer.parseInt(eintragGelesen[spalte_StartPX]),Integer.parseInt(eintragGelesen[spalte_StartPY]),Integer.parseInt(eintragGelesen[spalte_EndPX]),Integer.parseInt(eintragGelesen[spalte_EndPY])));

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
Füllt das Array "Sammlung" mit Pfad-Objekten des gewählten Merkmals
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
                	//Sammlung[Zaehler] = new Pfad(Integer.parseInt(eintragGelesen[spalte_StartPX]),Integer.parseInt(eintragGelesen[spalte_StartPY]),Integer.parseInt(eintragGelesen[spalte_EndPX]),Integer.parseInt(eintragGelesen[spalte_EndPY]));
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
	private String punktnamen(int Knoten)
	{
		switch(Knoten)
		{
		case 1:return ": Bushaltestelle UniCenter";
		case 2:return ": Unicenter";
		case 3:return ": Verbindung UniCenter/ Bus";
		case 4:return ": Kreuzung Ubahn / Campusbrücke";
		case 5:return ": U-Bahnhaltestelle Ruhr-Uni";
		case 6:return ": Parkhaus B";
		case 7:return ": Kreuzung PB / Campusbrücke";
		case 8:return ": Kreuzung MZ / Campusbrücke";
		case 9:return ": Musisches Zentrum (MZ)";
		case 10:return ": Kulturcafe";
		case 11:return ": Studierenden-Service-Center (SSC)";
		case 12:return ": Info-Schild Ruhr-Uni (oben)";
		case 13:return ": Bushaltestelle Ruhr-Uni (unten)";
		case 14:return ": Kreuzung UV / SH";
		case 15:return ": Studierendenhaus (SH)";
		case 16:return ": Universitäts-Verwaltung (UV)";
		case 17:return ": Kreuzung BIB";
		case 18:return ": Kreuzung UB / MA";
		case 19:return ": Universitäts-Bibliothek / Kunstsammlung (UB)";
		case 20:return ": Krezuung UB / Treppe zum Forum";
		case 21:return ": Krezuung oben MA";
		case 22:return ": MA / HMA";
		case 23:return ": Krezung MZ /  Treppe West";
		case 24:return ": Krezung m-Nordstraße / Treppe West";
		case 25:return ": Zentrum für klinische Forschung";
		case 26:return ": Parkhasu für Mieter des TZR";
		case 27:return ": AkaFö TestDaF";
		case 28:return ": Biomedizinisches Zentrum (BMZ)";
		case 29:return ": Kreuzung Max-Imdahl-Straße / Weststr.";
		case 30:return ": Parkplatz Max-Imdahl-Straße / Fakultät für Sportwissenschaften";
		case 31:return ": Krezung Zentrum med. Forsch. / MA";
		case 32:return ": Kreuzung PH TZR / Parkplatz VC / TZR";
		case 33:return ": Parkplatz VC / TZR";
		case 34:return ": Technologiezentrum Ruhr (TZR)";
		case 35:return ": Vita Campus (VC)";
		case 36:return ": P12";
		case 37:return ": Kreuzung Weststraße / P13";
		case 38:return ": Kreuzung Forum oben / MA";
		case 39:return ": MA oben ";
		case 40:return ": Krezung M-Südstraße / MA";
		case 41:return ": Kreuzung M-Südstraße / TZR";
		case 42:return ": Kreuzung M-Südstraße / VC";
		case 43:return ": Kreuzung M-Südstraße / Weststraße ";
		case 44:return ": P13";
		case 45:return ": Treppenhaus P13";
		case 46:return ": Kreuzung Grünfläche / M-Südstraße ";
		case 47:return ": Grünfläche West - Nord";
		case 48:return ": Grünfläche West - Ost";
		case 49:return ": Grünfläche West - Nord";
		case 50:return ": Grünfläche West -Nord";
		case 51:return ": Kreuzung Grünfläche / Forum oben";
		case 52:return ": Kreuzung Forum / Kunstsammlung";
		case 53:return ": Parkhaus T (Forum)";
		case 54:return ": Kreuzung Forum / HGA";
		case 55:return ": Kreuzung CC / HGA";
		case 56:return ": HGA";
		case 57:return ": Campus-Center (CC)";
		case 58:return ": Kreuzung Grünfläche / CC Nord";
		case 59:return ": Kreuzung Grünfläche Ring G/M Süd";
		case 60:return ": Kreuzung Grünfläche Ring G/M / Q-West";
		case 61:return ": Q-West";
		case 62:return ": Kreuzung Q-West / HGB";
		case 63:return ": Krezuung Grünfläche / M-Südstraße ";
		case 64:return ": HGB";
		case 65:return ": Kreuzung GA / Grünfläche";
		case 66:return ": GA";
		case 67:return ": Kreuzung Grünfläche / CC West";
		case 68:return ": Kreuzung GB / Grünfläche";
		case 69:return ": GB";
		case 70:return ": HGC";
		case 71:return ": Kreuzung Grünfläche / GC";
		case 72:return ": GC";
		case 73:return ": Kreuzung Grünfläche / Weststraße / Nordstraße ";
		case 74:return ": Ende Weststraße";
		case 75:return ": Kreuzung UB / FNO";
		case 76:return ": Forum Nord Ost (FNO)";
		case 77:return ": Kreuzung AudiMax, Forum, West, oben";
		case 78:return ": Kreuzung Forum West/ GA";
		case 79:return ": Audimax";
		case 80:return ": Kreuzung Audimax Treppen West";
		case 81:return ": Kreuzung Mensa West";
		case 82:return ": Mensa / Veranstaltungszentrum (VZ)";
		case 83:return ": Kreuzung Kunstsammlungen / Grünfläche West";
		case 270:return ": Kreuzung Nordstraße / Weststraße ";
		case 84:return ": Kreuzung Grünfläche Ost/ Mitte Forum (kein Anschluss)";
		case 85:return ": Kreuzung Campus West Aufzug Mitte";
		case 86:return ": Kreuzung Campus West Aufzug G-Nordstraße ";
		case 87:return ": Kreuzung G-Nordstraße / GA";
		case 88:return ": Kreuzung G-Nordstraße / GB";
		case 89:return ": Kreuzung G-Nordstraße / GC";
		case 90:return ": GD";
		case 91:return ": Kreuzung Mensa West / Mittelebene";
		case 92:return ": Kreuzung Mensa West / Unten";
		case 93:return ": Kreuzung Mensa Südwest";
		case 94:return ": Kreuzung Mensa Südwest unten";
		case 95:return ": Kreuzung Mensa Südost unten";
		case 96:return ": Kreuzung Mensa Südost oben";
		case 97:return ": Kreuzung Mensa Ost";
		case 98:return ": VZ unten West";
		case 99:return ": VZ oben West";
		case 100:return ": VZ oben Ost";
		case 101:return ": VZ unten Ost";
		case 102:return ": Kreuzung G-Südstraße / Mensa";
		case 103:return ": Veranstaltungszentrum (VZ)";
		case 104:return ": Kreuzung Mensa West unten / GAFO";
		case 105:return ": Kreuzung GAFO / GA";
		case 106:return ": Kreuzung G-Südstraße / Frauenparkplatz rechts";
		case 107:return ": Kreuzung G-Südstraße / GA";
		case 108:return ": Kreuzung G-Südstraße / Frauenparkplatz Mitte";
		case 109:return ": Kreuzung G-Südstraße / GB";
		case 110:return ": Kreuzung G-Südstraße / Frauenparkplatz links, Ost";
		case 111:return ": Frauenparkplatz Links";
		case 112:return ": Kreuzung G-Südstraße / GC";
		case 113:return ": Kreuzung G-Südstraße / Frauenparkplatz rechts West";
		case 114:return ": Kreuzung G-Südstraße / GD";
		case 115:return ": Kreuzung Frauenparkplatz links / Tennisplatz west";
		case 116:return ": Kreuzung Frauenparkplatz links / Tennisplatz ost";
		case 117:return ": Kreuzung Frauenparkplatz Mitte West / Sportplatz ";
		case 118:return ": Sportplatz";
		case 119:return ": Kreuzung Frauenparkplatz Mitte Ost / Sportplatz";
		case 120:return ": Frauenparkplatz Mitte";
		case 121:return ": Kreuzung Uni Kids / Frauenparkplatz rechts";
		case 122:return ": UniKids Kindertagesstätte";
		case 123:return ": Kreuzung Frauenparkplatz rechts / CASPO";
		case 124:return ": Frauenparkplatz Rechts";
		case 125:return ": Kreuzung CASPO / Aufzug VZ";
		case 126:return ": Campussport (CASPO)";
		case 127:return ": Parkplatz CASPO";
		case 128:return ": Kreuzung Mensa Treppe Ost / N-Südstraße ";
		case 129:return ": Zum Beckmanns Hof";
		case 130:return ": N-Süd";
		case 131:return ": Zum Botanischen Garten";
		case 132:return ": Kreuzung Treppen N-Südstraße";
		case 133:return ": Kreuzung N-Südstraße / NA";
		case 134:return ": Kreuzung Treppen Botanischer Garten / N-Südstraße";
		case 135:return ": Kreuzung Südstraße / NABF";
		case 136:return ": Kreuzung N-Südstraße / NB";
		case 137:return ": NT";
		case 138:return ": Kreuzung N-Südstraße / RUBION";
		case 139:return ": RUBION";
		case 140:return ": Kreuzung Südstraße / NC";
		case 141:return ": Isotopenlabor";
		case 142:return ": Kreuzung N-Südstraße / NI";
		case 143:return ": NI";
		case 144:return ": Kreuzung N-Südstraße / ND";
		case 145:return ": Kreuzung N-Südstraße / ZN";
		case 146:return ": Zentrum für Neuroinformatik (ZN)";
		case 147:return ": ND";
		case 148:return ": NC";
		case 149:return ": NB";
		case 150:return ": NA";
		case 151:return ": Kreuzung N-Nordstraße / NA";
		case 152:return ": Kreuzung N-Nordstraße / NB";
		case 153:return ": Kreuzung N-Nordstraße / NC";
		case 154:return ": Kreuzung N-Nordstraße / ND";
		case 155:return ": Kreuzung N-Nordstraße / ZEMOS";
		case 156:return ": ZEMOS";
		case 157:return ": Kreuzung N-Nordstraße / Oststraße";
		case 158:return ": Kreuzung N-Südstraße / Oststraße ";
		case 159:return ": Kreuzung N-Nordstraße Grünfläche NI";
		case 160:return ": Kreuzung Mensa Ost / Unten NA";
		case 161:return ": Kreuzung Mensa Ost / Unten NA vor Treppe";
		case 162:return ": Kreuzung Mensa Ost / Mitte NA nach Treppe";
		case 163:return ": Kreuzung Audimax Ost / NA Süd";
		case 164:return ": Kreuzung Audimax Ost / NA Nord";
		case 165:return ": Kreuzung NA / Audimax";
		case 166:return ": Kreuzung NA (vor Treppe) / Audimax ";
		case 167:return ": Kreuzung NA (vor Stich) / Audimax";
		case 168:return ": Kreuzung Audimax / Forum";
		case 169:return ": Kreuzung NA-Süd";
		case 170:return ": NA-Südeingang";
		case 171:return ": Kreuzung NB Südwest";
		case 172:return ": Kreuzung NB Südwest / Nordwest";
		case 173:return ": Kreuzung NB Südost";
		case 174:return ": Kreuzung NB / NC West";
		case 175:return ": Südwesteingang NB";
		case 176:return ": Kreuzung NB / NC Ost";
		case 177:return ": Kreuzung NC / ND Südwest";
		case 178:return ": Kreuzung NC / ND Südost";
		case 179:return ": Kreuzung ND / HNC Süd";
		case 180:return ": Kreuzung ND Nordwest";
		case 181:return ": Kreuzung Treppen vor I-Reihe";
		case 182:return ": Kreuzung HNC";
		case 183:return ": HNC ";
		case 184:return ": Kreuzung NC Nordost";
		case 185:return ": Kreuzung NC Nordwest";
		case 186:return ": Kreuzung NC / HNB Süd";
		case 187:return ": Kreuzung HNB";
		case 188:return ": HNB";
		case 189:return ": Kreuzung Brücke HNB zur I-Reihe";
		case 190:return ": Kreuzung NB Nordost";
		case 191:return ": Kreuzung NB Nordwest";
		case 192:return ": Kreuzung NB Nord-Süd";
		case 193:return ": Kreuzung NA/ NB Nord";
		case 194:return ": Kreuzung NB Nordost";
		case 195:return ": Kreuzung NB Nord";
		case 196:return ": Kreuzung NB Nord / HNA";
		case 197:return ": HNA";
		case 198:return ": Kreuzung HNA / HZO";
		case 199:return ": Kreuzung HZO / unterer Eingang";
		case 200:return ": Kreuzung HZO / Treppenabgang";
		case 201:return ": Kreuzung HZO / Eingang hinten";
		case 202:return ": HZO";
		case 203:return ": Kreuzung HZO Eingang Mitte";
		case 204:return ": Kreuzung Forum Ost";
		case 205:return ": Kreuzung HZO oben / Treppe Forum";
		case 206:return ": Kreuzung HZO oben";
		case 207:return ": Kreuzung Forum / HZO Nord oben";
		case 208:return ": Kreuzung Forum / HZO Nord unten";
		case 209:return ": Kreuzung Forum / Forum Treppe Ost unten";
		case 210:return ": Kreuzung HZO Nord / IA";
		case 211:return ": Kreuzung Grünfläche Ecke";
		case 212:return ": Kreuzung HZO Sitzbänke Süd";
		case 213:return ": Kreuzung Grünfläche / Außentreppe Nord";
		case 214:return ": Kreuzung Grünfläche / Sitzbänke";
		case 215:return ": Kreuzung Sitzfläche Nord";
		case 216:return ": Kreuzung Sitzbänke / I-Südstraße";
		case 217:return ": Kreuzung Treppe IA";
		case 218:return ": Kreuzung IA";
		case 219:return ": Kreuzung I-Südstraße / IA";
		case 220:return ": Kreuzung HIA";
		case 221:return ": HIA";
		case 222:return ": Kreuzung IB / Treppen oben";
		case 223:return ": Kreuzung IB / Treppen unten";
		case 224:return ": Kreuzung Grünfläche Ecke HIB";
		case 225:return ": Kreuzung Brücke Treppe HNB";
		case 226:return ": Kreuzung Treppenabsatz NB / NC";
		case 227:return ": Kreuzung HIB";
		case 228:return ": HIB";
		case 229:return ": Kreuzung IC / Treppe oben";
		case 230:return ": Kreuzung IC / Treppe unten";
		case 231:return ": Kreuzung I-Südstraße / IC";
		case 232:return ": Kreuzung I-Südstraße / IB";
		case 233:return ": Kreuzung HIC";
		case 234:return ": Kreuzung Grünfläche / HIC ";
		case 235:return ": Kreuzung Sitzflächen Teich";
		case 236:return ": Kreuzung Teich / Dreieck West";
		case 237:return ": Kreuzung Teich / Dreieck Süd";
		case 238:return ": Kreuzung Teich / Dreieck Nord";
		case 239:return ": Kreuzung Grünfläche / I-Südstraße ";
		case 240:return ": HIC";
		case 241:return ": Kreuzung I-Südstraße / Grünfläche";
		case 242:return ": Kreuzung I-Südstraße / ID";
		case 243:return ": Kreuzung ID Süd / Grünfläche";
		case 244:return ": Kreuzung Grünfläche Ost";
		case 245:return ": Kreuzung Grünfläche oberer Ausgang";
		case 246:return ": Kreuzung I-Südstraße / Oststraße ";
		case 247:return ": IA";
		case 248:return ": Kreuzung IA Südwest";
		case 249:return ": IB";
		case 250:return ": IC";
		case 251:return ": Kreuzung ID";
		case 252:return ": ID";
		case 253:return ": Zur Hochschule Bochum";
		case 254:return ": Kreuzung IA West";
		case 255:return ": Kreuzung IA / Grünfläche vor IA";
		case 256:return ": Kreuzung I-Nordstraße / IA";
		case 257:return ": IAN";
		case 258:return ": Kreuzung IA / I-Nordstraße Grünfläche";
		case 259:return ": Kreuzung SSC unten";
		case 260:return ": SSC";
		case 261:return ": Kreuzung I-Nordstraße / ZGH";
		case 262:return ": Zentrum für grenzflächendominierte Höchstleistungswerkstoffe (ZGH)";
		case 263:return ": Kreuzung I-Nordstraße / IB / IBN";
		case 264:return ": IBN";
		case 265:return ": Kreuzung I-Nordstraße / IC / ICN";
		case 266:return ": ICN";
		case 267:return ": Kreuzung I-Nordstraße / ID";
		case 268:return ": Kreuzung I-Nordstraße / Oststraße";
		case 269:return ": Zum Lennershof";
		case 271:return ": Botanischer Garten / ND Eingang";
		case 272:return ": CASPO Süd / Treppe";

	default:
		return "nicht benannt";
		}
	}
	public String naechstenPunktErmitteln(int xSuche, int ySuche)
	{
		BufferedReader br = null;
        String line = "";
        int dzahl=0;
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
                	/*dzahl=Integer.parseInt(eintragGelesen[spalte_NamePStart]);
                	String zwInt=String.format("%03d", dzahl);
                	bestYet=zwInt+punktnamen(dzahl);*/
                	bestYet=eintragGelesen[spalte_NamePStart];
                }
                if (Math.sqrt(Math.pow((Integer.parseInt(eintragGelesen[spalte_EndPX])-xSuche),2)+Math.pow((Integer.parseInt(eintragGelesen[spalte_EndPY])-ySuche),2))<minDist)
                {
                	minDist=Math.sqrt(Math.pow((Integer.parseInt(eintragGelesen[spalte_EndPX])-xSuche),2)+Math.pow((Integer.parseInt(eintragGelesen[spalte_EndPY])-ySuche),2));
                	/*dzahl=Integer.parseInt(eintragGelesen[spalte_NamePStart]);
                	String zwInt=String.format("%03d", dzahl);
                	bestYet=zwInt+punktnamen(dzahl);*/
                	bestYet=eintragGelesen[spalte_NamePEnd];
                }
            }
        	dzahl=Integer.parseInt(bestYet);
        	String zwInt=String.format("%03d", dzahl);
        	bestYet=zwInt+punktnamen(dzahl);

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