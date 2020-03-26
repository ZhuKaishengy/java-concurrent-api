# 第4章 Executor与ThreadPoolExecutor的使用

处理执行时间很短而数目却非常巨大的请求，如果为每一个请求创建一个新的线程，会导致性能上的瓶颈，因为**线程对象的创建和销毁需要JVM频繁地进行处理**，如果请求的执行时间很短，可能花在创建和销毁线程对象上的时间大于真正执行任务的时间，若这样，则系统性能大幅降低。
在JDK5中提供了线程池的支持，主要的作用是支持高并发的访问处理，并且可以将线程对象进行复用，核心原理即创建了一个运行效率比较优异的“线程池ThreadPool”，在池中支持线程对象管理，包括创建与销毁，使用池时只需要执行具体的任务即可，线程对象的处理都在池中被封装了。

## 4.1 Executor接口介绍

在介绍线程池之前，先要了解一下接口java.util.concurrent.Executor，与线程池有关的大部分类都是实现此接口的。此接口的结构非常简洁，仅有一个方法，`void execute(Runnable command);`，但Executor是接口，并不能直接使用，所以还得需要实现类。接口ExecutorService是Executor的子接口，在内部添加了比较多的方法。虽然接口ExecutorService添加了若干个方法的定义，但还是不能实例化，那么就要看一下它的唯一子实现类AbstractExecutorService，它是abstract抽象的，所以类AbstractExecutorService同样也是不能实例化的。再来看一下AbstractExecutorService类的子类ThreadPoolExecutor，通过查看源代码发现，类ThreadPoolExecutor并不是抽象的，所以可进行实例化，进而使用ThreadPoolExecutor类中方法所提供的功能。

## 4.2 使用Executors工厂类创建线程池

接口Executor仅仅是一种规范，是一种声明，是一种定义，并没有实现任何的功能，所以大多数的情况下，需要使用接口的实现类来完成指定的功能，比如ThreadPoolExecutor类就是Executor的实现类，但ThreadPoolExecutor在使用上并不是那么方便，在实例化时需要传入很多个参数，还要考虑线程的并发数等与线程池运行效率有关的参数，所以**官方建议使用Executors工厂类来创建线程池对象**。

### 4.2.1 使用newCachedThreadPool()方法创建无界线程池

使用Executors类的newCachedThreadPool()方法创建的是无界线程池，可以进行线程自动回收。所谓的“无界线程池”就是池中存放线程个数是理论上的Integer.MAX_VALUE最大值。

示例代码参考example01包。

### 4.2.2 验证newCachedThreadPool()创建为Thread池

前面的实验都没有验证newCachedThreadPool()方法创建的是线程池，在本测试中将论证线程复用。

示例代码参考example02包。

### 4.2.3 使用newCachedThreadPool (ThreadFactory)定制线程工厂

无界线程池中的Thread类还可以由程序员自己定制，方法newCachedThreadPool(Thread Factory)就是解决这个问题的。
示例代码参考example03包。

### 4.2.4 使用newFixedThreadPool(int)方法创建有界线程池

方法newFixedThreadPool(int)创建的是有界线程池，也就是池中的线程个数可以指定最大数量。使用有界线程池后线程池中的最多线程个数是可控的。

示例代码参考example04包。

### 4.2.5 使用newFixedThreadPool(int, ThreadFactory)定制线程工厂

有界线程池中的Thread类还可以由程序员自己定制

示例代码参考example04包。

### 4.2.6 使用newSingleThreadExecutor()方法创建单一线程池

使用newSingleThreadExecutor()方法可以创建单一线程池，单一线程池可以实现以队列的方式来执行任务。

示例代码参考example05包。

### 4.2.7 使用newSingleThreadExecutor(ThreadFactory)定制线程工厂

此方法的使用方式与前面章节的使用方式大体一致。

## 4.3 ThreadPoolExecutor的使用

类ThreadPoolExecutor可以非常方便地创建线程池对象，而不需要程序员设计大量的new实例化Thread相关的代码。使用Executors工厂类的newXXXThreadExecutor()方法可以快速方便地创建线程池，但创建的细节却未知，通过查看源代码在调用newSingleThreadExecutor()方法时内部其实是实例化了1个ThreadPoolExecutor类的实例。所以下一步就要细化研究一下ThreadPoolExecutor类的使用。

### 4.3.1 构造方法的测试

类ThreadPoolExecutor最常使用的构造方法是：
`ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue)`
参数解释如下：

*  corePoolSize：池中所保存的线程数，包括空闲线程，也就是**核心池**的大小。
* maximumPoolSize：池中允许的最大线程数。
* keepAliveTime：**当线程数量大于corePoolSize值时，在没有超过指定的时间内是不从线程池中将空闲线程删除的，如果超过此时间单位，则删除**。
*  unit：keepAliveTime参数的时间单位。
* workQueue：执行前用于保持任务的队列。此队列仅保持由execute方法提交的Runnable任务。

为了更好地理解这些参数在使用上的一些关系，可以将它们进行详细化的注释：
1）A代表execute(runnable)欲执行的runnable的数量；
2）B代表corePoolSize；
3）C代表maximumPoolSize；
4）D代表A-B(假设A>=B)；
5）E代表new LinkedBlockingDeque<Runnable>()；队列，无构造参数；
6）F代表SynchronousQueue队列；
7）G代表keepAliveTime。
构造方法中5个参数之间都有使用上的关系，在使用线程池的过程中大部分会出现如下5种过程：

1. 如果A<=B（提交的任务数<核心线程数），那么马上创建线程运行这个任务，并不放入扩展队列Queue中，其他参数功能忽略；
2. 如果A>B&&A<=C&&E（核心线程数 < 提交的任务数 < 最大线程数 && 使用阻塞队列），则C和G参数忽略，并把D放入E中等待被执行；
3. 如果A>B&&A<=C&&F（核心线程数 < 提交的任务数 < 最大线程数 && 使用同步队列），则C和G参数有效，并且马上创建线程运行这些任务，而不把D放入F中，D执行完任务后在指定时间后发生超时时将D进行清除；
4. 如果A>B&&A>C&&E，则C和G参数忽略，并把D放入E中等待被执行；
5. 如果A>B&&A>C&&F，则处理C的任务，其他任务则不再处理抛出异常。

分为若干情况进行实验。
1． 前2个参数与getCorePoolSize()和getMaximumPoolSize()方法

2． 在线程池中添加的线程数量<=corePoolSize

3． 数量>corePoolSize并且<=maximumPoolSize的情况，BlockingQueue只是一个接口，常用的实现类有LinkedBlockingQueue和ArrayBlockingQueue。用LinkedBlockingQueue的好处在于没有大小限制，优点是队列容量非常大，所以执行execute()不会抛出异常，而线程池中运行的线程数也永远不会超过corePoolSize值，因为其他多余的线程被放入LinkedBlockingQueue队列中，keepAliveTime参数也就没有意义了。

4． 数量>maximumPoolSize的情况

示例代码参考example06包。

### 4.3.2 方法shutdown()和shutdownNow()与返回值

方法shutdown()的作用是**使当前未执行完的线程继续执行，而不再添加新的任务Task**，还有shutdown()方法不会阻塞，调用shutdown()方法后，主线程main就马上结束了，而线程池会继续运行直到所有任务执行完才会停止。如果不调用shutdown()方法，那么线程池会一直保持下去，以便随时执行被添加的新Task任务。

方法shutdownNow()的作用是中断所有的任务Task，并且抛出InterruptedException异常，前提是在Runnable中使用if (Thread.currentThread().isInterrupted() == true)语句来判断当前线程的中断状态，而未执行的线程不再执行，也就是从执行队列中清除。如果没有if (Thread.currentThread().isInterrupted() == true)语句及抛出异常的代码，则**池中正在运行的线程直到执行完毕，而未执行的线程不再执行**，也从执行队列中清除。

当线程池调用shutdown()方法时，线程池的状态则立刻变成SHUTDOWN状态，此时不能再往线程池中添加任何任务，否则将会抛出**RejectedExecutionException**异常。但是，此时线程池不会立刻退出，直到线程池中的任务都已经处理完成，才会退出。
而shutdownNow()方法是使线程池的状态立刻变成STOP状态，并试图停止所有正在执行的线程（如果有if判断则人为地抛出异常），不再处理还在池队列中等待的任务，当然，**它会返回那些未执行的任务**。

示例代码参考example07包。

### 4.3.3 方法isShutdown()

方法isShutdown()的作用是判断线程池是否已经关闭。只要调用了shutdown ()方法，isShutdown()方法的返回值就是true。

示例代码参考example07包。

### 4.3.4 方法isTerminating()和isTerminated()

如果正在执行的程序处于shutdown或shutdownNow之后处于正在终止但尚未完全终止的过程中，调用方法isTerminating()则返回true。此方法可以比喻成，门是否正在关闭。门彻底关闭时，线程池也就关闭了。
如果线程池关闭后，也就是所有任务都已完成，则方法isTerminated()返回true。此方法可以比喻成，门是否已经关闭。

示例代码参考example08包。

### 4.3.5 方法awaitTermination(long timeout, TimeUnit unit)

方法awaitTermination(long timeout, TimeUnit unit)的作用就是查看在指定的时间之间，线程池是否已经终止工作，也就是最多等待多少时间后去判断线程池是否已经终止工作。
此方法需要有shutdown()方法的配合。

示例代码参考example08包。

### 4.3.6 工厂ThreadFactory+execute()+UncaughtExceptionHandler处理异常

有时需要对线程池中创建的线程属性进行定制化，这时就得需要配置ThreadFactory线程工厂。

示例代码参考example09包。

### 4.3.7 方法set/getRejectedExecutionHandler()

方法setRejectedExecutionHandler()和getRejectedExecutionHandler()的作用是可以处理任务被拒绝执行时的行为。

示例代码参考example10包。

### 4.3.8 方法allowsCoreThreadTimeOut()/(boolean)

方法allowsCoreThreadTimeOut()和allowsCoreThreadTimeOut(boolean value)的作用是配置核心线程是否有超时的效果。

示例代码参考example11包。

### 4.3.9 方法prestartCoreThread()和prestartAllCoreThreads()

方法prestartCoreThread()每调用一次就创建一个核心线程，返回值为boolean，含义是是否启动了。
方法prestartAllCoreThreads()的作用是启动全部核心线程，返回值是启动核心线程的数量。

示例代码参考example12包。

### 4.3.10 方法getCompletedTaskCount()

方法getCompletedTaskCount()的作用是取得已经执行完成的任务数。

示例代码参考example13包。

### 4.3.11 常见3种队列结合max值的因果效果

在使用ThreadPoolExecutor线程池的过程中，常使用3种队列**ArrayBlockingQueue、LinkedBlockingDeque和SynchronousQueue**。其中ArrayBlockingQueue和LinkedBlockingDeque类可以指定队列存储元素的多少，如果传入capacity值是<=0时是出现异常的。
那么本节要实验的环境是欲执行任务的数量大于ThreadPoolExecutor线程池的maximum-PoolSize最大值时，在不同队列中会出现什么样的情况。

示例代码参考example13包。

### 4.3.12 线程池ThreadPoolExecutor的拒绝策略

线程池中的资源全部被占用的时候，对新添加的Task任务有不同的处理策略，在默认的情况下，ThreadPoolExecutor类中有4种不同的处理方式：

* AbortPolicy：当任务添加到线程池中被拒绝时，它将抛出RejectedExecutionException异常（默认策略）
* CallerRunsPolicy：当任务添加到线程池中被拒绝时，会使用调用线程池的Thread线程对象处理被拒绝的任务。
* DiscardOldestPolicy：当任务添加到线程池中被拒绝时，线程池会放弃等待队列中最旧的未处理任务，然后将被拒绝的任务添加到等待队列中。
* DiscardPolicy：当任务添加到线程池中被拒绝时，线程池将丢弃被拒绝的任务。

示例代码参考example13包。

### 4.3.13 方法afterExecute()和beforeExecute()

在线程池ThreadPoolExecutor类中重写这两个方法可以对线程池中执行的线程对象实现监控。

示例代码参考example13包。

### 4.3.14 方法remove(Runnable)的使用

方法remove(Runnable)可以删除尚未被执行的Runnable任务。

示例代码参考example13包。

### 4.3.15 多个get方法

线程池ThreadPoolExecutor有很多getX()方法，熟悉这些方法是观察线程池状态最好的方式。

* 方法getActiveCount()的作用是取得有多少个线程正在执行任务。
* 方法getPoolSize()获得的是当前线程池里面有多少个线程，这些线程数包括正在执行任务的线程，也包括正在休眠的线程。
* 方法getActiveCount()获得正在执行任务的线程数。
* 方法getCompletedTaskCount()的作用是取得有多少个线程已经执行完任务了。
* 方法getCorePoolSize()的作用是取得构造方法传入的corePoolSize参数值。
* 方法getMaximumPoolSize()的作用是取得构造方法传入的maximumPoolSize参数值。
* 方法getTaskCount()的作用是取得有多少个任务发送给了线程池。

## 4.4 本章总结

本章主要介绍ThreadPoolExecutor类的构造方法中各个参数的作用与使用效果，还介绍了Executors工厂类常用API的使用，也将大部分ThreadPoolExecutor线程池类的常见API一同进行了介绍，并且对ThreadPoolExecutor线程池的拒绝策略进行了实验，通过使用线程池能最大幅度地减少创建线程对象的内存与CPU开销，加快程序运行效率，也对创建线程类的代码进行了封装，方便开发并发类型的软件项目。