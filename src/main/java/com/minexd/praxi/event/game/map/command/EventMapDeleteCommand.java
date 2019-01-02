package com.minexd.praxi.event.game.map.command;

import com.minexd.praxi.event.game.map.EventGameMap;
import com.minexd.praxi.event.game.map.impl.SpreadEventGameMap;
import com.minexd.praxi.event.game.map.impl.TeamEventGameMap;
import com.minexd.zoot.util.CC;
import com.qrakn.honcho.command.CPL;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.entity.Player;

@CommandMeta(label = "event map delete", permission = "praxi.admin.event")
public class EventMapDeleteCommand {

	public void execute(Player player, @CPL("map") EventGameMap gameMap) {
		if (gameMap == null) {
			player.sendMessage(CC.RED + "An event map with that name already exists.");
			return;
		}

		gameMap.delete();

		EventGameMap.getMaps().remove(gameMap);

		player.sendMessage(CC.GREEN + "You successfully deleted the event map \"" + gameMap.getMapName() + "\".");
	}

}
