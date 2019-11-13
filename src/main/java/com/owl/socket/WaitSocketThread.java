package com.owl.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/11/12.
 */
public class WaitSocketThread {

    public void innit(int port) throws IOException {
        //socketServer
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //非阻塞
        serverSocketChannel.configureBlocking(false);
        //build port
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            if (selector.select() == 0) {
                continue;
            }
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while(iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if(key.isAcceptable()){
                    System.out.println(key.toString());
//                    SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();
//                    socketChannel.configureBlocking(false);
//                    socketChannel.register(selector, SelectionKey.OP_ACCEPT);
                }
            }
        }
    }

}
