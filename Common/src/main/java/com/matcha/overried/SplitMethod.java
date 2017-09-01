package com.matcha.overried;

import org.springframework.beans.factory.support.MethodReplacer;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/4/27.
 */
public class SplitMethod implements MethodReplacer
{
    @Override
    public Object reimplement(Object obj, Method method, Object[] args) throws Throwable
    {
        return splitMethod((String) args[0], (String) args[1]);
    }

    protected Object splitMethod(String argument1, String argument2)
    {
        Pattern pattern = Pattern.compile(argument1);
        return pattern.split(argument2);
    }
}
