package com.frame.netty.future;

/**
 * @author wangzhilong
 * @date 2019-04-08
 */
public abstract class TaskCallback {

    /**
     * 执行动作
     * @param future
     * @return
     */
    public abstract TaskFuture apply(TaskFuture future);

    public TaskCallback compose(final TaskCallback before) {
        return new TaskCallback() {
            @Override
            public TaskFuture apply(TaskFuture future) {
                return TaskCallback.this.apply(before.apply(future));
            }
        } ;
    }

    public TaskCallback andThen(final TaskCallback after) {
        return new TaskCallback() {
            @Override
            public TaskFuture apply(TaskFuture future) {
                return after.apply(TaskCallback.this.apply(future));
            }
        };
    }


}
