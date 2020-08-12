package com.aurgiyalgo.SlimefunNukes.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.aurgiyalgo.SlimefunNukes.SFNukesUtils;
import com.aurgiyalgo.SlimefunNukes.SlimefunNukes;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.core.attributes.Radioactive;
import io.github.thebusybiscuit.slimefun4.core.attributes.Radioactivity;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

public class Nuke extends SlimefunItem implements Radioactive {

	public Nuke(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
		super(category, item, recipeType, recipe);
	}

	@Override
	public void preRegister() {
		BlockUseHandler blockUseHandler = this::onBlockRightClick;
		addItemHandler(blockUseHandler);
	}

	private void onBlockRightClick(PlayerRightClickEvent event) {
		event.cancel();
		if (!event.getItem().getType().equals(Material.FLINT_AND_STEEL)) return;
		Location blockLocation = event.getClickedBlock().get().getLocation().add(0.5, 0, 0.5);
		TNTPrimed tnt = (TNTPrimed) blockLocation.getWorld().spawnEntity(blockLocation, EntityType.PRIMED_TNT);
		tnt.setFuseTicks(10 * 20);
		tnt.setIsIncendiary(true);
		tnt.setYield(50f);
		BlockStorage.clearBlockInfo(blockLocation);
		event.getClickedBlock().get().setType(Material.AIR);
		
		BukkitRunnable sphereShapeTask = new BukkitRunnable() {
			
			@Override
			public void run() {
				Thread t = new Thread(new Runnable() {
					
					@Override
					public void run() {
						final int BLOCKS_PER_ITERATION = 1000;
						List<Location> sphereBlocks = SFNukesUtils.getSphereBlocks(blockLocation, 30);
						BukkitRunnable sphereRemoveBlocksTask = new BukkitRunnable() {
							
							@Override
							public void run() {
								if (sphereBlocks.size() < BLOCKS_PER_ITERATION) {
									for (Location l : sphereBlocks) {
										if (l.getBlock().getType().equals(Material.BEDROCK)) continue;
										if (l.getBlock().getType().equals(Material.AIR)) continue;
										if (BlockStorage.hasBlockInfo(l)) continue;
										l.getBlock().setType(Material.AIR);
									}
									cancel();
									return;
								}
								List<Location> blocksRemoved = new ArrayList<>();
								for (int i = 0; i < BLOCKS_PER_ITERATION; i++) {
									if (sphereBlocks.get(i).getBlock().getType().equals(Material.BEDROCK)) continue;
									if (sphereBlocks.get(i).getBlock().getType().equals(Material.AIR)) continue;
									if (BlockStorage.hasBlockInfo(sphereBlocks.get(i))) continue;
									sphereBlocks.get(i).getBlock().setType(Material.AIR);
									blocksRemoved.add(sphereBlocks.get(i));
								}
								sphereBlocks.removeAll(blocksRemoved);
							}
						};
						sphereRemoveBlocksTask.runTaskTimer(SlimefunNukes.getInstance(), 0, 10);
					}
				});
				t.run();
			}
		};
		
		sphereShapeTask.runTaskLater(SlimefunNukes.getInstance(), 10 * 20);
	}

	@Override
	public Radioactivity getRadioactivity() {
		return Radioactivity.LOW;
	}

}
