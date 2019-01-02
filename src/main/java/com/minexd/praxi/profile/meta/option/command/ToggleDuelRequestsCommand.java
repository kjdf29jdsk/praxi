package com.minexd.praxi.profile.meta.option.command;

import com.minexd.praxi.Locale;
import com.minexd.praxi.profile.Profile;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.entity.Player;

@CommandMeta(label = { "toggleduels", "tgr", "tgd" })
public class ToggleDuelRequestsCommand {

    public void execute(Player player) {
        Profile profile = Profile.getByUuid(player.getUniqueId());
        profile.getOptions().receiveDuelRequests(!profile.getOptions().receiveDuelRequests());

        if (profile.getOptions().receiveDuelRequests()) {
            player.sendMessage(Locale.OPTIONS_RECEIVE_DUEL_REQUESTS_ENABLED.format());
        } else {
            player.sendMessage(Locale.OPTIONS_RECEIVE_DUEL_REQUESTS_DISABLED.format());
        }
    }

}
