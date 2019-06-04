package WBM_TestPackage;

import javax.swing.JComponent;
import java.awt.*;

public class Linie extends JComponent
{
	private int xstart;
	private int ystart;
	private int xend;
	private int yend;
	private int rgb1=0;
	private int rgb2=0;
	private int rgb3=0;
	private int stroke=3;
	
	public void WerteFestlegen(int axstart, int aystart, int axend, int ayend, int argb1, int argb2, int argb3, int astroke)
	{
		xstart=axstart;
		ystart=aystart;
		xend=axend;
		yend=ayend;
		rgb1=argb1;
		rgb2=argb2;
		rgb3=argb3;
		stroke=astroke;
	}
	
	public void WerteFestlegen(int axstart, int aystart, int axend, int ayend)
	{
		xstart=axstart;
		ystart=aystart;
		xend=axend;
		yend=ayend;
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(stroke));
		g2.setColor(new Color(rgb1,rgb2,rgb3));
		g2.drawLine(xstart,ystart,xend,yend);
	}
}
