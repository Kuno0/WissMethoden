package WBM_TestPackage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import alice.tuprolog.Prolog;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.Theory;
public class prolog {
	//private Prolog engine;
	//private Theory vocTheory;
	private int pstart=12, pend=122;
	private int merkmal;
	
	public prolog() throws Exception 
	{
		//engine = new Prolog();
		//vocTheory = new Theory(new FileInputStream("prolog/a2.pl"));
		//engine.setTheory(vocTheory);
	}
	public void setEingang(int eStart)
	{
		pstart=eStart;		
	}
	public void setAusgang(int eEnde)
	{
		pend=eEnde;		
	}

	public void setMerkmal(int pMerkmal)
	{
		merkmal=pMerkmal;
	}
	
	public String[] berechneRoute() throws Exception
	{
		  Prolog engine = new Prolog();
	      Theory vocTheory = new Theory(new FileInputStream("prolog/a2.pl"));
		  engine.addTheory(vocTheory);
	      SolveInfo info = engine.solve("go("+pstart+","+pend+",P).");
	      if (info.isSuccess()) 
	      {    
	    	  String uString=info.toString().substring(11, info.toString().length());
		      var sammlung= uString.split("\\]");
		      String laenge=sammlung[1].replaceAll(",","");
		      var pfad=sammlung[0].split(",");
		      //int ilaenge=Integer.parseInt(laenge);
	    	  
	        if (engine.hasOpenAlternatives()) {
	           info = engine.solveNext();
	        }
	        return pfad;
	      }
	      return null;
	}
	}
