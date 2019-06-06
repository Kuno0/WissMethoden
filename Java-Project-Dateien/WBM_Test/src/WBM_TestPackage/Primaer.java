package WBM_TestPackage;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.*;

public class Primaer
{
	public static Pfad[] Sammlung;
	
	public static void main(String[] args)
	{		
		JFrame fenster = new JFrame();
		//fenster.setExtendedState(Frame.MAXIMIZED_BOTH);
		fenster.setLocationRelativeTo(null);
		fenster.setTitle("Navigationskarte");
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//pfadObjekteLaden("Ohne");
		pfadObjekteLaden("Barrierefrei");
		
		ImageIcon verwendetesBild = new ImageIcon(".\\Karte.png");
		JLabel kartenLabel = new JLabel(verwendetesBild, JLabel.LEFT);
		kartenLabel.setOpaque(true);		
		
		Drawing linienUndPunkte = new Drawing();
		linienUndPunkte.WertUebergeben(Sammlung);
		
		FlowLayout LayoutManagement = new FlowLayout();
		LayoutManagement.setHgap(0);
		LayoutManagement.setVgap(0);
		LayoutManagement.setAlignment(FlowLayout.LEFT);
		
		Container contentpaneKarte = fenster.getContentPane();
		contentpaneKarte.setLayout(LayoutManagement);
		contentpaneKarte.add(kartenLabel);

		fenster.setGlassPane(linienUndPunkte);
		linienUndPunkte.setVisible(true);

		fenster.pack();
		fenster.setSize(1600, 920);
		fenster.setLocation(120,60);
		fenster.setVisible(true);
	}

	public static void pfadObjekteLaden(String aMerkmal)
	{
		String csvDatenbasis = ".\\DatenBasis.CSV";
		//Sammlung=null;
		
		int spalte_StartPX=3;
		int spalte_StartPY=4;
		int spalte_EndPX=5;
		int spalte_EndPY=6;
		int spalte_MerkmalBarrierefrei=8;
		
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
                System.out.println(eintragGelesen[aktivesMerkmal]);
                if ((aktivesMerkmal==0)||(Integer.parseInt(eintragGelesen[aktivesMerkmal])==1))
                {
                	Sammlung = Arrays.copyOf(Sammlung, 1+Zaehler);
                	Sammlung[Zaehler] = new Pfad(Integer.parseInt(eintragGelesen[spalte_StartPX]),Integer.parseInt(eintragGelesen[spalte_StartPY]),Integer.parseInt(eintragGelesen[spalte_EndPX]),Integer.parseInt(eintragGelesen[spalte_EndPY]));
            		System.out.println(Zaehler);
                	Zaehler++;
                }
                
                br.readLine();
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