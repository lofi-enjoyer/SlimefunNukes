package com.aurgiyalgo.SlimefunNukes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import me.mrCookieSlime.Slimefun.api.BlockStorage;

public class BlockListener implements Listener {

	public BlockListener(Main instance) {
		plugin = instance;
	}

	Main plugin;
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) { Player p = event.getPlayer();
		if (SlimefunManager.isItemSimiliar(p.getItemInHand(), Main.nuke_key, false)) {
			event.setCancelled(true);
			SlimefunItem item = BlockStorage.check(event.getClickedBlock());
			if (SlimefunManager.isItemSimiliar(item.getItem(), Main.nuke1, false)) {
				nukeExplode("1", p, event);
			}
			
			if (SlimefunManager.isItemSimiliar(item.getItem(), Main.nuke2, false)) {
				nukeExplode("2", p, event);
			}
			
			if (SlimefunManager.isItemSimiliar(item.getItem(), Main.nuke3, false)) {
				nukeExplode("3", p, event);
			}
			
			if (SlimefunManager.isItemSimiliar(item.getItem(), Main.nuke4, false)) {
				nukeExplode("4", p, event);
			}
		}
	}
				
	@SuppressWarnings("deprecation")
	@EventHandler (priority = EventPriority.LOWEST)
	public void onBlockPlace(BlockPlaceEvent event) { Player p = event.getPlayer();	
	  if (SlimefunManager.isItemSimiliar(p.getItemInHand(), Main.airstrike, true)) {		  
		  event.setCancelled(true);
		  int quant = (p.getItemInHand().getAmount());
		  final ItemStack object = (p.getItemInHand());
		    if (p.getGameMode() == GameMode.SURVIVAL) {
		      if (quant > 1) {
		  	    object.setAmount(quant - 1);
		  	  } else {
		  	    p.getInventory().removeItem(object);
		  	  }
		  }
		  Location l = event.getBlock().getLocation();
		  Location place = event.getBlock().getLocation();
		  l.setX(event.getBlock().getLocation().getX() + 0.5D);
		  l.setZ(event.getBlock().getLocation().getZ() + 0.5D);
		  l.setY(event.getBlock().getLocation().getWorld().getHighestBlockYAt(l) + 1D);
		  TNTPrimed tnt = (TNTPrimed)p.getWorld().spawnEntity(l, EntityType.PRIMED_TNT);
		  tnt.setFuseTicks((Main.config.getInt("air-strike.delay") * 20));
		  tnt.setGravity(false);		  
		  for (Player player : Bukkit.getOnlinePlayers()) {
		        if (player == p) {
		        	if (Main.config.getBoolean("air-strike.messages.player.enabled") == true) {	    	
		    		    String alertmessage1 = (Main.config.getString("air-strike.messages.player.onCall"));
		    		    alertmessage1 = alertmessage1.replace("%player%", p.getDisplayName());
		    		    alertmessage1 = alertmessage1.replace("%power%", String.valueOf(Main.config.getInt("air-strike.power")));
		    		    alertmessage1 = alertmessage1.replace("%coords%", ("X=" + place.getBlockX() + " Y=" + place.getBlockY() + " Z=" + place.getBlockZ()));
		    		    alertmessage1 = alertmessage1.replace("%world%", l.getWorld().getName());
		    		    player.sendMessage(Main.prefix + ChatColor.translateAlternateColorCodes('&', alertmessage1));	    	
		    	    }	    	    
		        } else {
		        	if (Main.config.getBoolean("air-strike.messages.server.enabled") == true) {	    	
		    		    String alertmessage1 = (Main.config.getString("air-strike.messages.server.onCall"));
		    		    alertmessage1 = alertmessage1.replace("%player%", p.getDisplayName());
		    		    alertmessage1 = alertmessage1.replace("%power%", String.valueOf(Main.config.getInt("air-strike.power")));
		    		    alertmessage1 = alertmessage1.replace("%coords%", ("X=" + place.getBlockX() + " Y=" + place.getBlockY() + " Z=" + place.getBlockZ()));
		    		    alertmessage1 = alertmessage1.replace("%world%", l.getWorld().getName());
		    		    player.sendMessage(Main.prefix + ChatColor.translateAlternateColorCodes('&', alertmessage1));	    	
		    	    }
		        }
		    }		  
		  Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable()
		    {
		        public void run() {
		          tnt.remove();
		          Location l = tnt.getLocation();
		          l.setX(tnt.getLocation().getX() + 0.5D);
		          l.setZ(tnt.getLocation().getZ() + 0.5D);
		          l.getWorld().createExplosion(l.getWorld().getHighestBlockAt(l).getLocation(), Main.config.getInt("air-strike.power"), (Main.config.getBoolean("air-strike.fire")));
		          for (Player player : Bukkit.getOnlinePlayers()) {
		  	        if (player == p) {
		  	        	if (Main.config.getBoolean("air-strike.messages.player.enabled") == true) {	    	
		  	    		    String alertmessage1 = (Main.config.getString("air-strike.messages.player.onDetonation"));
			    		    alertmessage1 = alertmessage1.replace("%player%", p.getDisplayName());
			    		    alertmessage1 = alertmessage1.replace("%power%", String.valueOf(Main.config.getInt("air-strike.power")));
			    		    alertmessage1 = alertmessage1.replace("%coords%", ("X=" + place.getBlockX() + " Y=" + place.getBlockY() + " Z=" + place.getBlockZ()));
			    		    alertmessage1 = alertmessage1.replace("%world%", l.getWorld().getName());
		  	    		    player.sendMessage(Main.prefix + ChatColor.translateAlternateColorCodes('&', alertmessage1));	    	
		  	    	    }	    	    
		  	        } else {
		  	        	if (Main.config.getBoolean("air-strike.messages.server.enabled") == true) {	    	
		  	    		    String alertmessage1 = (Main.config.getString("air-strike.messages.server.onDetonation"));
			    		    alertmessage1 = alertmessage1.replace("%player%", p.getDisplayName());
			    		    alertmessage1 = alertmessage1.replace("%power%", String.valueOf(Main.config.getInt("air-strike.power")));
			    		    alertmessage1 = alertmessage1.replace("%coords%", ("X=" + place.getBlockX() + " Y=" + place.getBlockY() + " Z=" + place.getBlockZ()));
			    		    alertmessage1 = alertmessage1.replace("%world%", l.getWorld().getName());
		  	    		    player.sendMessage(Main.prefix + ChatColor.translateAlternateColorCodes('&', alertmessage1));	    	
		  	    	    }
		  	        }
		  	    }	
		      }
		    }, (Main.config.getInt("air-strike.delay") * 20));		  
	  }	  
	  if (SlimefunManager.isItemSimiliar(p.getItemInHand(), Main.nuke1, false)) {
		  BlockStorage.addBlockInfo(event.getBlockPlaced(), "nuke", "1");
	  }	  
	  if (SlimefunManager.isItemSimiliar(p.getItemInHand(), Main.nuke2, false)) {
		  BlockStorage.addBlockInfo(event.getBlockPlaced(), "nuke", "2");
	  }	  
	  if (SlimefunManager.isItemSimiliar(p.getItemInHand(), Main.nuke3, false)) {
		  BlockStorage.addBlockInfo(event.getBlockPlaced(), "nuke", "3");
	  }	  
	  if (SlimefunManager.isItemSimiliar(p.getItemInHand(), Main.nuke4, false)) {
		  BlockStorage.addBlockInfo(event.getBlockPlaced(), "nuke", "4");
	  }	  
	}	
	public static List<Location> sphereShape(Location explosionBlock, int radius, boolean hollow) {		
		List<Location> sphereBlocks = new ArrayList<Location>();
		int bX = explosionBlock.getBlockX();
		int bY = explosionBlock.getBlockY();
		int bZ = explosionBlock.getBlockZ();		
		for (int y = bY - radius; y <= bY + radius; y++) {
			for (int x = bX - radius; x <= bX + radius; x++) {
				for (int z = bZ - radius; z <= bZ + radius; z++) {					
					double distance = ((bX - x) * (bX - x) + ((bZ - z) * (bZ - z)) + ((bY - y) * (bY - y)));					
					if (distance < radius * radius && !(hollow && distance < ((radius - 1) * (radius - 1)))) {
						Location lo = new Location(explosionBlock.getWorld(), x, y, z);
						if (lo.getBlock().getType() != Material.BEDROCK && !BlockStorage.hasBlockInfo(lo) && lo.getBlock().getType() != Material.WATER) {
							sphereBlocks.add(lo);
						}
					}
				}
			}
		}
		return sphereBlocks;
	}	
	private void nukeExplode(String nuke, Player p, PlayerInteractEvent event) {		
		event.getClickedBlock().setType(Material.AIR);
	    Location l = event.getClickedBlock().getLocation();
	    Location place = event.getClickedBlock().getLocation();
	    l.setX(event.getClickedBlock().getLocation().getX() + 0.5D);
	    l.setZ(event.getClickedBlock().getLocation().getZ() + 0.5D);
	    TNTPrimed tnt = (TNTPrimed)event.getClickedBlock().getWorld().spawnEntity(l, EntityType.PRIMED_TNT);
	    tnt.setFuseTicks((Main.config.getInt(nuke + ".delay") * 20));
	    if (nuke.equals("air-strike")) {
	    	tnt.setGravity(false);
	    	nuke = "air-strike";
	    } else {
	    	nuke = "nuke" + nuke;
	    }
	    if (Main.config.getBoolean(nuke + ".messages.enabled")) {
	      String alertmessage = (Main.config.getString(nuke + ".messages.onPlace"));
	      alertmessage = alertmessage.replace("%power%", String.valueOf(Main.config.getInt(nuke + ".power")));
	      alertmessage = alertmessage.replace("%coords%", ("X=" + place.getBlockX() + " Y=" + place.getBlockY() + " Z=" + place.getBlockZ()));
	      alertmessage = alertmessage.replace("%world%", l.getWorld().getName());
	      if (Main.config.getBoolean("custom-name-broadcast")) {
	    	  alertmessage = alertmessage.replace("%player%", p.getDisplayName());
	      } else {
	    	  alertmessage = alertmessage.replace("%player%", p.getName());
	      }
	      plugin.getServer().broadcastMessage(Main.prefix + ChatColor.translateAlternateColorCodes('&', alertmessage));	    	    
	    }
        final String name = nuke;
	    Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable()
	    {
	        public void run() {
	          tnt.remove();
	          Location l = tnt.getLocation();
	          l.setX(tnt.getLocation().getX() + 0.5D);
	          l.setZ(tnt.getLocation().getZ() + 0.5D);
	          if (Main.config.getBoolean(name + ".sphere-shape")) {
	        	  for (Location lu : sphereShape(l, (Main.config.getInt(name + ".radius")), false)) {
	  			    lu.getBlock().setType(Material.AIR);
	  			  }
	          }
	          l.getWorld().createExplosion(l.getX(), l.getY(), l.getZ(), Main.config.getInt(name + ".power"), (Main.config.getBoolean("nuke" + name + ".fire")));
	          for (Player player : Bukkit.getOnlinePlayers()) {
	        	  if ((l.distance(player.getLocation())) < Main.config.getInt(name + ".power")) {
	        		  player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Main.config.getInt(name + ".delay"), 3));
	        		  player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Main.config.getInt(name + ".delay"), 3));
	        		  player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, Main.config.getInt(name + ".delay"), 3));
	        		  player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Main.config.getInt(name + ".delay"), 3));
	        	  }
	        	  if (Main.config.getBoolean(name + ".messages.enabled")) {
  	    		    String alertmessage = (Main.config.getString(name + ".messages.onDetonation"));
	    		    alertmessage = alertmessage.replace("%power%", String.valueOf(Main.config.getInt(name + ".power")));
	    		    alertmessage = alertmessage.replace("%coords%", ("X=" + place.getBlockX() + " Y=" + place.getBlockY() + " Z=" + place.getBlockZ()));
	    		    alertmessage = alertmessage.replace("%world%", l.getWorld().getName());
				    if (Main.config.getBoolean("custom-name-broadcast")) {
				    	alertmessage = alertmessage.replace("%player%", p.getDisplayName());
				    } else {
				    	alertmessage = alertmessage.replace("%player%", p.getName());
				    }
  	    		    plugin.getServer().broadcastMessage(Main.prefix + ChatColor.translateAlternateColorCodes('&', alertmessage));
	        	  }
	           }  
	      }
	    }, (Main.config.getInt(name + ".delay") * 20));		
	}
}
