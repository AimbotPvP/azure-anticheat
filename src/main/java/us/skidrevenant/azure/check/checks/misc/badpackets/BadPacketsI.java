package us.skidrevenant.azure.check.checks.misc.badpackets;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;
import us.skidrevenant.azure.check.Check;
import us.skidrevenant.azure.stats.PlayerStats;

/**
 * @author SkidRevenant
 * at 30/05/2018
 */
public class BadPacketsI extends Check {
    public BadPacketsI(PlayerStats player) {
        super(player);
    }

    @Override
    public void onPacketReceiving(Class<? extends Packet> type, Packet packet) {
        if (type == PacketPlayInBlockDig.class && ((PacketPlayInBlockDig) packet).c() == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK && player.getLastSwing() + 150L < System.currentTimeMillis()) {
            onViolation("BadPackets Type I", 60000L, 0);
        }
    }
}
