package us.skidrevenant.azure.check.checks.combat.aura;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import us.skidrevenant.azure.check.Check;
import us.skidrevenant.azure.stats.PlayerStats;

/**
 * @author SkidRevenant
 * at 30/05/2018
 */
public class AuraA extends Check {

    public AuraA(PlayerStats player) {
        super(player);
    }

    @Override
    public void onPacketReceiving(Class<? extends Packet> type, Packet packet) {
        if (type == PacketPlayInUseEntity.class && ((PacketPlayInUseEntity) packet).a() == PacketPlayInUseEntity.EnumEntityUseAction.ATTACK && player.getLastMovePacket() + 20L > System.currentTimeMillis()) {
            onViolation("Aura Type A", 10000L, 6);
        }
    }
}
