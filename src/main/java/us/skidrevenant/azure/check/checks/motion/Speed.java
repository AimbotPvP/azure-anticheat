package us.skidrevenant.azure.check.checks.motion;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;
import us.skidrevenant.azure.check.Check;
import us.skidrevenant.azure.stats.PlayerStats;
import us.skidrevenant.azure.util.entity.NMSUtil;
import us.skidrevenant.azure.util.location.BlockUtil;
import us.skidrevenant.azure.util.math.MathUtil;

/**
 * @author SkidRevenant
 * at 28/05/2018
 */
public class Speed extends Check {

    private double previousDistance, moveSpeed;
    private double blockFriction = 0.91;
    private int fallTicks, waterTicks;
    private int threshold;

    public Speed(PlayerStats player) {
        super(player);
    }

    @Override
    public void onMove(Location from, Location to, PlayerMoveEvent event) {
        double deltaX = to.getX() - from.getX();
        double deltaY = to.getY() - from.getY();
        double deltaZ = to.getZ() - from.getZ();

        double moveSpeed = 1;

        double blockFriction = this.blockFriction;

        EntityPlayer nmsPlayer = NMSUtil.getNmsPlayer(player.getBukkitPlayer());

        boolean onGround = nmsPlayer.onGround;

        Bukkit.broadcastMessage("" + onGround + " : " + BlockUtil.isOnGround(to.getX(), 0, to.getZ(), to.getBlockX(), to.getBlockY(), to.getBlockZ(), to.getWorld()));

        if (deltaY < 0) {
            ++fallTicks;
        } else {
            fallTicks = 0;
        }

        if (onGround) {
            blockFriction *= 0.91;

            moveSpeed *= blockFriction > 0.708 ? 1.3 : 0.23315;

            moveSpeed *= 0.16277136 / Math.pow(blockFriction, 3);

            if (deltaY > 0) {
                moveSpeed += 0.2;

                if (deltaY < 0.41999998688697815) {
                    moveSpeed *= 0.76;
                }
            } else if (deltaY < 0.0){
                moveSpeed *= 0.2;
            } else {
                moveSpeed -= 0.1;
            }
        } else {
            moveSpeed = 0.026;
            blockFriction = 0.91;

            if (nmsPlayer.inWater) {
                moveSpeed += 1;
            }

            if (fallTicks == 1) {
                double dy = Math.abs(deltaY);
                if (dy > 0.08 || dy < 0.07) {
                    moveSpeed /= (dy * 150);
                }
            }
        }

        double previousHorizontal = previousDistance;

        double horizontalDistance = MathUtil.getMagnitude(deltaX, deltaZ);

        boolean underBlock = isUnderBlock(nmsPlayer);

        if (underBlock) {
            moveSpeed += 0.05;
        }

        moveSpeed += player.decreaseVelocity(horizontalDistance / 6.5);

        double horizontalMove = (horizontalDistance - previousHorizontal) / moveSpeed;

        //TODO: Implement a way better fix for this
        boolean fallFix = moveSpeed > 0.046 && moveSpeed < 0.047 &&
                deltaY == (-0.07840000152587923) ||
                deltaY == (-0.07840000152587834) ||
                deltaY == (-0.078400001525879);

        if (fallFix) {
            horizontalMove *= 0.34;
        }

        if (horizontalDistance > 0.2763 && horizontalMove > 1.0) {
            onViolation("Speed", 65000L, 0);
        }

        previousDistance = horizontalDistance * blockFriction;

        this.blockFriction = nmsPlayer.world.getType(new BlockPosition(nmsPlayer.locX, nmsPlayer.locY, nmsPlayer.locZ)).getBlock().frictionFactor;
    }

    private boolean isUnderBlock(EntityPlayer player) {
        return player.world.c(player.getBoundingBox().grow(0.5, 0, 0.5).a(0.0, 1, 0.0));
    }
}
