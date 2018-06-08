package us.skidrevenant.azure.check.checks.combat.aimassist;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import us.skidrevenant.azure.check.Check;
import us.skidrevenant.azure.stats.PlayerStats;

/**
 * @author SkidRevenant
 * at 28/05/2018
 */
public class AimAssistA extends Check {

    private float previousPitch, previousYaw, pitch, yaw;

    public AimAssistA(PlayerStats player) {
        super(player);
    }

    @Override
    public void onPacketReceiving(Class<? extends Packet> type, Packet packet) {
        if (packet instanceof PacketPlayInFlying) {
            PacketPlayInFlying flying = (PacketPlayInFlying) packet;

            if (!flying.h()) {
                return;
            }

            float pitch = flying.e();
            float yaw = flying.d();
            float pitchChange = Math.abs(pitch - this.pitch);
            float yawChange = Math.abs(yaw - this.yaw);
            float pitchDifference = Math.abs(previousPitch - pitchChange);

            float yawDifference = Math.abs(previousYaw - yawChange);

            //Emulating mouse moves
            if (yawChange > yawDifference && yawDifference > 0.3 && pitchChange > 0 && pitchChange <= pitchDifference && pitchDifference < 0.1) {
                onViolation("Aim Assist Type A", 60000L, 0);
            }

            //Flaws in randomization
            if (yawChange > yawDifference && yawDifference > 0.0 && yawDifference < 0.1 && pitchChange > 0.08) {
                onViolation("Aim Assist Type A2", 1000L, 5);
            }

            //Extremely randomized
            if (yawChange > yawDifference && yawDifference > 0.0 && pitchChange > 0 && pitchChange < 0.02 && pitchDifference > pitchChange * 2) {
                onViolation("Aim Assist Type A3", 60000L, 1);
            }

            //Turning aim assist on and off
            if (yawDifference > 900 && pitchChange > 0 && pitchDifference < 10) {
                onViolation("Aim Assist Type A4", 60000L, 0);
            }

            //Rounded yaw
            if (yawDifference > 0 && Math.abs(Math.floor(yawDifference) - yawDifference) < 0.0000000001) {
                onViolation("Aim Assist Type A5", 60000L, 0);
            }

            this.pitch = pitch;
            this.yaw = yaw;
            previousPitch = pitchChange;
            previousYaw = yawChange;
        }
    }
}
