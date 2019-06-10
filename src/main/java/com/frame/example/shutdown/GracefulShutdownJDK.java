package com.frame.example.shutdown;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 优雅停机
 *
 * @author wangzhilong
 * @date 2019-04-13
 */
public class GracefulShutdownJDK implements Runnable {

    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
        start();
        System.out.println("应用程序启动成功-----");
        while (true) {

        }

    }

    public static void start() {
        //启动任务
        executorService.submit(new GracefulShutdownJDK());
        executorService.submit(new GracefulShutdownJDK());

        //注册钩子
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("应用程序准备关闭--------");
                stop();
            }
        }));
    }

    @Override
    public void run() {
        int i = 1;
        while (true) {
            try {
                System.out.println("任务运行中---[" + i + "]----" + Thread.currentThread().getName());
                i++;
                if (i >= 5) {
                    break;
                }
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void stop() {
        executorService.shutdown();
    }
}
