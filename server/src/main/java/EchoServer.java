import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        if(args.length != 1 ){
            System.err.println("Usage:" + EchoServer.class.getSimpleName() + " <port>");
        }
        int port = Integer.parseInt(args[0]);
        new EchoServer(port).start();
    }

    private void start() throws Exception{
        final EchoServerHandler handler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup(); // Event Loop Group를 생성
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
            .channel(NioServerSocketChannel.class) // NIO 전송채널 이용
            .localAddress(new InetSocketAddress(port))
            .childHandler(new ChannelInitializer<SocketChannel>() { // Echo서버 핸들러 하나를 채널의 파이프라인으로 추가
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(handler);
                }
            });

            ChannelFuture f= b.bind().sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }


    }

}
