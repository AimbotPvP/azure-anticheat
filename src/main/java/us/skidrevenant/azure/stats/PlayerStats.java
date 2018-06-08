package us.skidrevenant.azure.stats;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.entity.Player;
import us.skidrevenant.azure.check.CheckManager;
import us.skidrevenant.azure.util.entity.NMSUtil;
import us.skidrevenant.azure.velocity.Velocity;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author SkidRevenant
 * at 28/05/2018
 */
@Getter
public class PlayerStats {

    private final Player bukkitPlayer;
    private final CheckManager checkManager;

    private final Deque<Velocity> velocityDeque = new LinkedList<>();

    @Setter
    private double horizontalVelocity;

    private long swingDelay, lastSwing;
    private long lastMovePacket;

    private boolean digging;
    private boolean inventoryOpen;

    public PlayerStats(Player bukkitPlayer) {
        this.bukkitPlayer = bukkitPlayer;

        (checkManager = new CheckManager(this)).start();
    }

    public boolean processIncomingPacket(Packet packet) {
        Class<? extends Packet> type = packet.getClass();
        
        if (packet instanceof PacketPlayInFlying) {
            swingDelay += 50L;
            lastMovePacket = System.currentTimeMillis();

            velocityDeque.removeIf(velocity -> velocity.getCreationTime() + 2000L < System.currentTimeMillis());
        } else if (type == PacketPlayInArmAnimation.class) {
            swingDelay = 0L;

            lastSwing = System.currentTimeMillis();
        } else if (type == PacketPlayInBlockDig.class) {
            switch (((PacketPlayInBlockDig) packet).c()) {
                case START_DESTROY_BLOCK:
                    digging = true;
                    break;

                case STOP_DESTROY_BLOCK:
                case ABORT_DESTROY_BLOCK:
                    digging = false;
                    break;
            }
        } else if (type == PacketPlayInClientCommand.class) {
            if (((PacketPlayInClientCommand)packet).a() == PacketPlayInClientCommand.EnumClientCommand.OPEN_INVENTORY_ACHIEVEMENT) {
                inventoryOpen = true;
            }
        } else if (type == PacketPlayInCloseWindow.class) {
            inventoryOpen = false;
        }

        checkManager.getChecks().forEach(check -> check.onPacketReceiving(type, packet));
        return true;
    }

    public boolean processOutgoingPacket(Packet packet) {
        checkManager.getChecks().forEach(check -> check.onPacketSend(packet.getClass(), packet));
        return true;
    }

    public double decreaseVelocity(double decrement) {
        return horizontalVelocity = Math.max(0, horizontalVelocity - decrement);
    }

    public boolean isOnGround() {
        return NMSUtil.getNmsPlayer(bukkitPlayer).onGround;
    }

    public int getTick() {
        return MinecraftServer.currentTick;
    }

}
