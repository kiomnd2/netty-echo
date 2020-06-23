package client;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;

public class TelnetClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }



    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.err.println(msg);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
