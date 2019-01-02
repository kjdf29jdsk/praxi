package com.minexd.praxi.event.command;

import com.minexd.praxi.Praxi;
import com.minexd.zoot.util.CC;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.entity.Player;

@CommandMeta(label = { "event", "event help" })
public class EventHelpCommand {

	public void execute(Player player) {
		for (String line : Praxi.get().getMainConfig().getStringList("EVENT.HELP")) {
			player.sendMessage(CC.translate(line));
		}
	}

}
