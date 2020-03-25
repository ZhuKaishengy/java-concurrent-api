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



