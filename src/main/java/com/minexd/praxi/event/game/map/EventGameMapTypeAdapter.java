package com.minexd.praxi.event.game.map;

import com.qrakn.honcho.command.adapter.CommandTypeAdapter;

public class EventGameMapTypeAdapter implements CommandTypeAdapter {

	@Override
	public <T> T convert(String string, Class<T> type) {
		return type.cast(EventGameMap.getByName(string));
	}

}

