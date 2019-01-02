package com.minexd.praxi.match.command;

import com.minexd.praxi.match.MatchSnapshot;
import com.minexd.praxi.match.menu.MatchDetailsMenu;
import com.minexd.zoot.util.CC;
import com.qrakn.honcho.command.CommandMeta;
import java.util.UUID;
import org.bukkit.entity.Player;

@CommandMeta(label = "viewinv")
public class ViewInventoryCommand {

	public void execute(Player player, String id) {
		MatchSnapshot cachedInventory;

		try {
			cachedInventory = MatchSnapshot.getByUuid(UUID.fromString(id));
		} catch (Exception e) {
			cachedInventory = MatchSnapshot.getByName(id);
		}

		if (cachedInventory == null) {
			player.sendMessage(CC.RED + "Couldn't find an inventory for that ID.");
			return;
		}

		new MatchDetailsMenu(cachedInventory).openMenu(player);
	}

}
