package com.minexd.praxi.event.game.map.vote.command;

import com.minexd.praxi.event.game.EventGame;
import com.minexd.praxi.event.game.map.EventGameMap;
import com.minexd.praxi.profile.Profile;
import com.minexd.praxi.profile.ProfileState;
import com.minexd.zoot.util.Cooldown;
import com.qrakn.honcho.command.CPL;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandMeta(label = "event map vote")
public class EventMapVoteCommand {

	public void execute(Player player, @CPL("map") EventGameMap gameMap) {
		if (gameMap == null) {
			player.sendMessage(ChatColor.RED + "You cannot vote for a map that doesn't exist!");
			return;
		}

		Profile profile = Profile.getByUuid(player.getUniqueId());

		if (profile.getState() == ProfileState.EVENT && EventGame.getActiveGame() != null) {
			if (profile.getVoteCooldown().hasExpired()) {
				profile.setVoteCooldown(new Cooldown(5000));
				EventGame.getActiveGame().getGameLogic().onVote(player, gameMap);
			} else {
				player.sendMessage(ChatColor.RED + "You can vote in another " +
						profile.getVoteCooldown().getTimeLeft() + ".");
			}
		} else {
			player.sendMessage(ChatColor.RED + "You are not in an event.");
		}
	}

}
