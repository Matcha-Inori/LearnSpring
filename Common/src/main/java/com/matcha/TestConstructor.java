package com.matcha;

import java.net.URL;

/**
 * Created by Matcha on 2017/6/5.
 */
public class TestConstructor
{
    private String argumentString;
    private URL argumentURL;
    private int argumentInt;

    public TestConstructor()
    {
    }

    public TestConstructor(String argumentString, URL argumentURL, int argumentInt)
    {
        this.argumentString = argumentString;
        this.argumentURL = argumentURL;
        this.argumentInt = argumentInt;
    }

    @Override
    public String toString()
    {
        return "TestConstructor{" +
                "argumentString='" + argumentString + '\'' +
                ", argumentURL=" + argumentURL +
                ", argumentInt=" + argumentInt +
                '}';
    }
}
