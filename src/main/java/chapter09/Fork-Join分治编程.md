# 第9章 Fork-Join分治编程

Fork/Join并行执行任务框架，它的主要作用是**把大任务分割成若干个小任务，再对每个小任务得到的结果进行汇总**，此种开发方法也叫分治编程，分治编程可以极大地利用CPU资源，提高任务执行的效率，也是目前与多线程有关的前沿技术。

## 9.1 Fork-Join分治编程与类结构

![image-20200420105527636](/Users/zhukaishengy/StudyWorkSpace/java-concurrent-api/src/main/java/chapter09/imgs//image-20200420105527636.png)

并行执行框架Fork-Join使用了“工作窃取（work-stealing）”算法，它是指**某个线程从其他队列里窃取任务来执行**。比如要完成一个比较大的任务，完全可以把这个大的任务分割为若干互不依赖的子任务/小任务，为了更加方便地管理这些任务，于是把这些子任务分别放到不同的队列里，这时就会出现有的线程会先把自己队列里的任务快速执行完毕，而其他线程对应的队列里还有任务等待处理，**完成任务的线程与其等着，不如去帮助其他线程分担要执行的任务，于是它就去其他线程的队列里窃取一个任务来执行**，这就是所谓的“工作窃取（work-stealing）”算法。
在JDK1.7中实现分治编程需要使用ForkJoinPool类，此类的主要作用是创建一个任务池。

![image-20200420105921760](/Users/zhukaishengy/StudyWorkSpace/java-concurrent-api/src/main/java/chapter09/imgs//image-20200420105921760.png)

类ForkJoinPool所提供的功能是一个任务池，而执行具体任务却不是ForkJoinPool，而是ForkJoinTask类。

![image-20200420110355079](/Users/zhukaishengy/StudyWorkSpace/java-concurrent-api/src/main/java/chapter09/imgs//image-20200420110355079.png)



## 9.2 使用RecursiveAction让任务跑起来

使用类RecursiveAction执行的任务是具有无返回值的，仅执行一次任务。

示例代码参考example01包。

## 9.3 使用RecursiveAction分解任务

前面的示例仅是让任务运行起来，并打印一个字符串信息，任务并没有得到fork分解，也就是并没有体现分治编程的运行效果。在调用ForkJoinTask.java类中的fork()方法时需要注意一下效率的问题，因为每一次调用fork都会分离任务，增加系统运行负担，所以在ForkJoinTask.java类中提供了public static void invokeAll(ForkJoinTask<? > t1, ForkJoinTask<? >t2)方法来优化执行效率。本示例也是使用此方法分离任务并执行。

示例代码参考example01包。

## 9.4 使用RecursiveTask取得返回值与join()和get()方法的区别

使用类RecursiveTask执行的任务具有返回值的功能。

示例代码参考example02包。

## 9.5 使用RecursiveTask执行多个任务并打印返回值

并且任务之间运行的方式是异步的，但join()方法却是同步的。

示例代码参考example02包。

## 9.6 使用RecursiveTask实现字符串累加

示例代码参考example03包。

## 9.7 使用Fork-Join实现求和

示例代码参考example04包。

## 9.9 类ForkJoinPool核心方法的实验

### 9.9.1 方法public void execute(ForkJoinTask<? > task)的使用

在ForkJoinPool.java类中的execute()方法是以异步的方式执行任务。

示例代码参考example05包#test1。

### 9.9.2 方法public void execute(Runnable task)的使用

示例代码参考example05包#test2。

### 9.9.3 方法public void execute(ForkJoinTask<? > task)如何处理返回值

虽然public void execute(ForkJoinTask<? > task)方法无返回值，但还是可以通过RecursiveTask对象处理返回值。

示例代码参考example05包#test3。

### 9.9.4 方法`public <T> ForkJoinTask<T> submit(ForkJoinTask<T> task)`的使用

方法execute()无返回值，submit()有返回值。

示例代码参考example05包#test4。

### 9.9.5 方法public ForkJoinTask<? > submit(Runnable task)的使用

传入Runnable接口返回值为null，调用get()方法呈阻塞状态。

示例代码参考example05包#test5。

### 9.9.6 方法`public <T> ForkJoinTask<T> submit(Callable<T> task)`的使用

示例代码参考example05包#test6。

### 9.9.7 方法`public <T> ForkJoinTask<T> submit(Runnable task, T result)`的使用

示例代码参考example05包#test7。

### 9.9.8 方法`public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)`的使用

方法public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)具有阻塞特性。

示例代码参考example05包#test8。

### 9.9.9 方法public void shutdown()的使用

示例代码参考example05包#test9。

### 9.9.10 方法`public List<Runnable> shutdownNow()`的使用

示例代码参考example05包#test10。

### 9.9.11 方法isTerminating()和isTerminated()的使用

使用shutdown()方法关闭pool池之前，isTerminating()方法的返回值一直是false。

示例代码参考example05包#test11。

### 9.9.12 方法public boolean isShutdown()的使用

示例代码参考example05包#test11。

### 9.9.13 方法public boolean awaitTermination(long timeout, TimeUnit unit)的使用

方法awaitTermination(long timeout, TimeUnit unit)的作用是等待池被销毁的最长时间，具有阻塞特性。

示例代码参考example05包#test12。

### 9.9.14 方法`public <T> T invoke(ForkJoinTask<T> task)`的使用

方法execute(task)、submit(task)以及invoke(task)都可以在异步队列中执行任务，需要注意的是，**方法invoke()是阻塞的**，而它们在使用上的区别其实很简单，execute(task)只执行任务，没有返回值，而submit(task)方法有返回值，返回值类型是ForkJoinTask，想取得返回值时，需要使用ForkJoinTask对象的get()方法，而invoke(task)和submit(task)方法一样都具有返回值的功能，区别就是invoke(task)方法直接将返回值进行返回，而不是通过ForkJoinTask对象的get()方法。

示例代码参考example05包#test13。

### 9.9.15 监视pool池的状态

提供了若干个方法来监视任务池的状态：
❑ 方法getParallelism()：获得并行的数量，与CPU的内核数有关；
❑ 方法getPoolSize()：获得任务池的大小；
❑ 方法getQueuedSubmissionCount()：取得已经提交但尚未被执行的任务数量；
❑ 方法hasQueuedSubmissions()：判断队列中是否有未执行的任务；
❑ 方法getActiveThreadCount()：获得活动的线程个数；
❑ 方法getQueuedTaskCount()：获得任务的总个数；
❑ 方法getStealCount()：获得偷窃的任务个数；
❑ 方法getRunningThreadCount()：获得正在运行并且不在阻塞状态下的线程个数；
❑ 方法isQuiescent()：判断任务池是否是静止未执行任务的状态。

示例代码参考example06包

## 9.10 类ForkJoinTask对异常的处理

方法isCompletedAbnormally()判断任务是否出现异常，方法isCompletedNormally()判断任务是否正常执行完毕，方法getException()返回报错异常。

## 9.11 本章总结

在本章主要介绍了Fork-Join分治编程类主要的API，需要细化掌握ForkJoinTask的2个常用子类的fork分解算法，虽然分治编程可以有效地利用CPU资源，但不要为了分治编程而分治，应该结合具体的业务场景来进行使用。