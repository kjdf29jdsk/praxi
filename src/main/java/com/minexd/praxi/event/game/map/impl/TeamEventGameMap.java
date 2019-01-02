package com.minexd.praxi.event.game.map.impl;

import com.minexd.praxi.Praxi;
import com.minexd.praxi.event.game.EventGame;
import com.minexd.praxi.event.game.map.EventGameMap;
import com.minexd.praxi.participant.GameParticipant;
import com.minexd.praxi.participant.GamePlayer;
import com.minexd.praxi.event.impl.sumo.SumoGameLogic;
import com.minexd.praxi.util.LocationUtil;
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class TeamEventGameMap extends EventGameMap {

	@Getter @Setter private Location spawnPointA;
	@Getter @Setter private Location spawnPointB;

	public TeamEventGameMap(String mapName) {
		super(mapName);
	}

	@Override
	public void teleportFighters(EventGame game) {
		int locationIndex = 0;
		Location[] locations = new Location[]{ spawnPointA, spawnPointB };

		if (game.getGameLogic() instanceof SumoGameLogic) {
			GameParticipant<? extends GamePlayer>[] participants = new GameParticipant[] {
					((SumoGameLogic) game.getGameLogic()).getParticipantA(),
					((SumoGameLogic) game.getGameLogic()).getParticipantB()
			};

			for (GameParticipant<? extends GamePlayer> participant : participants) {
				int processed = 0;

				for (GamePlayer gamePlayer : participant.getPlayers()) {
					processed++;

					Player player = gamePlayer.getPlayer();

					if (player != null) {
						player.teleport(locations[locationIndex]);
					}

					if (processed == participant.getPlayers().size()) {
						locationIndex++;
					}
				}
			}
		}
	}

	@Override
	public boolean isSetup() {
		return spectatorPoint != null && spawnPointA != null && spawnPointB != null;
	}

	@Override
	public void save() {
		super.save();

		FileConfiguration config = Praxi.get().getEventsConfig().getConfiguration();
		config.set("EVENT_MAPS." + getMapName() + ".TYPE", "TEAM");
		config.set("EVENT_MAPS." + getMapName() + ".SPAWN_POINT_A", LocationUtil.serialize(spawnPointA));
		config.set("EVENT_MAPS." + getMapName() + ".SPAWN_POINT_B", LocationUtil.serialize(spawnPointB));

		try {
			config.save(Praxi.get().getEventsConfig().getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
