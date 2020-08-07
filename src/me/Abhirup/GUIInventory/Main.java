package me.Abhirup.GUIInventory;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	public void onEnable() {
		System.out.println("'GUIInventory' plugin has worked.");
		Bukkit.getPluginManager().registerEvents(new GUI(), this);
		getCommand("menu").setExecutor(new MenuCommand());
	}

}
