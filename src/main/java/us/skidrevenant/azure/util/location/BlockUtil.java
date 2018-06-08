package us.skidrevenant.azure.util.location;

import org.bukkit.World;

/**
 * @author SkidRevenant
 * at 06/06/2018
 */
public class BlockUtil {

    public static boolean isOnGround(double x, double y, double z, int blockX, int blockY, int blockZ, World world) {
        blockY -= y;

        if (isSolid(blockX, blockY, blockZ, world)) {
            return true;
        }

        switch (getFacing(Math.round((Math.abs(x) * 1000) % 1000), Math.round((Math.abs(z) * 1000) % 1000))) {
            case 0:
                if (isSolid(blockX - 1, blockY, blockZ - 1, world)) {
                    return true;
                }

                if (isSolid(blockX, blockY, blockZ - 1, world)) {
                    return true;
                }

                if (isSolid(blockX + 1, blockY, blockZ - 1, world)) {
                    return true;
                }

                break;

            case 1:
                if (isSolid(blockX - 1, blockY, blockZ + 1, world)) {
                    return true;
                }

                if (isSolid(blockX, blockY, blockZ + 1, world)) {
                    return true;
                }

                if (isSolid(blockX + 1, blockY, blockZ + 1, world)) {
                    return true;
                }

                break;

            case 2:
                if (isSolid(blockX + 1, blockY, blockZ - 1, world)) {
                    return true;
                }

                if (isSolid(blockX, blockY, blockZ - 1, world)) {
                    return true;
                }

                if (isSolid(blockX - 1, blockY, blockZ - 1, world)) {
                    return true;
                }

                break;

            case 3:
                if (isSolid(blockX + 1, blockY, blockZ + 1, world)) {
                    return true;
                }

                if (isSolid(blockX, blockY, blockZ + 1, world)) {
                    return true;
                }

                if (isSolid(blockX - 1, blockY, blockZ + 1, world)) {
                    return true;
                }

                break;

            case 4:
                return isSolid(blockX, blockY, blockZ - 1, world);

            case 5:
                return isSolid(blockX, blockY, blockZ + 1, world);

            case 6:
                return isSolid(blockX - 1, blockY, blockZ, world);

            case 7:
                return isSolid(blockX + 1, blockY, blockZ, world);

                default:
                    return false;
        }

        return false;
    }

    private static int getFacing(double x, double z) {
        if (x < 300) {
            if (z < 300) {
                return 0;
            } else if (z > 700) {
                return 1;
            } else {
                return 6;
            }
        } else if (x > 700) {
            if (z < 300) {
                return 2;
            } else if (z > 700) {
                return 3;
            } else {
                return 7;
            }
        } else if (z < 300) {
            return 4;
        } else if (z > 700) {
            return 5;
        }

        return -1;
    }

    private static boolean isSolid(int blockX, int blockY, int blockZ, World world) {
        return world.getBlockAt(blockX, blockY, blockZ).getType().isSolid();
    }
}
