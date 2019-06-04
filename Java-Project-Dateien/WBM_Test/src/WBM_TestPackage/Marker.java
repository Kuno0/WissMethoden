package WBM_TestPackage;

import javax.swing.JComponent;
import java.awt.*;

public class Marker extends JComponent
{
	private int xpos;
	private int ypos;
	private int rgb1=0;
	private int rgb2=0;
	private int rgb3=0;
	private int stroke=4;
	
	public void WerteFestlegen(int axpos, int aypos, int argb1, int argb2, int argb3, int astroke)
	{
		xpos=axpos;
		ypos=aypos;
		rgb1=argb1;
		rgb2=argb2;
		rgb3=argb3;
		stroke=astroke;
	}
	
	public void WerteFestlegen(int axpos, int aypos)
	{
		xpos=axpos;
		ypos=aypos;
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(stroke));
		g2.setColor(new Color(rgb1,rgb2,rgb3));
		g2.drawOval(xpos-((int)(0.5*stroke)), ypos-((int)(0.5*stroke)), stroke, stroke);
	}
}
