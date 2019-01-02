package com.minexd.praxi.event;

import com.minexd.praxi.Praxi;
import com.minexd.praxi.event.game.EventGame;
import com.minexd.praxi.event.game.EventGameLogic;
import com.minexd.praxi.event.impl.sumo.SumoEvent;
import com.minexd.zoot.Zoot;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public interface Event {

	List<Event> events = new ArrayList<>();

	static void init() {
		events.add(new SumoEvent());

		for (Event event : events) {
			for (Listener listener : event.getListeners()) {
				Praxi.get().getServer().getPluginManager().registerEvents(listener, Praxi.get());
			}

			for (Object command : event.getCommands()) {
				Zoot.get().getHoncho().registerCommand(command);
			}
		}
	}

	static <T extends Event> T getEvent(Class<? extends Event> clazz) {
		for (Event event : events) {
			if (event.getClass() == clazz) {
				return (T) clazz.cast(event);
			}
		}

		return null;
	}

	String getDisplayName();

	String getDisplayName(EventGame game);

	List<String> getDescription();

	Location getLobbyLocation();

	void setLobbyLocation(Location location);

	ItemStack getIcon();

	boolean canHost(Player player);

	List<String> getAllowedMaps();

	List<Listener> getListeners();

	default List<Object> getCommands() {
		return new ArrayList<>();
	}

	EventGameLogic start(EventGame game);

	void save();

}
