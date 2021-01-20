package com.aurgiyalgo.SlimefunNukes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import com.aurgiyalgo.SlimefunNukes.items.Nuke;
import com.aurgiyalgo.SlimefunNukes.recipes.NukeRecipe;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.core.attributes.Radioactivity;
import io.github.thebusybiscuit.slimefun4.core.researching.Research;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.bstats.bukkit.Metrics;

public class SlimefunNukes extends JavaPlugin implements SlimefunAddon {
	
	public static void main(String[] args) {
		
	}

	private static SlimefunNukes instance;
	
	public static final String SUPPORT_URL = "https://github.com/aurgiyalgo/SlimefunNukes/issues";
	
	private boolean usingTowny;
	private boolean usingWG;
	
	public void onEnable() {
		instance = this;
		
		setupConfig();
		setupCommand();
		
		new Metrics(instance, 8507);
		
		getServer().getPluginManager().registerEvents(new SFNukesListener(this), this);
		
		usingTowny = getServer().getPluginManager().isPluginEnabled("Towny");
		usingWG = getServer().getPluginManager().isPluginEnabled("WorldGuard");
		
		//Category setup
		NamespacedKey categoryId = new NamespacedKey(instance, "sfnukes_category");
		CustomItem categoryItem = new CustomItem(Material.TNT, "&6Advanced Weaponry");
		Category category = new Category(categoryId, categoryItem);
		
		NamespacedKey researchId = new NamespacedKey(this, "nukes_research");
		Research research = new Research(researchId, 1341, "Now I am become Death, the destroyer of worlds", 50);
		
		List<Map<?, ?>> nukeList = getConfig().getMapList("nukes");
		
		for (int i = 0; i < nukeList.size(); i++) {
			try {
				Map<String, Object> nuke = (Map<String, Object>) nukeList.get(i);
				
				String id = (String) nuke.get("id");
				String name = (String) nuke.get("name");
				int radius = (int) nuke.get("radius");
				int fuse = (int) nuke.get("fuse");
				List<String> recipe = (List<String>) nuke.get("recipe");
				boolean incendiary = (boolean) nuke.get("incendiary");

				SlimefunItemStack itemStack = new SlimefunItemStack(id, Material.TNT, name, "", LoreBuilder.radioactive(Radioactivity.LOW), LoreBuilder.HAZMAT_SUIT_REQUIRED);
				
				Nuke sfNuke = new Nuke(category, itemStack, RecipeType.ENHANCED_CRAFTING_TABLE, new NukeRecipe(recipe.toArray(new String[recipe.size()])).getRecipe(), radius, fuse, incendiary);
				sfNuke.register(this);
				
				research.addItems(sfNuke);
			} catch (Exception e) {
				getLogger().warning("Error while loading a nuke!");
				getLogger().warning(e.getMessage());
			}
		}
		
		//Research setup
		research.register();
	}
	
	public void setupConfig() {
		List<Map<String, Object>> nukeList = new ArrayList<Map<String, Object>>();
		Map<String, Object> defaultNuke1 = new HashMap<String, Object>();
		defaultNuke1.put("id", "LITTLE_NUKE");
		defaultNuke1.put("name", "&cNuclear warhead");
		defaultNuke1.put("radius", 16);
		defaultNuke1.put("fuse", 30);
		defaultNuke1.put("incendiary", false);
		defaultNuke1.put("recipe", new String[] {
				"COAL_BLOCK", "IRON_BLOCK", "COAL_BLOCK",
				"IRON_BLOCK", "URANIUM",    "IRON_BLOCK",
				"COAL_BLOCK", "IRON_BLOCK", "COAL_BLOCK"
			});
		Map<String, Object> defaultNuke2 = new HashMap<String, Object>();
		defaultNuke2.put("id", "MEDIUM_NUKE");
		defaultNuke2.put("name", "&cMedium nuclear warhead");
		defaultNuke2.put("radius", 32);
		defaultNuke1.put("fuse", 45);
		defaultNuke2.put("incendiary", true);
		defaultNuke2.put("recipe", new String[] {
				"COAL_BLOCK",       "REINFORCED_PLATE", "COAL_BLOCK",
				"REINFORCED_PLATE", "PLUTONIUM",          "REINFORCED_PLATE",
				"COAL_BLOCK",       "REINFORCED_PLATE", "COAL_BLOCK"
			});
		
		nukeList.add(defaultNuke1);
		nukeList.add(defaultNuke2);
		
		getConfig().addDefault("blocks-per-second", Integer.valueOf(10000));
		getConfig().addDefault("nukes", nukeList);
		getConfig().addDefault("research-cost", Integer.valueOf(50));
		getConfig().addDefault("research-cost", Integer.valueOf(50));
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		Configuration.BLOCKS_PER_SECOND = getConfig().getInt("blocks-per-second");
	}
	
	public void setupCommand() {
		getCommand("sfnukes").setExecutor(new SfNukesExecutor());
	}
	
	public static class Configuration {
		public static int BLOCKS_PER_SECOND = 10000;
	}
	
	public boolean isUsingTowny() {
		return usingTowny;
	}

	public boolean isUsingWG() {
		return usingWG;
	}
	
	public static SlimefunNukes getInstance() {
		return instance;
	}

	@Override
	public JavaPlugin getJavaPlugin() {
		return this;
	}

	@Override
	public String getBugTrackerURL() {
		return SUPPORT_URL;
	}
	
}
