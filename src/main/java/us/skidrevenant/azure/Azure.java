package us.skidrevenant.azure;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import us.skidrevenant.azure.listener.BukkitListener;
import us.skidrevenant.azure.stats.PlayerStats;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author SkidRevenant
 * at 28/05/2018
 */
public class Azure extends JavaPlugin {

    private final Map<Player, PlayerStats> statsMap = new WeakHashMap<>();

    public static Azure INST;

    @Override
    public void onEnable() {
        INST = this;

        new BukkitListener();
    }

    @Override
    public void onDisable() {
        statsMap.clear();
    }

    /**
     * Gets the PlayerStats instance of {@param player}
     *
     * @param player The player to get the stats of
     * @return The PlayerStats instance of {@param player}
     */
    public PlayerStats getStats(Player player) {
        return statsMap.computeIfAbsent(player, PlayerStats::new);
    }
}
