package us.skidrevenant.azure.check.checks.misc.badpackets;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;
import us.skidrevenant.azure.check.Check;
import us.skidrevenant.azure.stats.PlayerStats;

/**
 * @author SkidRevenant
 * at 02/06/2018
 */
public class BadPacketsJ extends Check {

    private long lastStop;

    public BadPacketsJ(PlayerStats player) {
        super(player);
    }

    @Override
    public void onPacketReceiving(Class<? extends Packet> type, Packet packet) {
        if (type == PacketPlayInBlockDig.class) {
            PacketPlayInBlockDig blockDig = (PacketPlayInBlockDig) packet;

            long now = System.currentTimeMillis();

            switch (blockDig.c()) {
                case START_DESTROY_BLOCK:
                    if (lastStop + 50L > now) {
                        onViolation("BadPackets Type J", 60000L, 0);
                    }
                    break;

                case STOP_DESTROY_BLOCK:
                    lastStop = now;
                    break;
            }
        }
    }
}
