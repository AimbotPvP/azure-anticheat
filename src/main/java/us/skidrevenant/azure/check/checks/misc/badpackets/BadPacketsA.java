package us.skidrevenant.azure.check.checks.misc.badpackets;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import us.skidrevenant.azure.check.Check;
import us.skidrevenant.azure.stats.PlayerStats;

/**
 * @author SkidRevenant
 * at 30/05/2018
 */
public class BadPacketsA extends Check {

    private int flyingPacketsInARow;

    public BadPacketsA(PlayerStats player) {
        super(player);
    }

    @Override
    public void onPacketReceiving(Class<? extends Packet> type, Packet packet) {
        if (packet instanceof PacketPlayInFlying) {
            PacketPlayInFlying flying = (PacketPlayInFlying) packet;

            if (!flying.g()) {
                if (++flyingPacketsInARow > 20) { //Vanilla clients will send Player Position once every 20 ticks
                 onViolation("BadPackets Type A", 60000L, 0);
                }
            } else {
                flyingPacketsInARow = 0;
            }
        }
    }
}
