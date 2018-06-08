package us.skidrevenant.azure.check.checks.misc;

import net.minecraft.server.v1_8_R3.*;
import us.skidrevenant.azure.check.Check;
import us.skidrevenant.azure.stats.PlayerStats;
import us.skidrevenant.azure.util.entity.NMSUtil;
import us.skidrevenant.azure.util.math.MathUtil;

/**
 * @author SkidRevenant
 * at 28/05/2018
 */
public class PingSpoof extends Check {

    private boolean sent;

    public PingSpoof(PlayerStats player) {
        super(player);
    }

    @Override
    public void onPacketReceiving(Class<? extends Packet> type, Packet packet) {
        if (packet instanceof PacketPlayInTransaction) {
            sent = false;

            long delay = System.currentTimeMillis() - player.getLastMovePacket();

            int playerPing = NMSUtil.getNmsPlayer(player.getBukkitPlayer()).ping;

            if (delay < 20L) {
                return;
            }

            if (playerPing > (delay + 50L) * 2.75) {
                this.onViolation(String.format("PingSpoof (+%dms)", playerPing - delay), 60000L, 0);
            }

        } else if (packet instanceof PacketPlayInFlying) {
            if (sent) {
                return;
            }

            NMSUtil.getNmsPlayer(player.getBukkitPlayer()).playerConnection.sendPacket(new PacketPlayOutTransaction(0, (short) MathUtil.RANDOM.nextInt(1000), false));
            sent = true;
        }
    }
}
