# 第8章 计划任务ScheduledExecutorService的使用

Java中的计划任务Timer工具类提供了以计时器或计划任务的功能来实现按指定时间或时间间隔执行任务，但**由于Timer工具类并不是以池pool，而是以队列的方式来管理线程的，所以在高并发的情况下运行效率较低，ScheduledExecutorService对象来解决效率与定时任务的功能**。

## 8.1 ScheduledExecutorService的使用

类ScheduledExecutorService的主要作用就是可以**将定时任务与线程池功能结合使用**。

## 8.2 ScheduledThreadPoolExecutor使用Callable延迟运行

本示例使用Callable接口进行任务延迟运行的实验，具有返回值的功能。

示例代码参考example01包。

## 8.3 ScheduledThreadPoolExecutor使用Runnable延迟运行

本示例使用Runnable接口进行无返回值的计划任务实验。

示例代码参考example01包。

## 8.5 使用scheduleAtFixedRate()方法实现周期性执行

此示例测试的是执行任务时间大于> period预定的周期时间，也就是产生了超时的效果。注意，scheduleAtFixedRate()方法返回的Scheduled-Future对象无法获得返回值，也就是**scheduleAtFixedRate()方法不具有获得返回值的功能**，而schedule()方法却可以获得返回值。所以当使用scheduleAtFixedRate()方法实现重复运行任务的效果时，需要结合自定义Runnable接口的实现类，不要使用FutureTask类，因为FutureTask类并不能实现重复运行的效果。

示例代码参考example02包。

## 8.6 使用scheduleWithFixedDelay()方法实现周期性执行

方法scheduleWithFixedDelay()的主要作用是设置多个任务之间固定的运行时间间隔。
此示例测试的是执行任务时间大于> period预定的时间。schedule-WithFixedDelay()并没有超时与非超时的情况，参数long delay的主要作用就是下一个任务的开始时间与上一个任务的结束时间的时间间隔。

示例代码参考example03包。

## 8.7 使用getQueue()与remove()方法

方法getQueue()的作用是取得队列中的任务，而这些任务是未来将要运行的，正在运行的任务不在此队列中。使用scheduleAtFixedRate()和scheduleWithFixedDelay()两个方法实现周期性执行任务时，未来欲执行的任务都是放入此队列中。注意：remove()方法的参数是ScheduledFuture数据类型。

示例代码参考example04包。

## 8.8 方法setExecuteExistingDelayedTasksAfterShutdownPolicy()的使用

方法setExecuteExistingDelayedTasksAfterShutdownPolicy()的作用是当对Scheduled-ThreadPoolExecutor执行了shutdown()方法时，任务是否继续运行，默认值是true，也就是当调用了shutdown()方法时任务还是继续运行，当使用setExecuteExistingDelayedTasksAfterShutdownPolicy(false)时任务不再运行。

**方法setExecuteExistingDelayedTasksAfterShutdownPolicy()可以与schedule()和shutdown()方法联合使用**，但setExecuteExistingDelayedTasksAfterShutdownPolicy ()方法不能与scheduleAt-FixedRate()和scheduleWithFixedDelay()方法联合使用。那么如果想实现shutdown关闭线程池后，池中的任务还会继续重复运行，则要将**scheduleAtFixedRate()和scheduleWithFixedDelay()方法与setContinueExistingPeriodicTasksAfterShutdownPolicy()方法联合使用**。

示例代码参考example05包。

## 8.9 方法setContinueExistingPeriodicTasksAfterShutdownPolicy()

方法setContinueExistingPeriodicTasksAfterShutdownPolicy()传入true的作用是当使用schedule-AtFixedRate()方法或scheduleWithFixedDelay()方法时，如果调用ScheduledThreadPool-Executor对象的shutdown()方法，任务还会继续运行，传入false时任务不运行，进程销毁。

示例代码参考example05包。

## 8.10 使用cancel(boolean)与setRemoveOnCancelPolicy()方法

方法cancel(boolean)的作用设定是否取消任务。
方法setRemoveOnCancelPolicy(boolean)的作用设定是否将取消后的任务从队列中清除。

## 8.11 本章总结

本章介绍了基于线程池ThreadPoolExecutor的ScheduledThreadPoolExecutor计划任务执行池对象，使用此类可以高效地实现计划任务线程池，不再重复创建Thread对象，提高了运行效率。此类也支持间隔运行的功能。