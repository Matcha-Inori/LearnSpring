package com.matcha.app.util;

/**
 * Created by Matcha on 2017/4/24.
 */
public class EqualsUtil
{
    public static boolean equals(Object firstObj, Object otherObj)
    {
        if(firstObj == null) return otherObj == null;
        return firstObj.equals(otherObj);
    }
}
