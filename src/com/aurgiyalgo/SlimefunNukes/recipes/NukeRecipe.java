package com.aurgiyalgo.SlimefunNukes.recipes;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;

public class NukeRecipe {
	
	private ItemStack[] recipe;
	
	public NukeRecipe(String[] recipeInput) {
		recipe = new ItemStack[9];
		for (int i = 0; i < 9; i++) {
			if (recipeInput[i] == null) {
				recipe[i] = null;
				continue;
			}
			if (SlimefunItem.getByID(recipeInput[i]) != null) {
				recipe[i] = SlimefunItem.getByID(recipeInput[i]).getItem();
				continue;
			}
			if (Material.getMaterial(recipeInput[i]) != null) {
				recipe[i] = new ItemStack(Material.getMaterial(recipeInput[i]));
				continue;
			}
			recipeInput[i] = null;
		}
	}
	
	public ItemStack[] getRecipe() {
		return recipe;
	}

}
