package us.skidrevenant.azure.util.math;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import us.skidrevenant.azure.util.math.shapes.Vec3;

/**
 * @author Minecraft
 */
public class MovingObjectPosition {
    private Location blockPos;

    public enum EnumFacing {
        DOWN, UP, NORTH, SOUTH, WEST, EAST
    }

    /** What type of ray trace hit was this? 0 = block, 1 = entity */
    public MovingObjectType typeOfHit;
    public EnumFacing sideHit;

    /** The vector position of the hit */
    public Vec3 hitVec;

    /** The hit entity */
    public Entity entityHit;

    public MovingObjectPosition(Player player, Vec3 hitVecIn, EnumFacing facing, Location blockPosIn) {
        this(player, MovingObjectType.BLOCK, hitVecIn, facing, blockPosIn);
    }

    public MovingObjectPosition(Player player, Vec3 p_i45552_1_, EnumFacing facing) {
        this(player, MovingObjectType.BLOCK, p_i45552_1_, facing, new Location(null, 0, 0, 0));
    }

    public MovingObjectPosition(Entity p_i2304_1_) {
        this(p_i2304_1_, new Vec3(p_i2304_1_.getLocation().getX(), p_i2304_1_.getLocation().getY(), p_i2304_1_.getLocation().getZ()));
    }

    public MovingObjectPosition(Player player, MovingObjectType typeOfHitIn, Vec3 hitVecIn, EnumFacing sideHitIn, Location blockPosIn) {
        this.typeOfHit = typeOfHitIn;
        this.blockPos = blockPosIn;
        this.sideHit = sideHitIn;
        this.hitVec = new Vec3(hitVecIn.xCoord, hitVecIn.yCoord, hitVecIn.zCoord);
        double closest = 0.5;

        for (Entity e : blockPosIn.getWorld().getEntities()) {
            if (!e.equals(player.getPlayer())) {
                double distance = getDistance(e.getLocation().getX(), e.getLocation().getZ(), hitVec.xCoord, hitVec.zCoord);
                if (Math.abs(e.getLocation().getY() - hitVec.yCoord) < 2.1) {
                    if (distance < closest) {
                        closest = distance;
                        this.entityHit = e;
                    }
                }
            }
        }

        if (entityHit != null) {
            this.typeOfHit = MovingObjectType.ENTITY;
            this.hitVec = new Vec3(entityHit.getLocation().getX(), entityHit.getLocation().getY(), entityHit.getLocation().getZ());
        }
    }

    public MovingObjectPosition(Entity entityHitIn, Vec3 hitVecIn) {
        this.typeOfHit = MovingObjectType.ENTITY;
        this.entityHit = entityHitIn;
        this.hitVec = hitVecIn;
    }

    public Location getBlockPos() {
        return this.blockPos;
    }

    public String toString() {
        return "HitResult{type=" + this.typeOfHit + ", blockpos=" + this.blockPos + ", f=" + this.sideHit + ", pos=" + this.hitVec + ", entity=" + this.entityHit + '}';
    }

    public enum MovingObjectType {
        MISS,
        BLOCK,
        ENTITY
    }

    public static double getDistance(double p1, double p2, double p3, double p4) {
        double delta1 = p3 - p1;
        double delta2 = p4 - p2;

        return Math.sqrt(delta1 * delta1 + delta2 * delta2);
    }
}
