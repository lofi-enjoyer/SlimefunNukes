package com.aurgiyalgo.SlimefunNukes;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor{

	Main plugin;
	
	public Commands(Main instance) {
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getLabel().equalsIgnoreCase("sfnukes") && args[0].equalsIgnoreCase("version")) {
			if (sender.hasPermission("sfnukes.version")) {
                Main.updateChecker(sender.getName(), true);
			}
		}
		if (cmd.getLabel().equalsIgnoreCase("sfnukes") && args[0].equalsIgnoreCase("reload")) {
			if (sender.hasPermission("sfnukes.version")) {
				Main.config.reload();
				sender.sendMessage(Main.prefix + ChatColor.GREEN + "Config files reloaded");
			}			
		}
		if (cmd.getLabel().equalsIgnoreCase("sfnukes") && args[0].equalsIgnoreCase("")) {
			sender.sendMessage(ChatColor.RED + "Please insert a valid argument! (version/reload)");
		}
		return true;
	}
}
