package us.skidrevenant.azure.check.checks.misc.badpackets;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace;
import us.skidrevenant.azure.check.Check;
import us.skidrevenant.azure.stats.PlayerStats;

/**
 * @author SkidRevenant
 * at 05/06/2018
 */
public class BadPacketsK extends Check {
    public BadPacketsK(PlayerStats player) {
        super(player);
    }

    @Override
    public void onPacketReceiving(Class<? extends Packet> type, Packet packet) {
        if (type == PacketPlayInBlockPlace.class) {
            PacketPlayInBlockPlace blockPlace = (PacketPlayInBlockPlace) packet;

            if (blockPlace.getFace() == 255 && blockPlace.a().asLong() >= 0) {
                onViolation("BadPackets Type K", 60000L, 0);
            }
        }
    }
}
