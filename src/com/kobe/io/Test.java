package com.kobe.io;

import java.io.*;

/**
 * <B>作者：</B>kobe<BR>
 * <B>时间：</B>2018/08/08/ 21:26 星期三<BR>
 *
 * <B>系统名称：</B>netty01<BR>
 * <B>概要说明：</B>netty01<BR>
 */
public class Test {
    public static void main(String[] args) throws IOException {
//        System.out.println("kobe hello world");

//        PrintStream printStream = System.out;
//        printStream.println("hello world");




        InputStream inputStream = System.in;
        // 转换流。也叫管道流。它们效率比较快。
        // 流 就是数据。
        InputStreamReader isr = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(isr);
        // 从控制台读取一行数据。
//        String s = bufferedReader.readLine();
        // 这样就可以一起打印。
        String s1;
        while ((s1 = bufferedReader.readLine()) != null) {
            System.out.println("一次读取一行的数据为： " + s1);
        }



    }
}
