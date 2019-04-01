package com.frame.example.concurrent.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AIOClient {
	
	public static void main(String[] args) throws Exception{
		final AsynchronousSocketChannel client = AsynchronousSocketChannel.open() ; 
		client.connect(new InetSocketAddress("localhost", 8888), null, new CompletionHandler<Void, Object>() {
			 public void completed(Void result, Object attachment){
				client.write(ByteBuffer.wrap("hello!".getBytes()),null, new CompletionHandler<Integer, Object>() {

					public void completed(Integer result, Object attachment) {
						try {
							ByteBuffer buffer = ByteBuffer.allocate(1024);
							client.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {

								public void completed(Integer result,ByteBuffer buffer) {
									buffer.flip() ;
									System.out.println(new String(buffer.array()));
									try {
										client.close();
									} catch (IOException e) {
										e.printStackTrace();
									}
								}

								public void failed(Throwable exc,ByteBuffer buffer) {
									
								}
								
							});
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					public void failed(Throwable exc, Object attachment) {
						
					}
					
				});
			 }
			 public void failed(Throwable exc, Object attachment){
				 
			 }
		});
		
		//由于主线程马上结束，这里等待上述处理全部完成
		Thread.sleep(1000);
	}
}
