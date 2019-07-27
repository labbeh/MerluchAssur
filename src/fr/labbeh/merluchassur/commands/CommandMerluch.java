package fr.labbeh.merluchassur.commands;

import org.bukkit.ChatColor;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.labbeh.merluchassur.Assure;
import fr.labbeh.merluchassur.MerluchAssur;
import static fr.labbeh.merluchassur.MerluchAssur.sendMsgToPlayer;

public class CommandMerluch implements CommandExecutor {
	/**
	 * Instance de la classe principale du plugin pour avoir des actions dessus
	 * */
	private MerluchAssur ctrl;
	
	/**
	 * Construit le gestionnaire de commande
	 * @param ctrl instance de JavaPlugin pour agir dessus
	 * */
	public CommandMerluch(MerluchAssur ctrl){
		this.ctrl = ctrl;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args){
		if(sender instanceof Player && args.length != 0 && cmdLabel.equalsIgnoreCase("merluch")) {
			Player player = (Player)sender;
			
			if(args[0].equalsIgnoreCase("setchest")) {
				Assure assure = this.ctrl.getAssure(player.getName());
				
				if(assure == null) {
					player.sendMessage(ChatColor.RED + MerluchAssur.PLUGIN_NAME + 
									   "ERREUR: vous n'êtes pas autorisé à  souscrire à  une assurance");
					return false;
				}
				
				player.sendMessage(ChatColor.GREEN + MerluchAssur.PLUGIN_NAME +
								   "Sélectionnez un coffre pour votre assurance");
				
				assure.setWaitingForChest();
			}
			
			else if(args[0].equalsIgnoreCase("status")) {
				Assure assure = this.ctrl.getAssure(player.getName());
				
				if(assure != null) {
					Chest playerChest = assure.getChest();
					if(playerChest == null) sendMsgToPlayer(player, ChatColor.BLUE, "Vous n'avez pas de coffre d'assurance" );
					else 					sendMsgToPlayer(player, ChatColor.BLUE, "Votre coffre d'assurance:" +playerChest);
				}
				else player.sendMessage(ChatColor.BLUE +MerluchAssur.PLUGIN_NAME+ "Votre coffre n'êtes pas éligible à une assurance");
			}
			
			return true;
		}
		else System.out.println("Erreur pas de joueur");
		return false;
	}
}
