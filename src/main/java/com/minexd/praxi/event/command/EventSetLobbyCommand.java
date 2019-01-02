package com.minexd.praxi.event.command;

import com.minexd.praxi.event.Event;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandMeta(label = "event set lobby", permission = "praxi.admin.event")
public class EventSetLobbyCommand {

	public void execute(Player player, Event event) {
		if (event != null) {
			event.setLobbyLocation(player.getLocation());
			event.save();

			player.sendMessage(ChatColor.GOLD + "You updated the " + ChatColor.GREEN + event.getDisplayName() +
			                   ChatColor.GOLD + " Event's lobby location.");
		} else {
			player.sendMessage(ChatColor.RED + "An event with that name does not exist.");
		}
	}

}
