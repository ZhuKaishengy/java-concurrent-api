# 第一章：Semaphore和Exchanger的使用
Semaphore(信号量)
* 类Semaphore所提供的功能完全就是synchronized关键字的升级版
* 主要的作用就是 **控制线程并发数**

Exchanger

* 类Exchanger的主要作用可以使2个线程之间互相方便地进行通信

## 1.1 Semaphore的使用
如果不限制线程并发的数量，则CPU的资源很快就被耗尽，每个线程执行的任务是相当缓慢，
因为CPU要把时间片分配给不同的线程对象，而且上下文切换也要耗时，最终造成系统运行效率大幅降低。

上下文切换（Context Switch）或者环境切换。操作系统中，CPU切换到另一个进程，
需要花费额外的时间**保存当前进程的状态并恢复另一个进程的状态**。一般来说以下情形会发生上下文切换：中断处理，多任务处理，用户态切换。

### 1.1.1 类Semaphore的同步性
**多线程中的同步**概念其实就是排着队去执行一个任务，执行任务是一个一个执行，并不能并行执行，
这样的优点是有助于程序逻辑的正确性，不会出现非线程安全问题，保证软件系统功能上的运行稳定性。
示例代码参考example01包。

### 1.1.2 类Semaphore构造方法permits参数作用
参数permits的作用是设置许可的个数，`privateSemaphore semaphore = newSemaphore(1);`
来进行程序的设计，使同一时间内最多只有1个线程可以执行acquire()和release()之间的代码，因为只有1个许可。
其实还可以传入>1的许可，代表同一时间内，最多允许有x个线程可以执行acquire()和release()之间的代码。
示例代码参考example02包。
**对Semaphore类的构造方法传递的参数permits值如果大于1时，该类并不能保证线程安全性**，
因为还是有可能会出现多个线程共同访问实例变量，导致出现脏数据的情况。

### 1.1.3 方法acquire(int permits)参数作用及动态添加permits许可数量
有参方法acquire(int permits)的功能是每调用1次此方法，就使用x个许可。
示例代码参考example03包。
如果多次调用Semaphore类的release()或release(int)方法时，还可以动态增加permits的个数。
构造参数`new Semaphore(5)；`中的5并不是最终的许可数量，仅仅是初始的状态值。
示例代码参考example04包。

### 1.1.4 方法acquireUninterruptibly()的使用
方法acquireUninterruptibly()的作用是使等待进入acquire()方法的线程，不允许被中断。
示例代码参考example05包。

### 1.1.5 方法availablePermits()和drainPermits()
availablePermits()返回此Semaphore对象中当前可用的许可数，此方法通常用于调试，因为许可的数量有可能实时在改变，并不是固定的数量。
drainPermits()可获取并返回立即可用的所有许可个数，并且将可用许可置0。
示例代码参考example06包。

### 1.1.6 方法getQueueLength()和hasQueuedThreads()
方法getQueueLength()的作用是取得等待许可的线程个数。
方法hasQueuedThreads()的作用是判断有没有线程在等待这个许可。
这两个方法通常都是在判断当前有没有等待许可的线程信息时使用。
示例代码参考example07包。

### 1.1.7 公平与非公平信号量
有些时候，获得许可的顺序与线程启动的顺序有关，这时信号量就要分为公平与非公平的。
所谓的公平信号量是获得锁的顺序与线程启动的顺序有关，但不代表100%地获得信号量，
仅仅是在概率上能得到保证。而非公平信号量就是无关的了。
示例代码参考example08包。

### 1.1.8 方法tryAcquire()的使用
无参方法tryAcquire()的作用是尝试地获得1个许可，如果获取不到则返回false，此方法通常与if语句结合使用，其具有无阻塞的特点。
无阻塞的特点可以使线程不至于在同步处一直持续等待的状态，如果if语句判断不成立则线程会继续走else语句，程序会继续向下运行。
示例代码参考example09包。

### 1.1.9 方法tryAcquire(int permits)的使用
有参方法tryAcquire(int permits)的作用是尝试地获得x个许可，如果获取不到则返回false。
示例代码参考example09包。

### 1.1.10 方法tryAcquire(long timeout, TimeUnit unit)的使用
有参方法tryAcquire(int long timeout, TimeUnit unit)的作用是在指定的时间内尝试地获得1个许可，如果获取不到则返回false。
示例代码参考example09包。

### 1.1.11 方法tryAcquire(int permits, long timeout, TimeUnit unit)的使用
有参方法tryAcquire(int permits, long timeout, TimeUnit unit)的作用是在指定的时间内尝试地获得x个许可，如果获取不到则返回false。
示例代码参考example09包。

### 1.1.12 多进路-多处理-多出路实验
本实现的目标是允许多个线程同时处理任务，更具体来讲，也就是每个线程都在处理自己的任务。

示例代码参考example10包。

### 1.1.13 多进路-单处理-多出路实验
本实现的目标是允许多个线程同时处理任务，但执行任务的顺序却是同步的，也就是阻塞的，所以也称单处理。

示例代码参考example10包。

### 1.1.14 使用Semaphore创建字符串池

类Semaphore可以有效地对并发执行任务的线程数量进行限制，这种功能可以应用在pool池技术中，可以设置同时访问pool池中数据的线程数量。
本实验的功能是同时有若干个线程可以访问池中的数据，但同时只有一个线程可以取得数据，使用完毕后再放回池中。

示例代码参考example11包。

### 1.1.15 使用Semaphore实现多生产者/多消费者模式

本实验的目的不光是实现生产者与消费者模式，还要限制生产者与消费者的数量，这样代码的复杂性就提高一些，但好在使用Semaphore类实现这个功能还是比较简单的。

示例代码参考example12包。

## 1.2 Exchanger的使用

类Exchanger的功能**可以使2个线程之间传输数据**，它比生产者/消费者模式使用的wait/notify要更加方便。

### 1.2.1 方法exchange()阻塞的特性

类Exchanger中的exchange()方法具有**阻塞**的特色，也就是**此方法被调用后等待其他线程来取得数据，如果没有其他线程取得数据，则一直阻塞等待**。
示例代码参考example13包。

### 1.2.2 方法exchange()传递数据

示例代码参考example14包。

### 1.2.3 方法exchange(V x, long timeout, TimeUnit unit)与超时

当调用exchange(V x, long timeout, TimeUnit unit)方法后在指定的时间内没有其他线程获取数据，则出现超时异常。

示例代码参考example14包#ThreadC。

