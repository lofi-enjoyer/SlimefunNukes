package com.aurgiyalgo.SlimefunNukes;

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
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.bstats.bukkit.Metrics;

public class SlimefunNukes extends JavaPlugin implements SlimefunAddon {
	
	public static final String SUPPORT_URL = "https://github.com/aurgiyalgo/SlimefunNukes/issues";
	
	private static SlimefunNukes instance;

	private Config config;
	
	public void onEnable() {
		instance = this;
		
		config = new Config(instance);
		
		new Metrics(instance, 8507);
		
		getServer().getPluginManager().registerEvents(new SFNukesListener(instance), instance);
		
		//Category setup
		NamespacedKey categoryId = new NamespacedKey(instance, "sfnukes_category");
		CustomItem categoryItem = new CustomItem(Material.TNT, "&6Advanced Weaponry");
		Category category = new Category(categoryId, categoryItem);
		
		//Nuke setup
		SlimefunItemStack itemStack = new SlimefunItemStack("LITTLE_NUKE", Material.TNT, "&cNuclear warhead", "", LoreBuilder.radioactive(Radioactivity.LOW), LoreBuilder.HAZMAT_SUIT_REQUIRED);
		
		ItemStack[] recipe = {
				
				SlimefunItems.CARBONADO,        SlimefunItems.REINFORCED_PLATE, SlimefunItems.CARBONADO,
			    SlimefunItems.REINFORCED_PLATE, SlimefunItems.PLUTONIUM,        SlimefunItems.REINFORCED_PLATE,
			    SlimefunItems.CARBONADO,        SlimefunItems.REINFORCED_PLATE, SlimefunItems.CARBONADO
			};
		
		Nuke sfNuke = new Nuke(category, itemStack, RecipeType.ENHANCED_CRAFTING_TABLE, recipe);
		sfNuke.register(this);
		
		//Research setup
		NamespacedKey researchId = new NamespacedKey(this, "nukes_research");
		Research research = new Research(researchId, 1341, "Now I am become Death, the destroyer of worlds", 50);
		research.addItems(sfNuke);
		research.register();
		
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
