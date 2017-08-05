package com.matcha.app.util;

/**
 * Created by Matcha on 2017/4/24.
 */
public class ArrayUtil
{
    public static <T> boolean isEmpty(T[] array)
    {
        return array == null || array.length == 0;
    }

    public static <T> boolean contains(T[] array, T element)
    {
        if(isEmpty(array))
            return false;
        for(T oneOfArray : array)
            if(EqualsUtil.equals(oneOfArray, element)) return true;
        return false;
    }
}
