# 第7章 接口ExecutorService的方法使用

接口ExecutorService中有很多工具方法，在本章中将对剩余方法的功能逐一进行介绍。ExecutorService接口中的方法有些与Future和Callable有关，所以Future和Callable的使用是学习ExecutorService接口中方法的基础。

## 7.1 在ThreadPoolExecutor中使用ExecutorService中的方法

**方法invokeAny()和invokeAll()具有阻塞特性。方法invokeAny()取得第一个完成任务的结果值，当第一个任务执行完成后，会调用interrupt()方法将其他任务中断**，所以在这些任务中可以结合if (Thread.currentThread(). isInterrupted()==true)代码来决定任务是否继续运行。
**方法invokeAll ()等全部线程任务执行完毕后，取得全部完成任务的结果值。**

## 7.2 方法invokeAny(Collection tasks)的使用与interrupt

此实验验证方法invokeAny()的确是取得第一个完成任务的结果值，但在这个过程中出现两种情况：
1）无if (Thread.currentThread().isInterrupted())代码：已经获得第一个运行的结果值后，其他线程被中断。
2）有if (Thread.currentThread().isInterrupted())代码：已经获得第一个运行的结果值后，其他线程如果使用throw new InterruptedException()代码则这些线程中断，虽然throw抛出了异常，但在main线程中并不能捕获异常。如果想捕获异常，则需要在Callable中使用try-catch显式进行捕获。

示例代码参考example01包。

## 7.3 方法invokeAny()与执行慢的任务异常

此实验验证在快的任务优先执行完毕后，执行慢的任务出现异常时，默认情况下不会在控制台输出异常信息。如果想要在Callable中捕获异常信息，则需要显式地添加try-catch语句块。加入显式的try-catch语句块可以捕获异常信息，但抛出去的异常在main()方法中却没有得到捕获，说明子线程出现异常时是不影响main线程的主流程的。

示例代码参考example01包。

## 7.4 方法invokeAny()与执行快的任务异常

此实验验证在执行快的任务出现异常时，在默认情况下是不在控制台输出异常信息的，除非显式使用try-catch捕获，而等待执行慢的任务返回的结果值。
先出现异常而不影响后面任务的取值的原理是在源代码中一直判断有没有正确的返回的值，如果直到最后都没有获得返回值则抛出异常，这个异常是最后出现的异常。
**执行快的任务抛出异常，main线程依然抓不到，会去等第一个可以执行完的任务执行完后，剩下的任务interrupt**。

示例代码参考example01包。

## 7.5 方法invokeAny()与全部异常

此实验验证在全部任务都出现异常时，程序抛出ExecutionException异常。出现异常时返回最后一个异常并输出。

示例代码参考example01包。

## 7.6 方法invokeAny(CollectionTasks, timeout, timeUnit)超时的测试

方法<T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)的主要作用就是在指定时间内取得第一个先执行完任务的结果值。

示例代码参考example01包。

## 7.7 方法invokeAll(Collection tasks)全正确

方法invokeAll()会返回所有任务的执行结果，并且此方法执行的效果也是阻塞执行的，要把所有的结果都取回时再继续向下运行。

示例代码参考example02包。

## 7.8 方法invokeAll(Collection tasks)快的正确慢的异常

在多个任务的过程中，执行任务快慢与运行时发生的异常也有一些联系。**invokeAll()方法对Callable抛出去的异常是可以处理的**，在main()方法中直接进入了catch语句块，所以导致后面的无法执行。
如果使用invokeAny()方法而某一个任务正确地返回值时，则其他Callable抛出去的异常在main()方法中是不被处理的。
如果使用invokeAny()方法时都没有正确的返回值时，则说明最后Callable抛出去的异常在main()方法中是被处理了的。

示例代码参考example02包。

## 7.10 方法invokeAll(Collection tasks, long timeout, TimeUnit unit)

方法invokeAll(Collection tasks, long timeout, TimeUnit unit)的作用是如果全部任务在指定的时间内没有完成，则出现异常。
使用invokeAll()方法出现超时后，调用Future对象的get()方法时出现的是CancellationException异常，而不是invokeAny()方法抛出来的TimeoutException异常。**出现超时，worker线程被中断**。

示例代码参考example02包。

## 7.13 本章总结

接口ExecutorService中的方法都以便携的方式去创建线程池，使用两个主要的方法invokeAny()和invokeAll()来取得第一个首先执行完任务的结果值，以及全部任务的结果值。

