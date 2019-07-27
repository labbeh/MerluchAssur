package fr.labbeh.merluchassur.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import fr.labbeh.merluchassur.Assure;
import fr.labbeh.merluchassur.MerluchAssur;
import net.md_5.bungee.api.ChatColor;

import static fr.labbeh.merluchassur.MerluchAssur.sendMsgToCmdSender;

/**
 * Cette commande d'administration permet aux ops du serveur de réaliser
 * des configurations du plugin
 * @author labbeh
 * */

public class CommandMerluchAdmin implements CommandExecutor
{
	private MerluchAssur ctrl;
	
	/**
	 * Constructeur par défaut
	 * */
	public CommandMerluchAdmin(MerluchAssur ctrl){this.ctrl = ctrl;}
	
	/**
	 * Ce qui se passe lorsque la commande est entrée
	 * */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if(sender instanceof ConsoleCommandSender || (sender instanceof Player && sender.isOp())){
			// on test le cas ou il n'y a pas d'arguments
			if(args.length == 0){
				sendMsgToCmdSender(sender, "Erreur: pas d'argument");
				return false;
			}
			
			if(args[0].equalsIgnoreCase("add")){
				if(args.length < 2){
					sendMsgToCmdSender(sender, "Erreur: saisir le nom d'un joueur");
					return false;
				}
				
				this.ctrl.add(args[1]);
				sendMsgToCmdSender(sender, "Joueur " +args[1]+ " assure !");
				return true;
			}
			else if(args[0].equalsIgnoreCase("clear")) {
				if(args.length < 2){
					sendMsgToCmdSender(sender, "ERREUR: vous devez saisir le nom des joueurs");
					return false;
				}
				else{
					for(int cpt=1; cpt<args.length; cpt++){
						Assure temp = this.ctrl.getAssure(args[cpt]);
						
						if(temp == null)
							sendMsgToCmdSender(sender, " ERREUR: joueur " +args[cpt]+ "introuvable");
						else{
							temp.getChest().getInventory().clear();
							sendMsgToCmdSender(sender, "Coffre de " +args[cpt]+ " vidé !");
						}
					}
				}
				return true;
			}
			
			else if(args[0].equalsIgnoreCase("save")){
				this.ctrl.saveAll();
				sendMsgToCmdSender(sender, "Sauvegarde dans le fichier");
				return true;
			}
			/* A REVOIR a partir d'ici */
			else if(args[0].equalsIgnoreCase("list")) {
				String s = "";
				for(String name: ctrl.getNames()) {
					Player player = ctrl.getServer().getPlayer(name);
					
					s += "  -> " + name +": ";
					if(player == null) s += ChatColor.RED + "non connecté" + ChatColor.WHITE;
					else 			   s += ChatColor.GREEN + "connecté"   + ChatColor.WHITE;
					s += "\n";
					
				}
				sendMsgToCmdSender(sender, "Joueurs actuellement assurés:\n" + s);
				return true;
			}
		}
		sendMsgToCmdSender(sender, "Vous n'avez pas l'autorisation d'utiliser cette commande");
		return false;
	}// fin de onCommand
}// fi,n de classe
