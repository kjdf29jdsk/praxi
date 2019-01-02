package com.minexd.praxi.util;

import net.minecraft.server.v1_7_R4.PacketPlayOutEntityDestroy;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketUtil {

    public static void destroy(Player player, int entityId) {
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(entityId);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

}
