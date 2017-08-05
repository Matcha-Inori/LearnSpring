package com.matcha.xml.element;

/**
 * Created by Matcha on 2017/4/24.
 */
public enum  DescriptionElement
{
    DESCRIPTION("description");
    private String elementName;

    DescriptionElement(String elementName)
    {
        this.elementName = elementName;
    }

    public String getElementName()
    {
        return elementName;
    }
}
