package com.chat.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @Auther: CQ02
 * @Date: 2018/12/5 13:55
 * @Description:
 */
public class Client {
    private Charset charset = Charset.forName("UTF-8");
    private static final int port = 5000;
    private Selector selector = null;
    private SocketChannel sc = null;

    public void init() throws IOException {
        selector = Selector.open();
        sc = SocketChannel.open(new InetSocketAddress("127.0.0.1", port));
        sc.configureBlocking(false);
        sc.register(selector, SelectionKey.OP_READ);
        new Thread(new ClientThread()).start();

        Scanner scan = new Scanner(System.in);
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            System.out.println(line);
            if ("".equals(line)) {
                continue;
            }
            int capacity = charset.encode(line).capacity();
            String strCapacity = capacity + "";

            // sc既能写也能读，这边是写
            sc.write(charset.encode(line));
        }
    }

    private class ClientThread implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    int readyChannels = selector.select();
                    if (readyChannels == 0) {
                        continue;
                    }
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> keyIterator = selectedKeys
                            .iterator();
                    while (keyIterator.hasNext()) {
                        SelectionKey sk = (SelectionKey) keyIterator.next();
                        keyIterator.remove();
                        dealWithSelectionKey(sk);
                    }
                }
            } catch (IOException io) {
            }
        }

        private void dealWithSelectionKey(SelectionKey sk) throws IOException {

            if (sk.isReadable()) {
                SocketChannel sc = (SocketChannel) sk.channel();
                ByteBuffer buff = ByteBuffer.allocate(1024);
                String content = "";
                while (sc.read(buff) > 0) {
                    buff.flip();
                    content += charset.decode(buff);
                }
                sk.interestOps(SelectionKey.OP_READ);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Client().init();
    }


}


