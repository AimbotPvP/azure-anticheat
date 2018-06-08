package us.skidrevenant.azure.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.util.Vector;
import us.skidrevenant.azure.Azure;
import us.skidrevenant.azure.packet.PacketHandler;
import us.skidrevenant.azure.stats.PlayerStats;
import us.skidrevenant.azure.util.entity.NMSUtil;
import us.skidrevenant.azure.velocity.Velocity;

/**
 * @author SkidRevenant
 * at 28/05/2018
 */
public class BukkitListener implements Listener {

    public BukkitListener() {
        Azure.INST.getServer().getPluginManager().registerEvents(this, Azure.INST);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        NMSUtil.getNmsPlayer(player).playerConnection.networkManager.channel.pipeline()
                .addBefore("packet_handler", "azure_packet_handler", new PacketHandler(Azure.INST.getStats(player)));
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Location from = event.getFrom(), to = event.getTo();

        Player player = event.getPlayer();

        if (from.distance(to) == 0) {
            return;
        }

        Azure.INST.getStats(player).getCheckManager().getChecks().forEach(check -> check.onMove(from, to, event));
    }

    @EventHandler
    public void onVelocity(PlayerVelocityEvent event) {
        Vector velocity = event.getVelocity();

        double x = velocity.getX();
        double y = velocity.getY();
        double z = velocity.getZ();

        double horizontal = Math.sqrt(x * x + z * z);
        double vertical = Math.abs(y);

        PlayerStats playerStats = Azure.INST.getStats(event.getPlayer());

        playerStats.setHorizontalVelocity(horizontal);

        playerStats.getVelocityDeque().addLast(new Velocity(horizontal, vertical));
    }
}
