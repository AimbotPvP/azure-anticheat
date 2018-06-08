package us.skidrevenant.azure.check;

import lombok.RequiredArgsConstructor;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;
import us.skidrevenant.azure.stats.PlayerStats;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author SkidRevenant
 * at 28/05/2018
 */
@RequiredArgsConstructor
public abstract class Check {

    public final PlayerStats player;

    private final Deque<Long> violations = new LinkedList<>();

    protected void onViolation(String details, long time, int vl) {
        long now = System.currentTimeMillis();

        //prevents excessive spam
        if (violations.contains(now)) {
            return;
        }

        violations.addLast(now);

        int violations = Math.toIntExact(this.violations.stream()
                .filter(timestamp -> timestamp + time > now)
                .count());

        if (violations > vl) {
            MinecraftServer.getServer().processQueue.add(() ->
            Bukkit.broadcastMessage(String.format("§c(A) §f%s §7was caught using §f%s §c(×%s)", player.getBukkitPlayer().getName(), details, violations)));
        }
    }

    public void onPacketReceiving(Class<? extends Packet> type, Packet packet) {

    }

    public void onPacketSend(Class<? extends Packet> type, Packet packet) {

    }

    public void onMove(Location from, Location to, PlayerMoveEvent event) {

    }
}
