package com.matcha.convert;

import com.matcha.util.ClassLoaderFinder;
import org.springframework.core.convert.converter.Converter;

import java.net.URL;

public class MyConverter implements Converter<String, URL>
{
    @Override
    public URL convert(String source)
    {
        ClassLoader classLoader = ClassLoaderFinder.findClassLoader();
        return classLoader.getResource(source);
    }
}
