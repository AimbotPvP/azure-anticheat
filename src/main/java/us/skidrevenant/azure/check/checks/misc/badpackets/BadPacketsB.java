package us.skidrevenant.azure.check.checks.misc.badpackets;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import us.skidrevenant.azure.check.Check;
import us.skidrevenant.azure.stats.PlayerStats;

/**
 * @author SkidRevenant
 * at 30/05/2018
 */
public class BadPacketsB extends Check {
    public BadPacketsB(PlayerStats player) {
        super(player);
    }

    @Override
    public void onPacketReceiving(Class<? extends Packet> type, Packet packet) {
        if (type == PacketPlayInArmAnimation.class && player.getLastMovePacket() + 20L > System.currentTimeMillis()) {
            onViolation("BadPackets Type B", 60000L, 2);
        }
    }
}
