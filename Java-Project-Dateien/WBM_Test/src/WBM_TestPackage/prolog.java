package WBM_TestPackage;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.jpl7.Atom;
import org.jpl7.JPL;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Variable;

//import alice.tuprolog.Prolog;
//import alice.tuprolog.SolveInfo;
//import alice.tuprolog.Theory;
public class prolog {
	//private Prolog engine;
	//private Theory vocTheory;
	private static long pstart=1, pende=200;
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
		pende=eEnde;		
	}

	public void setMerkmal(int pMerkmal)
	{
		merkmal=pMerkmal;
	}
	
	public String[] berechneRoute() throws Exception
	{
		if(merkmal==0)
		{
		Query q1 = 
			    new Query( 
				"consult", 
				new Term[] {new Atom("prolog/A2.pl")} 
			    );
		q1.hasSolution();
		
		//System.out.println( "consult " + (q1.hasSolution() ? "succeeded" : "failed"));
		Variable X = new Variable("X");
		//Variable X = new Variable("X");
		Query q2 = 
				  new Query( 
				      "go", 
				      new Term[] {new org.jpl7.Integer(pstart),new org.jpl7.Integer(pende),X} 
				      //new Term[] {new Atom("1"),new Atom("20"),X} 
				  );
			q2.hasSolution();
		    Map binding = q2.next();
		    Term t = (Term) binding.get("X");
		    //Term[] oneTerm=t.toTermArray();
		    String debugstring=t.toString().replace("'[|]'(", "");	
		    debugstring=debugstring.toString().replace(", '[]'", "");
		    debugstring=debugstring.toString().replace(")","");
		    debugstring=debugstring.toString().replace(" ", "");
		    String[] ergebnis=debugstring.split("\\),");
		    String[] pfad=ergebnis[0].split(",");
	    	return pfad;

		}
        else
		{
			Query q1 = 
				    new Query( 
					"consult", 
					new Term[] {new Atom("prolog/a2_beh.pl")} 
				    );
			q1.hasSolution();
			//System.out.println( "consult " + (q1.hasSolution() ? "succeeded" : "failed"));
			Variable X = new Variable("X");
			//Variable X = new Variable("X");
			Query q2 = 
					  new Query( 
					      "go", 
					      new Term[] {new org.jpl7.Integer(pstart),new org.jpl7.Integer(pende),X} 
					      //new Term[] {new Atom("1"),new Atom("20"),X} 
					  );
				q2.oneSolution();
			    Map binding = q2.next();
			    Term t = (Term) binding.get("X");
			    //Term[] oneTerm=t.toTermArray();
			    String debugstring=t.toString().replace("'[|]'(", "");	
			    debugstring=debugstring.toString().replace(", '[]'", "");
			    debugstring=debugstring.toString().replace(")","");
			    debugstring=debugstring.toString().replace(" ", "");
			    String[] ergebnis=debugstring.split("\\),");
			    String[] pfad=ergebnis[0].split(",");
		    	return pfad;
		}
		}
	}
