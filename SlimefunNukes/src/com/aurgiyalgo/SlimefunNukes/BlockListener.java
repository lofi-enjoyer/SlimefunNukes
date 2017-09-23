package com.aurgiyalgo.SlimefunNukes;

import com.aurgiyalgo.SlimefunNukes.Main;
import com.palmergames.bukkit.towny.object.TownyPermission;
import com.palmergames.bukkit.towny.utils.PlayerCacheUtil;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import me.mrCookieSlime.Slimefun.api.BlockStorage;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
				event.getClickedBlock().setType(Material.AIR);
			    Location l = event.getClickedBlock().getLocation();
			    Location place = event.getClickedBlock().getLocation();
			    l.setX(event.getClickedBlock().getLocation().getX() + 0.5D);
			    l.setZ(event.getClickedBlock().getLocation().getZ() + 0.5D);
			    TNTPrimed tnt = (TNTPrimed)event.getClickedBlock().getWorld().spawnEntity(l, EntityType.PRIMED_TNT);
			    tnt.setFuseTicks((Main.config.getInt("nuke1.delay") * 20));
			    
			    if (Main.config.getBoolean("nuke1.messages.enabled")) {
			      String alertmessage1 = (Main.config.getString("nuke1.messages.onPlace"));
			      alertmessage1 = alertmessage1.replace("%power%", String.valueOf(Main.config.getInt("nuke1.power")));
			      alertmessage1 = alertmessage1.replace("%coords%", ("X=" + place.getBlockX() + " Y=" + place.getBlockY() + " Z=" + place.getBlockZ()));
			      alertmessage1 = alertmessage1.replace("%world%", l.getWorld().getName());
			      if (Main.config.getBoolean("custom-name-broadcast")) {
			    	  alertmessage1 = alertmessage1.replace("%player%", p.getDisplayName());
			      } else {
			    	  alertmessage1 = alertmessage1.replace("%player%", p.getName());
			      }
			      plugin.getServer().broadcastMessage(Main.prefix + ChatColor.translateAlternateColorCodes('&', alertmessage1));	    	    
			    }
			    Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable()
			    {
			        public void run() {
			          tnt.remove();
			          Location l = tnt.getLocation();
			          l.setX(tnt.getLocation().getX() + 0.5D);
			          l.setZ(tnt.getLocation().getZ() + 0.5D);
			          if (Main.config.getBoolean("nuke1.sphere-shape")) {
			        	  for (Location lu : sphereShape(l, (Main.config.getInt("nuke1.radius")), false)) {
			  			    lu.getBlock().setType(Material.AIR);
			  			  }
			          }
			          l.getWorld().createExplosion(l.getX(), l.getY(), l.getZ(), Main.config.getInt("nuke1.power"), (Main.config.getBoolean("nuke1.fire")));
			          for (Player player : Bukkit.getOnlinePlayers()) {
			        	  if ((l.distance(player.getLocation())) < Main.config.getInt("nuke1.power")) {
			        		  player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Main.config.getInt("nuke1.delay"), 3));
			        		  player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Main.config.getInt("nuke1.delay"), 3));
			        		  player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, Main.config.getInt("nuke1.delay"), 3));
			        		  player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Main.config.getInt("nuke1.delay"), 3));
			        	  }
			        	  if (Main.config.getBoolean("nuke1.messages.enabled")) {
		  	    		    String alertmessage1 = (Main.config.getString("nuke1.messages.onDetonation"));
			    		    alertmessage1 = alertmessage1.replace("%power%", String.valueOf(Main.config.getInt("nuke1.power")));
			    		    alertmessage1 = alertmessage1.replace("%coords%", ("X=" + place.getBlockX() + " Y=" + place.getBlockY() + " Z=" + place.getBlockZ()));
			    		    alertmessage1 = alertmessage1.replace("%world%", l.getWorld().getName());
						    if (Main.config.getBoolean("custom-name-broadcast")) {
						    	alertmessage1 = alertmessage1.replace("%player%", p.getDisplayName());
						    } else {
						    	alertmessage1 = alertmessage1.replace("%player%", p.getName());
						    }
		  	    		    plugin.getServer().broadcastMessage(Main.prefix + ChatColor.translateAlternateColorCodes('&', alertmessage1));
			        	  }
			           }  
			      }
			    }, (Main.config.getInt("nuke1.delay") * 20));
			}
			
			if (SlimefunManager.isItemSimiliar(item.getItem(), Main.nuke2, false)) {
				event.getClickedBlock().setType(Material.AIR);
			    Location l = event.getClickedBlock().getLocation();
			    Location place = event.getClickedBlock().getLocation();
			    l.setX(event.getClickedBlock().getLocation().getX() + 0.5D);
			    l.setZ(event.getClickedBlock().getLocation().getZ() + 0.5D);
			    TNTPrimed tnt = (TNTPrimed)event.getClickedBlock().getWorld().spawnEntity(l, EntityType.PRIMED_TNT);
			    tnt.setFuseTicks((Main.config.getInt("nuke2.delay") * 20));
			    
			    if (Main.config.getBoolean("nuke2.messages.enabled")) {
			    	String alertmessage1 = (Main.config.getString("nuke2.messages.onPlace"));
				    alertmessage1 = alertmessage1.replace("%power%", String.valueOf(Main.config.getInt("nuke2.power")));
				    alertmessage1 = alertmessage1.replace("%coords%", ("X=" + place.getBlockX() + " Y=" + place.getBlockY() + " Z=" + place.getBlockZ()));
				    alertmessage1 = alertmessage1.replace("%world%", l.getWorld().getName());
				    if (Main.config.getBoolean("custom-name-broadcast")) {
				        alertmessage1 = alertmessage1.replace("%player%", p.getDisplayName());
				    } else {
				    	alertmessage1 = alertmessage1.replace("%player%", p.getName());
				    }
				    plugin.getServer().broadcastMessage(Main.prefix + ChatColor.translateAlternateColorCodes('&', alertmessage1));
			    }
			    Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable()
			    {
			        public void run() {
			          tnt.remove();
			          Location l = tnt.getLocation();
			          l.setX(tnt.getLocation().getX() + 0.5D);
			          l.setZ(tnt.getLocation().getZ() + 0.5D);
			          if (Main.config.getBoolean("nuke2.sphere-shape")) {
			        	  for (Location lu : sphereShape(l, (Main.config.getInt("nuke2.radius")), false)) {
			  			    lu.getBlock().setType(Material.AIR);
			  			  }
			          }
			          l.getWorld().createExplosion(l.getX(), l.getY(), l.getZ(), Main.config.getInt("nuke2.power"), (Main.config.getBoolean("nuke2.fire")));
			          for (Player player : Bukkit.getOnlinePlayers()) {
			        	  if ((l.distance(player.getLocation())) < Main.config.getInt("nuke2.power")) {
			        		  player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Main.config.getInt("nuke2.delay"), 3));
			        		  player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Main.config.getInt("nuke2.delay"), 3));
			        		  player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, Main.config.getInt("nuke2.delay"), 3));
			        		  player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Main.config.getInt("nuke2.delay"), 3));
			        	  }
			        	  if (Main.config.getBoolean("nuke2.messages.enabled")) {
		  	    		    String alertmessage1 = (Main.config.getString("nuke2.messages.onDetonation"));
			    		    alertmessage1 = alertmessage1.replace("%power%", String.valueOf(Main.config.getInt("nuke2.power")));
			    		    alertmessage1 = alertmessage1.replace("%coords%", ("X=" + place.getBlockX() + " Y=" + place.getBlockY() + " Z=" + place.getBlockZ()));
			    		    alertmessage1 = alertmessage1.replace("%world%", l.getWorld().getName());
						    if (Main.config.getBoolean("custom-name-broadcast")) {
						    	alertmessage1 = alertmessage1.replace("%player%", p.getDisplayName());
						    } else {
						    	alertmessage1 = alertmessage1.replace("%player%", p.getName());
						    }
		  	    		    plugin.getServer().broadcastMessage(Main.prefix + ChatColor.translateAlternateColorCodes('&', alertmessage1));
			              }
			          }
			      }
			    }, (Main.config.getInt("nuke2.delay") * 20));
			}
			
			if (SlimefunManager.isItemSimiliar(item.getItem(), Main.nuke3, false)) {
				event.getClickedBlock().setType(Material.AIR);
			    Location l = event.getClickedBlock().getLocation();
			    Location place = event.getClickedBlock().getLocation();
			    l.setX(event.getClickedBlock().getLocation().getX() + 0.5D);
			    l.setZ(event.getClickedBlock().getLocation().getZ() + 0.5D);
			    TNTPrimed tnt = (TNTPrimed)event.getClickedBlock().getWorld().spawnEntity(l, EntityType.PRIMED_TNT);
			    tnt.setFuseTicks((Main.config.getInt("nuke3.delay") * 20));
			    
			    if (Main.config.getBoolean("nuke3.messages.enabled")) {
			    	String alertmessage1 = (Main.config.getString("nuke3.messages.onPlace"));
				    alertmessage1 = alertmessage1.replace("%power%", String.valueOf(Main.config.getInt("nuke3.power")));
				    alertmessage1 = alertmessage1.replace("%coords%", ("X=" + place.getBlockX() + " Y=" + place.getBlockY() + " Z=" + place.getBlockZ()));
				    alertmessage1 = alertmessage1.replace("%world%", l.getWorld().getName());
				    if (Main.config.getBoolean("custom-name-broadcast")) {
				    	alertmessage1 = alertmessage1.replace("%player%", p.getDisplayName());
				    } else {
				    	alertmessage1 = alertmessage1.replace("%player%", p.getName());
				    }
				    plugin.getServer().broadcastMessage(Main.prefix + ChatColor.translateAlternateColorCodes('&', alertmessage1));
			    }
			    
			    Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable()
			    {
			        public void run() {
			          tnt.remove();
			          Location l = tnt.getLocation();
			          l.setX(tnt.getLocation().getX() + 0.5D);
			          l.setZ(tnt.getLocation().getZ() + 0.5D);
			          if (Main.config.getBoolean("nuke3.sphere-shape")) {
			        	  for (Location lu : sphereShape(l, (Main.config.getInt("nuke3.radius")), false)) {
			  			    lu.getBlock().setType(Material.AIR);
			  			  }
			          }
			          l.getWorld().createExplosion(l.getX(), l.getY(), l.getZ(), Main.config.getInt("nuke3.power"), (Main.config.getBoolean("nuke3.fire")));
			          for (Player player : Bukkit.getOnlinePlayers()) {
			        	  if ((l.distance(player.getLocation())) < Main.config.getInt("nuke3.power")) {
			        		  player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Main.config.getInt("nuke3.delay"), 3));
			        		  player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Main.config.getInt("nuke3.delay"), 3));
			        		  player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, Main.config.getInt("nuke3.delay"), 3));
			        		  player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Main.config.getInt("nuke3.delay"), 3));
			        	  }
			        	  if (Main.config.getBoolean("nuke3.messages.enabled")) {
		  	    		    String alertmessage1 = (Main.config.getString("nuke3.messages.onDetonation"));
			    		    alertmessage1 = alertmessage1.replace("%power%", String.valueOf(Main.config.getInt("nuke3.power")));
			    		    alertmessage1 = alertmessage1.replace("%coords%", ("X=" + place.getBlockX() + " Y=" + place.getBlockY() + " Z=" + place.getBlockZ()));
			    		    alertmessage1 = alertmessage1.replace("%world%", l.getWorld().getName());
						    if (Main.config.getBoolean("custom-name-broadcast")) {
						    	alertmessage1 = alertmessage1.replace("%player%", p.getDisplayName());
						    } else {
						    	alertmessage1 = alertmessage1.replace("%player%", p.getName());
						    }
		  	    		    plugin.getServer().broadcastMessage(Main.prefix + ChatColor.translateAlternateColorCodes('&', alertmessage1));
			        	  }
			  	    }
			      }
			    }, (Main.config.getInt("nuke3.delay") * 20));
			}
			
			if (SlimefunManager.isItemSimiliar(item.getItem(), Main.nuke4, false)) {
				event.getClickedBlock().setType(Material.AIR);
			    Location l = event.getClickedBlock().getLocation();
			    Location place = event.getClickedBlock().getLocation();
			    l.setX(event.getClickedBlock().getLocation().getX() + 0.5D);
			    l.setZ(event.getClickedBlock().getLocation().getZ() + 0.5D);
			    TNTPrimed tnt = (TNTPrimed)event.getClickedBlock().getWorld().spawnEntity(l, EntityType.PRIMED_TNT);
			    tnt.setFuseTicks((Main.config.getInt("nuke4.delay") * 20));
			    
			    if (Main.config.getBoolean("nuke4.messages.enabled")) {
			    	String alertmessage1 = (Main.config.getString("nuke4.messages.onPlace"));
				    alertmessage1 = alertmessage1.replace("%power%", String.valueOf(Main.config.getInt("nuke4.power")));
				    alertmessage1 = alertmessage1.replace("%coords%", ("X=" + place.getBlockX() + " Y=" + place.getBlockY() + " Z=" + place.getBlockZ()));
				    alertmessage1 = alertmessage1.replace("%world%", l.getWorld().getName());
				    if (Main.config.getBoolean("custom-name-broadcast")) {
				    	alertmessage1 = alertmessage1.replace("%player%", p.getDisplayName());
				    } else {
				    	alertmessage1 = alertmessage1.replace("%player%", p.getName());
				    }
				    plugin.getServer().broadcastMessage(Main.prefix + ChatColor.translateAlternateColorCodes('&', alertmessage1));
			    }
			    
			    Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable()
			    {
			        public void run() {
			          tnt.remove();
			          Location l = tnt.getLocation();
			          l.setX(tnt.getLocation().getX() + 0.5D);
			          l.setZ(tnt.getLocation().getZ() + 0.5D);
			          if (Main.config.getBoolean("nuke4.sphere-shape")) {
			        	  for (Location lu : sphereShape(l, (Main.config.getInt("nuke4.radius")), false)) {
			  			    lu.getBlock().setType(Material.AIR);
			  			  }
			          }
			          l.getWorld().createExplosion(l.getX(), l.getY(), l.getZ(), Main.config.getInt("nuke4.power"), (Main.config.getBoolean("nuke4.fire")));
			          for (Player player : Bukkit.getOnlinePlayers()) {
			        	  if ((l.distance(player.getLocation())) < Main.config.getInt("nuke4.power")) {
			        		  player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Main.config.getInt("nuke3.delay"), 3));
			        		  player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Main.config.getInt("nuke3.delay"), 3));
			        		  player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, Main.config.getInt("nuke3.delay"), 3));
			        		  player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Main.config.getInt("nuke3.delay"), 3));
			        	  }
			        	  if (Main.config.getBoolean("nuke4.messages.enabled")) {
		  	    		    String alertmessage1 = (Main.config.getString("nuke4.messages.onDetonation"));
			    		    alertmessage1 = alertmessage1.replace("%power%", String.valueOf(Main.config.getInt("nuke4.power")));
			    		    alertmessage1 = alertmessage1.replace("%coords%", ("X=" + place.getBlockX() + " Y=" + place.getBlockY() + " Z=" + place.getBlockZ()));
			    		    alertmessage1 = alertmessage1.replace("%world%", l.getWorld().getName());
						    if (Main.config.getBoolean("custom-name-broadcast")) {
						    	alertmessage1 = alertmessage1.replace("%player%", p.getDisplayName());
						    } else {
						    	alertmessage1 = alertmessage1.replace("%player%", p.getName());
						    }
		  	    		    plugin.getServer().broadcastMessage(Main.prefix + ChatColor.translateAlternateColorCodes('&', alertmessage1));
			        	  }
			  	    }
			      }
			    }, (Main.config.getInt("nuke4.delay") * 20));
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
}
