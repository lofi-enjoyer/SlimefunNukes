package com.aurgiyalgo.SlimefunNukes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.aurgiyalgo.SlimefunNukes.items.Nuke;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.core.attributes.Radioactivity;
import io.github.thebusybiscuit.slimefun4.core.researching.Research;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.bstats.bukkit.Metrics;

public class SlimefunNukes extends JavaPlugin implements SlimefunAddon {
	
	public static void main(String[] args) {
	}
	
	public static final String SUPPORT_URL = "https://github.com/aurgiyalgo/SlimefunNukes/issues";
	
	private static SlimefunNukes instance;
	
	public void onEnable() {
		instance = this;
		
		setupConfig();
		setupCommand();
		
		new Metrics(instance, 8507);
		
		getServer().getPluginManager().registerEvents(new SFNukesListener(instance), instance);
		
		//Category setup
		NamespacedKey categoryId = new NamespacedKey(instance, "sfnukes_category");
		CustomItem categoryItem = new CustomItem(Material.TNT, "&6Advanced Weaponry");
		Category category = new Category(categoryId, categoryItem);
		
		//Nuke setup
		
		ItemStack[] recipe = {
				SlimefunItems.CARBONADO,        SlimefunItems.REINFORCED_PLATE, SlimefunItems.CARBONADO,
			    SlimefunItems.REINFORCED_PLATE, SlimefunItems.PLUTONIUM,        SlimefunItems.REINFORCED_PLATE,
			    SlimefunItems.CARBONADO,        SlimefunItems.REINFORCED_PLATE, SlimefunItems.CARBONADO
			};
		
		NamespacedKey researchId = new NamespacedKey(this, "nukes_research");
		Research research = new Research(researchId, 1341, "Now I am become Death, the destroyer of worlds", 50);
		
		List<Map<?, ?>> nukeList = getConfig().getMapList("nukes");
		
		for (int i = 0; i < nukeList.size(); i++) {
			Map<String, Object> defaultNuke = (Map<String, Object>) nukeList.get(i);
			
			String id = (String) defaultNuke.get("id");
			String name = (String) defaultNuke.get("name");
			int radius = (int) defaultNuke.get("radius");

			SlimefunItemStack itemStack = new SlimefunItemStack(id, Material.TNT, name, "", LoreBuilder.radioactive(Radioactivity.LOW), LoreBuilder.HAZMAT_SUIT_REQUIRED);
			
			Nuke sfNuke = new Nuke(category, itemStack, RecipeType.ENHANCED_CRAFTING_TABLE, recipe, radius, Configuration.BLOCKS_PER_SECOND);
			sfNuke.register(this);
			
			research.addItems(sfNuke);
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
		Map<String, Object> defaultNuke2 = new HashMap<String, Object>();
		defaultNuke2.put("id", "MEDIUM_NUKE");
		defaultNuke2.put("name", "&cMedium nuclear warhead");
		defaultNuke2.put("radius", 30);
		nukeList.add(defaultNuke2);
		
		getConfig().addDefault("blocks-per-second", Integer.valueOf(10000));
		getConfig().addDefault("nukes", nukeList);
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
