package com.java8;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

import org.junit.Test;

public class TestNonBlockingNIO {
	@Test
	public void client() throws IOException{
		//建立通道
		SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9999));
		//切换为非阻塞模式
		sChannel.configureBlocking(false);
		//设置 缓冲区buffer
		ByteBuffer buf = ByteBuffer.allocate(1024);
		Scanner scan = new Scanner(System.in);
		while(scan.hasNext()){
			String str = scan.next();
			buf.put(str.getBytes());
			buf.flip();
			sChannel.write(buf);
			buf.clear();
		}
		sChannel.close();
	}
	
	@Test
	public void server() throws IOException{
		ServerSocketChannel ssChannel = ServerSocketChannel .open();
		//切换为 非阻塞
		ssChannel.configureBlocking(false);
		//绑定 socket
		ssChannel.bind(new InetSocketAddress(9999));
		//获取 选择器 selector
		Selector selector = Selector.open();
		//将 ssChannel 通道注册到选择器上  并设置其“监听接收事件”
		ssChannel.register(selector, SelectionKey.OP_ACCEPT);
		while (selector.select() > 0) {
			Iterator<SelectionKey> it = selector.selectedKeys().iterator();
			while(it.hasNext()){
				SelectionKey sk = it.next();
				//判断什么事件准备就绪
				if (sk.isAcceptable()) {
					//"接收就绪"
					SocketChannel sChannel = ssChannel.accept();    
					//切换为 非阻塞
					sChannel.configureBlocking(false);
					//将当前通道 注册到 选择器 并设置其 “监听事件”
					sChannel.register(selector, SelectionKey.OP_READ);
				}else if(sk.isReadable()){   //sk.isReadable()与 SelectionKey.OP_READ 对应
					SocketChannel sChannel = (SocketChannel)sk.channel();  //返回这个 sk 所在的通道
					ByteBuffer buf = ByteBuffer.allocate(1024);
					//int len = 0;
					while(sChannel.read(buf) > 0){
						buf.flip();
						System.out.println(new String(buf.array() , 0 , buf.limit()));
						buf.clear();
					}
				}
				//取消选择键
				it.remove();
			}	
		}
	}
}
