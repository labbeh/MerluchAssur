package fr.labbeh.merluchassur;

import org.bukkit.block.Chest;

/**
 * @author labbeh
 * */

public class Assure
{
	/*-----------*/
	/* ATTRIBUTS */
	/*-----------*/
	
	/**
	 * Nombre de mort maximal a laquelle le joueur a droit pour son assurance
	 * */
	public static final int NB_MORT_MAX = 3;
	
	/**
	 * Nom du joueur
	 * */
	private String playerName;
	
	/**
	 * nombre de fois ou le joueur est mort
	 * */
	private int nbMorts;
	
	/**
	 * Coffre d'assurance
	 * */
	private Chest chest;
	
	/**
	 * Vrai après que la commande setchest est été entrée
	 * */
	private boolean waitingForChestSelect;
	
	/**
	 * Contructeur d'un Assure
	 * @param playerName nom du joueur
	 * @param nbMorts nombre de fois le joueur est mort
	 * @param chest coffre d'assurance du joueur
	 * */
	public Assure(String playerName, int nbMorts, Chest chest)
	{
		this.playerName = playerName;
		this.nbMorts 	= nbMorts	;
		this.chest 		= chest		;
	}
	
	/*public Assure(String playerName, AssureLevel level)
	{
		this.playerName = playerName;
		this.nbMorts 	= 0			;
		
		this.chest = null ;
		this.level = level;
		
		this.waitingForChestSelect = false;
	}*/
	
	/**
	 * Construit un Assure à partir de son nom
	 * Initialise le nombre de mort à 0 et le coffre à null
	 * @param playerName nom du joueur en String
	 * */
	public Assure(String playerName)
	{
		this(playerName, 0, null);
	}
	
	/* ACCESSEURS */
	public String 	   getPlayerName 	() { return this.playerName; }
	public int 		   getNbMorts		() { return this.nbMorts   ; }
	//public AssureLevel getAssureLevel	() { return this.level;}
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
