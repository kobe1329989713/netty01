package com.kobe.io;

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
    public static void main(String[] args) throws Exception {
        //  找到服务器的ip 和端口号。
        System.out.println("连接服务器！");
        Socket socket = new Socket("127.0.0.1", 8000);
        System.out.println("连接服务器成功。");



        // 发送消息给服务器。
        OutputStream outputStream = socket.getOutputStream();
        PrintStream printStream = new  PrintStream(outputStream);
        // 不是打印到控制台了，这是打印服务器了，
//        printStream.println("您好，我是我劳资。发送给服务器的数据。");



        // 从控制台一起往服务器发送消息。
        InputStream inputStream = System.in;
        // 转换流。也叫管道流。它们效率比较快。
        // 流 就是数据。
        InputStreamReader isr = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(isr);
        // 从控制台读取一行数据。
        String s = bufferedReader.readLine();
//        System.out.println("一次读取一行的数据为： " + s);
        // 这样它就可以一起 读取控制台的数据。打印出来。
        String s1;
        while ((s1 = bufferedReader.readLine()) != null) {
            printStream.println(s1);
        }

    }
}
