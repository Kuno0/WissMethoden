package WBM_TestPackage;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.*;

public class Drawing extends JComponent
{
	private static final long serialVersionUID = -5489965288772961937L;
	
	private Pfad[] erhaltenesArray;
	
	private int rgb1=0;
	private int rgb2=0;
	private int rgb3=0;
	private int strokeline=5;
	private int strokedot=8;
	
	public void WertUebergeben(Pfad[] aArray)
	{
		erhaltenesArray=aArray;
	}
	
	public void paintComponent(Graphics g)
	{			
		for (int i=0; i<erhaltenesArray.length;i++)
			{
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(strokeline));
				g2.setColor(new Color(rgb1,rgb2,rgb3));
				g2.drawLine(erhaltenesArray[i].getStartXPos(),erhaltenesArray[i].getStartYPos(),erhaltenesArray[i].getEndXPos(),erhaltenesArray[i].getEndYPos());
				g2.drawOval(erhaltenesArray[i].getStartXPos()-((int)(0.5*strokedot)), erhaltenesArray[i].getStartYPos()-((int)(0.5*strokedot)), strokedot, strokedot);
				g2.drawOval(erhaltenesArray[i].getEndXPos()-((int)(0.5*strokedot)), erhaltenesArray[i].getEndYPos()-((int)(0.5*strokedot)), strokedot, strokedot);
			}
		
	}
}
