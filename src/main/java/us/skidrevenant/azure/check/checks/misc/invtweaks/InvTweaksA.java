package us.skidrevenant.azure.check.checks.misc.invtweaks;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInCloseWindow;
import us.skidrevenant.azure.check.Check;
import us.skidrevenant.azure.stats.PlayerStats;

/**
 * @author SkidRevenant
 * at 03/06/2018
 */
public class InvTweaksA extends Check {
    public InvTweaksA(PlayerStats player) {
        super(player);
    }

    @Override
    public void onPacketReceiving(Class<? extends Packet> type, Packet packet) {
        if (type == PacketPlayInCloseWindow.class && player.getBukkitPlayer().isSprinting()) {
            onViolation("InvTweaks Type A", 60000L, 0);
        }
    }
}
