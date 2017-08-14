package com.aurgiyalgo.SlimefunNukes;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor{

	Main plugin;
	
	public Commands(Main instance) {
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("snversion")) {
			Main.updateChecker(sender.getName(), true);
		}
		return true;
	}
}
