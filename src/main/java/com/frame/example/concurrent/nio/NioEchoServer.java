package com.frame.example.concurrent.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NioEchoServer {
	private Selector selector ; //NIO 选择器
	private ExecutorService tp = Executors.newCachedThreadPool() ;
	public static Map<Socket,Long> time_start = new HashMap<Socket,Long>(10240);
	
	private void startServer() throws Exception{
		selector = SelectorProvider.provider().openSelector() ; //通过工厂方法获得一个selector对象的实例
		ServerSocketChannel ssc = ServerSocketChannel.open() ; 
		ssc.configureBlocking(false) ; //将这个SocketChannel 设置为非阻塞模式
		
		InetSocketAddress isa = new InetSocketAddress(InetAddress.getLocalHost(), 8888) ;
//		InetSocketAddress isa = new InetSocketAddress(9000);
		ssc.socket().bind(isa);  //端口绑定
		//将ServerSocketChannel 绑定到selector上，并注册感兴趣的事件为accept
		//这样selector就能为channel服务了，当selector发现ServerSocketChannel有新的客户端连接时，就会通知ServerSocketChannel进行处理。
		SelectionKey acceptKey = ssc.register(selector, SelectionKey.OP_ACCEPT); 
		
		for(;;){  //无穷循环 等待分发网络消息
			selector.select() ; //阻塞方法，如果当前没有任何数据准备好时阻塞
			Set<SelectionKey> readKeys = selector.selectedKeys(); //获取那些准备好的selectionKey,因为selector同时为多个channel服务，因此已经准备就绪的的channel就可能有多个
			Iterator<SelectionKey> i = readKeys.iterator() ; //变量集合，挨个处理
			long e = 0 ;
			while(i.hasNext()){
				SelectionKey sk = (SelectionKey) i.next();
				i.remove();  //处理一个，移除一个避免重复处理
				if(sk.isAcceptable()){ //判断是否处理accepttable状态
					doAccept(sk) ;
				}else if(sk.isValid() && sk.isReadable()){
					if(!time_start.containsKey(((SocketChannel)sk.channel()).socket())){
						time_start.put(((SocketChannel)sk.channel()).socket(), System.currentTimeMillis()) ;
					}
					doRead(sk) ;
				}else if(sk.isValid() && sk.isWritable()){
					doWrite(sk);
					e = System.currentTimeMillis() ;
					long b = time_start.remove(((SocketChannel)sk.channel()).socket()) ;
					System.out.println("spend:"+(e-b)+"ms");
				}
			}
		}
	}
	
	/**
	 * 与客户端建立连接
	 * @param SelectionKey sk 表示一对selector 和 channel的关系、契约
	 */
	private void doAccept(SelectionKey sk){
		ServerSocketChannel server = (ServerSocketChannel)sk.channel() ;
		SocketChannel clientChannel ;
		try {
			clientChannel = server.accept() ; //产生一个新的channel代表这个连接，生成的channel就表示和客户端通信的通道
			clientChannel.configureBlocking(false) ; //非阻塞，也就是要求系统在准备好IO后再通知我们来的线程来读取或者写入
			//注册channel的read事件,这样selector发现这个channel已经准备好读时，就能给线程一个通知
			SelectionKey clientKey = clientChannel.register(selector, SelectionKey.OP_READ) ; 
			//allocate an EchoClient instance and attach it to this selection Key ;
			EchoClient echoClient = new EchoClient() ;  //附加一个EchoClient实例到表示这个连接的SelectionKey上。这样就能在整个连接的处理过程中共享实例。
			clientKey.attach(echoClient) ;
			
			InetAddress clientAddress = clientChannel.socket().getInetAddress() ;
			System.out.println("Accepted connection from "+clientAddress.getHostAddress()+".");
		} catch (IOException e) {
			System.err.println("Failed to accept new client");
			e.printStackTrace();
		}
	}
	
	private void disconnect(SelectionKey sk){
			try {
				SocketChannel channel = (SocketChannel)sk.channel();
				if(channel != null)
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	private void doRead(SelectionKey sk){
		SocketChannel channel = (SocketChannel)sk.channel();
		ByteBuffer bb = ByteBuffer.allocate(8192) ; //分配8K缓冲区
		int len ; 
		
		try {
			len = channel.read(bb) ;
			if(len < 0){
				disconnect(sk);
				return ;
			}
		} catch (IOException e) {
			System.err.println("Failed to read from client.");
			e.printStackTrace();
			disconnect(sk);
			return ;
		}
		bb.flip() ;  //重置缓冲区，为数据处理做准备
		tp.execute(new HandleMsg(sk,bb));  //使用线程池进行数据处理，这样如果数据处理很复杂，就能在单独的线程中进行，而不用阻塞任务派发线程
	}
	
	
	class HandleMsg implements Runnable{
		SelectionKey sk ;
		ByteBuffer bb ;
		public HandleMsg(SelectionKey sk, ByteBuffer bb){
			this.sk = sk ;
			this.bb = bb ;
		}
		
		public void run(){
			EchoClient echoClient = (EchoClient)sk.attachment() ;
			echoClient.enqueue(bb); //简单的将数据压入EchoClient的队列
			//数据处理完后，就可以准备将结果回写到客户端，因此重新注册感兴趣的消息事件
			sk.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE) ; 
			//强迫selector立即返回
			selector.wakeup() ;
		}
	}
	
	private void doWrite(SelectionKey sk){
		SocketChannel channel = (SocketChannel)sk.channel();
		EchoClient echoClient = (EchoClient)sk.attachment() ;
		LinkedList<ByteBuffer> outq = echoClient.getOutputQueue() ;
		ByteBuffer bb = outq.getLast() ;
		try {
			int len = channel.write(bb);
			if(len == -1){
				disconnect(sk);
				return ;
			}
			if(bb.remaining() == 0){
				//数据写出完成后，移除它
				outq.removeLast() ;
			}
		} catch (IOException e) {
			System.err.println("Failed to write to client.");
			e.printStackTrace();
			disconnect(sk);
		}
		if(outq.size() == 0){
			sk.interestOps(SelectionKey.OP_READ) ;
		}
	}
	
	public static void main(String[] args) {
		NioEchoServer server = new NioEchoServer() ;
		try {
			server.startServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
