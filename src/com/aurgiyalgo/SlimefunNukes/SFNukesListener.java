package com.aurgiyalgo.SlimefunNukes;

import org.bukkit.event.Listener;

public class SFNukesListener implements Listener {

	SlimefunNukes plugin;

	public SFNukesListener(SlimefunNukes instance) {
		plugin = instance;
	}
	
//	@SuppressWarnings("deprecation")
//	@EventHandler
//	public void onPlayerInteract(PlayerInteractEvent event) { Player p = event.getPlayer();
//		if (SlimefunManager.isItemSimiliar(p.getItemInHand(), SlimefunNukes.nuke_key, false)) {
//			event.setCancelled(true);
//			SlimefunItem item = BlockStorage.check(event.getClickedBlock());
//			if (SlimefunManager.isItemSimiliar(item.getItem(), SlimefunNukes.nuke1, false)) {
//				nukeExplode("1", p, event);
//			}
//			
//			if (SlimefunManager.isItemSimiliar(item.getItem(), SlimefunNukes.nuke2, false)) {
//				nukeExplode("2", p, event);
//			}
//			
//			if (SlimefunManager.isItemSimiliar(item.getItem(), SlimefunNukes.nuke3, false)) {
//				nukeExplode("3", p, event);
//			}
//			
//			if (SlimefunManager.isItemSimiliar(item.getItem(), SlimefunNukes.nuke4, false)) {
//				nukeExplode("4", p, event);
//			}
//		}
//	}
//				
//	@SuppressWarnings("deprecation")
//	@EventHandler (priority = EventPriority.LOWEST)
//	public void onBlockPlace(BlockPlaceEvent event) { Player p = event.getPlayer();	
//	  if (SlimefunManager.isItemSimiliar(p.getItemInHand(), SlimefunNukes.airstrike, true)) {		  
//		  event.setCancelled(true);
//		  int quant = (p.getItemInHand().getAmount());
//		  final ItemStack object = (p.getItemInHand());
//		    if (p.getGameMode() == GameMode.SURVIVAL) {
//		      if (quant > 1) {
//		  	    object.setAmount(quant - 1);
//		  	  } else {
//		  	    p.getInventory().removeItem(object);
//		  	  }
//		  }
//		  Location l = event.getBlock().getLocation();
//		  Location place = event.getBlock().getLocation();
//		  l.setX(event.getBlock().getLocation().getX() + 0.5D);
//		  l.setZ(event.getBlock().getLocation().getZ() + 0.5D);
//		  l.setY(event.getBlock().getLocation().getWorld().getHighestBlockYAt(l) + 1D);
//		  TNTPrimed tnt = (TNTPrimed)p.getWorld().spawnEntity(l, EntityType.PRIMED_TNT);
//		  tnt.setFuseTicks((SlimefunNukes.config.getInt("air-strike.delay") * 20));
//		  tnt.setGravity(false);		  
//		  for (Player player : Bukkit.getOnlinePlayers()) {
//		        if (player == p) {
//		        	if (SlimefunNukes.config.getBoolean("air-strike.messages.player.enabled") == true) {	    	
//		    		    String alertmessage1 = (SlimefunNukes.config.getString("air-strike.messages.player.onCall"));
//		    		    alertmessage1 = alertmessage1.replace("%player%", p.getDisplayName());
//		    		    alertmessage1 = alertmessage1.replace("%power%", String.valueOf(SlimefunNukes.config.getInt("air-strike.power")));
//		    		    alertmessage1 = alertmessage1.replace("%coords%", ("X=" + place.getBlockX() + " Y=" + place.getBlockY() + " Z=" + place.getBlockZ()));
//		    		    alertmessage1 = alertmessage1.replace("%world%", l.getWorld().getName());
//		    		    player.sendMessage(SlimefunNukes.prefix + ChatColor.translateAlternateColorCodes('&', alertmessage1));	    	
//		    	    }	    	    
//		        } else {
//		        	if (SlimefunNukes.config.getBoolean("air-strike.messages.server.enabled") == true) {	    	
//		    		    String alertmessage1 = (SlimefunNukes.config.getString("air-strike.messages.server.onCall"));
//		    		    alertmessage1 = alertmessage1.replace("%player%", p.getDisplayName());
//		    		    alertmessage1 = alertmessage1.replace("%power%", String.valueOf(SlimefunNukes.config.getInt("air-strike.power")));
//		    		    alertmessage1 = alertmessage1.replace("%coords%", ("X=" + place.getBlockX() + " Y=" + place.getBlockY() + " Z=" + place.getBlockZ()));
//		    		    alertmessage1 = alertmessage1.replace("%world%", l.getWorld().getName());
//		    		    player.sendMessage(SlimefunNukes.prefix + ChatColor.translateAlternateColorCodes('&', alertmessage1));	    	
//		    	    }
//		        }
//		    }		  
//		  Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable()
//		    {
//		        public void run() {
//		          tnt.remove();
//		          Location l = tnt.getLocation();
//		          l.setX(tnt.getLocation().getX() + 0.5D);
//		          l.setZ(tnt.getLocation().getZ() + 0.5D);
//		          l.getWorld().createExplosion(l.getWorld().getHighestBlockAt(l).getLocation(), SlimefunNukes.config.getInt("air-strike.power"), (SlimefunNukes.config.getBoolean("air-strike.fire")));
//		          for (Player player : Bukkit.getOnlinePlayers()) {
//		  	        if (player == p) {
//		  	        	if (SlimefunNukes.config.getBoolean("air-strike.messages.player.enabled") == true) {	    	
//		  	    		    String alertmessage1 = (SlimefunNukes.config.getString("air-strike.messages.player.onDetonation"));
//			    		    alertmessage1 = alertmessage1.replace("%player%", p.getDisplayName());
//			    		    alertmessage1 = alertmessage1.replace("%power%", String.valueOf(SlimefunNukes.config.getInt("air-strike.power")));
//			    		    alertmessage1 = alertmessage1.replace("%coords%", ("X=" + place.getBlockX() + " Y=" + place.getBlockY() + " Z=" + place.getBlockZ()));
//			    		    alertmessage1 = alertmessage1.replace("%world%", l.getWorld().getName());
//		  	    		    player.sendMessage(SlimefunNukes.prefix + ChatColor.translateAlternateColorCodes('&', alertmessage1));	    	
//		  	    	    }	    	    
//		  	        } else {
//		  	        	if (SlimefunNukes.config.getBoolean("air-strike.messages.server.enabled") == true) {	    	
//		  	    		    String alertmessage1 = (SlimefunNukes.config.getString("air-strike.messages.server.onDetonation"));
//			    		    alertmessage1 = alertmessage1.replace("%player%", p.getDisplayName());
//			    		    alertmessage1 = alertmessage1.replace("%power%", String.valueOf(SlimefunNukes.config.getInt("air-strike.power")));
//			    		    alertmessage1 = alertmessage1.replace("%coords%", ("X=" + place.getBlockX() + " Y=" + place.getBlockY() + " Z=" + place.getBlockZ()));
//			    		    alertmessage1 = alertmessage1.replace("%world%", l.getWorld().getName());
//		  	    		    player.sendMessage(SlimefunNukes.prefix + ChatColor.translateAlternateColorCodes('&', alertmessage1));	    	
//		  	    	    }
//		  	        }
//		  	    }	
//		      }
//		    }, (SlimefunNukes.config.getInt("air-strike.delay") * 20));		  
//	  }	  
//	  if (SlimefunManager.isItemSimiliar(p.getItemInHand(), SlimefunNukes.nuke1, false)) {
//		  BlockStorage.addBlockInfo(event.getBlockPlaced(), "nuke", "1");
//	  }	  
//	  if (SlimefunManager.isItemSimiliar(p.getItemInHand(), SlimefunNukes.nuke2, false)) {
//		  BlockStorage.addBlockInfo(event.getBlockPlaced(), "nuke", "2");
//	  }	  
//	  if (SlimefunManager.isItemSimiliar(p.getItemInHand(), SlimefunNukes.nuke3, false)) {
//		  BlockStorage.addBlockInfo(event.getBlockPlaced(), "nuke", "3");
//	  }	  
//	  if (SlimefunManager.isItemSimiliar(p.getItemInHand(), SlimefunNukes.nuke4, false)) {
//		  BlockStorage.addBlockInfo(event.getBlockPlaced(), "nuke", "4");
//	  }	  
//	}	
//	public static List<Location> sphereShape(Location explosionBlock, int radius, boolean hollow) {		
//		List<Location> sphereBlocks = new ArrayList<Location>();
//		int bX = explosionBlock.getBlockX();
//		int bY = explosionBlock.getBlockY();
//		int bZ = explosionBlock.getBlockZ();		
//		for (int y = bY - radius; y <= bY + radius; y++) {
//			for (int x = bX - radius; x <= bX + radius; x++) {
//				for (int z = bZ - radius; z <= bZ + radius; z++) {					
//					double distance = ((bX - x) * (bX - x) + ((bZ - z) * (bZ - z)) + ((bY - y) * (bY - y)));					
//					if (distance < radius * radius && !(hollow && distance < ((radius - 1) * (radius - 1)))) {
//						Location lo = new Location(explosionBlock.getWorld(), x, y, z);
//						if (lo.getBlock().getType() != Material.BEDROCK && !BlockStorage.hasBlockInfo(lo) && lo.getBlock().getType() != Material.WATER) {
//							sphereBlocks.add(lo);
//						}
//					}
//				}
//			}
//		}
//		return sphereBlocks;
//	}	
//	private void nukeExplode(String nuke, Player p, PlayerInteractEvent event) {		
//		event.getClickedBlock().setType(Material.AIR);
//	    Location l = event.getClickedBlock().getLocation();
//	    Location place = event.getClickedBlock().getLocation();
//	    l.setX(event.getClickedBlock().getLocation().getX() + 0.5D);
//	    l.setZ(event.getClickedBlock().getLocation().getZ() + 0.5D);
//	    TNTPrimed tnt = (TNTPrimed)event.getClickedBlock().getWorld().spawnEntity(l, EntityType.PRIMED_TNT);
//	    tnt.setFuseTicks((SlimefunNukes.config.getInt(nuke + ".delay") * 20));
//	    if (nuke.equals("air-strike")) {
//	    	tnt.setGravity(false);
//	    	nuke = "air-strike";
//	    } else {
//	    	nuke = "nuke" + nuke;
//	    }
//	    if (SlimefunNukes.config.getBoolean(nuke + ".messages.enabled")) {
//	      String alertmessage = (SlimefunNukes.config.getString(nuke + ".messages.onPlace"));
//	      alertmessage = alertmessage.replace("%power%", String.valueOf(SlimefunNukes.config.getInt(nuke + ".power")));
//	      alertmessage = alertmessage.replace("%coords%", ("X=" + place.getBlockX() + " Y=" + place.getBlockY() + " Z=" + place.getBlockZ()));
//	      alertmessage = alertmessage.replace("%world%", l.getWorld().getName());
//	      if (SlimefunNukes.config.getBoolean("custom-name-broadcast")) {
//	    	  alertmessage = alertmessage.replace("%player%", p.getDisplayName());
//	      } else {
//	    	  alertmessage = alertmessage.replace("%player%", p.getName());
//	      }
//	      plugin.getServer().broadcastMessage(SlimefunNukes.prefix + ChatColor.translateAlternateColorCodes('&', alertmessage));	    	    
//	    }
//        final String name = nuke;
//	    Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable()
//	    {
//	        public void run() {
//	          tnt.remove();
//	          Location l = tnt.getLocation();
//	          l.setX(tnt.getLocation().getX() + 0.5D);
//	          l.setZ(tnt.getLocation().getZ() + 0.5D);
//	          if (SlimefunNukes.config.getBoolean(name + ".sphere-shape")) {
//	        	  for (Location lu : sphereShape(l, (SlimefunNukes.config.getInt(name + ".radius")), false)) {
//	  			    lu.getBlock().setType(Material.AIR);
//	  			  }
//	          }
//	          l.getWorld().createExplosion(l.getX(), l.getY(), l.getZ(), SlimefunNukes.config.getInt(name + ".power"), (SlimefunNukes.config.getBoolean("nuke" + name + ".fire")));
//	          for (Player player : Bukkit.getOnlinePlayers()) {
//	        	  if ((l.distance(player.getLocation())) < SlimefunNukes.config.getInt(name + ".power")) {
//	        		  player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, SlimefunNukes.config.getInt(name + ".delay"), 3));
//	        		  player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, SlimefunNukes.config.getInt(name + ".delay"), 3));
//	        		  player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, SlimefunNukes.config.getInt(name + ".delay"), 3));
//	        		  player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, SlimefunNukes.config.getInt(name + ".delay"), 3));
//	        	  }
//	        	  if (SlimefunNukes.config.getBoolean(name + ".messages.enabled")) {
//  	    		    String alertmessage = (SlimefunNukes.config.getString(name + ".messages.onDetonation"));
//	    		    alertmessage = alertmessage.replace("%power%", String.valueOf(SlimefunNukes.config.getInt(name + ".power")));
//	    		    alertmessage = alertmessage.replace("%coords%", ("X=" + place.getBlockX() + " Y=" + place.getBlockY() + " Z=" + place.getBlockZ()));
//	    		    alertmessage = alertmessage.replace("%world%", l.getWorld().getName());
//				    if (SlimefunNukes.config.getBoolean("custom-name-broadcast")) {
//				    	alertmessage = alertmessage.replace("%player%", p.getDisplayName());
//				    } else {
//				    	alertmessage = alertmessage.replace("%player%", p.getName());
//				    }
//  	    		    plugin.getServer().broadcastMessage(SlimefunNukes.prefix + ChatColor.translateAlternateColorCodes('&', alertmessage));
//	        	  }
//	           }  
//	      }
//	    }, (SlimefunNukes.config.getInt(name + ".delay") * 20));		
//	}
}
