package server;

import io.netty.channel.*;
import io.netty.util.concurrent.EventExecutorGroup;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TelnetServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.write("welcome");
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("command :: " + msg);
        String response = "";


        //
        if("list".equals(msg)) {

            File file = FileSystemView.getFileSystemView().getRoots()[0];
            Files.walk(Paths.get(file.getPath()))
                    .filter(Files::isRegularFile)
                    .forEach(System.out::println);
        }

        ChannelFuture f = ctx.write(response);

        if("close".equals(msg)) {
            System.out.println("bye");
            f.addListener(ChannelFutureListener.CLOSE);
        }



    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
