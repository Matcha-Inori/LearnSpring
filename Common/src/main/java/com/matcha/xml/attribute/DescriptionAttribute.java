package com.matcha.xml.attribute;

/**
 * Created by Matcha on 2017/4/24.
 */
public enum  DescriptionAttribute
{
    NAME("name"),
    VALUE("value");

    private String attributeName;

    DescriptionAttribute(String attributeName)
    {
        this.attributeName = attributeName;
    }

    public String getAttributeName()
    {
        return attributeName;
    }
}
