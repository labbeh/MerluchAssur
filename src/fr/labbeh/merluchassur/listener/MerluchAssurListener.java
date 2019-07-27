package fr.labbeh.merluchassur.listener;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import fr.labbeh.merluchassur.Assure;
import fr.labbeh.merluchassur.MerluchAssur;
import static fr.labbeh.merluchassur.MerluchAssur.sendMsgToPlayer;

import org.bukkit.block.Chest;


/**
 * @author labbeh
 * */

public class MerluchAssurListener implements Listener
{
	private MerluchAssur ctrl;
	
	public MerluchAssurListener( MerluchAssur ctrl )
	{
		this.ctrl = ctrl;
	}
	
	/* transfÃ¨re de l'invetaire dans le coffre au moment de la mort */
	@EventHandler
	public void onDeath(PlayerDeathEvent  evt) 
	{
		Player player = evt.getEntity();
		Chest playerChest = this.ctrl.getAssure(player.getName()).getChest();
		
		if(playerChest == null) return;// si le joueur n'a pas de coffre on s'arrÃªte lÃ 
		
		// si le joueur est déjà mort plus du nombre de fois autorisé
		// son stuff n'est pas déplacé dans le coffre
		if(this.ctrl.getAssure(player.getName()).getNbMorts() < Assure.NB_MORT_MAX)
		{
			PlayerInventory inventory 	  = player.getInventory	 ();
			ItemStack[] 	inventContent = inventory.getContents();
		
			playerChest.getInventory().clear(); // si le joueur est mort avant et qu'il n'a pas rï¿½ucpï¿½rï¿½ son stuff dans le coffre il est effacï¿½
			for(int cpt=0; cpt<inventContent.length; cpt++)
				if(inventContent[cpt] != null) playerChest.getInventory().addItem(inventContent[cpt]);
		
			evt.getDrops().clear(); // ne laisse pas les objets par terre au moment de la mort
			this.ctrl.incMort(player.getName());
			this.ctrl.save(player.getName());
		}
	}
	
	/* SÃ©lection du coffre d'assurance */
	@EventHandler
	public void onClick(PlayerInteractEvent evt)
	{		
		Player player 		= evt.getPlayer		 ();
		Block  clickedBlock = evt.getClickedBlock();
		Assure assure;
		
		if(clickedBlock == null) return;
		if(clickedBlock.getType() != Material.CHEST) return;
		
		assure = this.ctrl.getAssure(player.getName());
		
		if(assure == null) return;
		if(assure.isWaitingForChest())
		{
			Location chestLoc = clickedBlock.getLocation();
			
			sendMsgToPlayer(player, ChatColor.RED, "Vous avez sélectionner le coffre en " +chestLoc.toString());
			assure.setChest((Chest)clickedBlock.getState());
			
			this.ctrl.save(player.getName()); // sauvegarde des modifications dans le fichier de configuration
		}
	}
	
	/**
	 *  Suppression de l'assurance en cas de destruction du coffre de l'assurÃ©
	 *   */
	@EventHandler
	public void onBreak(BlockBreakEvent evt)
	{
		if(evt.isCancelled()) return;
		
		Player player = evt.getPlayer();
		
		if(evt.getBlock().getType() == Material.CHEST)
		{
			Chest breakedChest = (Chest)evt.getBlock().getState();
			
			for(Assure assure: ctrl.getAssures())
			{
				Chest assureChest = assure.getChest();
				
				if(assureChest != null && breakedChest.equals(assureChest))
				{
					sendMsgToPlayer(player, ChatColor.RED, "vous avez dÃ©truit le coffre de " + assure.getPlayerName());
					
					assure.setChest(null);
					System.out.println("chest: " +assure.getChest());
					this.ctrl.save(assure.getPlayerName()); // sauvegarde des modifications
					
					return; // on arrÃªte la boucle
				}
			}
		}
	}
}
