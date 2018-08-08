package com.kobe.io;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;

/**
 * <B>作者：</B>kobe<BR>
 * <B>时间：</B>2018/08/08/ 20:46 星期三<BR>
 *
 * <B>系统名称：</B>netty01<BR>
 * <B>概要说明：</B>netty01<BR>
 */
public class Server {
    public static void main(String[] args) throws Exception {
        // 11111111
        System.out.println("创建好了服务器。");
        ServerSocket serverSocket = new ServerSocket(8000);
        System.out.println("等待客户端连接。");
        // 打开socket
        // 没有客户端与它连接它会阻塞在这里面的，下面的输出语句是不会打印的。
        Socket socket = serverSocket.accept();
        System.out.println("客户端与服务器连接成功…");


        // 2222222222
        // 接收客户端发来的消息。
        InputStream inputStream = socket.getInputStream();
        // 字节流与字符流转换。
        // 把字节流转换成字符流，操作快。
        // InputStreamReader 这是转换流。
        InputStreamReader isr = new InputStreamReader(inputStream);
        // 在转换成 缓冲流。
        BufferedReader bufferedReader = new BufferedReader(isr);
        // 读取客户端发来的信息。
//        String s = bufferedReader.readLine();
//        System.out.println("来自客户端的信息：“》》》" + s);






        // 服务器一直接收客户端发来的消息。
        String s;
        while ((s=bufferedReader.readLine())!=null) {
            System.out.println("客户端发来的消息为：" + s);
        }


    }
}
