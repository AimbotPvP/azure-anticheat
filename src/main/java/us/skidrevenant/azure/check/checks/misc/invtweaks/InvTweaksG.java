package us.skidrevenant.azure.check.checks.misc.invtweaks;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInWindowClick;
import us.skidrevenant.azure.check.Check;
import us.skidrevenant.azure.stats.PlayerStats;

/**
 * @author SkidRevenant
 * at 03/06/2018
 */
public class InvTweaksG extends Check {

    private long lastClick;

    public InvTweaksG(PlayerStats player) {
        super(player);
    }

    @Override
    public void onPacketReceiving(Class<? extends Packet> type, Packet packet) {
        if (type == PacketPlayInWindowClick.class) {
            PacketPlayInWindowClick packetPlayInWindowClick = (PacketPlayInWindowClick) packet;

            if (packetPlayInWindowClick.c() == 0) { //player inventory

                int slot = packetPlayInWindowClick.b();

                if (slot < 9 || slot > 35) { //hotbar, out of inventory
                    return;
                }

                long now = System.currentTimeMillis();

                long delay = now - lastClick;

                if (delay > 51L && delay < 130L) {
                    this.onViolation("InvTweaks Type G", 5000L, 1);
                }

                this.lastClick = System.currentTimeMillis();
            }
        }
    }
}
