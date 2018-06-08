package us.skidrevenant.azure.check.checks.misc.badpackets;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInHeldItemSlot;
import us.skidrevenant.azure.check.Check;
import us.skidrevenant.azure.stats.PlayerStats;

/**
 * @author SkidRevenant
 * at 05/06/2018
 */
public class BadPacketsM extends Check {
    public BadPacketsM(PlayerStats player) {
        super(player);
    }

    @Override
    public void onPacketReceiving(Class<? extends Packet> type, Packet packet) {
        if (type == PacketPlayInHeldItemSlot.class && player.getLastMovePacket() + 20L > System.currentTimeMillis() && !player.getBukkitPlayer().isDead()) {
            onViolation("BadPackets Type M", 60000L, 0);
        }
    }
}
