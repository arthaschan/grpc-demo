package com.grpc.demo.proxy.proxy1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by yeyc on 2016/11/21.
 */
public class HexDumpProxy {
    private static final int LOCAL_PORT = 8080;


    public static void main(String[] args) throws Exception {
        System.err.println("Proxying *:" + LOCAL_PORT );

        // Configure the bootstrap.
        EventLoopGroup bossGroup = new NioEventLoopGroup(8);
        EventLoopGroup workerGroup = new NioEventLoopGroup(8);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new HexDumpProxyInitializer())
                    .childOption(ChannelOption.AUTO_READ, false)
                    .bind(LOCAL_PORT).sync().channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}