package us.skidrevenant.azure.check.checks.misc.badpackets;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;
import us.skidrevenant.azure.check.Check;
import us.skidrevenant.azure.stats.PlayerStats;

/**
 * @author SkidRevenant
 * at 30/05/2018
 */
public class BadPacketsC extends Check {

    private long lastDig;

    public BadPacketsC(PlayerStats player) {
        super(player);
    }

    @Override
    public void onPacketReceiving(Class<? extends Packet> type, Packet packet) {
        if (type == PacketPlayInBlockDig.class) {
            PacketPlayInBlockDig blockDig = (PacketPlayInBlockDig) packet;

            if (blockDig.c() == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK) {
                long now = System.currentTimeMillis();

                if (lastDig + 20L > now) {
                    onViolation("BadPackets Type C", 60000L, 0);
                }

                lastDig = now;
            }
        }
    }
}
