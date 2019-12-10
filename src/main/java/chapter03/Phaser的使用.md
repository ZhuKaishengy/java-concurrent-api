# 第3章Phaser的使用

> 通过使用CyclicBarrier类解决了CountDownLatch类的种种缺点，但不可否认的是，CyclicBarrier类还是有一些自身上的缺陷，比如**不可以动态添加parties计数，调用一次await()方法仅仅占用1个parties计数**，所以在JDK1.7中新增加了一个名称为Phaser的类来解决这样的问题。

## 3.1 Phaser的使用

中文翻译为移相器，类Phaser对计数的操作是加法操作。

## 3.2 类Phaser的arriveAndAwaitAdvance()方法测试1

方法arriveAndAwaitAdvance()的作用与CountDownLatch类中的await()方法大体一样，通过从方法的名称解释来看，arrive是到达的意思，wait是等待的意思，而advance是前进、促进的意思，所以**执行这个方法的作用就是当前线程已经到达屏障，在此等待一段时间，等条件满足后继续向下一个屏障继续执行**。
通过前面的解释可以发现，类Phaser具有设置多屏障的功能，有些类似于体育竞赛中“赛段”的作用，运动员第一赛段结束后，开始休整准备，然后集体到达第二赛段的起跑点，等待比赛开始后，运动员们又继续比赛了，说明Phaser类与CyclicBarrier类在功能上有重叠。下面的章节将使用Phaser来实现一个比赛过程中的“多赛段”问题。

示例代码参考example01包。

## 3.3 类Phaser的arriveAndAwaitAdvance()方法测试2

本节还是使用方法arriveAndAwaitAdvance()来进行实验，实验的目标就是当计数不足时，线程呈阻塞状态，不继续向下运行。

示例代码参考example01包。

## 3.4 类Phaser的arriveAndDeregister()方法测试

方法arriveAndDeregister()的作用是使当前线程（运动员）退出比赛，并且使parties值减1。

示例代码参考example02包。

## 3.5 类Phaser的getPhase()和onAdvance()方法测试

方法getPhase()获取的是已经到达第几个屏障。方法onAdvance()的作用是通过新的屏障时被调用，返回true不等待了，Phaser呈无效/销毁的状态。

示例代码参考example03包。

## 3.6 类Phaser的getRegisteredParties()方法和register()测试

方法getRegisteredParties()获得注册的parties数量。每执行一次方法register()就动态添加一个parties值。

示例代码参考example04包。

## 3.7 类Phaser的bulkRegister()方法测试

方法bulkRegister()可以批量增加parties数量。

示例代码参考example04包。

## 3.8 类Phaser的getArrivedParties()和getUnarrivedParties()方法测试

方法getArrivedParties()获得已经被使用的parties个数。方法getUnarrivedParties()获得未被使用的parties个数。

示例代码参考example04包。

## 3.9 类Phaser的arrive()方法测试1

方法arrive()的功能是使getArrivedParties()计数加1，不等待其他线程到达屏障。Phaser类在经过屏障点后计数功能被重置。

示例代码参考example05包。

## 3.10 类Phaser的arrive()方法测试2

本节的实验目标还是测试当计数不足时，线程A和B依然呈等待状态。

示例代码参考example05包。

## 3.11 类Phaser的awaitAdvance(int phase)方法测试1

方法awaitAdvance(int Phase)的作用是：如果传入参数phase值和当前getPhase()方法返回值一样，则在屏障处等待，否则继续向下面运行，有些类似于旁观者的作用，当观察的条件满足了就等待（旁观），如果条件不满足，则程序向下继续运行。方法awaitAdvance(int Phase)并不参与parties计数的操作，仅仅具有判断的功能。

示例代码参考example06包。

## 3.12 类Phaser的awaitAdvance(int phase)方法测试2

方法awaitAdvance(int)是不可中断的。

示例代码参考example06包。

## 3.13 类Phaser的awaitAdvanceInterruptibly(int)方法测试1

方法awaitAdvanceInterruptibly(int)是可中断的。

示例代码参考example07包。

## 3.14 类Phaser的awaitAdvanceInterruptibly(int)方法测试2

方法awaitAdvanceInterruptibly(int)的作用是当线程执行的栏数不符合指定的参数值时，则继续执行下面的代码。

示例代码参考example07包。

## 3.15 类Phaser的awaitAdvanceInterruptibly(int, long, TimeUnit)方法测试3

方法awaitAdvanceInterruptibly(int, long, TimeUnit)的作用是在指定的栏数等待最大的单位时间，如果在指定的时间内，栏数未变，则出现异常，否则继续向下运行。

示例代码参考example08包。

## 3.16 类Phaser的forceTermination()和isTerminated()方法测试

方法forceTermination()使Phaser对象的屏障功能失效，而方法isTerminated()是判断Phaser对象是否已经呈销毁状态。类Phaser执行forceTermination()方法时仅仅将屏障取消，线程继续执行后面的代码，并不出现异常，而CyclicBarrier类的reset()方法执行时却出现异常

示例代码参考example09包。

## 3.17 控制Phaser类的运行时机

前面的实验都是线程一起到达屏障后继续运行，有些情况下是需要进行控制的，也就是到达屏障后不允许继续运行。此实验说明运行的时机是可以通过逻辑控制的，主要的原理就是计数+1，然后通过逻辑代码的方式来决定线程是否继续向下运行

示例代码参考example10包。

## 3.18 本章总结

类Phaser提供了动态增减parties计数，这点比CyclicBarrier类操作parties更加方便，通过若干个方法来控制多个线程之间同步运行的效果，还可以实现针对某一个线程取消同步运行的效果，而且支持在指定屏障处等待，在等待时还支持中断或非中断等功能，使用Java并发类对线程进行分组同步控制时，Phaser比CyclicBarrier类功能更加强大，建议使用。