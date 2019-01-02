package com.minexd.praxi.party.command;

import com.minexd.praxi.profile.Profile;
import com.minexd.zoot.util.CC;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.entity.Player;

@CommandMeta(label = { "p info", "party info" })
public class PartyInfoCommand {

	public void execute(Player player) {
		Profile profile = Profile.getByUuid(player.getUniqueId());

		if (profile.getParty() == null) {
			player.sendMessage(CC.RED + "You do not have a party.");
			return;
		}

		profile.getParty().sendInformation(player);
	}

}
