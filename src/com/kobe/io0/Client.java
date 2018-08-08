package com.kobe.io0;

import java.io.*;
import java.net.Socket;

/**
 * <B>作者：</B>kobe<BR>
 * <B>时间：</B>2018/08/08/ 20:53 星期三<BR>
 *
 * <B>系统名称：</B>netty01<BR>
 * <B>概要说明：</B>netty01<BR>
 */
public class Client {

    private static Socket socket;


    public static void main(String[] args) throws Exception {
        socket = new Socket("127.0.0.1", 8000);
        Client client = new Client();
        // 创建线程发送消息。
        ZiThread t0 = new ZiThread();
        t0.start();


        // 创建线程接收消息。
        ZiThread2 t2 = new ZiThread2();
        t2.start();
    }


    static class ZiThread2 extends   Thread{
        public void run() {
            try {
                getMessage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    static class ZiThread extends   Thread{
        public void run() {
            try {
                sendMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
            printStream.println("来自服务器的消息："+s1);
        }
        System.out.println("聊天 game over!");
    }



    // 接收消息的方法
    public static void getMessage() throws Exception {
        InputStream inputStream = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(isr);
        String s;
        while ((s=bufferedReader.readLine())!=null) {
            System.out.println("客户端发来的消息为：" + s);
        }
    }

}
