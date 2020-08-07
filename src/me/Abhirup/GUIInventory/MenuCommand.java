package me.Abhirup.GUIInventory;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MenuCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.getInventory().firstEmpty() == -1) {
				//inventory is full
				Location loc = player.getLocation();
				World world = player.getWorld();
				world.dropItemNaturally(loc, GUI.compassMenu());
			}
			player.getInventory().addItem(GUI.compassMenu());
			
			return true;
		}	
		
		return false;
	}

}
