package com.Joselu.NukeAddon;

import me.mrCookieSlime.CSCoreLibPlugin.PluginUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.Research;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.Slimefun;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.Joselu.NukeAddon.BlockListener;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.Joselu.NukeAddon.Metrics;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Main extends JavaPlugin {

	public static File cfile;
	public static String prefix;
	public static ItemStack nuke1;
	public static ItemStack nuke2;
	public static ItemStack nuke3;
	public static ItemStack nuke4;
	public static ItemStack airstrike;
	public static JavaPlugin plugin;
	public static Config config;
	public static boolean towny = false;
	
	
	
	public void onEnable() {
		if (getServer().getPluginManager().isPluginEnabled("Slimefun")) {
			System.out.println("[" + getName() + "] " + getName() + " v" + getDescription().getVersion() + " has been enabled!");
			}
		else {
			System.err.println("[" + getName() + "] Slimefun not found!");
			System.err.println("Please install Slimefun");
			System.err.println("Without it, this Plugin will not work");
			System.err.println("You can download it here:");
			System.err.println("http://dev.bukkit.org/bukkit-plugins/slimefun");
			getServer().getPluginManager().disablePlugin(this);
			return;
        }
		
		getWorldGuard();
		
		plugin = this;
		PluginUtils utils = new PluginUtils(this);
		utils.setupConfig();
		config = utils.getConfig();		
		
		PluginManager pm = getServer().getPluginManager();
		BlockListener listener = new BlockListener(this);
		pm.registerEvents(listener, this);

		Metrics metrics = new Metrics(this);
		
    	nuke1 = new CustomItem(new ItemStack(Material.TNT), config.getString("nuke1.name"), new String[] {"&cWARNING: &7Radioactive item", "&cHazmat Suit useless on explosion" });
    	nuke2 = new CustomItem(new ItemStack(Material.TNT), config.getString("nuke2.name"), new String[] {"&cWARNING: &7Radioactive item", "&cHazmat Suit useless on explosion" });
    	nuke3 = new CustomItem(new ItemStack(Material.TNT), config.getString("nuke3.name"), new String[] {"&cWARNING: &7Radioactive item", "&cHazmat Suit useless on explosion" });
    	nuke4 = new CustomItem(new ItemStack(Material.TNT), config.getString("nuke4.name"), new String[] {"&cWARNING: &7Radioactive item", "&cHazmat Suit useless on explosion" });
    	airstrike = new CustomItem(new ItemStack(Material.REDSTONE_TORCH_ON), config.getString("air-strike.name"), new String[] {"&cCalls an &4&lAirStrike", "&cwhen you use it" });
    	
        Category NUKES = new Category(new CustomItem(new ItemStack(Material.TNT), "&aSlimeFun &eN&7uk&ees", new String[] { "", "&a > Click to open" }));
        
        if (config.getBoolean("nuke1.radioactive")) {
			SlimefunItem.setRadioactive(nuke1);
		}
		
		if (config.getBoolean("nuke2.radioactive")) {
			SlimefunItem.setRadioactive(nuke2);
		}
		
		if (config.getBoolean("nuke3.radioactive")) {
			SlimefunItem.setRadioactive(nuke3);
		}
		
		if (config.getBoolean("nuke4.radioactive")) {
			SlimefunItem.setRadioactive(nuke4);
		}
		
		new SlimefunItem(NUKES, nuke1, "Nuke1", RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] { SlimefunItems.LEAD_INGOT, new ItemStack(Material.REDSTONE_BLOCK), SlimefunItems.LEAD_INGOT, new ItemStack(Material.REDSTONE_COMPARATOR), SlimefunItems.TINY_URANIUM, new ItemStack(Material.REDSTONE_COMPARATOR), SlimefunItems.LEAD_INGOT, new ItemStack(Material.IRON_BLOCK), SlimefunItems.LEAD_INGOT }).register();
			
		new SlimefunItem(NUKES, nuke2, "Nuke2", RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] { SlimefunItems.LEAD_INGOT, new ItemStack(Material.REDSTONE_BLOCK), SlimefunItems.LEAD_INGOT, new ItemStack(Material.REDSTONE_COMPARATOR), SlimefunItems.SMALL_URANIUM, new ItemStack(Material.REDSTONE_COMPARATOR), SlimefunItems.LEAD_INGOT, new ItemStack(Material.GOLD_BLOCK), SlimefunItems.LEAD_INGOT }).register();
			
		new SlimefunItem(NUKES, nuke3, "Nuke3", RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] { SlimefunItems.BILLON_INGOT, new ItemStack(Material.REDSTONE_LAMP_OFF), SlimefunItems.BILLON_INGOT, new ItemStack(Material.REDSTONE_COMPARATOR), SlimefunItems.URANIUM, new ItemStack(Material.REDSTONE_COMPARATOR), SlimefunItems.BILLON_INGOT, new ItemStack(Material.GOLD_BLOCK), SlimefunItems.BILLON_INGOT }).register();
			
		new SlimefunItem(NUKES, nuke4, "Nuke4", RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] { new ItemStack(Material.OBSIDIAN), new ItemStack(Material.REDSTONE_LAMP_OFF), new ItemStack(Material.OBSIDIAN), new ItemStack(Material.QUARTZ_BLOCK), SlimefunItems.NUCLEAR_REACTOR, new ItemStack(Material.QUARTZ_BLOCK), new ItemStack(Material.OBSIDIAN), new ItemStack(Material.EMERALD_BLOCK), new ItemStack(Material.OBSIDIAN) }).register();
		
		new SlimefunItem(NUKES, airstrike, "air-strike", RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] { new ItemStack(Material.FEATHER), new ItemStack(Material.REDSTONE_LAMP_OFF), new ItemStack(Material.FEATHER), new ItemStack(Material.ANVIL), nuke1, new ItemStack(Material.ANVIL), new ItemStack(Material.FEATHER), new ItemStack(Material.GOLD_BLOCK), new ItemStack(Material.FEATHER) }).register();
		
		Slimefun.registerResearch(new Research(777, "Unmeasurable nuclear power", 30), new ItemStack[] { nuke1, nuke2, nuke3, nuke4, airstrike });
						
		prefix = ChatColor.translateAlternateColorCodes('&', config.getString("prefix"));
		cfile = new File(getDataFolder(), "config.yml");
	}
	
	public static WorldGuardPlugin getWorldGuard() {
	    Plugin worldGuard = Bukkit.getPluginManager().getPlugin("WorldGuard");
	 
	    if ((worldGuard == null) || (!(worldGuard instanceof WorldGuardPlugin))) {
	        return null;
	    }
	 
	    return (WorldGuardPlugin) worldGuard ;
	}
}
