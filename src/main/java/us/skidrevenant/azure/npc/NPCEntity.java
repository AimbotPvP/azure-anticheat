package us.skidrevenant.azure.npc;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * @author SkidRevenant
 * at 01/05/2018
 */
public class NPCEntity extends EntityPlayer {
    public NPCEntity(Player entity, GameProfile gameprofile) {
        super(MinecraftServer.getServer(), (WorldServer) ((CraftPlayer) entity).getHandle().world, gameprofile, new NPCInteractManager(((CraftPlayer) entity).getHandle().world));
        this.collidesWithEntities = false;
    }

    public NPCEntity(Entity entity, GameProfile gameprofile) {
        super(MinecraftServer.getServer(), (WorldServer) entity.world, gameprofile, new NPCInteractManager(entity.world));
        this.collidesWithEntities = false;
    }


}
