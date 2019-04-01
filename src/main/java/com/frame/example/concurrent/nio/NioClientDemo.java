package com.frame.example.concurrent.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;

public class NioClientDemo {
	
	private Selector selector ;
	
	public void init(String ip,int port) throws IOException{
		SocketChannel channel = SocketChannel.open() ; //创建一个channel实例
		channel.configureBlocking(false); //设置通道为非阻塞
		this.selector = SelectorProvider.provider().openSelector() ;  //通过工厂方法创建一个selector 
		channel.connect(new InetSocketAddress(ip, port)) ;  //由于当前的channel是非阻塞的，因此connect方法返回时，连接并不一定建立成功，在后续使用这个连接时，还需要使用finishConnect()方法
		channel.register(selector, SelectionKey.OP_CONNECT) ;  //注册感兴趣的事件
	}
	
	public void working() throws IOException{
		while(true){
			if(!selector.isOpen()){
				break;
			}
			selector.select() ; //阻塞方法，如果当前没有任何数据准备好时阻塞
			Iterator<SelectionKey> ite = this.selector.selectedKeys().iterator() ;
			while(ite.hasNext()){
				SelectionKey key = ite.next() ;
				ite.remove();
				//连接事件发生
				if(key.isConnectable()){
					connect(key);
				}else if(key.isReadable()){
					read(key);
				}
			}
			
		}
	}
	
	public void connect(SelectionKey key) throws IOException{
		SocketChannel channel = (SocketChannel)key.channel() ;
		//如果正在连接，则完成连接
		if(channel.isConnectionPending()){
			channel.finishConnect() ; //完成连接
		}
		channel.configureBlocking(false);
		channel.write(ByteBuffer.wrap(new String("hello server!\r\n").getBytes()));
		channel.register(this.selector, SelectionKey.OP_READ) ;  //注册读事件为感兴趣的事件
	}
	
	public void read(SelectionKey key) throws IOException{
		SocketChannel channel = (SocketChannel) key.channel() ;
		//创建读取的缓冲区
		ByteBuffer buffer = ByteBuffer.allocate(100) ;
		channel.read(buffer);
		byte[] data = buffer.array() ;
		String msg = new String(data).trim() ;
		System.out.println("客户端收到的消息："+msg);
		channel.close();
		key.selector().close();
	}
	
	public static void main(String[] args) throws IOException {
		NioClientDemo client = new NioClientDemo() ;
		client.init("10.34.43.50", 8888);
		client.working();
	}
	
	
}
