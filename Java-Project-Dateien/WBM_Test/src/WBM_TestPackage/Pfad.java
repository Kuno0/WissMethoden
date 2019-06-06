package WBM_TestPackage;

public class Pfad
{	
	private int startXPos;
	private int startYPos;
	private int endXPos;
	private int endYPos;
	private double Laenge;

	public Pfad(int astartXPos, int astartYPos, int aendXPos, int aendYPos)
	{
		startXPos=astartXPos;
		startYPos=astartYPos;
		endXPos=aendXPos;
		endYPos=aendYPos;
		
		Laenge=Math.sqrt(Math.pow((endXPos-startXPos),2)+Math.pow((endYPos-startYPos),2));
	}
	
	public int getStartXPos() {
		return startXPos;
	}

	public void setStartXPos(int startXPos) {
		this.startXPos = startXPos;
	}

	public int getStartYPos() {
		return startYPos;
	}

	public void setStartYPos(int startYPos) {
		this.startYPos = startYPos;
	}

	public int getEndXPos() {
		return endXPos;
	}

	public void setEndXPos(int endXPos) {
		this.endXPos = endXPos;
	}

	public int getEndYPos() {
		return endYPos;
	}

	public void setEndYPos(int endYPos) {
		this.endYPos = endYPos;
	}

	public double getLaenge() {
		return Laenge;
	}

	public void setLaenge(double laenge) {
		Laenge = laenge;
	}

	
}