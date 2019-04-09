package com.frame.example.frames.redisson;

import org.junit.Assert;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.api.listener.StatusListener;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

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
            //采用JackSon序列化方式
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

    @Test
    public void testTopic() throws InterruptedException {
        RTopic topic = redissonClient.getTopic("myTopic");
        //监听消费
        topic.addListener(CustomMessage.class, (channel, msg) -> System.out.println("msg:" + msg.toString()));
        //发布数据
        topic.publishAsync(new CustomMessage(1, "wzl", 28));

        topic.addListener(new StatusListener() {
            @Override
            public void onSubscribe(String channel) {
                System.out.println("onSubscribe:"+channel);
            }

            @Override
            public void onUnsubscribe(String channel) {
                System.out.println("onUnsubscribe:"+channel);
            }
        });

        Thread.sleep(3000);
    }

    @Test
    public void testMap() {
        RMap<String, CustomMessage> myMap = redissonClient.getMap("myMap");
        myMap.put("122", CustomMessage.builder().age(18).id(122).name("lisi").build());
        myMap.put("123", CustomMessage.builder().age(18).id(123).name("li123").build());
        CustomMessage message = myMap.get("122");
        System.out.println("message:" + message);
        Assert.assertEquals(message.getId(),Integer.valueOf(122));
    }

    @Test
    public void testSet() {
        RSet<Integer> mySet = redissonClient.getSet("mySet");
        mySet.add(121);
        mySet.add(122);
        if (mySet.contains(121)) {
            System.out.println("121 存在");
            mySet.add(120);
        }

    }

    @Test
    public void testList() {
        RList<CustomMessage> list = redissonClient.getList("myList");
        list.add(CustomMessage.builder().age(12).name("list").id(1001).build());
        list.add(CustomMessage.builder().age(11).name("list").id(1011).build());

        List<CustomMessage> customMessages = list.readAll();
        customMessages.stream().forEach(System.out::println);
    }

    @Test
    public void testLiveObject() {
        RLiveObjectService liveObjectService = redissonClient.getLiveObjectService();
        LedgerLiveObject obj = new LedgerLiveObject("gua22","hello good");
        liveObjectService.persist(obj);
        LedgerLiveObject zhou = liveObjectService.get(LedgerLiveObject.class, "digua");
        System.out.println(zhou);

    }
    @Test
    public void testGetLiveObject() {
        RLiveObjectService liveObjectService = redissonClient.getLiveObjectService();
        LedgerLiveObject digua2 = liveObjectService.get(LedgerLiveObject.class, "digua3");
//        System.out.println(digua2.getName());

    }



    public static void main(String[] args) throws IOException, InterruptedException {
//        System.out.println("start time:" + LocalDateTime.now());
//        RLock lock = redissonClient.getLock("myLock");
//        lock.lock(10000,TimeUnit.SECONDS);
//        System.out.println("end time:" + LocalDateTime.now());
//        lock.unlock();
        //
    }
}
