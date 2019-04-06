package com.frame.example.frames.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author wangzhilong
 * @date 2019-04-02
 */
public class CaffeineTest {

    @Test
    public void test() throws InterruptedException {
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
                .expireAfterAccess(1, TimeUnit.SECONDS)
                .removalListener((key, value, cause) ->
                        System.out.println("remove time,key="+key+",value="+value+" :" + LocalDateTime.now() + ",cause:" + cause.name())
                );
        System.out.println("start:" + LocalDateTime.now());
        Cache<Object, Object> cache = caffeine.build();
        cache.put("wzl",100);

        Object wzl = cache.getIfPresent("wzl");
        System.out.println("get cache value:" + wzl);
        Thread.sleep(10000);
        System.out.println("get cache value:" + wzl);
    }



}
