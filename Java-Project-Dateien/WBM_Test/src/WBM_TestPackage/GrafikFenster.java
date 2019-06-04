package WBM_TestPackage;

import javax.swing.*;
import java.awt.*;

public class GrafikFenster extends JFrame
{
	private static final long serialVersionUID = 6268564818735637684L;
	int sleepTime = 40;

	public GrafikFenster()
	{
		setSize(1460, 798);
		setLocationRelativeTo(null);
		setTitle("Navigationskarte");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void paintPfad(int xstart, int ystart, int xend, int yend, int rgb1, int rgb2, int rgb3, int stroke)
	{
		Linie DC = new Linie();
		DC.WerteFestlegen(xstart, ystart, xend, yend, rgb1, rgb2, rgb3, stroke);
		add(DC);
		this.setVisible(true);
	}
	
	public void paintPfad(int xstart, int ystart, int xend, int yend)
	{
		Linie DC = new Linie();
		DC.WerteFestlegen(xstart, ystart, xend, yend);
		add(DC);
		this.setVisible(true);
	}
	
	public void paintMarker(int xpos, int ypos, int rgb1, int rgb2, int rgb3, int stroke)
	{
		Marker DC = new Marker();
		DC.WerteFestlegen(xpos, ypos, rgb1, rgb2, rgb3, stroke);
		add(DC);
		this.setVisible(true);
	}
	
	public void paintMarker(int xpos, int ypos)
	{
		Marker DC = new Marker();
		DC.WerteFestlegen(xpos, ypos);
		add(DC);
		this.setVisible(true);
	}
	
	
	public void paintWallpaper()
	{
		ImageIcon pngAbbildungKarte = new ImageIcon("C:\\Users\\Jan\\Ordnerstruktur\\CodeRepository\\WBM_Test\\Karte.png");
		JLabel KartenLabel = new JLabel (pngAbbildungKarte);
		this.add(KartenLabel);
		this.setVisible(true);
	}
	

}