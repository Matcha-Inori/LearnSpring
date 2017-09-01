package com.matcha.thread;

import com.matcha.circulation.ICirculationReference;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Matcha on 2017/5/9.
 */
public class ApplicationContextRunnable implements Runnable
{
    private static final AtomicLong nextId;

    static
    {
        nextId = new AtomicLong(0);
    }

    private long id;
    private CyclicBarrier cyclicBarrier;
    private ApplicationContext applicationContext;

    public ApplicationContextRunnable(CyclicBarrier cyclicBarrier, ApplicationContext applicationContext)
    {
        this.id = nextId.getAndIncrement();
        this.cyclicBarrier = cyclicBarrier;
        this.applicationContext = applicationContext;
    }

    @Override
    public void run()
    {
        try
        {
            this.cyclicBarrier.await();
            ICirculationReference referenceOne = this.applicationContext.getBean(
                    id % 2 == 0 ? "circulationReferenceA" : "circulationReferenceB",
                    ICirculationReference.class
            );
            ICirculationReference referenceTwo = referenceOne.getReference();
            boolean isNull = referenceTwo.getReference() == null;
            System.out.println(String.format("thread - %d get : %s", id, String.valueOf(isNull)));
        }
        catch (InterruptedException | BrokenBarrierException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
