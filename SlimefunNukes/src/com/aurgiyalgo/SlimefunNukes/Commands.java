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
		if (label.equalsIgnoreCase("snreload")) {
			sender.sendMessage(Main.prefix + ChatColor.GREEN + "Command disabled at the moment");
		}
		return true;
	}
}
