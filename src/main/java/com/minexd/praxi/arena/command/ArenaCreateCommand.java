package com.minexd.praxi.arena.command;

import com.minexd.praxi.arena.Arena;
import com.minexd.praxi.arena.impl.SharedArena;
import com.minexd.praxi.arena.selection.Selection;
import com.minexd.zoot.util.CC;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.entity.Player;

@CommandMeta(label = "arena create", permission = "praxi.admin.arena")
public class ArenaCreateCommand {

	public void execute(Player player, String arenaName) {
		if (Arena.getByName(arenaName) == null) {
			Selection selection = Selection.createOrGetSelection(player);

			if (selection.isFullObject()) {
				Arena arena = new SharedArena(arenaName, selection.getPoint1(), selection.getPoint2());
				Arena.getArenas().add(arena);

				player.sendMessage(CC.GOLD + "Created new arena \"" + arenaName + "\"");
			} else {
				player.sendMessage(CC.RED + "Your selection is incomplete.");
			}
		} else {
			player.sendMessage(CC.RED + "An arena with that name already exists.");
		}
	}

}
