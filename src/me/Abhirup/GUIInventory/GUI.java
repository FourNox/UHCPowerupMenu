package me.Abhirup.GUIInventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GUI implements Listener{
	
	private final Inventory inv;
	
	public GUI() {
		
		inv = Bukkit.createInventory(null, 9, "Menu");
		
		initialiseItems();
		
	}
	//creates and adds items to inventory
	public void initialiseItems() { 
		
		inv.addItem(createInvItem(Material.IRON_PICKAXE, ChatColor.GOLD + "Speedy pick", Enchantment.DIG_SPEED, 50, true, true, true, "Mine super fast!", "Costs 3 hearts"));
		inv.addItem(createInvItem(Material.BREWING_STAND, ChatColor.GOLD + "Buffs", Enchantment.DURABILITY, 1, true, true, true, "Speed II for 30 seconds", "Strength II for 30 seconds, Resistance II for 30 seconds", "Halves current health"));
		inv.addItem(createInvItem(Material.DIAMOND_SWORD, ChatColor.GOLD + "Lifesteal", Enchantment.DAMAGE_ALL, 3, false, false, false, "Heal part of the damage you with this sword", "Sets you on half a heart"));
		inv.addItem(createInvItem(Material.ROTTEN_FLESH, ChatColor.MAGIC + "Cursed" + ChatColor.GOLD + "Regeneration", Enchantment.DURABILITY, 1, true, true, true, "Regenerates health but completeley cripples you", "No health cost"));
		inv.addItem(createInvItem(Material.APPLE, ChatColor.GOLD + "Increase max life", Enchantment.DURABILITY, 1, true, true, true, "Increases max hearts by 2 but sets you on 1 heart"));
		inv.addItem(createInvItem(Material.BEACON, ChatColor.GOLD + "Haste", Enchantment.DURABILITY, 1, true, true, true, "Haste for 5 minutes", "Costs 4 hearts"));
		inv.addItem(createInvItem(Material.POTION, ChatColor.GOLD + "Invisibilty", Enchantment.DURABILITY, 1, true, true, true, "Invisibility for 1 minute", "Costs 3 hearts"));
		
	}
	
	public ItemStack createInvItem(final Material material, final String name, final Enchantment enchantment1, final int enchantment1Level, final boolean hideAttributes, final boolean hideEnchants, final boolean hidePotionEffects, final String... lore ) {
	
		 final ItemStack item = new ItemStack(material, 1);
		 final ItemMeta meta = item.getItemMeta();
		 
		 meta.setDisplayName(name);
		 meta.addEnchant(enchantment1, enchantment1Level, true);
		 
		 if (hideEnchants == true) {
			 meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		 }
		 if (hideAttributes == true) {
			 meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		 }
		 if (hidePotionEffects == true) {
			 meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		 }
		
		meta.setLore(Arrays.asList(lore));
		
		item.setItemMeta(meta);
		
		return item;
	}
		
	
	public static ItemStack compassMenu() {
		ItemStack compass = new ItemStack(Material.COMPASS);
		ItemMeta compassim = compass.getItemMeta();
	   
		compassim.setDisplayName(ChatColor.GOLD + "Menu");
	    List<String> compasslore = new ArrayList<String>();
	    compasslore.add("Right click this to open the menu");
	    compassim.setLore(compasslore);
	    compassim.addEnchant(Enchantment.DURABILITY, 3, true);
	    compassim.addItemFlags(ItemFlag.HIDE_ENCHANTS);		
	    compass.setItemMeta(compassim);
		
		return compass;
	}
	
	 public void openInventory(final HumanEntity ent) {
	        ent.openInventory(inv);
	    }
	
	
	@EventHandler
	public void onCompassClick(PlayerInteractEvent e ) {
		
		Player p = e.getPlayer();
		Action a = e.getAction();
		if((p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Menu")) || (p.getInventory().getItemInOffHand().getItemMeta().getDisplayName().contains("Menu")))
		 if((p.getInventory().getItemInMainHand().getItemMeta().getLore().contains("Right click this to open the menu")) || (p.getInventory().getItemInOffHand().getItemMeta().getLore().contains("Right click this to open the menu")))
		  if ((a.equals(Action.RIGHT_CLICK_AIR)) || (a.equals(Action.RIGHT_CLICK_BLOCK)) ) {
			
			  openInventory(p);
		}
		
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e1) {
		
		if (e1.getInventory() != inv) return;
		e1.setCancelled(true);
		final ItemStack clickedItem = e1.getCurrentItem();	
		if (clickedItem == null || clickedItem.getType() == Material.AIR) return;	
		final Player p1 = (Player) e1.getWhoClicked();
		
		
		if (clickedItem.getItemMeta().getDisplayName().contains(ChatColor.GOLD + "Speedy pick")) {
			
			ItemStack speedyPick = new ItemStack(Material.IRON_PICKAXE);
			ItemMeta pickim = speedyPick.getItemMeta();
			pickim.setDisplayName(ChatColor.GOLD + "Speedy pick");
			pickim.addEnchant(Enchantment.DIG_SPEED, 50, true);
			speedyPick.setItemMeta(pickim);
			if (p1.getInventory().firstEmpty() == -1) {
				//inventory is full
				Location loc = p1.getLocation();
				World world = p1.getWorld();
				world.dropItemNaturally(loc, speedyPick);
			}
			p1.getInventory().addItem(speedyPick);
			
			p1.damage(6.0);				
		}
		
		if (clickedItem.getItemMeta().getDisplayName().contains(ChatColor.GOLD + "Buffs")) {
			
			double currentHealth = p1.getHealth();
			
			p1.addPotionEffect((new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 600, 1)));
			p1.addPotionEffect((new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 600, 1)));
			p1.addPotionEffect((new PotionEffect(PotionEffectType.SPEED, 600, 1)));			
			
			p1.setHealth(currentHealth / 2);
			
			p1.sendMessage(ChatColor.BOLD + "You feel like you can break the world in two...with your bare hands!");
		}
		
		if (clickedItem.getItemMeta().getDisplayName().contains(ChatColor.GOLD + "Lifesteal")) {
			
			ItemStack lifesteal = new ItemStack(Material.DIAMOND_SWORD);
			ItemMeta swordim = lifesteal.getItemMeta();
			swordim.setDisplayName(ChatColor.RED + "Lifesteal");
			swordim.addEnchant(Enchantment.DAMAGE_ALL, 3, true);
			List<String> swordlore = new ArrayList<String>();
			swordlore.add("Heal part of the damage you deal with this sword");
			swordim.setLore(swordlore);
			lifesteal.setItemMeta(swordim);
			if (p1.getInventory().firstEmpty() == -1) {
				//inventory is full
				Location loc = p1.getLocation();
				World world = p1.getWorld();
				world.dropItemNaturally(loc, lifesteal);
			}
			p1.getInventory().addItem(lifesteal);

			p1.setHealth(1);
		}
		
		if (clickedItem.getItemMeta().getDisplayName().contains(ChatColor.MAGIC + "Cursed" + ChatColor.GOLD + "Regeneration")) {
				
			p1.addPotionEffect((new PotionEffect(PotionEffectType.REGENERATION, 200, 1)));
			p1.addPotionEffect((new PotionEffect(PotionEffectType.SLOW, 200, 3)));
			p1.addPotionEffect((new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 3)));
			p1.addPotionEffect((new PotionEffect(PotionEffectType.BLINDNESS, 200, 1)));
			p1.addPotionEffect((new PotionEffect(PotionEffectType.WEAKNESS, 200, 2)));
			
			p1.setFoodLevel(0);
			
			p1.sendMessage(ChatColor.RED + "Was it worth it?");
		}
		
		if (clickedItem.getItemMeta().getDisplayName().contains(ChatColor.GOLD + "Increase max life")) {
			
			p1.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(24.0);
			p1.setHealth(1.0);
		}
		
		if (clickedItem.getItemMeta().getDisplayName().contains(ChatColor.GOLD + "Haste")) {
			
			p1.addPotionEffect((new PotionEffect(PotionEffectType.FAST_DIGGING, 6000, 1)));
			p1.damage(8.0);
		}
		
		if (clickedItem.getItemMeta().getDisplayName().contains(ChatColor.GOLD + "Invisibilty")) {
			
			p1.addPotionEffect((new PotionEffect(PotionEffectType.INVISIBILITY, 1200, 0)));
			p1.damage(6.0);
		}
		
	}
	
	 @EventHandler
	    public void onInventoryClick(final InventoryDragEvent e) {
	        
		 if (e.getInventory() == inv) {
	         
			 e.setCancelled(true);
	        }
	 }
	 
	 @EventHandler
	 	public void onLifestealDamage(EntityDamageByEntityEvent e) {
		 Entity damager = e.getDamager();
		 
		 if (damager instanceof Player) {
			
			 Player d = (Player) damager;
			 if (d.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains(ChatColor.RED + "Lifesteal")) {
				 
				 double damage = e.getDamage();
				 d.setHealth(d.getHealth() + (damage / 2));
			 }
		 }

	 }
}
