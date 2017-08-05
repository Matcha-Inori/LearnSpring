package com.matcha.aop;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * Created by Matcha on 2017/5/21.
 */
public class SpringAdvice implements AfterReturningAdvice, MethodBeforeAdvice
{
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable
    {
        System.out.println("SpringAdvice.afterReturning");
    }

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable
    {
        System.out.println("SpringAdvice.before");
    }
}
