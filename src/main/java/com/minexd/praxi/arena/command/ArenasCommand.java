package com.minexd.praxi.arena.command;

import com.minexd.praxi.arena.Arena;
import com.minexd.praxi.arena.ArenaType;
import com.minexd.zoot.chat.util.ChatComponentBuilder;
import com.minexd.zoot.util.CC;
import com.minexd.zoot.util.ChatHelper;
import com.qrakn.honcho.command.CommandMeta;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandMeta(label = "arenas", permission = "praxi.admin.arena")
public class ArenasCommand {

	public void execute(Player player) {
		player.sendMessage(CC.GOLD + "Arenas:");

		if (Arena.getArenas().isEmpty()) {
			player.sendMessage(CC.GRAY + "There are no arenas.");
			return;
		}

		for (Arena arena : Arena.getArenas()) {
			if (arena.getType() != ArenaType.DUPLICATE) {
				ChatComponentBuilder builder = new ChatComponentBuilder("")
						.parse("&7- " + (arena.isSetup() ? "&a" : "&c") + arena.getName() +
						       "&7(" + arena.getType().name() + ") ");

				ChatComponentBuilder status = new ChatComponentBuilder("").parse("&7[&6STATUS&7]");
				status.attachToEachPart(ChatHelper.hover("&6Click to view this arena's status."));
				status.attachToEachPart(ChatHelper.click("/arena status " + arena.getName()));

				builder.append(" ");

				for (BaseComponent component : status.create()) {
					builder.append((TextComponent) component);
				}

				player.spigot().sendMessage(builder.create());
			}
		}
	}

}
