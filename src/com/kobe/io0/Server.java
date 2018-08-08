package com.kobe.io0;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <B>作者：</B>kobe<BR>
 * <B>时间：</B>2018/08/08/ 20:46 星期三<BR>
 *
 * <B>系统名称：</B>netty01<BR>
 * <B>概要说明：</B>netty01<BR>
 */
public class Server {

    private static Socket socket;

    public static void main(String[] args) throws Exception {
        // 11111111
        System.out.println("创建好了服务器。");
        ServerSocket serverSocket = new ServerSocket(8000);
        System.out.println("等待客户端连接。");
        // 打开socket
        // 没有客户端与它连接它会阻塞在这里面的，下面的输出语句是不会打印的。
        socket = serverSocket.accept();
        System.out.println("客户端与服务器连接成功…");


        // 创建线程接收 客户端发来的消息。
        ziThread t0 = new ziThread();
        t0.start();



        // 创建线程给客户端发送消息。
        ZiThread2 t2 = new ZiThread2();
        t2.start();

    }

    static class ZiThread2 extends Thread {
        public void run() {
            try {
                sendMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    static class ziThread extends Thread{
        @Override
        public void run() {
            try {
                getMessage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 接收消息的方法。
    public static void getMessage() throws Exception {
        InputStream inputStream = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(isr);
        String s;
        while ((s=bufferedReader.readLine())!=null) {
            System.out.println("客户端发来的消息为：" + s);
        }
        System.out.println("聊天 game over!");
    }



    public static void sendMessage() throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        PrintStream printStream = new  PrintStream(outputStream);
        InputStream inputStream = System.in;
        InputStreamReader isr = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(isr);
        String s = bufferedReader.readLine();
        String s1;
        while ((s1 = bufferedReader.readLine()) != null) {
            if ("kobe".equals(s1)) {
                break;
            }
            printStream.println(s1);
        }
    }



}
