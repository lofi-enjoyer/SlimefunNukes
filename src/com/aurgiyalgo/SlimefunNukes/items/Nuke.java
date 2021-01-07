package com.aurgiyalgo.SlimefunNukes.items;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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
	
	private int radius;
	private int blocksPerSecond;

	public Nuke(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int radius, int blocksPerSecond) {
		super(category, item, recipeType, recipe);
		this.radius = radius;
		this.blocksPerSecond = blocksPerSecond;
	}

	@Override
	public void preRegister() {
		BlockUseHandler blockUseHandler = this::onBlockRightClick;
		addItemHandler(blockUseHandler);
	}

	private void onBlockRightClick(PlayerRightClickEvent event) {
		if (!event.getItem().getType().equals(Material.FLINT_AND_STEEL)) return;
		event.cancel();
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
						List<Location> sphereBlocks = SFNukesUtils.getSphereBlocks(blockLocation, radius);
						BlockStorage.clearBlockInfo(blockLocation);
						AtomicReference<Integer> iteratorCount = new AtomicReference<Integer>();
						iteratorCount.set(0);
						BukkitRunnable sphereRemoveBlocksTask = new BukkitRunnable() {
							
							@Override
							public void run() {
								if (iteratorCount.get() < blocksPerSecond) {
									for (Location l : sphereBlocks) {
										if (BlockStorage.hasBlockInfo(l)) continue;
										l.getBlock().setType(Material.AIR);
									}
									cancel();
									return;
								}
								for (int i = 0; i < blocksPerSecond; i++) {
									if (BlockStorage.hasBlockInfo(sphereBlocks.get(i + iteratorCount.get()))) continue;
									sphereBlocks.get(i + iteratorCount.get()).getBlock().setType(Material.AIR);
								}
								iteratorCount.set(iteratorCount.get() + blocksPerSecond);
							}
						};
						sphereRemoveBlocksTask.runTaskTimer(SlimefunNukes.getInstance(), 0, 20);
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
