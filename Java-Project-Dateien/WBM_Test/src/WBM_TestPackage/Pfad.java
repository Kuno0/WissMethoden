package WBM_TestPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class Pfad
{	
	public static void main (String[] args)
	{
		GrafikFenster Karte = new GrafikFenster();
		//Karte: 1460 x 758
		Pfad ersterPfad = new Pfad(150,150,100,350, Karte);
		Pfad zweiterPfad = new Pfad(100,350,300,410, Karte);
		Pfad dritterPfad = new Pfad(300,410,140,320, Karte);
		Pfad vierterPfad = new Pfad(140,320,130,280, Karte);
		Pfad fuenfterPfad = new Pfad(130,280,530,300, Karte);
		Pfad sechsterPfad = new Pfad(300,410,330,400, Karte);
		Pfad siebterPfad = new Pfad(330,400,400,330, Karte);
		Pfad achterPfad = new Pfad(400,330,530,300, Karte);
		
		
		Karte.paintWallpaper();
	}
	
	private static int FortlaufendeID;
	private int ID;
	private int startXPos;
	private int startYPos;
	private int endXPos;
	private int endYPos;
	private double Laenge;

	public Pfad(int astartXPos, int astartYPos, int aendXPos, int aendYPos, GrafikFenster aKarte) {
		FortlaufendeID++;
		ID=FortlaufendeID;
		startXPos=astartXPos;
		startYPos=astartYPos;
		endXPos=aendXPos;
		endYPos=aendYPos;
		
		Laenge=Math.sqrt(Math.pow((endXPos-startXPos),2)+Math.pow((endYPos-startYPos),2));
		
		aKarte.paintPfad(startXPos,startYPos,endXPos,endYPos);
		aKarte.paintMarker(startXPos,startYPos);
		aKarte.paintMarker(endXPos,endYPos);
	}

	public void display()
	{
		System.out.println("Start X: "+ startXPos);
		System.out.println("Start Y: "+ startYPos);
		System.out.println("End X: "+ endXPos);
		System.out.println("End Y: "+ endYPos);
	}
	
	public double getLaenge() {
		return Laenge;
	}
	
	
	public int getID() {
		return ID;
	}
		public int getStartXPos() {
		return startXPos;
	}
	public int getStartYPos() {
		return startYPos;
	}
	public int getEndXPos() {
		return endXPos;
	}
	public int getEndYPos() {
		return endYPos;
	}
}