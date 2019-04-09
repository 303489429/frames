package com.frame.netty.promise;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author wangzhilong
 * @date 2019-04-08
 */
public  class MyPromise {

    private Boolean isDone = false;
    private String caseStr ;

    private List<FutureTask> futureTaskList = new LinkedList<>();

    public Boolean isDone(){
        return isDone == null ? true : isDone;
    }

    public void setSuccess(Boolean b) {
        this.isDone = b;
        if (isDone()) {
            notifyAllTask();
        }
    }

    public void setFailure(Boolean b,String caseStr) {
        this.isDone = b;
        this.caseStr = caseStr;
    }

    protected MyPromise addListener(FutureTask futureTask){
        if (futureTask != null) {
            futureTaskList.add(futureTask);
        }
        return this ;
    }

    private void notifyAllTask() {
        if (futureTaskList.size() > 0) {
            Iterator<FutureTask> taskIterator = futureTaskList.iterator();
            while (taskIterator.hasNext()) {
                FutureTask next = taskIterator.next();
                next.doSomthing() ;
            }
        }
    }

    public static void main(String[] args) {

        MyPromise promise = new MyPromise();
        promise.addListener(() -> {
            System.out.println("add listener method called.");
            return "add listener method called.";
        }) ;


        System.out.println("开始执行任务");

        promise.setSuccess(true);

        System.out.println("任务执行完成");

    }

}
