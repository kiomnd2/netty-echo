package server;

import io.netty.channel.*;
import io.netty.util.concurrent.EventExecutorGroup;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class TelnetServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.write("welcome");
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("command :: " + msg);
        StringBuilder response = new StringBuilder();


        //
        if("list".equals(msg)) {
            File file = FileSystemView.getFileSystemView().getRoots()[0];
            Files.walkFileTree(Paths.get(file.getPath()), new SimpleFileVisitor<Path>(){
                int cnt  = 0 ;
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

                    if(attrs.isRegularFile()) {
                        if(cnt < 10000) {
                            System.out.println(file);
                            response.append(file).append("\n");
                        } else {
                            return FileVisitResult.TERMINATE;
                        }
                        cnt = cnt +1;
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return FileVisitResult.SKIP_SUBTREE;
                }
            });
        }
        System.out.println(response);

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
