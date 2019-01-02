package com.minexd.praxi.event.game.map.command;

import com.minexd.praxi.event.game.map.EventGameMap;
import com.minexd.zoot.util.CC;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.entity.Player;

@CommandMeta(label = "event maps", permission = "praxi.admin.event")
public class EventMapsCommand {

	public void execute(Player player) {
		player.sendMessage(CC.GOLD + CC.BOLD + "Event Maps");

		if (EventGameMap.getMaps().isEmpty()) {
			player.sendMessage(CC.GRAY + "There are no event maps.");
		} else {
			for (EventGameMap gameMap : EventGameMap.getMaps()) {
				player.sendMessage(" - " + (gameMap.isSetup() ? CC.GREEN : CC.RED) + gameMap.getMapName());
			}
		}
	}

}
