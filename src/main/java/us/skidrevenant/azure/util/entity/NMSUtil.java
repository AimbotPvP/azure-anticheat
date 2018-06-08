package us.skidrevenant.azure.util.entity;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * @author SkidRevenant
 * at 28/05/2018
 */
public class NMSUtil {

    /**
     * Returns the {@link EntityPlayer} instance of {@param player}
     *
     * @param player The player to retrieve the {@link EntityPlayer} instance from
     * @return The {@link EntityPlayer} of {@param player}
     */
    public static EntityPlayer getNmsPlayer(Player player) {
        return ((CraftPlayer) player).getHandle();
    }
}
