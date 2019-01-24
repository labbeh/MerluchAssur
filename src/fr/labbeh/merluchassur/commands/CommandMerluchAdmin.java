package fr.labbeh.merluchassur.commands;

import org.bukkit.ChatColor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import fr.labbeh.merluchassur.Assure;
import fr.labbeh.merluchassur.MerluchAssur;

/**
 * @author labbeh
 * */

public class CommandMerluchAdmin implements CommandExecutor
{
	private MerluchAssur ctrl;
	
	public CommandMerluchAdmin(MerluchAssur ctrl)
	{
		this.ctrl = ctrl;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) 
	{
		if(sender instanceof ConsoleCommandSender)
		{
			ConsoleCommandSender ccs = (ConsoleCommandSender)sender;
			if(args.length == 0)
			{
				ccs.sendMessage(MerluchAssur.PLUGIN_NAME+ "Erreur: pas d'argument");
				return false;
			}
			else if(args[0].equalsIgnoreCase("add"))
			{
				if(args.length < 2)
				{
					ccs.sendMessage(MerluchAssur.PLUGIN_NAME+ "Erreur: saisir le nom d'un joueur");
					return false;
				}
				
				this.ctrl.add(args[1]);
				ccs.sendMessage(MerluchAssur.PLUGIN_NAME+ "Joueur " +args[1]+ " assure !");
				return true;
			}
		}
		
		if(sender instanceof Player && args.length != 0 && cmdLabel.equalsIgnoreCase("merluchadmin")) 
		{
			Player player = (Player)sender;
			
			if(player.getName().equalsIgnoreCase("labbeh") || player.getName().equalsIgnoreCase("tsaero")) 
			{
				if(args[0].equals("clear")) 
				{
					if(args.length < 2)
					{
						player.sendMessage(ChatColor.RED 			+ 
										   MerluchAssur.PLUGIN_NAME +
										   "ERREUR: vous devez saisir le nom des joueurs"
										  );
						return false;
					}
					else
					{
						for(int cpt=1; cpt<args.length; cpt++)
						{
							Assure temp = this.ctrl.getAssure(args[cpt]);
							
							if(temp == null)
								player.sendMessage(ChatColor.RED 			+ 
												   MerluchAssur.PLUGIN_NAME +
												   " ERREUR: joueur " +args[cpt]+ "introuvable"
												  );
							else
							{
								temp.getChest().getInventory().clear();
								player.sendMessage(MerluchAssur.PLUGIN_NAME + " Coffre de " +args[cpt]+ " vidé !");
							}
						}
					}
					return true;
				}
				
				else if(args[0].equalsIgnoreCase("save"))
				{
					this.ctrl.saveAll();
					
					player.sendMessage(ChatColor.GREEN +MerluchAssur.PLUGIN_NAME+ "Sauvegarde dans le fichier");
					return true;
				}
			}
			player.sendMessage(ChatColor.RED 		   +
							   MerluchAssur.PLUGIN_NAME+
							   "Vous n'avez pas l'autorisation d'utiliser cette commande"
							  );
		}
		
		return false;
	}

}
