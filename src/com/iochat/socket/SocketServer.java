package com.iochat.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @Auther: CQ02
 * @Date: 2018/12/5 16:03
 * @Description:
 */
public class SocketServer {

    private void serviceInit() throws IOException {
        int port = 5000;
        ServerSocket server = new ServerSocket(port);

        //如果使用多线程，那就需要线程池，防止并发过高时创建过多线程耗尽资源
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                5,
                10,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r);
                    }
                },
                new ThreadPoolExecutor.AbortPolicy());

        while (true) {
            //等待连接
            Socket socket = server.accept();
            Runnable runnable = () -> {
                try {
                    // 建立好连接后，从socket中获取输入流，并建立缓冲区进行读取
                    InputStream inputStream = socket.getInputStream();
                    byte[] byteBuff = new byte[1024];
                    int len;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((len = inputStream.read(byteBuff)) != -1) {
                        stringBuilder.append(new String(byteBuff, 0, len, "UTF-8"));
                    }
                    System.out.println(stringBuilder.toString());
                    inputStream.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
            threadPool.submit(runnable);
        }

    }

    public static void main(String[] args) {
        try {
            new SocketServer().serviceInit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
