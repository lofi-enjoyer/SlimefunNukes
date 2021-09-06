package com.aurgiyalgo.SlimefunNukes;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.api.researches.Research;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.aurgiyalgo.SlimefunNukes.items.Nuke;
import com.aurgiyalgo.SlimefunNukes.recipes.NukeRecipe;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.core.attributes.Radioactivity;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;

public class SlimefunNukes extends JavaPlugin implements SlimefunAddon {
	
	public static void main(String[] args) {
		
	}

	private static SlimefunNukes instance;
	
	public static final String SUPPORT_URL = "https://github.com/aurgiyalgo/SlimefunNukes/issues";
	
	private boolean usingTowny;
	private boolean usingWG;
	
	private YamlConfiguration configuration;
	
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
		CustomItemStack categoryItem = new CustomItemStack(Material.TNT, "&6Advanced Weaponry");
		ItemGroup category = new ItemGroup(categoryId, categoryItem);
		
		NamespacedKey researchId = new NamespacedKey(this, "nukes_research");
		Research research = new Research(researchId, 1341, "Now I am become Death, the destroyer of worlds", 50);
		
		List<Map<?, ?>> nukeList = configuration.getMapList("nukes");

		for (Map<?, ?> nuke : nukeList) {
			try {
				String id = (String) nuke.get("id");
				String name = (String) nuke.get("name");
				int radius = (int) nuke.get("radius");
				int fuse = (int) nuke.get("fuse");
				List<String> recipe = (List<String>) nuke.get("recipe");
				boolean incendiary = (boolean) nuke.get("incendiary");

				SlimefunItemStack itemStack = new SlimefunItemStack(id, Material.TNT, name, "", LoreBuilder.radioactive(Radioactivity.LOW), LoreBuilder.HAZMAT_SUIT_REQUIRED);

				Nuke sfNuke = new Nuke(category, itemStack, RecipeType.ENHANCED_CRAFTING_TABLE, new NukeRecipe(recipe).getRecipe(), radius, fuse, incendiary);
				sfNuke.register(this);

				research.addItems(sfNuke);
			} catch (Exception e) {
				getLogger().warning("Error while loading a nuke!");
				e.printStackTrace();
			}
		}
		
		//Research setup
		research.register();
	}
	
	public void setupConfig() {
		File configFile = new File(getDataFolder(), "config.yml");
		if (!configFile.exists()) {
			saveResource("config.yml", false);
		}

		configuration = YamlConfiguration.loadConfiguration(configFile);
		
		Configuration.BLOCKS_PER_SECOND = configuration.getInt("blocks-per-second");
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
