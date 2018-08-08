package com.kobe.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * <B>作者：</B>kobe<BR>
 * <B>时间：</B>2018/08/09/ 0:24 星期四<BR>
 *
 * <B>系统名称：</B>netty01<BR>
 * <B>概要说明：</B>netty01<BR>
 */
public class NIOServer {
    public static void main(String[] args) throws Exception {
        NIOServer nioServer = new NIOServer();
        nioServer.initServer(8000);
        nioServer.listen();
    }

    private Selector selector;

    /**
     * 获取一个 ServerSocket 通道，并对该通道做一些初始化的功能。
     * @throws Exception
     */
    public void initServer(int port) throws Exception {

        // 创建一个NIO通道，获取一个serverSocket 通道。
        ServerSocketChannel socketChannel = ServerSocketChannel.open();

        // 设置该通道为非阻塞，设置为true 就有异常。
        socketChannel.configureBlocking(false);

        // 创建基于NIO 通道的socket连接。
        ServerSocket serverSocket = socketChannel.socket();

        // 新建socket 通道的端口。将该通道对应的ServerSocket 邦宝到port端口。
        serverSocket.bind(new InetSocketAddress(port));

        // 获取一个通道管理器。
        selector = Selector.open();

        // 将该通道管理器和通道绑定，并注册这个事件 SelectionKey.OP_ACCEPT
        // 当该事件到达的时候，selector.select() 会返回。
        // 如果该事件没有到达 selector.select() 就会一起阻塞。
        // 将NIO 通道绑定到选择器，绑定后分配的主件为 selectionKey
        SelectionKey socket = socketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }



    /**
     * 采用轮询的方式监听selector 是否需要处理事件，如果有，就进行处理
     * 轮询：由cpu 定时发出询问，依次询问每一个周边设置是否需要服务。有即给服务，服务结束后再问下一个。
     */
    public void listen() throws Exception {
        System.out.println("服务器启动成功！");
        // 轮询访问selector
        while (true) {
            // 当注册的事件到达时，方法返回。否则方法就会一起阻塞。
            // 获取通道内是否需要有选择器的服务事件。
            int num = selector.select();
            if (num < 1) {
                continue;
            }

            /**
             * 获取通道服务事件的集合。
             * 一个新顾客的事件，一个是老顾客(前面已经有对应的服务员)
             */
            Set selectionKeys = selector.selectedKeys();
            Iterator it = selectionKeys.iterator();
            while (it.hasNext()) {
                // 得到该顾客具体的key
                SelectionKey key = (SelectionKey) it.next();
                // 删除已经处理事件，以防止重复处理。
                it.remove();

                //
                handler(key);
            }
        }
    }


    /**
     * 处理连接请求，是老顾客还是新顾客
     */
    public void handler(SelectionKey key) throws Exception {
        // 判断是否是新顾客，(测试此密钥的通道是否已经准备好接爱新的套接字连接。)
        if (key.isAcceptable()) {
            handlerAccept(key);
        } else if (key.isReadable()) {
            // isReadable()
            // 测试此密钥的频道是否可以阅读。就是老顾客的处理。
            handlerRead(key);
        }
    }
    // 老顾客处理。
    private void handlerRead(SelectionKey key) throws IOException {
        // 服务器可读取客户端信息。得到事件发送的Socket 通道。
        // 读取数据，接收顾客的信息。
        SocketChannel socketChannel = (SocketChannel) key.channel();

        // 创建读取的缓冲区，一次能接收字节。相当于我们前面的 byte 数组存储读取数据。
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 读取字节往ByteBuffer 里面读，read 长度个数。
        int read = socketChannel.read(buffer);
        if (read > 0) {
            // 把buffer 变成字节数组。
            byte[] data = buffer.array();
            String msg = new String(data).trim();
            System.out.println("服务器接收至今自为：" + msg);

            // 给客户端回写数据。
            ByteBuffer outBuffer = ByteBuffer.wrap("好的".getBytes());
            socketChannel.write(outBuffer);
        } else {
            System.out.println("客户端关闭。");
            key.cancel();
        }
    }

    // 新顾客的处理。
    public void handlerAccept(SelectionKey key) throws Exception {
        // 新顾客就需要从新注册下。
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        // 获取与客户端连接的通道。
        SocketChannel socketChannel = serverSocketChannel.accept();
        // 设置非阻塞。
        socketChannel.configureBlocking(false);
        // 可以给客户端发送信息。
        System.out.println("新的客户端连接。");
        // 在和客户端连接成功后，为了接收到客户端的信息，需要给通道设置权限
        SelectionKey newKey = socketChannel.register(selector, SelectionKey.OP_READ);
    }




}



















