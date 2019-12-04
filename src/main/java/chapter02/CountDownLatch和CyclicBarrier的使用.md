# 第二章：CountDownLatch和CyclicBarrier的使用

> 这两个工具将同步与线程“组团”做任务完美进行了支持。

## 2.1 CountDownLatch的使用

类CountDownLatch所提供的功能是**判断count计数不为0时则当前线程呈wait状态**，也就是在屏障处等待。类CountDownLatch也是一个同步功能的辅助类，使用效果是给定一个计数，当使用这个CountDownLatch类的线程判断计数不为0时，则呈wait状态，如果为0时则继续运行。实现等待与继续运行的效果分别需要使用await()和countDown()方法来进行。调用await()方法时判断计数是否为0，如果不为0则呈等待状态。其他线程可以调用count-Down()方法将计数减1，当计数减到为0时，呈等待的线程继续运行。而方法getCount()就是获得当前的计数个数。**计数无法被重置**，如果需要重置计数，请考虑使用CyclicBarrier类。**类CountDownLatch的计数是减法操作。**

### 2.1.1 初步使用

在本节将实现一个当CountDownLatch的计数不为0时，线程呈阻塞状态的效果。通过本实验可以看到线程执行任务具有“组团”的特性了。

示例代码参考example01包。

### 2.1.2 裁判在等全部的运动员到来

本实验想要实现的是裁判员要等待所有运动员全部到来的效果，主要论证的就是多个线程与同步点间阻塞的特性，线程必须都到达同步点后才可以继续向下运行。

示例代码参考example02包。

### 2.1.3 各就各位准备比赛

本实验想要实现的是裁判员要等待所有运动员各就各位后全部准备完毕，再开始比赛的效果。此实验虽然运行成功，但并不能保证在main主线程中执行`service.doRun()`时，所有的工作线程都呈wait状态，因为某些线程有可能准备的时间花费较长，方法就达不到“唤醒所有线程”继续向下运行的目的了，也就是说裁判员没有等全部的运动员到来时，就让发令枪响起开始比赛了，这是不对的，所以就需要对代码进行修改，来达到相对比较完善的比赛流程。

示例代码参考example03包。

### 2.1.4 完整的比赛流程

本节要使用CountDownLatch类来实现“所有的线程”呈wait后再统一唤醒的效果，此实验大量使用CountDownLatch类来实现业务要求的同步效果。

示例代码参考example04包。

### 2.1.5 方法await(long timeout, TimeUnit unit)

方法await(long timeout, TimeUnit unit)的作用使**线程在指定的最大时间单位内进入WAITING状态，如果超过这个时间则自动唤醒，程序继续向下运行**。参数timeout是等待的时间，而unit参数是时间的单位。

示例代码参考example05包。

### 2.1.6 方法getCount()的使用

方法getCount()获取当前计数的值。

示例代码参考example06包。

## 2.2 CyclicBarrier的使用

类CyclicBarrier不仅有CountDownLatch所具有的功能，还可以实现屏障等待的功能，也就是阶段性同步，它在使用上的意义在于可以循环地实现线程要一起做任务的目标，而不是像类CountDownLatch一样，仅仅支持一次线程与同步点阻塞的特性。

类CyclicBarrier和Semaphore及CountDown-Latch一样，也是一个同步辅助类。它允许一组线程互相等待，直到到达某个公共屏障点（common barrier point），这些线程必须实时地互相等待，这种情况下就可以使用CyclicBarrier类来方便地实现这样的功能。CyclicBarrier类的公共屏障点可以重用，所以类的名称中有“cyclic循环”的单词。
通过上面段落中的文字可以发现，CyclicBarrier类与CountDownLatch类在功能上有些相似，但在细节上还是有一些区别。

1. CountDownLatch作用：一个线程或者多个线程，等待另外一个线程或多个线程完成某个事情之后才能继续执行。
2. CyclicBarrier的作用：多个线程之间相互等待，任何一个线程完成之前，所有的线程都必须等待，所以对于CyclicBarrier来说，重点是**“多个线程之间”任何一个线程没有完成任务，则所有的线程都必须等待**。

CountDownLatch类的使用情况是两个角色之间互相等待，而Cyclic-Barrier的使用情况是同类互相等待。
和CountDownLatch类不同，类CyclicBarrier的计数是加法操作。

### 2.2.1 初步使用

CountDownLatch类的计数不可以重置，想要再次获得同步的功能只有通过添加代码，增加代码的复杂度才可以换取想要实现的功能。在处理某些特殊业务的要求时，通常可以将CountDownLatch和CyclicBarrier类联合使用。

示例代码参考example07包。

### 2.2.2 验证屏障重置性及getNumberWaiting()方法的使用

类CyclicBarrier具有屏障重置性，本节将要结合方法getNumberWaiting()进行实验，该方法的作用是获得有几个线程已经到达屏障点。

示例代码参考example08包。

### 2.2.3 用CyclicBarrier类实现阶段跑步比赛

在本节中将丰富跑步比赛这个案例的内容，借助CyclicBarrier类具有计数重置性实现多赛段的比赛实验。

示例代码参考example09包。

### 2.2.4 方法isBroken()的使用

方法isBroken()查询此屏障是否处于损坏状态。

类CyclicBarrier对于线程的中断interrupte处理会使用全有或者全无的破坏模型（breakage model），意思是如果有一个线程由于中断或者超时提前离开了屏障点，其他所有在屏障点等待的线程也会抛出BrokenBarrierException或者InterruptedException异常，并且离开屏障点。

示例代码参考example10包。

### 2.2.5 方法await(long timeout, TimeUnit unit)超时出现异常的测试

方法await(long timeout, TimeUnit unit)的功能是如果在指定的时间内达到parties的数量，则程序继续向下运行，否则如果出现超时，则抛出TimeoutException异常。

示例代码参考example11包。

### 2.2.6 方法getNumberWaiting()和getParties()

方法getNumberWaiting()的作用是有几个线程已经到达屏障点。
方法getParties()的作用是取得parties个数。

示例代码参考example12包。

### 2.2.7 方法reset()

方法reset()的作用是重置屏障。

示例代码参考example13包。

## 2.3 本章总结

本章主要介绍了CountDownLatch、CyclicBarrier这两个类的使用，**使用CountDown-Latch类可以实现两种角色的线程等待对方的效果，而CyclicBarrier类可以使同类线程互相等待达到同步的效果，使用这两个类可以更加完善地实现线程对象之间的同步性**，对线程对象执行的轨迹控制更加方便。