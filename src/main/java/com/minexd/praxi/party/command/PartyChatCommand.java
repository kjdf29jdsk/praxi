package com.minexd.praxi.party.command;

import com.minexd.praxi.profile.Profile;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.entity.Player;

@CommandMeta(label = { "party chat", "p chat" })
public class PartyChatCommand {

	public void execute(Player player, String message) {
		Profile profile = Profile.getByUuid(player.getUniqueId());

		if (profile.getParty() != null) {
			profile.getParty().sendChat(player, message);
		}
	}

}
