package fr.labbeh.merluchassur.commands;

import org.bukkit.ChatColor;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.labbeh.merluchassur.Assure;
import fr.labbeh.merluchassur.MerluchAssur;

public class CommandMerluch implements CommandExecutor
{
	/**
	 * @author labbeh
	 * */
	
	private MerluchAssur ctrl;
	
	public CommandMerluch(MerluchAssur ctrl)
	{
		this.ctrl = ctrl;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args)
	{
		if(sender instanceof Player && args.length != 0 && cmdLabel.equalsIgnoreCase("merluch")) 
		{
			Player player = (Player)sender;
			
			if(args[0].equalsIgnoreCase("setchest"))
			{
				Assure assure = this.ctrl.getAssure(player.getName());
				
				if(assure == null)
				{
					player.sendMessage(ChatColor.RED + MerluchAssur.PLUGIN_NAME + 
									   "ERREUR: vous n'êtes pas autorisé à souscrire à une assurance");
					return false;
				}
				
				player.sendMessage(ChatColor.GREEN + MerluchAssur.PLUGIN_NAME +
								   "Sélectionnez un coffre pour votre assurance");
				
				assure.setWaitingForChest();
			}
			
			else if(args[0].equalsIgnoreCase("status"))
			{
				Chest playerChest = this.ctrl.getAssure(player.getName()).getChest();
				
				if(playerChest == null) player.sendMessage(ChatColor.BLUE +MerluchAssur.PLUGIN_NAME+ "Vous n'avez pas de coffre d'assurance" );
				else 					player.sendMessage(ChatColor.BLUE +MerluchAssur.PLUGIN_NAME+ "Votre coffre d'assurance:" +playerChest);
			}
			
			/*else if(args[0].equalsIgnoreCase("save"))
			{
				String playerName = player.getName();
				this.ctrl.save(playerName);
				
				player.sendMessage(ChatColor.GREEN +MerluchAssur.PLUGIN_NAME+ "Sauvegarde dans le fichier");
			}*/
			
			
			return true;
		}
		return false;
	}
}
