package us.skidrevenant.azure.check.checks.misc.invtweaks;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import us.skidrevenant.azure.check.Check;
import us.skidrevenant.azure.stats.PlayerStats;

/**
 * @author SkidRevenant
 * at 03/06/2018
 */
public class InvTweaksC extends Check {

    public InvTweaksC(PlayerStats player) {
        super(player);
    }

    @Override
    public void onPacketReceiving(Class<? extends Packet> type, Packet packet) {
        if (type == PacketPlayInArmAnimation.class && player.isInventoryOpen()) {
            onViolation("InvTweaks Type C", 60000L, 2);
        }
    }
}
