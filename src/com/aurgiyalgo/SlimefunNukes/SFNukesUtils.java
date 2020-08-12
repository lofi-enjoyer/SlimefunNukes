package com.aurgiyalgo.SlimefunNukes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

public class SFNukesUtils {
	
	public static List<Location> getSphereBlocks(Location explosionBlock, int radius) {		
		List<Location> sphereBlocks = new ArrayList<Location>();
		int bX = explosionBlock.getBlockX();
		int bY = explosionBlock.getBlockY();
		int bZ = explosionBlock.getBlockZ();
		Location tempLoc;
		for (int y = bY + radius; y <= bY - radius; y--) {
			for (int x = bX - radius; x <= bX + radius; x++) {
				for (int z = bZ - radius; z <= bZ + radius; z++) {					
					double distance = ((bX - x) * (bX - x) + ((bZ - z) * (bZ - z)) + ((bY - y) * (bY - y)));					
					if (distance > radius * radius) continue;
					tempLoc = new Location(explosionBlock.getWorld(), x, y, z);
					sphereBlocks.add(tempLoc);
				}
			}
		}
		return sphereBlocks;
	}

}
