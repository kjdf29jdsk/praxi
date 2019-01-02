package com.minexd.praxi;

import com.bizarrealex.aether.Aether;
import com.bizarrealex.aether.AetherOptions;
import com.minexd.praxi.arena.Arena;
import com.minexd.praxi.arena.ArenaListener;
import com.minexd.praxi.arena.ArenaType;
import com.minexd.praxi.arena.ArenaTypeAdapter;
import com.minexd.praxi.arena.ArenaTypeTypeAdapter;
import com.minexd.praxi.arena.command.ArenaAddKitCommand;
import com.minexd.praxi.arena.command.ArenaCreateCommand;
import com.minexd.praxi.arena.command.ArenaDeleteCommand;
import com.minexd.praxi.arena.command.ArenaGenHelperCommand;
import com.minexd.praxi.arena.command.ArenaGenerateCommand;
import com.minexd.praxi.arena.command.ArenaRemoveKitCommand;
import com.minexd.praxi.arena.command.ArenaSaveCommand;
import com.minexd.praxi.arena.command.ArenaSelectionCommand;
import com.minexd.praxi.arena.command.ArenaSetSpawnCommand;
import com.minexd.praxi.arena.command.ArenaStatusCommand;
import com.minexd.praxi.arena.command.ArenasCommand;
import com.minexd.praxi.event.Event;
import com.minexd.praxi.event.EventTypeAdapter;
import com.minexd.praxi.event.command.EventAdminCommand;
import com.minexd.praxi.event.command.EventHelpCommand;
import com.minexd.praxi.event.command.EventSetLobbyCommand;
import com.minexd.praxi.event.game.EventGameListener;
import com.minexd.praxi.event.game.command.EventCancelCommand;
import com.minexd.praxi.event.game.command.EventClearCooldownCommand;
import com.minexd.praxi.event.game.command.EventForceStartCommand;
import com.minexd.praxi.event.game.command.EventHostCommand;
import com.minexd.praxi.event.game.command.EventInfoCommand;
import com.minexd.praxi.event.game.command.EventJoinCommand;
import com.minexd.praxi.event.game.command.EventLeaveCommand;
import com.minexd.praxi.event.command.EventsCommand;
import com.minexd.praxi.event.game.map.EventGameMap;
import com.minexd.praxi.event.game.map.EventGameMapTypeAdapter;
import com.minexd.praxi.event.game.map.command.EventMapCreateCommand;
import com.minexd.praxi.event.game.map.command.EventMapDeleteCommand;
import com.minexd.praxi.event.game.map.command.EventMapSetSpawnCommand;
import com.minexd.praxi.event.game.map.command.EventMapStatusCommand;
import com.minexd.praxi.event.game.map.command.EventMapsCommand;
import com.minexd.praxi.event.command.EventAddMapCommand;
import com.minexd.praxi.event.command.EventRemoveMapCommand;
import com.minexd.praxi.event.game.map.vote.command.EventMapVoteCommand;
import com.minexd.praxi.match.command.MatchTestCommand;
import com.minexd.praxi.match.command.ViewInventoryCommand;
import com.minexd.praxi.duel.command.DuelAcceptCommand;
import com.minexd.praxi.duel.command.DuelCommand;
import com.minexd.praxi.duel.command.RematchCommand;
import com.minexd.praxi.kit.Kit;
import com.minexd.praxi.kit.KitTypeAdapter;
import com.minexd.praxi.kit.command.KitCreateCommand;
import com.minexd.praxi.kit.command.KitGetLoadoutCommand;
import com.minexd.praxi.kit.command.KitsCommand;
import com.minexd.praxi.kit.command.KitSetLoadoutCommand;
import com.minexd.praxi.kit.KitEditorListener;
import com.minexd.praxi.match.Match;
import com.minexd.praxi.match.command.SpectateCommand;
import com.minexd.praxi.match.command.StopSpectatingCommand;
import com.minexd.praxi.match.MatchListener;
import com.minexd.praxi.party.Party;
import com.minexd.praxi.party.command.PartyChatCommand;
import com.minexd.praxi.party.command.PartyCloseCommand;
import com.minexd.praxi.party.command.PartyCreateCommand;
import com.minexd.praxi.party.command.PartyDisbandCommand;
import com.minexd.praxi.party.command.PartyHelpCommand;
import com.minexd.praxi.party.command.PartyInfoCommand;
import com.minexd.praxi.party.command.PartyInviteCommand;
import com.minexd.praxi.party.command.PartyJoinCommand;
import com.minexd.praxi.party.command.PartyKickCommand;
import com.minexd.praxi.party.command.PartyLeaveCommand;
import com.minexd.praxi.party.command.PartyOpenCommand;
import com.minexd.praxi.party.PartyListener;
import com.minexd.praxi.profile.Profile;
import com.minexd.praxi.profile.command.FlyCommand;
import com.minexd.praxi.profile.ProfileListener;
import com.minexd.praxi.profile.hotbar.Hotbar;
import com.minexd.praxi.profile.meta.option.command.ToggleDuelRequestsCommand;
import com.minexd.praxi.profile.meta.option.command.ToggleScoreboardCommand;
import com.minexd.praxi.profile.meta.option.command.ToggleSpectatorsCommand;
import com.minexd.praxi.queue.QueueListener;
import com.minexd.praxi.queue.QueueThread;
import com.minexd.praxi.scoreboard.ScoreboardAdapter;
import com.minexd.praxi.util.InventoryUtil;
import com.minexd.zoot.Zoot;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.qrakn.phoenix.lang.file.type.BasicConfigurationFile;
import java.util.Arrays;
import lombok.Getter;
import org.bukkit.Difficulty;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class Praxi extends JavaPlugin {

	private static Praxi praxi;

	@Getter private BasicConfigurationFile mainConfig;
	@Getter private BasicConfigurationFile arenasConfig;
	@Getter private BasicConfigurationFile kitsConfig;
	@Getter private BasicConfigurationFile eventsConfig;
	@Getter private MongoDatabase mongoDatabase;

	@Override
	public void onEnable() {
		praxi = this;

		mainConfig = new BasicConfigurationFile(this, "config");
		arenasConfig = new BasicConfigurationFile(this, "arenas");
		kitsConfig = new BasicConfigurationFile(this, "kits");
		eventsConfig = new BasicConfigurationFile(this, "events");

		loadMongo();

		Hotbar.init();
		Kit.init();
		Arena.init();
		Profile.init();
		Match.init();
		Party.init();
		Event.init();
		EventGameMap.init();

		new Aether(this, new ScoreboardAdapter(), new AetherOptions().hook(true));
		new QueueThread().start();

		Zoot.get().getHoncho().registerTypeAdapter(Arena.class, new ArenaTypeAdapter());
		Zoot.get().getHoncho().registerTypeAdapter(ArenaType.class, new ArenaTypeTypeAdapter());
		Zoot.get().getHoncho().registerTypeAdapter(Kit.class, new KitTypeAdapter());
		Zoot.get().getHoncho().registerTypeAdapter(EventGameMap.class, new EventGameMapTypeAdapter());
		Zoot.get().getHoncho().registerTypeAdapter(Event.class, new EventTypeAdapter());

		Arrays.asList(
				new ArenaAddKitCommand(),
				new ArenaRemoveKitCommand(),
				new ArenaCreateCommand(),
				new ArenaDeleteCommand(),
				new ArenaGenerateCommand(),
				new ArenaGenHelperCommand(),
				new ArenaSaveCommand(),
				new ArenasCommand(),
				new ArenaSelectionCommand(),
				new ArenaSetSpawnCommand(),
				new ArenaStatusCommand(),
				new DuelCommand(),
				new DuelAcceptCommand(),
				new EventAdminCommand(),
				new EventHelpCommand(),
				new EventCancelCommand(),
				new EventClearCooldownCommand(),
				new EventForceStartCommand(),
				new EventHostCommand(),
				new EventInfoCommand(),
				new EventJoinCommand(),
				new EventLeaveCommand(),
				new EventSetLobbyCommand(),
				new EventMapCreateCommand(),
				new EventMapDeleteCommand(),
				new EventMapsCommand(),
				new EventMapSetSpawnCommand(),
				new EventMapStatusCommand(),
				new EventMapVoteCommand(),
				new EventAddMapCommand(),
				new EventRemoveMapCommand(),
				new EventsCommand(),
				new RematchCommand(),
				new SpectateCommand(),
				new StopSpectatingCommand(),
				new FlyCommand(),
				new PartyChatCommand(),
				new PartyCloseCommand(),
				new PartyCreateCommand(),
				new PartyDisbandCommand(),
				new PartyHelpCommand(),
				new PartyInfoCommand(),
				new PartyInviteCommand(),
				new PartyJoinCommand(),
				new PartyKickCommand(),
				new PartyLeaveCommand(),
				new PartyOpenCommand(),
				new KitCreateCommand(),
				new KitGetLoadoutCommand(),
				new KitSetLoadoutCommand(),
				new KitsCommand(),
				new ViewInventoryCommand(),
				new MatchTestCommand(),
				new ToggleScoreboardCommand(),
				new ToggleSpectatorsCommand(),
				new ToggleDuelRequestsCommand()
		).forEach(command -> Zoot.get().getHoncho().registerCommand(command));

		Arrays.asList(
				new KitEditorListener(),
				new PartyListener(),
				new ProfileListener(),
				new PartyListener(),
				new MatchListener(),
				new QueueListener(),
				new ArenaListener(),
				new EventGameListener()
		).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));

		Arrays.asList(
				Material.WORKBENCH,
				Material.STICK,
				Material.WOOD_PLATE,
				Material.WOOD_BUTTON,
				Material.SNOW_BLOCK
		).forEach(InventoryUtil::removeCrafting);

		// Set the difficulty for each world to HARD
		// Clear the droppedItems for each world
		getServer().getWorlds().forEach(world -> {
			world.setDifficulty(Difficulty.HARD);
			Zoot.get().getEssentials().clearEntities(world);
		});
	}

	@Override
	public void onDisable() {
		Match.cleanup();
	}

	private void loadMongo() {
		if (mainConfig.getBoolean("MONGO.AUTHENTICATION.ENABLED")) {
			mongoDatabase = new MongoClient(
					new ServerAddress(
							mainConfig.getString("MONGO.HOST"),
							mainConfig.getInteger("MONGO.PORT")
					),
					MongoCredential.createCredential(
							mainConfig.getString("MONGO.AUTHENTICATION.USERNAME"),
							"admin", mainConfig.getString("MONGO.AUTHENTICATION.PASSWORD").toCharArray()
					),
					MongoClientOptions.builder().build()
			).getDatabase("praxi");
		} else {
			mongoDatabase = new MongoClient(mainConfig.getString("MONGO.HOST"), mainConfig.getInteger("MONGO.PORT"))
					.getDatabase("praxi");
		}
	}

	public static Praxi get() {
		return praxi;
	}

}
