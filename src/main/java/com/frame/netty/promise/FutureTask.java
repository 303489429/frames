package com.frame.netty.promise;

/**
 * @author wangzhilong
 * @date 2019-04-08
 */
public interface FutureTask<T> {


    /**
     * 处理并返一个具体的值
     * @param t
     * @return
     */
    T doSomthing();

}
