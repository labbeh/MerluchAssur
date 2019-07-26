package fr.labbeh.merluchassur;

import fr.labbeh.merluchassur.commands.*;
import fr.labbeh.merluchassur.data.IData;
import fr.labbeh.merluchassur.data.TextFileData;
import fr.labbeh.merluchassur.files.*;
import fr.labbeh.merluchassur.listener.MerluchAssurListener;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MerluchAssur extends JavaPlugin
{
	/**
	 * @author labbeh
	 * */
	
	/**
	 * constante pour afficher le nom du plugins lorsqu'il envoie un message dans la console
	 * ou dans le chat
	 * */
	public static final String PLUGIN_NAME = "[MerluchAssur] ";
	
	/**
	 * Chemin du dossier qui contient les fichiers de configuration
	 * */
	public static final String DATAS_PATH  	 = "./plugins/merluchDatas"; 
	
	/**
	 * HashMap d'assuré qui associe au nom du joueur son objet Assure qui lui est
	 * associé et charger à partir du fichier de sauvegarde
	 * */
	//private HashMap<String, Assure> assures;
	
	/**
	 * Instance de FileUtilities pour la gestion de l'�criture et lecture dans les fichiers de configuration
	 * */
	//private FileUtilities fu;
	
	/**
	 * Gestion des données
	 * */
	private IData datas;
	
	
	/**
	 * Construit une instance du plugin
	 * */
	public MerluchAssur() {
		super();
		//this.assures = new HashMap<>();
		//this.fu = new FileUtilities(this);
		datas = new TextFileData(this);
		
		//this.enDure();
		//this.setAssure(null,0,0.0,0.0,0.0);
	}
	
	/**
	 * Mode test pour rentrer des assurés en dure
	 * Ajoute 2 assurés du nom de labbeh et tsaero
	 * */
	/*public void enDure() {
		this.assures.put("labbeh", new Assure("labbeh"));
		this.assures.put("tsaero", new Assure("tsaero"));
	}*/
	
	/**
	 * Ajoute un nouvel assur� et cr��er son fichier de configuration
	 * @param playerName nom du joueur � assurer en String
	 * */
	public void add(String playerName) {
		/*this.assures.put(playerName, new Assure(playerName));
		this.saveAll();*/
		datas.addAssure(new Assure(playerName));
	}
	
	/**
	 * Démarre le plugin en chargeant en mémoire tous les assurés
	 * Crééer le dossier de configuration si il n'existe pas
	 * Affiche un message pour dire que le plugin est actif
	 * */
	@Override
	public void onEnable() {
		super.onEnable();
		
		this.getCommand("merluch"	  ).setExecutor(new CommandMerluch(this)	 );
		this.getCommand("merluchadmin").setExecutor(new CommandMerluchAdmin(this));
		
		this.getServer().getPluginManager().registerEvents(new MerluchAssurListener(this), this);
		
		// pour le dossier qui contiendra les fichiers de configuration / serializations des assurances des joueurs
		/*if(this.prepareSerialization())
			System.out.println(MerluchAssur.PLUGIN_NAME +"Aucun dossier de fichiers de configuration, il vient d'être crééer");
		else
			System.out.println(MerluchAssur.PLUGIN_NAME +"dossier de fichiers de configuration détecté");
		
		this.fu.readAssureStat();*/
		datas.init();
		
		System.out.println("MerluchAssur beta1.0 by labbeh: enabled");
	}
	
	/**
	 * Affiche un message dans la console à la fermeture
	 * */
	@Override
	public void onDisable() {
		super.onDisable();
		System.out.println("MerluchAssur beta1.0 by labbeh: disabled");
	}
	
	/**
	 * Crééer le dossier de sauvegarde des fichiers de données des assurés si il n'exsite pas
	 * @return vrai si le dossier n'exsite pas et qu'il vient d'être crééer
	 * */
	/*public boolean prepareSerialization() {
		File dataDirectory = new File(MerluchAssur.DATAS_PATH);
		return dataDirectory.mkdir();
	}*/
	
	/**
	 * Sauvgarde les infos de l'assuré dont le nom est passé en paramètre
	 * @param playerName nom de l'assuré en String
	 * */
	public void save(String playerName) {
		//this.fu.saveAssureStat(playerName);
		datas.save(playerName);
	}
	
	/**
	 * Sauvegarde les infos de tous les assurés
	 * */
	public void saveAll() {
		/*Collection<Assure> assures = this.assures.values();
		
		for(Assure assure: assures)
			this.save(assure.getPlayerName());*/
		datas.saveAll();
	}
	
	/**
	 * Vide le coffre d'assurance de l'assuré dont le nom est passé en paramètre
	 * @param playerName nom de l'assuré
	 * */
	public boolean clearChests(String playerName) {
		Assure assure = this.getAssure(playerName);
		if(assure == null) return false;
		
		assure.getChest().getInventory().clear();
		
		return true;
	}
	
	/*---------------*/
	/* MODIFICATEURS */
	/*---------------*/
	/**
	 * Ajoute un Assure existant dans un fichier lors du démarrage du serveur
	 * @param worldName nom du monde
	 * @param playerName nom du joueur
	 * @param nbMorts nombre de fois ou est mort le joueur
	 * @param posX position X du joueur
	 * @param posY position Y du joueur
	 * @param posZ position Z du joueur
	 * */
	public void setAssure(String worldName, String playerName, int nbMorts, double posX, double posY, double posZ) {
		World 	 worldOfChest = this.getServer().getWorld(worldName);
		Location locChest  	  = new Location(worldOfChest, posX, posY, posZ);
		Chest 	 chest 		  = (Chest)locChest.getBlock().getState();
		
		//assures.put(playerName, new Assure(playerName, nbMorts, chest));
		datas.addAssure(new Assure(playerName, nbMorts, chest));
	}
	
	/**
	 * Ajoute un Assure existant dans un fichier lors du démarrage du serveur
	 * lorsqu'il est éligible à une assurance mais qu'il n'a pas de coffre
	 * @param playerName nom du joueur
	 * */
	public void setAssure(String playerName) {
		datas.addAssure(new Assure(playerName));
	}
	
	/**
	 * Incrémente le nombre de mort de l'assuré dont le nom est passé en paramètre
	 * @param name nom de l'Assure
	 * */
	public void incMort(String name) {
		this.getAssure(name).incMort();
	}
	
	/*------------*/
	/* ACCESSEURS */
	/*------------*/
	/**
	 * Retourne un Assure associé au nom passé en paramètre, null si il n'existe pas
	 * @param playerName nom de l'assuré en String
	 * @return Assure associé au nom en paramètre ou null
	 * */
	public Assure getAssure(String playerName) {
		//return assures.get(playerName);
		return datas.getAssure(playerName);
	}
	
	/**
	 * Retourne une Collection de l'ensemble des Assures
	 * @return Collection de l'ensemble des Assure
	 * */
	public Collection<Assure> getAssures() {
		//return assures.values();
		return datas.getAssures();
	}
	
	/**
	 * Retourne le nombre d'assurés
	 * */
	/*public int getNbAssure() {
		//return assures.size();
	}*/
	
	/**
	 * Permet d'envoyer un message dans la console du joueur
	 * en ajoutant le nom du plugin et une couleur précise
	 * @param player instance du joueur destinataire du message
	 * @param color couleur dans laquelle apparaitra le message,
	 * des constantes sont définis dans la classe ChatColor
	 * @param msg contenu du message
	 * */
	public static void sendMsgToPlayer(Player player, ChatColor color, String msg) {
		player.sendMessage(color +PLUGIN_NAME+ msg);
	}
	
	/**
	 * Envoi un message au joueur dans sa couleur par défaut
	 * @param player instance du joueur destinataire du message
	 * @param msg contenu du message
	 * */
	public static void sendMsgToPlayer(Player player, String msg) {
		player.sendMessage(msg);
	}

}
