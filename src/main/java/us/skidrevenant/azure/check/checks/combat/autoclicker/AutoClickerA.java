package us.skidrevenant.azure.check.checks.combat.autoclicker;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import us.skidrevenant.azure.check.Check;
import us.skidrevenant.azure.stats.PlayerStats;

/**
 * @author SkidRevenant
 * at 28/05/2018
 */
public class AutoClickerA extends Check {

    private int threshold;

    public AutoClickerA(PlayerStats player) {
        super(player);
    }

    @Override
    public void onPacketReceiving(Class<? extends Packet> type, Packet packet) {
        if (player.isDigging()) {
            threshold /= 4;
            return;
        }

        if (packet instanceof PacketPlayInFlying) {
            if (player.getSwingDelay() <= 100L) { //no cps drops, proably cheating
                if (++threshold > 100) { //TODO: find appropriate threshold
                    onViolation("AutoClicker Type A", 60000L, 0);

                    threshold = 0;
                }

            } else {
                threshold /= 4;
            }
        }
    }
}
