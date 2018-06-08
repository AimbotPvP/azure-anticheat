package us.skidrevenant.azure.check.checks.motion;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;
import us.skidrevenant.azure.check.Check;
import us.skidrevenant.azure.stats.PlayerStats;
import us.skidrevenant.azure.util.entity.NMSUtil;

/**
 * @author SkidRevenant
 * at 28/05/2018
 */
public class Fly extends Check {

    private int airTicks;

    public Fly(PlayerStats player) {
        super(player);
    }

    @Override
    public void onMove(Location from, Location to, PlayerMoveEvent event) {
        if (player.getBukkitPlayer().isFlying() || from.getY() > to.getY()) {
            airTicks = 0;
            return;
        }

        double deltaY = to.getY() - from.getY();

        EntityPlayer nmsPlayer = NMSUtil.getNmsPlayer(player.getBukkitPlayer());

        if (++airTicks == 1 && !nmsPlayer.world.c(nmsPlayer.getBoundingBox().grow(0.1, 0.42 * 0.99, 0.1)) && deltaY < 0.42 * 0.99) {
            onViolation("Fly Type A2", 60000L, 0);
        }

        if (!nmsPlayer.world.c(nmsPlayer.getBoundingBox().grow(1.0, 1.0, 1.0))) {
            if (to.getY() - from.getY() > 0.165) {
                onViolation("Fly Type A", 60000L, 0);
            }
        }
    }
}
