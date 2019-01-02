package com.minexd.praxi.event.command;

import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.entity.Player;

@CommandMeta(label = "events", permission = "praxi.event.host")
public class EventsCommand {

	public void execute(Player player) {
		player.sendMessage("WIP");
	}

}
