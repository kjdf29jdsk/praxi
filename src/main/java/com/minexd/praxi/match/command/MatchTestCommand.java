package com.minexd.praxi.match.command;

import com.minexd.praxi.Locale;
import com.minexd.praxi.match.Match;
import com.minexd.praxi.match.participant.MatchGamePlayer;
import com.minexd.praxi.participant.GameParticipant;
import com.qrakn.honcho.command.CommandMeta;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;

@CommandMeta(label = "match test", permission = "praxi.admin")
public class MatchTestCommand {

	public void execute(Player player) {
		List<GameParticipant<MatchGamePlayer>> list = Arrays.asList(
				new GameParticipant<>(new MatchGamePlayer(UUID.randomUUID(), "Test1")),
				new GameParticipant<>(new MatchGamePlayer(UUID.randomUUID(), "Test2")),
				new GameParticipant<>(new MatchGamePlayer(UUID.randomUUID(), "Test3")),
				new GameParticipant<>(new MatchGamePlayer(UUID.randomUUID(), "Test4")));

		BaseComponent[] components = Match.generateInventoriesComponents(
				Locale.MATCH_END_WINNER_INVENTORY.format("s"), list);

		player.spigot().sendMessage(components);
	}

}
