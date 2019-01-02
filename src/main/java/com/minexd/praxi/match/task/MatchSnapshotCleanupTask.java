package com.minexd.praxi.match.task;

import com.minexd.praxi.match.MatchSnapshot;
import org.bukkit.scheduler.BukkitRunnable;

public class MatchSnapshotCleanupTask extends BukkitRunnable {

	@Override
	public void run() {
		MatchSnapshot.getSnapshots().entrySet().removeIf(entry -> {
			return System.currentTimeMillis() - entry.getValue().getCreatedAt() >= 45_000;
		});
	}

}
