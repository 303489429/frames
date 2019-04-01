package com.frame.example.frames.redisson;

import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Iterator;

/**
 * @author wangzhilong
 * @date 2019-03-30
 */
public class RedissonDemo {

    private static RedissonClient redissonClient ;

    static {
        try {
            URL resource = RedissonDemo.class.getClassLoader().getResource("singleNodeConfig.json");
            Config config = Config.fromJSON(new File(resource.toURI()));
            config.setCodec(new JsonJacksonCodec()) ;
            redissonClient = Redisson.create(config);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testKeys(){
        RKeys keys = redissonClient.getKeys();
        Iterable<String> keys1 = keys.getKeys();
        Iterator<String> iterator = keys1.iterator();
        while (true) {
            if (!iterator.hasNext()) break;
            String next = iterator.next();
            System.out.println(next);
        }
    }

    @Test
    public void testKeysByPattern() {
        RKeys keys = redissonClient.getKeys();
        Iterable<String> pattern = keys.getKeysByPattern("name*");
        Iterator<String> iterator = pattern.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        keys.deleteByPattern("name*");

        pattern = keys.getKeysByPattern("name*");
        iterator = pattern.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    public void testObject() {
        Person p1 = Person.builder()
                .name("wzl")
                .age(18)
                .address("成都").build();
        RBucket<Person> person = redissonClient.getBucket("person");
        person.set(p1);
        Person person1 = person.get();
        System.out.println(person1);
    }




    public static void main(String[] args) throws IOException {
        System.out.println("start time:" + LocalDateTime.now());
//        RLock lock = redissonClient.getLock("myLock");
//        lock.lock(10000,TimeUnit.SECONDS);
//        System.out.println("end time:" + LocalDateTime.now());
//        lock.unlock();
    }
}
