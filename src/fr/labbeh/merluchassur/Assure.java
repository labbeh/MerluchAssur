package fr.labbeh.merluchassur;

import org.bukkit.block.Chest;

/**
 * @author labbeh
 * */

public class Assure
{
	/* ATTRIBUTS */
	public static final int NB_MORT_MAX = 3;
	
	private String playerName;
	private int    nbMorts	 ;
	
	private AssureLevel level;
	
	private Chest chest; // coffre de l'assure
	
	private boolean waitingForChestSelect; // true après que la commande setchest est été entrée
	
	public Assure(String playerName, int nbMorts, Chest chest)
	{
		this.playerName = playerName;
		this.nbMorts 	= nbMorts	;
		this.chest 		= chest		;
	}
	
	public Assure(String playerName, AssureLevel level)
	{
		this.playerName = playerName;
		this.nbMorts 	= 0			;
		
		this.chest = null ;
		this.level = level;
		
		this.waitingForChestSelect = false;
	}
	
	public Assure(String playerName)
	{
		this(playerName, AssureLevel.NORMAL);
	}
	
	/* ACCESSEURS */
	public String 	   getPlayerName 	() { return this.playerName; }
	public int 		   getNbMorts		() { return this.nbMorts   ; }
	public AssureLevel getAssureLevel	() { return this.level;}
	public Chest 	   getChest			() { return this.chest;}
	public boolean 	   isWaitingForChest() { return this.waitingForChestSelect; }
	
	/* MODIFICATEURS */
	public void setWaitingForChest() { this.waitingForChestSelect = true; }
	public void incMort			  () { this.nbMorts++					; }
	public void resetNbMorts	  () { this.nbMorts 			  = 0	; }
	public void setChest(Chest chest)
	{
		this.chest = chest;
		this.waitingForChestSelect = false;
	}
	
}
