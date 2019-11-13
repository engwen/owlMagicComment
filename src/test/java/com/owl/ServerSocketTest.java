package com.owl;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/11/13.
 */
public class ServerSocketTest {

    @Test
    public void start() throws IOException {
        SocketServer socketServer = new SocketServer(8092);
        socketServer.run();
    }
}
