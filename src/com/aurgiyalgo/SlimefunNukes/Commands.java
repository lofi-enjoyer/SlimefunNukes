package com.aurgiyalgo.SlimefunNukes;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor{

	SlimefunNukes plugin;
	
	public Commands(SlimefunNukes instance) {
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if ((sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only a player can do this!");
			return true;
		}
		if (args.length <= 1) {
			sender.sendMessage(ChatColor.RED + "Not enough arguments! Use /sfnukes [version/reload]");
			return true;
		}
		switch (args[0].toLowerCase()) {
		case "version":
			sender.sendMessage(ChatColor.GREEN + "You\'re running " + ChatColor.GRAY + "v" + SlimefunNukes.getInstance().getDescription().getVersion());
			return true;
		default:
			return true;
		}
//		if (cmd.getLabel().equalsIgnoreCase("sfnukes") && args[0].equalsIgnoreCase("version")) {
//			if (sender.hasPermission("sfnukes.version")) {
//                SlimefunNukes.updateChecker(sender.getName(), true);
//			}
//		}
//		if (cmd.getLabel().equalsIgnoreCase("sfnukes") && args[0].equalsIgnoreCase("reload")) {
//			if (sender.hasPermission("sfnukes.version")) {
//				SlimefunNukes.config.reload();
//				sender.sendMessage(SlimefunNukes.prefix + ChatColor.GREEN + "Config files reloaded");
//			}			
//		}
//		if (cmd.getLabel().equalsIgnoreCase("sfnukes") && args[0].equalsIgnoreCase("")) {
//			sender.sendMessage(ChatColor.RED + "Please insert a valid argument! (version/reload)");
//		}
		return true;
	}
}
