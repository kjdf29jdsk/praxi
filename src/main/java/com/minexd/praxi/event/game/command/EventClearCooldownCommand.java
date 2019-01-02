package com.minexd.praxi.event.game.command;

import com.minexd.praxi.event.game.EventGame;
import com.minexd.zoot.util.Cooldown;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

@CommandMeta(label = { "event clearcooldown", "event clearcd" }, permission = "praxi.admin.event")
public class EventClearCooldownCommand {

	public void execute(CommandSender sender) {
		EventGame.setCooldown(new Cooldown(0));
		sender.sendMessage(ChatColor.GREEN + "You cleared the event cooldown.");
	}

}
