package com.aurgiyalgo.SlimefunNukes;

import me.mrCookieSlime.CSCoreLibPlugin.CSCoreLib;
import me.mrCookieSlime.CSCoreLibPlugin.PluginUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.CSCoreLibPlugin.general.Reflection.ReflectionUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.World.CustomSkull;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.Research;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.Slimefun;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.aurgiyalgo.SlimefunNukes.BlockListener;
import com.aurgiyalgo.SlimefunNukes.Metrics;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Skull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

public class Main extends JavaPlugin {

	public static File cfile;
	public static String prefix;
	public static ItemStack nuke1;
	public static ItemStack nuke2;
	public static ItemStack nuke3;
	public static ItemStack nuke4;
	public static ItemStack nuke_key;
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
		setupConfig("none", false);
		
		PluginManager pm = getServer().getPluginManager();
		BlockListener listener = new BlockListener(this);
		pm.registerEvents(listener, this);

		getCommand("sfnukes").setExecutor(new Commands(this));
		@SuppressWarnings("unused")
		Metrics metrics = new Metrics(this);
		updateChecker("none", false);
		
		nuke1 = new CustomItem(new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal()), config.getString("nuke1.name"), new String[] {"&cWARNING: &7Radioactive item", "&cHazmat Suit useless on explosion" });
    	nuke2 = new CustomItem(new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal()), config.getString("nuke2.name"), new String[] {"&cWARNING: &7Radioactive item", "&cHazmat Suit useless on explosion" });
    	nuke3 = new CustomItem(new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal()), config.getString("nuke3.name"), new String[] {"&cWARNING: &7Radioactive item", "&cHazmat Suit useless on explosion" });
    	nuke4 = new CustomItem(new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal()), config.getString("nuke4.name"), new String[] {"&cWARNING: &7Radioactive item", "&cHazmat Suit useless on explosion" });
    	airstrike = new CustomItem(new ItemStack(Material.REDSTONE_TORCH_ON), config.getString("air-strike.name"), new String[] {"&cCalls an &4&lAirStrike", "&cwhen you use it" });
    	nuke_key = new CustomItem(new ItemStack(Material.TRIPWIRE_HOOK), "&6Nuke activation Key");
    	
    	SkullMeta meta = (SkullMeta) nuke1.getItemMeta();
    	meta.setOwner("MHF_TNT2");
    	nuke1.setItemMeta(meta);
    	meta = (SkullMeta) nuke2.getItemMeta();
    	meta.setOwner("MHF_TNT2");
    	nuke2.setItemMeta(meta);
    	meta = (SkullMeta) nuke3.getItemMeta();
    	meta.setOwner("MHF_TNT2");
    	nuke3.setItemMeta(meta);
    	meta = (SkullMeta) nuke4.getItemMeta();
    	meta.setOwner("MHF_TNT2");
    	nuke4.setItemMeta(meta);
    	
        Category NUKES = new Category(new CustomItem(nuke1, "&aSlimeFun &eN&7uk&ees", new String[] { "", "&a > Click to open" }));
        
        new SlimefunItem(NUKES, nuke1, "Nuke1", RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] { SlimefunItems.LEAD_INGOT, new ItemStack(Material.REDSTONE_BLOCK), SlimefunItems.LEAD_INGOT, new ItemStack(Material.REDSTONE_COMPARATOR), SlimefunItems.TINY_URANIUM, new ItemStack(Material.REDSTONE_COMPARATOR), SlimefunItems.LEAD_INGOT, new ItemStack(Material.IRON_BLOCK), SlimefunItems.LEAD_INGOT }).register();
			
		new SlimefunItem(NUKES, nuke2, "Nuke2", RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] { SlimefunItems.LEAD_INGOT, new ItemStack(Material.REDSTONE_BLOCK), SlimefunItems.LEAD_INGOT, new ItemStack(Material.REDSTONE_COMPARATOR), SlimefunItems.SMALL_URANIUM, new ItemStack(Material.REDSTONE_COMPARATOR), SlimefunItems.LEAD_INGOT, new ItemStack(Material.GOLD_BLOCK), SlimefunItems.LEAD_INGOT }).register();
			
		new SlimefunItem(NUKES, nuke3, "Nuke3", RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] { SlimefunItems.BILLON_INGOT, new ItemStack(Material.REDSTONE_LAMP_OFF), SlimefunItems.BILLON_INGOT, new ItemStack(Material.REDSTONE_COMPARATOR), SlimefunItems.URANIUM, new ItemStack(Material.REDSTONE_COMPARATOR), SlimefunItems.BILLON_INGOT, new ItemStack(Material.GOLD_BLOCK), SlimefunItems.BILLON_INGOT }).register();
			
		new SlimefunItem(NUKES, nuke4, "Nuke4", RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] { new ItemStack(Material.OBSIDIAN), new ItemStack(Material.REDSTONE_LAMP_OFF), new ItemStack(Material.OBSIDIAN), new ItemStack(Material.QUARTZ_BLOCK), SlimefunItems.NUCLEAR_REACTOR, new ItemStack(Material.QUARTZ_BLOCK), new ItemStack(Material.OBSIDIAN), new ItemStack(Material.EMERALD_BLOCK), new ItemStack(Material.OBSIDIAN) }).register();
		
		new SlimefunItem(NUKES, airstrike, "air-strike", RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] { new ItemStack(Material.FEATHER), new ItemStack(Material.REDSTONE_LAMP_OFF), new ItemStack(Material.FEATHER), new ItemStack(Material.ANVIL), nuke1, new ItemStack(Material.ANVIL), new ItemStack(Material.FEATHER), new ItemStack(Material.GOLD_BLOCK), new ItemStack(Material.FEATHER) }).register();
		
		new SlimefunItem(NUKES, nuke_key, "NUKE-KEY", RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] { new ItemStack(Material.AIR), new ItemStack(Material.IRON_BARDING), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.REDSTONE_BLOCK), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.STONE_BUTTON), new ItemStack(Material.AIR) }).register();
				
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
	
	public static void setupConfig(String p, boolean command) {		
		PluginUtils utils = new PluginUtils(plugin);
		utils.setupConfig();
		config = utils.getConfig();
	}
	
	public static void updateChecker(String p, boolean cmd) {
		
		try {
		       HttpURLConnection c = (HttpURLConnection)new URL("http://www.spigotmc.org/api/general.php").openConnection();
		       c.setDoOutput(true);
		       c.setRequestMethod("POST");
		       c.getOutputStream().write(("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=/slimefun-nuke.42670/").getBytes("UTF-8"));
		       String oldVersion = plugin.getDescription().getVersion();
		       String newVersion = new BufferedReader(new InputStreamReader(c.getInputStream())).readLine().replaceAll("[a-zA-Z ]", "");
		       if (cmd) {
	        	   plugin.getServer().getPlayer(p).sendMessage(prefix + ChatColor.WHITE + "You're running " + ChatColor.GRAY + "v" + oldVersion.toString() + ChatColor.WHITE + " of" + ChatColor.GREEN + " SlimefunNukes" + ChatColor.WHITE + ". The latest version on" + ChatColor.GOLD + " Spigot" + ChatColor.WHITE + " is" + ChatColor.GRAY + " v" + newVersion.toString());
	           } else {
	        	   System.out.println("[" + plugin.getName() + "] You're running v" + oldVersion.toString() + " of Slimefun. The latest version on Spigot is v" + newVersion.toString());
	           }
		     }
		     catch(Exception e) {
		       if (cmd) {
		    	   plugin.getServer().getPlayer(p).sendMessage(prefix + ChatColor.RED + "Error while checking plugin version");
		       } else {
		    	   System.out.println("[" + plugin.getName() + "] Error while checking the plugin version");
		       }
		       System.out.println(e);
		     }
		
	}
	
}
