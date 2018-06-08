package us.skidrevenant.azure.check.checks.misc.timer;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayOutPosition;
import us.skidrevenant.azure.check.Check;
import us.skidrevenant.azure.stats.PlayerStats;

/**
 * @author SkidRevenant
 * at 28/05/2018
 */
public class Timer extends Check {

    private long lastFlying;
    private int threshold;

    public Timer(PlayerStats player) {
        super(player);
    }

    @Override
    public void onPacketReceiving(Class<? extends Packet> type, Packet packet) {
        if (packet instanceof PacketPlayInFlying) {
            long now = System.currentTimeMillis();

            if (lastFlying + 47L > now) {
                double timerSpeed = 50.0 / (now - lastFlying);

                if (++threshold > 7) {
                    onViolation(String.format("Timer Type A (%d%s, %dL)", Math.min(Math.round(timerSpeed * 100), 5000), "%", (now - lastFlying)), 60000L, 3);
                }

            } else if (lastFlying + 70L < now) {
                threshold -= 3;
            } else if (threshold > 0) {
                threshold--;
            }

            lastFlying = now;
        }
    }

    @Override
    public void onPacketSend(Class<? extends Packet> type, Packet packet) {
        if (type == PacketPlayOutPosition.class) {
            threshold = -10;
        }
    }
}
