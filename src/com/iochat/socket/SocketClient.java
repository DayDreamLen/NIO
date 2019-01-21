package com.iochat.socket;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Auther: CQ02
 * @Date: 2018/12/5 17:00
 * @Description:
 */
public class SocketClient {

    private void clientInit() {
        //为了简单起见，所有的异常都直接往外抛
        String host = "localHost";  //要连接的服务端IP地址
        int port = 5000;   //要连接的服务端对应的监听端口
        Socket client = null;
        try {
            //与服务端建立连接
            client = new Socket(host, port);
            while (true) {
                //建立连接后就可以往服务端写数据了
                OutputStream outputStream = client.getOutputStream();
                Scanner scanner = new Scanner(System.in);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    System.out.println(line);
                    outputStream.write(line.getBytes("UTF-8"));
                    outputStream.flush();
                }
                //写完以后进行读操作
                InputStream inputStream = client.getInputStream();
                byte[] bytes = new byte[1024];
                int len;
                StringBuilder sb = new StringBuilder();
                while ((len = inputStream.read(bytes)) != -1) {
                    //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
                    sb.append(new String(bytes, 0, len, "UTF-8"));
                }
                System.out.println("get message from server: " + sb);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) throws IOException {
        new SocketClient().clientInit();
    }
}
