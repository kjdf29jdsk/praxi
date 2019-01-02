package com.minexd.praxi.match.impl;

import com.minexd.praxi.Locale;
import com.minexd.praxi.arena.Arena;
import com.minexd.praxi.kit.Kit;
import com.minexd.praxi.match.Match;
import com.minexd.praxi.match.MatchSnapshot;
import com.minexd.praxi.match.participant.MatchGamePlayer;
import com.minexd.praxi.participant.GameParticipant;
import com.minexd.praxi.profile.Profile;
import com.minexd.praxi.profile.meta.ProfileKitData;
import com.minexd.praxi.queue.Queue;
import com.minexd.praxi.util.PlayerUtil;
import com.minexd.zoot.chat.util.ChatComponentBuilder;
import com.minexd.zoot.util.CC;
import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BasicFreeForAllMatch extends Match {

	private final List<GameParticipant<MatchGamePlayer>> participants;
	private GameParticipant<MatchGamePlayer> winningParticipant;

	public BasicFreeForAllMatch(Queue queue, Kit kit, Arena arena, List<GameParticipant<MatchGamePlayer>> participants) {
		super(queue, kit, arena, false);

		this.participants = participants;
	}

	@Override
	public void setupPlayer(Player player) {
		// Set the player as alive
		MatchGamePlayer gamePlayer = getGamePlayer(player);
		gamePlayer.setDead(false);

		// If the player disconnected, skip any operations for them
		if (gamePlayer.isDisconnected()) {
			return;
		}

		// Reset the player's inventory
		PlayerUtil.reset(player);

		// Deny movement if the kit is sumo
		if (getKit().getGameRules().isSumo()) {
			PlayerUtil.denyMovement(player);
		}

		// Set the player's max damage ticks
		player.setMaximumNoDamageTicks(getKit().getGameRules().getHitDelay());

		// If the player has no kits, apply the default kit, otherwise
		// give the player a list of kit books to choose from
		if (!getKit().getGameRules().isSumo()) {
			Profile profile = Profile.getByUuid(player.getUniqueId());
			ProfileKitData kitData = profile.getKitData().get(getKit());

			if (kitData.getKitCount() > 0) {
				profile.getKitData().get(getKit()).giveBooks(player);
			} else {
				player.getInventory().setArmorContents(getKit().getKitLoadout().getArmor());
				player.getInventory().setContents(getKit().getKitLoadout().getContents());
				player.sendMessage(Locale.MATCH_GIVE_KIT.format("Default"));
			}
		}

		// Teleport the player to their spawn point
		Location spawn = arena.getSpawnA();

		if (spawn.getBlock().getType() == Material.AIR) {
			player.teleport(spawn);
		} else {
			player.teleport(spawn.add(0, 2, 0));
		}
	}

	@Override
	public boolean canEndMatch() {
		return getRemainingTeams() <= 1;
	}

	@Override
	public boolean canStartRound() {
		return false;
	}

	@Override
	public boolean canEndRound() {
		return getRemainingTeams() <= 1;
	}

	@Override
	public void onRoundEnd() {
		for (GameParticipant<MatchGamePlayer> gameParticipant : participants) {
			if (!gameParticipant.isAllDead()) {
				winningParticipant = gameParticipant;
				break;
			}
		}

		if (!kit.getGameRules().isSumo()) {
			// Make all snapshots available
			for (MatchSnapshot snapshot : snapshots) {
				snapshot.setCreatedAt(System.currentTimeMillis());
				MatchSnapshot.getSnapshots().put(snapshot.getUuid(), snapshot);
			}
		}

		super.onRoundEnd();
	}

	@Override
	public boolean isOnSameTeam(Player first, Player second) {
		return first.equals(second);
	}

	@Override
	public List<GameParticipant<MatchGamePlayer>> getParticipants() {
		return new ArrayList<>(participants);
	}

	@Override
	public org.bukkit.ChatColor getRelationColor(Player viewer, Player target) {
		if (viewer.equals(target)) {
			return org.bukkit.ChatColor.GREEN;
		} else {
			for (GameParticipant<MatchGamePlayer> participant : participants) {
				if (participant.containsPlayer(target.getUniqueId())) {
					return org.bukkit.ChatColor.RED;
				}
			}

			return org.bukkit.ChatColor.YELLOW;
		}
	}

	@Override
	public List<String> getScoreboardLines(Player player) {
		List<String> lines = new ArrayList<>();

		if (getParticipant(player) != null) {
			lines.add("&cDuration: &r" + getDuration());
			lines.add("&cOpponents: &r" + (getRemainingTeams() - 1));
		} else {
			lines.add("&cKit: &7" + getKit().getName());
			lines.add("&cDuration: &7" + getDuration());
			lines.add("&cTeams: &7" + getRemainingTeams());
		}

		return lines;
	}

	@Override
	public void addSpectator(Player spectator, Player target) {
		super.addSpectator(spectator, target);

		spectator.sendMessage(Locale.MATCH_START_SPECTATING.format(CC.GREEN, target.getUniqueId()));
	}

	@Override
	public List<BaseComponent[]> generateEndComponents() {
		List<BaseComponent[]> componentsList = new ArrayList<>();

		for (String line : Locale.MATCH_END_DETAILS.formatLines()) {
			if (line.equalsIgnoreCase("%INVENTORIES%")) {
				List<GameParticipant<MatchGamePlayer>> participants = new ArrayList<>(this.participants);
				participants.remove(winningParticipant);

				BaseComponent[] winners = generateInventoriesComponents(
						Locale.MATCH_END_WINNER_INVENTORY.format(""), winningParticipant);

				BaseComponent[] losers = generateInventoriesComponents(
						Locale.MATCH_END_LOSER_INVENTORY.format(participants.size() > 1 ? "s" : ""), participants);

				componentsList.add(winners);
				componentsList.add(losers);

				continue;
			}

			if (line.equalsIgnoreCase("%ELO_CHANGES%")) {
				continue;
			}

			componentsList.add(new ChatComponentBuilder("").parse(line).create());
		}

		return componentsList;
	}

	private int getRemainingTeams() {
		int remaining = 0;

		for (GameParticipant<MatchGamePlayer> gameParticipant : participants) {
			if (!gameParticipant.isAllDead()) {
				remaining++;
			}
		}

		return remaining;
	}

}
