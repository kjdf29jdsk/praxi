package com.minexd.praxi.match.task;

import com.minexd.praxi.Locale;
import com.minexd.praxi.profile.Profile;
import com.minexd.praxi.profile.ProfileState;
import com.minexd.praxi.Praxi;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class MatchPearlCooldownTask extends BukkitRunnable {

	@Override
	public void run() {
		for (Player player : Praxi.get().getServer().getOnlinePlayers()) {
			Profile profile = Profile.getByUuid(player.getUniqueId());

			if (profile.getState() == ProfileState.FIGHTING || profile.getState() == ProfileState.EVENT) {
				if (profile.getEnderpearlCooldown().hasExpired()) {
					if (!profile.getEnderpearlCooldown().isNotified()) {
						profile.getEnderpearlCooldown().setNotified(true);
						player.sendMessage(Locale.MATCH_ENDERPEARL_COOLDOWN_EXPIRED.format());
					}
				} else {
					int seconds = Math.round(profile.getEnderpearlCooldown().getRemaining()) / 1_000;

					player.setLevel(seconds);
					player.setExp(profile.getEnderpearlCooldown().getRemaining() / 16_000.0F);
				}
			} else {
				if (player.getLevel() > 0) {
					player.setLevel(0);
				}

				if (player.getExp() > 0.0F) {
					player.setExp(0.0F);
				}
			}
		}
	}

}
