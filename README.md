# java-concurrent-api
> java并发编程：核心方法与框架
## 第1章讲解了Semaphore和Exchanger类的使用，学完本章后，能更好地控制线程间的同步性，以及线程间如何更好、更方便地传输数据。

## 第2章主要讲解了CountDownLatch、CyclicBarrier类的使用及在Java并发包中对并发访问的控制。本章主要包括Semaphore、CountDownLatch和CyclicBarrier的使用。

## 第3章，由于CountDownLatch和CyclicBarrier类都有相应的弊端，所以在JDK1.7中新增加了Phaser类来解决这些缺点。

## 第4章中讲解了Executor接口与ThreadPoolExecutor线程池的使用，可以说本章中的知识也是Java并发包中主要的应用技术点，线程池技术也在众多的高并发业务环境中使用。掌握线程池能更有效地提高程序运行效率，更好地统筹线程执行的相关任务。

## 第5章中讲解Future和Callable的使用，接口Runnable并不支持返回值，但在有些情况下真的需要返回值，所以Future就是用来解决这样的问题的。

## 第6章介绍Java并发包中的CompletionService的使用，该接口可以增强程序运行效率，因为可以以异步的方式获得任务执行的结果。

## 第7章主要介绍的是ExecutorService接口，该接口提供了若干方法来方便地执行业务，是比较常见的工具接口对象。

## 第8章主要介绍计划任务ScheduledExecutorService的使用，学完本章可以掌握如何将计划任务与线程池结合使用。

## 第9章主要介绍Fork-Join分治编程。分治编程在多核计算机中应用很广，它可以将大的任务拆分成小的任务再执行，最后再把执行的结果聚合到一起，完全利用多核CPU的优势，加快程序运行效率。

## 第10章主要介绍并发集合框架。Java中的集合在开发项目时占有举足轻重的地位，在Java并发包中也提供了在高并发环境中使用的Java集合工具类，读者需要着重掌握Queue接口的使用。