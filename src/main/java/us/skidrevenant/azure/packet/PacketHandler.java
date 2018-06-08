package us.skidrevenant.azure.packet;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import lombok.RequiredArgsConstructor;
import net.minecraft.server.v1_8_R3.Packet;
import us.skidrevenant.azure.stats.PlayerStats;

/**
 * @author SkidRevenant
 * at 28/05/2018
 */
@RequiredArgsConstructor
public class PacketHandler extends ChannelDuplexHandler {

    private final PlayerStats player;

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        try {
            if (!player.processOutgoingPacket((Packet) msg)) {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.write(ctx, msg, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            if (!player.processIncomingPacket((Packet) msg)) {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.channelRead(ctx, msg);
    }

}
