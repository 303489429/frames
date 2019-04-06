package com.frame.example.frames.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author wangzhilong
 * @date 2019-04-02
 */
public class CaffeienDemo {

   private static Cache<String, Object> cache ;

   static {
       cache = Caffeine.newBuilder()
               .expireAfterWrite(2, TimeUnit.SECONDS)
               .maximumSize(10)
               .removalListener((key, value, cause) ->
                       System.out.println("remove time,key="+key+",value="+value+" :" + LocalDateTime.now() + ",cause:" + cause.name()))
               .build() ;
   }

    public static void main(String[] args) throws InterruptedException {
        String str = "wzl";
        cache.put(str,28);

        Object obj = cache.getIfPresent(str);
        System.out.println("get 1 : " + obj +", date:" + LocalDateTime.now());
        while (true) {
            obj = cache.getIfPresent(str);
            if (obj == null) {
                cache.invalidateAll();
                System.out.println("get 1 : " + obj +", date:" + LocalDateTime.now());
                break;
            }
        }

    }

}

