package us.skidrevenant.azure.check.checks.misc.invtweaks;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import us.skidrevenant.azure.check.Check;
import us.skidrevenant.azure.stats.PlayerStats;

/**
 * @author SkidRevenant
 * at 03/06/2018
 */
public class InvTweaksB extends Check {

    private int movesReceived;

    public InvTweaksB(PlayerStats player) {
        super(player);
    }

    @Override
    public void onPacketReceiving(Class<? extends Packet> type, Packet packet) {
        if (packet instanceof PacketPlayInFlying && type != PacketPlayInFlying.class) {

            if (player.getHorizontalVelocity() > 0) {
                this.movesReceived = 0;
                return;
            }

            if (this.player.isInventoryOpen()) {
                if (++movesReceived > 20) {
                    this.onViolation("InvTweaks Type B", 1000L, 1);
                }
            } else {
                movesReceived = 0;
            }
        }
    }
}
