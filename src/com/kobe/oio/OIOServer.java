package com.kobe.oio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <B>作者：</B>java自带的连接池
 *
 *   一个服务器多个客户端的demo
 *
 * <BR>
 * <B>时间：</B>2018/08/08/ 23:38 星期三<BR>
 *
 * <B>系统名称：</B>netty01<BR>
 * <B>概要说明：</B>netty01<BR>
 */
public class OIOServer {
    public static void main(String[] args) throws Exception {
        // 创建线程池。注：这里有好几种线程池的。
        ExecutorService executorService = Executors.newCachedThreadPool();

        // 创建服务器端的套接字。
        ServerSocket serverSocket = new ServerSocket(10101);
        System.out.println("启动服务器。");

        // 一个服务器多个客户端的
        while (true) {
            // 获取套接字，等待客户端连接。
            final Socket socket = serverSocket.accept();
            System.out.println("来了一个客户端！");

            // 源源不断的接收客户端连接，来一个请求就开一个线程去处理它
            // 改成 lambda 表达式的
            // 注：
            executorService.execute(() -> {
                try {
                    handler(socket);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
//            executorService.execute(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        handler(socket);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
            // end
        }


    }


    // 该方法，是专门用于接收数据的方法。
    public static void handler(Socket socket) throws Exception {
        // 把接收的信息存入一个字节数组里。
        byte[] bytes = new byte[1024];
        // 从socket 得到流。
        InputStream inputStream = socket.getInputStream();
        while (true) {
            // 然后就可以从流中读取数据。存入到字节数组里
            // read 是：接收信息的字节个数。
            int read = inputStream.read(bytes);
            if (-1 != read) {
                System.out.println("来自客户端：" + new String(bytes, 0, read));
            } else {
                break;
            }
        }
        // 关闭socket
        socket.close();
    }


}
