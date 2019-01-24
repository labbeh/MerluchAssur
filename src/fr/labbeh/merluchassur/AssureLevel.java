package fr.labbeh.merluchassur;

public enum AssureLevel
{
	CHEF  (1, "chef", 100),
	NORMAL(2, "normal", 50),
	GUEUX (3, "gueux", 25);
	
	private int    numNiveau;
	private int    xpRendu	;
	private String type	 	;
	
	AssureLevel(int numNiveau, String type, int xpRendu)
	{
		this.numNiveau = numNiveau;
		this.type 	   = type	  ;
		this.xpRendu   = xpRendu  ;
	}
}
