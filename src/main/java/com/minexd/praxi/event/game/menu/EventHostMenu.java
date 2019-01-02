package com.minexd.praxi.event.game.menu;

import com.minexd.praxi.event.Event;
import com.minexd.zoot.util.CC;
import com.minexd.zoot.util.ItemBuilder;
import com.minexd.zoot.util.TextSplitter;
import com.minexd.zoot.util.menu.Button;
import com.minexd.zoot.util.menu.Menu;
import com.minexd.zoot.util.menu.button.DisplayButton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class EventHostMenu extends Menu {

	{
		setPlaceholder(true);
	}

	@Override
	public String getTitle(Player player) {
		return "&6&lSelect an Event";
	}

	@Override
	public Map<Integer, Button> getButtons(Player player) {
		Map<Integer, Button> buttons = new HashMap<>();

		int pos = 10;

		for (Event event : Event.events) {
			buttons.put(pos++, new SelectEventButton(event));
		}

		if (pos <= 16) {
			for (int i = pos; i < 16; i++) {
				buttons.put(i, new DisplayButton(new ItemBuilder(Material.STAINED_GLASS_PANE)
						.durability(7).name(" ").build(), true));
			}
		}

		return buttons;
	}

	@AllArgsConstructor
	private class SelectEventButton extends Button {

		private Event event;

		@Override
		public ItemStack getButtonItem(Player player) {
			List<String> lore = new ArrayList<>();
			lore.add(CC.MENU_BAR);

			for (String descriptionLine : TextSplitter.split(28, event.getDescription(), "&7", " ")) {
				lore.add(" " + descriptionLine);
			}

			lore.add("");

			if (event.canHost(player)) {
				lore.add(ChatColor.GREEN + "You can host this event.");
				lore.add(ChatColor.GREEN + "Maximum Slots: " + ChatColor.YELLOW + 50);
			} else {
				lore.add(ChatColor.RED + "You cannot host this event.");
				lore.add(ChatColor.RED + "Purchase a rank upgrade on our store.");
			}

			lore.add(CC.MENU_BAR);

			return new ItemBuilder(event.getIcon().clone())
					.name("&6&l" + event.getDisplayName())
					.lore(lore)
					.build();
		}

		@Override
		public void clicked(Player player, ClickType clickType) {
			if (event.canHost(player)) {
				player.chat("/host " + event.getDisplayName());
			} else {
				player.sendMessage(ChatColor.RED + "You cannot host that event.");
			}
		}

	}

}
