package com.minexd.praxi.duel.menu;

import com.minexd.praxi.kit.Kit;
import com.minexd.praxi.profile.Profile;
import com.minexd.zoot.util.CC;
import com.minexd.zoot.util.ItemBuilder;
import com.minexd.zoot.util.menu.Button;
import com.minexd.zoot.util.menu.Menu;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class DuelSelectKitMenu extends Menu {

	@Override
	public String getTitle(Player player) {
		return "&6&lSelect a kit";
	}

	@Override
	public Map<Integer, Button> getButtons(Player player) {
		Map<Integer, Button> buttons = new HashMap<>();

		for (Kit kit : Kit.getKits()) {
			if (kit.isEnabled()) {
				buttons.put(buttons.size(), new SelectKitButton(kit));
			}
		}

		return buttons;
	}

	@Override
	public void onClose(Player player) {
		if (!isClosedByMenu()) {
			Profile profile = Profile.getByUuid(player.getUniqueId());
			profile.setDuelProcedure(null);
		}
	}

	@AllArgsConstructor
	private class SelectKitButton extends Button {

		private Kit kit;

		@Override
		public ItemStack getButtonItem(Player player) {
			return new ItemBuilder(kit.getDisplayIcon())
					.name("&6&l" + kit.getName())
					.build();
		}

		@Override
		public void clicked(Player player, ClickType clickType) {
			Profile profile = Profile.getByUuid(player.getUniqueId());

			if (profile.getDuelProcedure() == null) {
				player.sendMessage(CC.RED + "Could not find duel procedure.");
				return;
			}

			// Update duel procedure
			profile.getDuelProcedure().setKit(kit);

			// Set closed by menu
			Menu.currentlyOpenedMenus.get(player.getName()).setClosedByMenu(true);

			// Force close inventory
			player.closeInventory();

			// Open arena selection menu
			new DuelSelectArenaMenu().openMenu(player);
		}

	}

}
