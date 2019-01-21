package com.chat.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;

import java.util.*;

/**
 * @Auther: CQ02
 * @Date: 2018/11/1 16:54
 * @Description:
 */
public class Server {

    private static List<Map<String, SocketChannel>> maps = new LinkedList<Map<String, SocketChannel>>();
    private Map<String, SocketChannel> map = new HashMap<String, SocketChannel>();
    private final static String USER_EXIST = "System message: User exist, please change a name!";
    private final static String USER_NOTEXIST = "System message: User not exist, please change a name!";
    private final static String MESSAGE_FORMAT_ERROR = "System message: Can't send a message to yourself!";

    private HashSet<SocketChannel> socketChannelOnline = new HashSet<SocketChannel>();
    private Charset charset = Charset.forName("UTF-8");
    private Selector selector = null;
    static final int port = 5000;

    private void init() {
        try {
            selector = Selector.open();
            //服务器套接字实例
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //绑定端口号
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            //设置为非阻塞
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Server is listening ...");
            while (true) {
                int readyChannels = selector.select();
                if (readyChannels == 0) {
                    continue;
                }
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey selectionKey = keyIterator.next();
                    keyIterator.remove();
                    dealWithSelectionKey(serverSocketChannel, selectionKey);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void dealWithSelectionKey(ServerSocketChannel server, SelectionKey selectionKey)
            throws IOException {
        if (selectionKey.isAcceptable()) {
            SocketChannel sc = server.accept();
            sc.configureBlocking(false);
            sc.register(selector, SelectionKey.OP_READ);
            selectionKey.interestOps(SelectionKey.OP_ACCEPT);
            System.out.println("Server is listening from client :"
                    + sc.socket().getRemoteSocketAddress());
            sc.write(charset.encode("please input your name:"));
        }

        System.out.println(selectionKey.isReadable());
        if (selectionKey.isReadable()) {
            SocketChannel sc = (SocketChannel) selectionKey.channel();
            StringBuilder content = new StringBuilder();
            int capacity = 1024;
            ByteBuffer buff = ByteBuffer.allocate(capacity);
            try {
                while (sc.read(buff) > 0) {
                    buff.flip();
                    content.append(charset.decode(buff));

                }
                System.out.println("Server is listening from client "
                        + sc.socket().getRemoteSocketAddress()
                        + " data rev is: " + content);
                selectionKey.interestOps(SelectionKey.OP_READ);
            } catch (IOException io) {
                selectionKey.cancel();
                if (selectionKey.channel() != null) {
                    selectionKey.channel().close();
                }
            }
            if (content.length() > 0) {
                //注册用户
                if (content.indexOf(":") == -1) {
                    String name = content.toString();
                    if (cheackOut(name)) {
                        sc.write(charset.encode(USER_EXIST));
                    } else {
                        map.put(name, sc);
                        maps.add(map);
                        int num = OnlineNum(selector);
                        // 这里模拟注册完了自动登录
                        socketChannelOnline.add(sc);
                        String message = "welcome " + name
                                + " to chat room! Online numbers:" + num;
                        BroadcastToAllClient(selector, message);
                    }
                } else if (content.indexOf(":") == 0) {
                    // 发送消息给除自己以外的所有用户
                    sendToOthersClient(selector, sc, content.substring(1));
                } else {
                    String[] arrayContent = content.toString().split(":");
                    String[] arrayName = arrayContent[0].toString().split("to");
                    String oneself = arrayName[0];
                    String target = arrayName[1];
                    // 发送消息给特定用户
                    if (arrayContent != null && arrayContent[0] != null) {
                        String message = arrayContent[1];
                        message = oneself + " say: " + message;
                        if (cheackOut(target)) {
                            if (!oneself.equals(target)) {
                                SendToSpecificClient(selector, target, message);
                            } else {
                                sc.write(charset.encode(MESSAGE_FORMAT_ERROR));
                            }
                        } else {
                            sc.write(charset.encode(USER_NOTEXIST));
                        }

                    }
                }

            }
        }
    }


    /**
     * 检测用户是否存在
     *
     * @param name
     * @return
     */
    public boolean cheackOut(String name) {
        boolean isExit = false;
        for (int i = 0; i < maps.size(); i++) {
            if (maps.get(i).containsKey(name)) {
                isExit = true;
                break;
            }
        }
        return isExit;
    }

    // 统计在线人数
    public static int OnlineNum(Selector selector) {
        int res = 0;
        for (SelectionKey key : selector.keys()) {
            Channel targetchannel = key.channel();
            if (targetchannel instanceof SocketChannel) {
                res++;
            }
        }
        return res;
    }


    /**
     * 发送给特定的用户
     *
     * @param selector
     * @param name
     * @param content
     * @throws IOException
     */
    public void SendToSpecificClient(Selector selector, String name,
                                     String content) throws IOException {

        SocketChannel desChannel = null;
        for (int i = 0; i < maps.size(); i++) {
            if (maps.get(i).get(name) != null) {
                desChannel = maps.get(i).get(name);
                break;
            }

        }
        for (SelectionKey key : selector.keys()) {
            Channel targetchannel = key.channel();
            if (targetchannel instanceof SocketChannel) {
                if (desChannel == null || desChannel.equals(targetchannel)) {
                    SocketChannel dest = (SocketChannel) targetchannel;
                    dest.write(charset.encode(content));
                }
            }

        }

    }


    /**
     * 广播给所有用户
     *
     * @param selector
     * @param content
     * @throws IOException
     */
    public void BroadcastToAllClient(Selector selector, String content)
            throws IOException {

        for (SelectionKey key : selector.keys()) {
            Channel targetchannel = key.channel();
            if (targetchannel instanceof SocketChannel) {
                SocketChannel dest = (SocketChannel) targetchannel;
                dest.write(charset.encode(content));
            }

        }

    }

    /**
     * 发送给除自己以外的所有用户
     *
     * @param selector
     * @param content
     * @throws IOException
     */
    public void sendToOthersClient(Selector selector, SocketChannel oneself,
                                   String content) throws IOException {

        for (SelectionKey key : selector.keys()) {
            Channel targetchannel = key.channel();
            if (targetchannel instanceof SocketChannel
                    && targetchannel != oneself) {
                SocketChannel dest = (SocketChannel) targetchannel;
                dest.write(charset.encode(content));
            }

        }

    }

    public static void main(String[] args) throws IOException {
        new Server().init();
    }


}
