package com.minexd.praxi.queue.menu;

import com.minexd.praxi.match.Match;
import com.minexd.praxi.profile.Profile;
import com.minexd.praxi.queue.Queue;
import com.minexd.zoot.util.CC;
import com.minexd.zoot.util.ItemBuilder;
import com.minexd.zoot.util.menu.Button;
import com.minexd.zoot.util.menu.Menu;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class QueueSelectKitMenu extends Menu {

	private boolean ranked;

	{
		setAutoUpdate(true);
	}

	@Override
	public String getTitle(Player player) {
		return "&6&lSelect a kit (" + (ranked ? "Ranked" : "Unranked") + ")";
	}

	@Override
	public Map<Integer, Button> getButtons(Player player) {
		Map<Integer, Button> buttons = new HashMap<>();

		int i = 0;

		for (Queue queue : Queue.getQueues()) {
			if (queue.isRanked() == ranked) {
				buttons.put(i++, new SelectKitButton(queue));
			}
		}

		return buttons;
	}

	@AllArgsConstructor
	private class SelectKitButton extends Button {

		private Queue queue;

		@Override
		public ItemStack getButtonItem(Player player) {
			List<String> lore = new ArrayList<>();
			lore.add("&cFighting: &r" + Match.getInFightsCount(queue));
			lore.add("&cQueueing: &r" + queue.getPlayers().size());

			return new ItemBuilder(queue.getKit().getDisplayIcon())
					.name("&4&l" + queue.getKit().getName())
					.lore(lore)
					.build();
		}

		@Override
		public void clicked(Player player, ClickType clickType) {
			Profile profile = Profile.getByUuid(player.getUniqueId());

			if (profile == null) {
				return;
			}

			if (player.hasMetadata("frozen")) {
				player.sendMessage(CC.RED + "You cannot queue while frozen.");
				return;
			}

			if (profile.isBusy()) {
				player.sendMessage(CC.RED + "You cannot queue right now.");
				return;
			}

			player.closeInventory();

			queue.addPlayer(player, queue.isRanked() ? profile.getKitData().get(queue.getKit()).getElo() : 0);
		}

	}
}
