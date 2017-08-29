package com.matcha.test.synchronization;

import org.springframework.transaction.support.TransactionSynchronization;

public class MyTransactionSynchronization implements TransactionSynchronization
{
    @Override
    public void suspend()
    {
        System.out.println("MyTransactionSynchronization - suspend");
    }

    @Override
    public void resume()
    {
        System.out.println("MyTransactionSynchronization - resume");
    }

    @Override
    public void flush()
    {
        System.out.println("MyTransactionSynchronization - flush");
    }

    @Override
    public void beforeCommit(boolean readOnly)
    {
        System.out.println("MyTransactionSynchronization - beforeCommit");
    }

    @Override
    public void beforeCompletion()
    {
        System.out.println("MyTransactionSynchronization - beforeCompletion");
    }

    @Override
    public void afterCommit()
    {
        System.out.println("MyTransactionSynchronization - afterCommit");
    }

    @Override
    public void afterCompletion(int status)
    {
        System.out.println("MyTransactionSynchronization - afterCompletion");
    }
}
