package com.matcha.lifecycle;

import org.springframework.context.SmartLifecycle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Administrator on 2017/5/15.
 */
public class ThreadPoolSmartLifecycle implements SmartLifecycle, ThreadFactory
{
    private volatile ExecutorService executorService;
    private volatile ThreadGroup threadGroup;
    private AtomicLong threadCount;

    public ThreadPoolSmartLifecycle()
    {
        this.executorService = null;
        this.threadGroup = new ThreadGroup(this.getClass().getName());
        this.threadCount = new AtomicLong(0);
    }

    @Override
    public Thread newThread(Runnable r)
    {
        if(threadGroup == null || threadGroup.isDestroyed())
            threadGroup = new ThreadGroup(this.getClass().getName());
        long threadId = threadCount.incrementAndGet();
        return new Thread(threadGroup, r, String.format("%s thread - %d", this.getClass().getName(), threadId));
    }

    @Override
    public boolean isAutoStartup()
    {
        return true;
    }

    @Override
    public void stop(Runnable callback)
    {
        this.stop();
        callback.run();
    }

    @Override
    public void start()
    {
        if(isRunning())
            this.executorService.shutdownNow();
        this.executorService = Executors.newFixedThreadPool(30, this);
    }

    @Override
    public void stop()
    {
        if(isRunning())
            this.executorService.shutdownNow();
        this.executorService = null;
    }

    @Override
    public boolean isRunning()
    {
        return this.executorService != null && !this.executorService.isShutdown();
    }

    @Override
    public int getPhase()
    {
        return 0;
    }
}
