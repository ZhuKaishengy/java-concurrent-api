# 第6章 CompletionService的使用
接口CompletionService的功能是以异步的方式一边生产新的任务，一边处理已完成任务的结果，这样可以将执行任务与处理任务分离开来进行处理。使用submit执行任务，使用take取得已完成的任务，并按照完成这些任务的时间顺序处理它们的结果。

## 6.1 CompletionService介绍

接口CompletionService仅有一个实现类ExecutorCompletionService，类ExecutorCompletionService需要依赖于Executor对象，大部分的实现也就是使用线程池ThreadPoolExecutor对象。

## 6.2 使用CompletionService解决Future的缺点

第5章演示过接口Future具有阻塞同步性，这样的代码运行效率会大打折扣，接口CompletionService可以解决这样的问题。
使用CompletionService接口中的take()方法，它的主要作用就是取得Future对象，在CompletionService接口中如果当前没有任务被执行完，则csRef. take().get()方法还是呈阻塞特性。

示例代码参考example01包。

## 6.3 使用take()方法

方法take()取得最先完成任务的Future对象，谁执行时间最短谁最先返回。

示例代码参考example01包。

## 6.4 使用poll()方法

方法poll()的作用是获取并移除表示下一个已完成任务的Future，如果不存在这样的任务，则返回null，方法poll()无阻塞的效果。

示例代码参考example02包。

## 6.5 使用poll(long timeout, TimeUnit unit)方法

方法Future<V> poll(long timeout, TimeUnit unit)的作用是等待指定的timeout时间，在timeout时间之内获取到值时立即向下继续执行，如果超时也立即向下执行。

示例代码参考example02包。

## 6.6 类CompletionService与异常

使用CompletionService执行任务的过程中不可避免会出现各种情况的异常。调用FutureTask类的get()方法时可能出现异常。

示例代码参考example03包。

## 6.7 方法`Future<V> submit(Runnable task, V result)`的测试

参数V是submit()方法的返回值。

示例代码参考example04包。

## 6.8 本章总结

接口CompletionService完全可以避免FutureTask类阻塞的缺点，可更加有效地处理Future的返回值，也就是哪个任务先执行完，CompletionService就先取得这个任务的返回值再处理。