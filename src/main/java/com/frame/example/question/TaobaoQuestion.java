package com.frame.example.question;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class TaobaoQuestion {  
    private static final int           cpu             = 100;  
    private static ExecutorService     executorService = Executors.newFixedThreadPool(cpu);  
    private static AtomicInteger       taskCounter     = new AtomicInteger(0);  
    private static int                 indexCounter    = 0;  
    private static Set<Long>           tmpResultSet    = new HashSet<Long>();  
    private static final List<Integer> ints            = new ArrayList<Integer>();  
  
    private TaobaoQuestion() {  
    }  
  
    public static void main(String[] args) {  
        //构建测试数据开始  
        final int count = 257023010;//我的笔记本最多能承受 值，过大就OOM =.=  
        for (int i = 0; i < count; i++) {  
            int value = (int) (Math.random() * 100);  
            ints.add(value);  
        }  
        //构建测试数据结束，开始求和  
        /** 
         * 假设为8核cpu，将整个list 虚拟地分别8段，每个线程负责一段的计算sum 
         */  
        final int avg = count / cpu;  
        while (taskCounter.getAndIncrement() < cpu) {//保障只能起和cpu个数一样多的线程  
            executorService.execute(new Runnable() {  
                public void run() {  
                    int curt = indexCounter++;  
                    int min = avg * curt;  
                    int max;  
                    if (curt != (cpu - 1)) {  
                        max = avg * (curt + 1);  
                    } else {  
                        max = count;  
                    }  
                    System.out.println(max);  
                    long result = 0;  
                    for (int i = min; i < max; i++) {  
                        result = result + ints.get(i);  
                    }  
                    tmpResultSet.add(result);  
                    if (tmpResultSet.size() == cpu) {  
                       long finalResult = 0;  
                        for (long curtTmpResult : tmpResultSet) {  
                            finalResult = finalResult + curtTmpResult;  
                        }  
                        System.out.println("finalResult=" + finalResult);  
                    }  
                }  
            });  
        }  
        executorService.shutdown();  
    }  
}  