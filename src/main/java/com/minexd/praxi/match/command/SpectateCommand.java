package com.minexd.praxi.match.command;

import com.minexd.praxi.profile.Profile;
import com.minexd.praxi.profile.ProfileState;
import com.minexd.zoot.util.CC;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.entity.Player;

@CommandMeta(label = { "spectate", "spec" })
public class SpectateCommand {

	public void execute(Player player, Player target) {
		if (player.hasMetadata("frozen")) {
			player.sendMessage(CC.RED + "You cannot spectate while frozen.");
			return;
		}

		if (target == null) {
			player.sendMessage(CC.RED + "A player with that name could not be found.");
			return;
		}

		Profile playerProfile = Profile.getByUuid(player.getUniqueId());

		if (playerProfile.isBusy()) {
			player.sendMessage(CC.RED + "You must be in the lobby and not queueing to spectate.");
			return;
		}

		if (playerProfile.getParty() != null) {
			player.sendMessage(CC.RED + "You must leave your party to spectate a match.");
			return;
		}

		Profile targetProfile = Profile.getByUuid(target.getUniqueId());

		if (targetProfile == null || targetProfile.getState() != ProfileState.FIGHTING) {
			player.sendMessage(CC.RED + "That player is not in a match.");
			return;
		}

		if (!targetProfile.getOptions().allowSpectators()) {
			player.sendMessage(CC.RED + "That player is not allowing spectators.");
			return;
		}

		targetProfile.getMatch().addSpectator(player, target);
	}

}
