package com.frame.example.concurrent.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 异步IO实现
 * @author wangzhilong
 * 2016年6月30日上午11:02:26
 */
public class AIOEchoServer {
	
	public final static int PORT = 8888; 
	private AsynchronousServerSocketChannel server ; //异步IO通道
	public AIOEchoServer() throws IOException{
		server = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(PORT)) ;  //绑定端口8888，并使用异步channel作为服务器
	}
	
	public void start() throws Exception{
		System.out.println("server listen on "+ PORT);
		//注册事件和事件完成后的处理器
		 //此方法会立即返回，它并不会真的去等待客户端的到来
		//accept两个参数 第一个是附件 可以是任何类型 
		//作用让当前线程和后续的回调方法可以共享信息，后续调用中传递给handler
		//completionHandler接口，有两个方法，分别在异步操作accept()成功或失败时被回调
		server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() { 
			final ByteBuffer buffer = ByteBuffer.allocate(1024) ;
			public void completed(AsynchronousSocketChannel result , Object attachment){ //执行时，说明已经有客户端连接成功了
				System.out.println(Thread.currentThread().getName());
				Future<Integer> writeResult = null ;  //Future模式
				try {
					buffer.clear() ;
					result.read(buffer).get(100, TimeUnit.SECONDS) ;
					buffer.flip();
					writeResult = result.write(buffer) ;
				} catch (Exception e) {
					e.printStackTrace();
				} finally{
					try {
						server.accept(null,this);
						writeResult.get();  //get进行等待，确保write操作已完成
						result.close();
					} catch (Exception e) {
						System.out.println(e.toString());
					}
				}
			}
			public void failed(Throwable exc, Object attachment){
				System.err.println("failed:"+exc);
			}
		});
	}
	
	public static void main(String[] args) throws Exception {
		AIOEchoServer server = new AIOEchoServer() ;
		server.start(); //开启服务器，由于start()方法使用的都是异步方法，因此它会马上返回，他并不像阻塞方法那样会进行等待
		//循环方法让程序驻守执行
		while(true){
			System.out.println("主线程跑我的……");
			Thread.sleep(5000);
		}
	}
}
