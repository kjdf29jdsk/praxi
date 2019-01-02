package com.minexd.praxi.arena.command;

import com.minexd.praxi.arena.Arena;
import com.minexd.praxi.arena.impl.SharedArena;
import com.minexd.zoot.util.CC;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.entity.Player;

@CommandMeta(label = "arena delete", permission = "praxi.admin.arena")
public class ArenaDeleteCommand {

	public void execute(Player player, Arena arena) {
		if (arena != null) {
			arena.delete();

			player.sendMessage(CC.GOLD + "Deleted arena \"" + arena.getName() + "\"");
		} else {
			player.sendMessage(CC.RED + "An arena with that name does not exist.");
		}
	}

}
