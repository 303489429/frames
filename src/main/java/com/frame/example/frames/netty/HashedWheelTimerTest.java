package com.frame.example.frames.netty;

import io.netty.util.HashedWheelTimer;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @author wangzhilong
 * @date 2019-04-02
 */
public class HashedWheelTimerTest {

    @Test
    public void test() throws InterruptedException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //默认512个格子，每个格子100毫秒
        HashedWheelTimer hashedWheelTimer = new HashedWheelTimer(100, TimeUnit.MILLISECONDS);

        System.out.println("start:" + LocalDateTime.now().format(formatter));

        hashedWheelTimer.newTimeout(timeout -> {
            System.out.println("task:" + LocalDateTime.now().format(formatter));
        }, 3, TimeUnit.SECONDS);

        Thread.sleep(10000);
    }




}
