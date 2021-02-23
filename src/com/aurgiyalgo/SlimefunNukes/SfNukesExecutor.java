package com.aurgiyalgo.SlimefunNukes;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SfNukesExecutor implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		sender.sendMessage(ChatColor.GREEN + "You\'re running " + ChatColor.GRAY + "v" + SlimefunNukes.getInstance().getDescription().getVersion());
		return true;
	}
}
