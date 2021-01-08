package com.aurgiyalgo.SlimefunNukes;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SfNukesExecutor implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only a player can do this!");
			return true;
		}
		if (args.length <= 1) {
			sender.sendMessage(ChatColor.RED + "Not enough arguments! Use /sfnukes version");
			return true;
		}
		if ("version".equalsIgnoreCase(args[0])) {
			sender.sendMessage(ChatColor.GREEN + "You're running " + ChatColor.GRAY + "v" + SlimefunNukes.getInstance().getDescription().getVersion());
			return true;
		}
		return true;
	}
}
