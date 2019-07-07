package WBM_TestPackage;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.*;

public class Drawing extends JComponent
{
	private static final long serialVersionUID = -5489965288772961937L;
	public ArrayList<Pfad> erhaltenesBackgroundArray;
	public ArrayList<Pfad> erhaltenesArray;
	
	private Boolean zeichnungsgitter=false;
	private int rgb1=200;
	private int rgb2=20;
	private int rgb3=0;
	private int rgbB1=0;
	private int rgbB2=100;
	private int rgbB3=0;
	private int strokeline=5;
	private int strokelineB=2;
	private int strokedot=8;
	
	public void WertUebergeben(ArrayList<Pfad> aArray)
	{
		erhaltenesArray=aArray;
		zeichnungsgitter=false;
	}
	public void WertUebergebenB(ArrayList<Pfad> aArray)
	{
		erhaltenesBackgroundArray=aArray;
		zeichnungsgitter=true;
		
	}	
	public void paintComponent(Graphics g)
	{	
		Graphics2D g2 = (Graphics2D) g;
		if(zeichnungsgitter==false)
		for (int i=0; i<erhaltenesArray.size();i++)
			{
				g2.setStroke(new BasicStroke(strokeline));
				g2.setColor(new Color(rgb1,rgb2,rgb3));
				g2.drawLine(erhaltenesArray.get(i).getStartXPos(),erhaltenesArray.get(i).getStartYPos(),erhaltenesArray.get(i).getEndXPos(),erhaltenesArray.get(i).getEndYPos());
				g2.drawOval(erhaltenesArray.get(i).getStartXPos()-((int)(0.5*strokedot)), erhaltenesArray.get(i).getStartYPos()-((int)(0.5*strokedot)), strokedot, strokedot);
				g2.drawOval(erhaltenesArray.get(i).getEndXPos()-((int)(0.5*strokedot)), erhaltenesArray.get(i).getEndYPos()-((int)(0.5*strokedot)), strokedot, strokedot);
				
			}else {
		
		for (int i=0; i<erhaltenesBackgroundArray.size();i++)
			{
				g2.setStroke(new BasicStroke(strokelineB));
				g2.setColor(new Color(rgbB1,rgbB2,rgbB3));
				g2.drawLine(erhaltenesBackgroundArray.get(i).getStartXPos(),erhaltenesBackgroundArray.get(i).getStartYPos(),erhaltenesBackgroundArray.get(i).getEndXPos(),erhaltenesBackgroundArray.get(i).getEndYPos());
				g2.drawOval(erhaltenesBackgroundArray.get(i).getStartXPos()-((int)(0.5*strokedot)), erhaltenesBackgroundArray.get(i).getStartYPos()-((int)(0.5*strokedot)), strokedot, strokedot);
				g2.drawOval(erhaltenesBackgroundArray.get(i).getEndXPos()-((int)(0.5*strokedot)), erhaltenesBackgroundArray.get(i).getEndYPos()-((int)(0.5*strokedot)), strokedot, strokedot);
			}
		}
	}
}
