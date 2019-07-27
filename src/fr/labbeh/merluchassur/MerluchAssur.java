package fr.labbeh.merluchassur;
/**
 * Classe principale du plugin MerluchAssur.
 * Ce plugin permet de crééer un système d'assurance pour les biens d'un joueur
 * lors de sa mort
 * @author labbeh
 * @version 2019-07-27, 1.0
 * */

import fr.labbeh.merluchassur.commands.*;
import fr.labbeh.merluchassur.data.IData;
import fr.labbeh.merluchassur.data.TextFileData;
import fr.labbeh.merluchassur.listener.MerluchAssurListener;

import java.util.Collection;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MerluchAssur extends JavaPlugin {	
	/**
	 * constante pour afficher le nom du plugins lorsqu'il envoie un message dans la console
	 * ou dans le chat
	 * */
	public static final String PLUGIN_NAME = "[MerluchAssur] ";
	
	/**
	 * Gestion des données
	 * */
	private IData datas;
	
	
	/**
	 * Construit une instance du plugin
	 * */
	public MerluchAssur() {
		super();
		datas = new TextFileData(this);
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
		datas.addAssure(new Assure(playerName));
		
		Player player = getServer().getPlayer(playerName);
		if(player != null)
			sendMsgToPlayer(player, ChatColor.GREEN, "L'administrateur du serveur vous à inscrit à l'assurance !"
												   + "Allez choisir un coffre au plus vite !");
	}
	
	/**
	 * Démarre le plugin en chargeant en mémoire tous les assurés
	 * Crééer le dossier de configuration si il n'existe pas
	 * Affiche un message pour dire que le plugin est actif
	 * */
	@Override
	public void onEnable() {
		super.onEnable();
		
		// enregistrement des 2 commandes du plugin
		this.getCommand("merluch"	  ).setExecutor(new CommandMerluch(this)	 );
		this.getCommand("merluchadmin").setExecutor(new CommandMerluchAdmin(this));
		
		// mise en place du listener sur les évènements du joueur
		// sert à la gestion du coffre
		this.getServer().getPluginManager().registerEvents(new MerluchAssurListener(this), this);
		
		// initialisation du système de gestion des données sur disque
		datas.init();
		
		// message(s) d'information au démarrage
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
	 * Retourne l'ensemble des noms des joueurs assurés
	 * @return un Set<String> contenant le nom des joueurs assurés
	 * */
	public Set<String> getNames(){
		return datas.getNames();
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
		player.sendMessage(PLUGIN_NAME+ msg);
	}
	
	/**
	 * Envoi un message dans la console
	 * @param msg message à envoyer dans la console
	 * */
	public static void sendMsgToConsole(String msg) {
		System.out.println(msg);
	}
	
	/**
	 * Permet d'envoyer un message à celui qui envoie une commande
	 * @param sender envoyeur de la commande
	 * @param msg message à lui envoyer
	 * */
	public static void sendMsgToCmdSender(CommandSender sender, String msg) {
		sender.sendMessage(PLUGIN_NAME + msg);
	}

}
