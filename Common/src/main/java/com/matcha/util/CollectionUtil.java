package com.matcha.util;

import java.util.Collection;

/**
 * Created by Matcha on 2017/4/24.
 */
public class CollectionUtil
{
    public static boolean isEmpty(Collection<?> collection)
    {
        return collection == null || collection.isEmpty();
    }

    public static <T> boolean contains(Collection<T> collection, T element)
    {
        if(isEmpty(collection))
            return false;

        return collection.contains(element);
    }

    public static <T> boolean contains(Collection<T> firstCollection, Collection<T> otherCollection)
    {
        if(isEmpty(firstCollection) || isEmpty(otherCollection)) return false;
        for(T oneOfFirst : firstCollection)
            for(T oneOfOther : otherCollection)
                if(EqualsUtil.equals(oneOfFirst, oneOfOther)) return true;
        return false;
    }

}
