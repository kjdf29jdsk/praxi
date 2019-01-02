package com.minexd.praxi.arena.command;

import com.minexd.praxi.arena.Arena;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

@CommandMeta(label = "arena save", permission = "praxi.admin.arena")
public class ArenaSaveCommand {

	public void execute(CommandSender sender) {
		for (Arena arena : Arena.getArenas()) {
			arena.save();
		}

		sender.sendMessage(ChatColor.GREEN + "Saved all arenas!");
	}

}
