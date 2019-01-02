package com.minexd.praxi.party.command;

import com.minexd.praxi.party.PartyPrivacy;
import com.minexd.praxi.profile.Profile;
import com.minexd.zoot.util.CC;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.entity.Player;

@CommandMeta(label = { "p open", "party open", "p unlock", "party unlock" })
public class PartyOpenCommand {

	public void execute(Player player) {
		Profile profile = Profile.getByUuid(player.getUniqueId());

		if (profile.getParty() == null) {
			player.sendMessage(CC.RED + "You do not have a party.");
			return;
		}

		if (!profile.getParty().getLeader().equals(player)) {
			player.sendMessage(CC.RED + "You are not the leader of your party.");
			return;
		}

		profile.getParty().setPrivacy(PartyPrivacy.OPEN);
	}

}
