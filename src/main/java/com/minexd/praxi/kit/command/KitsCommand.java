package com.minexd.praxi.kit.command;

import com.minexd.praxi.kit.Kit;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

@CommandMeta(label = "kits", permission = "praxi.admin.kit")
public class KitsCommand {

	public void execute(CommandSender sender) {
		sender.sendMessage(ChatColor.GOLD + "Kits");

		for (Kit kit : Kit.getKits()) {
			sender.sendMessage(kit.getName());
		}
	}

}
