package com.minexd.praxi.party.command;

import com.minexd.praxi.Locale;
import com.minexd.praxi.party.Party;
import com.minexd.praxi.profile.Profile;
import com.minexd.praxi.profile.ProfileState;
import com.minexd.praxi.profile.hotbar.Hotbar;
import com.minexd.zoot.util.CC;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.entity.Player;

@CommandMeta(label = { "p create", "party create" })
public class PartyCreateCommand {

	public void execute(Player player) {
		if (player.hasMetadata("frozen")) {
			player.sendMessage(CC.RED + "You cannot create a party while frozen.");
			return;
		}

		Profile profile = Profile.getByUuid(player.getUniqueId());

		if (profile.getParty() != null) {
			player.sendMessage(CC.RED + "You already have a party.");
			return;
		}

		if (profile.getState() != ProfileState.LOBBY) {
			player.sendMessage(CC.RED + "You must be in the lobby to create a party.");
			return;
		}

		profile.setParty(new Party(player));

		Hotbar.giveHotbarItems(player);

		player.sendMessage(Locale.PARTY_CREATE.format());
	}

}
