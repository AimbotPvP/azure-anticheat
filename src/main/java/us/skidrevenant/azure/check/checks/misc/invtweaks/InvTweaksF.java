package us.skidrevenant.azure.check.checks.misc.invtweaks;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInEntityAction;
import us.skidrevenant.azure.check.Check;
import us.skidrevenant.azure.stats.PlayerStats;

/**
 * @author SkidRevenant
 * at 03/06/2018
 */
public class InvTweaksF extends Check {

    private int threshold;

    public InvTweaksF(PlayerStats player) {
        super(player);
    }

    @Override
    public void onPacketReceiving(Class<? extends Packet> type, Packet packet) {
        if (type == PacketPlayInEntityAction.class) {
            if (player.isInventoryOpen()) {
                if (++threshold > 1) {
                    onViolation("InvTweaks Type F", 60000L, 0);
                }
            } else {
                threshold = 0;
            }
        }
    }
}
