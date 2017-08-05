package com.matcha.aop;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Created by Matcha on 2017/5/21.
 */
public class MyAdvice
{
    public void beforeMethod()
    {
        System.out.println("MyAdvice.beforeMethod");
    }

    public void afterMethod()
    {
        System.out.println("MyAdvice.afterMethod");
    }

    public Object aroundMethod(ProceedingJoinPoint proceedingJoinPoint)
    {
        System.out.println("MyAdvice.aroundMethod before");
        Object returnValue;
        try
        {
            returnValue = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
            throw new RuntimeException(throwable);
        }
        return returnValue;
    }

    public void afterReturning(Object returnValue)
    {
        System.out.println("MyAdvice.afterReturning");
    }
}
