# 第5章 Future和Callable的使用
在默认情况下，线程Thread对象不具有返回值的功能，如果在需要取得返回值的情况下是极为不方便的，但在Java1.5的并发包中可以使用Future和Callable来使线程具有返回值的功能。

## 5.1 Future和Callable的介绍

接口Callable与线程功能密不可分，但和Runnable的主要区别为：
1）Callable接口的call()方法可以有返回值，而Runnable接口的run()方法没有返回值。
2）Callable接口的call()方法可以声明抛出异常，而Runnable接口的run()方法不可以声明抛出异常。
执行完Callable接口中的任务后，返回值是通过Future接口进行获得的。

## 5.2 方法get()结合ExecutorService中的submit(Callable<T>)的使用

方法submit(Callable<T>)可以执行参数为Callable的任务。方法get()用于获得返回值。

示例代码参考example01包。

## 5.3 方法get()结合ExecutorService中的submit(Runnable)和isDone()的使用

方法submit()不仅可以传入Callable对象，也可以传入Runnable对象，说明submit()方法支持有返回值和无返回值的功能。
如果submit()方法传入Callable接口则可以有返回值，如果传入Runnable则无返回值，打印的结果就是null。方法**get()具有阻塞特性，而isDone()方法无阻塞特性**。

示例代码参考example01包。

## 5.4 使用ExecutorService接口中的方法submit(Runnable, T result)

方法submit(Runnable, T result)的第2个参数result可以作为执行结果的返回值，而不需要使用get()方法来进行获得。

示例代码参考example02包。

## 5.5 方法cancel(boolean mayInterruptIfRunning)和isCancelled()的使用

方法cancel(boolean mayInterruptIfRunning)的参数mayInterruptIfRunning的作用是：如果线程正在运行则是否中断正在运行的线程，在代码中需要使用if (Thread.currentThread(). isInterrupted())进行配合。
方法cancel()的返回值代表发送取消任务的命令是否成功完成。

示例代码参考example03包。

## 5.6 方法get(long timeout, TimeUnit unit)的使用

方法get(long timeout, TimeUnit unit)的作用是在指定的最大时间内等待获得返回值。

示例代码参考example04包。

## 5.7 异常的处理

接口Callable任务在执行时有可能会出现异常，那在Callable中异常是如何处理的呢？在get时捕获异常。

示例代码参考example05包。

## 5.8 验证Future的缺点

接口Future的实现类是FutureTask.java，而且在使用线程池时，默认的情况下也是使用FutureTask. java类作为接口Future的实现类，也就是说，如果在使用Future与Callable的情况下，使用Future接口也就是在使用FutureTask.java类。
Callable接口与Runnable接口在对比时主要的优点是，Callable接口可以通过Future取得返回值。但需要注意的是，Future接口调用get()方法取得处理的结果值时是阻塞性的，也就是如果调用Future对象的get()方法时，任务尚未执行完成，则调用get()方法时一直阻塞到此任务完成时为止。如果是这样的效果，则前面先执行的任务一旦耗时很多，则后面的任务调用get()方法就呈阻塞状态，也就是排队进行等待，大大影响运行效率。也就是**主线程并不能保证首先获得的是最先完成任务的返回值**，这就是Future的缺点，影响效率。

## 5.9 本章总结

本章对Future和Callable接口中的全部API进行了介绍，这两个接口的优点就是从线程中返回数据以便进行后期的处理，但FutureTask类也有其自身的缺点，就是阻塞性，解决这个缺点请参看第6章。