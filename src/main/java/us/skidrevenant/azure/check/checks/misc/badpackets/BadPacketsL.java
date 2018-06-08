package us.skidrevenant.azure.check.checks.misc.badpackets;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import us.skidrevenant.azure.check.Check;
import us.skidrevenant.azure.stats.PlayerStats;

/**
 * @author SkidRevenant
 * at 05/06/2018
 */
public class BadPacketsL extends Check {
    public BadPacketsL(PlayerStats player) {
        super(player);
    }

    @Override
    public void onPacketReceiving(Class<? extends Packet> type, Packet packet) {
        if (packet instanceof PacketPlayInFlying) {
            PacketPlayInFlying flying = (PacketPlayInFlying) packet;

            if (!flying.h()) {
                return;
            }

            if (Math.abs(flying.e()) > 90) {
                onViolation("BadPackets Type L", 60000L, 0);
            }
        }
    }
}
