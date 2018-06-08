package us.skidrevenant.azure.check.checks.misc.badpackets;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInKeepAlive;
import us.skidrevenant.azure.check.Check;
import us.skidrevenant.azure.stats.PlayerStats;

/**
 * @author SkidRevenant
 * at 30/05/2018
 */
public class BadPacketsG extends Check {
    public BadPacketsG(PlayerStats player) {
        super(player);
    }

    @Override
    public void onPacketReceiving(Class<? extends Packet> type, Packet packet) {
        if (player.getBukkitPlayer().isDead()) {
            return;
        }

        if (type == PacketPlayInKeepAlive.class && player.getLastMovePacket() + 550L < System.currentTimeMillis()) {
            onViolation("BadPackets Type G", 60000L, 0);
        }
    }
}
