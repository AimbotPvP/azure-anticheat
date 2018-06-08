package us.skidrevenant.azure.check.checks.combat.velocity;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityVelocity;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;
import us.skidrevenant.azure.check.Check;
import us.skidrevenant.azure.stats.PlayerStats;
import us.skidrevenant.azure.util.reflection.ReflectionUtil;

/**
 * @author SkidRevenant
 * at 30/05/2018
 */
public class VelocityA extends Check {

    private double verticalVelocity;

    public VelocityA(PlayerStats player) {
        super(player);
    }

    @Override
    public void onMove(Location from, Location to, PlayerMoveEvent event) {
        if (from.getY() >= to.getY()) {
            return;
        }

        double deltaY = to.getY() - from.getY();

        if (deltaY > 0 && deltaY < 0.42 && deltaY < verticalVelocity * 0.77) {
            long velocity = Math.round(100.0 * deltaY / verticalVelocity);
            verticalVelocity = 0;

            onViolation(String.format("Velocity Type A (%d%%)", velocity), 10000L, 4);
        }
    }

    @Override
    public void onPacketSend(Class<? extends Packet> type, Packet packet) {
        if (type == PacketPlayOutEntityVelocity.class) {
            PacketPlayOutEntityVelocity entityVelocity = (PacketPlayOutEntityVelocity) packet;

            int entityId = ReflectionUtil.getFieldValue(ReflectionUtil.getField(PacketPlayOutEntityVelocity.class, "a", int.class), entityVelocity);

            if (entityId == player.getBukkitPlayer().getEntityId()) {

                if (player.getBukkitPlayer().getLastDamageCause() != null) {
                    switch (player.getBukkitPlayer().getLastDamageCause().getCause()) {
                        case STARVATION:
                        case FIRE_TICK:
                        case DROWNING:
                        case POISON:
                        case WITHER:
                        case THORNS:
                        case FALL:
                        case LAVA:
                        case FIRE:

                            verticalVelocity = 0;
                            return;
                    }
                }

                verticalVelocity = (int) ReflectionUtil.getFieldValue(ReflectionUtil.getField(PacketPlayOutEntityVelocity.class, "c", int.class), entityVelocity) / 8000.0;
            }

        }
    }
}
