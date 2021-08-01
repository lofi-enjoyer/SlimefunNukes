package com.aurgiyalgo.SlimefunNukes.recipes;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;

import java.util.List;

public class NukeRecipe {
	
	private ItemStack[] recipe;
	
	public NukeRecipe(List<String> recipeInput) {
		recipe = new ItemStack[9];
		for (int i = 0; i < 9; i++) {
			if (recipeInput.get(i) == null) {
				recipe[i] = null;
				continue;
			}
			if (SlimefunItem.getByID(recipeInput.get(i)) != null) {
				recipe[i] = SlimefunItem.getByID(recipeInput.get(i)).getItem();
				continue;
			}
			if (Material.getMaterial(recipeInput.get(i)) != null) {
				recipe[i] = new ItemStack(Material.getMaterial(recipeInput.get(i)));
				continue;
			}
		}
	}
	
	public ItemStack[] getRecipe() {
		return recipe;
	}

}
