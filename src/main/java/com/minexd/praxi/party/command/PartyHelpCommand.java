package com.minexd.praxi.party.command;

import com.minexd.praxi.Locale;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.entity.Player;

@CommandMeta(label = { "p", "p help", "party", "party help" })
public class PartyHelpCommand {

	public void execute(Player player) {
		Locale.PARTY_HELP.formatLines().forEach(player::sendMessage);
	}

}
