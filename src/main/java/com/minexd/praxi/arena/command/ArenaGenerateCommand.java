package com.minexd.praxi.arena.command;

import com.minexd.praxi.Praxi;
import com.minexd.praxi.arena.Arena;
import com.minexd.praxi.arena.ArenaType;
import com.minexd.praxi.arena.generator.ArenaGenerator;
import com.minexd.praxi.arena.generator.Schematic;
import com.minexd.praxi.arena.impl.StandaloneArena;
import com.minexd.zoot.util.CC;
import com.qrakn.honcho.command.CommandMeta;
import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

@CommandMeta(label = "arena generate", permission = "praxi.admin.arena", async = true)
public class ArenaGenerateCommand {

	public void execute(CommandSender sender) {
		File schematicsFolder = new File(Praxi.get().getDataFolder().getPath() + File.separator + "schematics");

		if (!schematicsFolder.exists()) {
			sender.sendMessage(CC.RED + "The schematics folder does not exist.");
			return;
		}

		for (File file : schematicsFolder.listFiles()) {
			if (!file.isDirectory() && file.getName().contains(".schematic")) {
				boolean duplicate = file.getName().endsWith("_duplicate.schematic");

				String name = file.getName()
				                  .replace(".schematic", "")
				                  .replace("_duplicate", "");

				Arena parent = Arena.getByName(name);

				if (parent != null) {
					if (!(parent instanceof StandaloneArena)) {
						System.out.println("Skipping " + name + " because it's not duplicate and an arena with that name already exists.");
						continue;
					}
				}

				new BukkitRunnable() {
					@Override
					public void run() {
						try {
							new ArenaGenerator(name, Bukkit.getWorlds().get(0), new Schematic(file), duplicate ?
									(parent != null ? ArenaType.DUPLICATE : ArenaType.STANDALONE) : ArenaType.SHARED)
									.generate(file, (StandaloneArena) parent);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}.runTask(Praxi.get());
			}
		}

		sender.sendMessage(CC.GREEN + "Generating arenas... See console for details.");
	}

}
