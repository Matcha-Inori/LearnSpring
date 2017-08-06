package com.matcha.util;

public class ClassLoaderFinder
{
    public static ClassLoader findClassLoader()
    {
        Thread currentThread = Thread.currentThread();
        ClassLoader classLoader = currentThread.getContextClassLoader();
        if(classLoader != null)
            return classLoader;
        classLoader = ClassLoaderFinder.class.getClassLoader();
        if(classLoader != null)
            return classLoader;
        return ClassLoader.getSystemClassLoader();
    }
}
