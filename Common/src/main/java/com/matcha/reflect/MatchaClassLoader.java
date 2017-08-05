package com.matcha.reflect;

import com.matcha.app.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Administrator on 2017/4/26.
 */
public class MatchaClassLoader extends ClassLoader
{
    private static final AtomicLong nextId;

    static
    {
        nextId = new AtomicLong(0);
    }

    private long id;

    public MatchaClassLoader()
    {
        this.id = nextId.incrementAndGet();
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException
    {
        if(StringUtil.isEmpty(name)) throw new ClassNotFoundException("class name can not be empty string");
        Class<?> loadedClass = findLoadedClass(name);
        if(loadedClass != null) return loadedClass;
        synchronized (this.getClassLoadingLock(name))
        {
            if(!name.startsWith("com.matcha.self.")) return super.loadClass(name, resolve);
            System.out.println(String.format("matcha class loader try load class %s", name));
            String classPath = String.format("%s.class", name.replaceAll("\\.", "/"));
            URL classURL = this.getResource(classPath);
            if(classURL == null) return super.loadClass(name, resolve);
            Class<?> theClass;
            if("file".equals(classURL.getProtocol()))
                theClass = loadClassFormFile(name, classURL);
            else
                theClass = loadClassFormURL(name, classURL);
            if(resolve)
                this.resolveClass(theClass);
            return theClass;
        }
    }

    private Class<?> loadClassFormURL(String name, URL classURL) throws ClassNotFoundException
    {
        try
        {
            InputStream inputStream = classURL.openStream();
            int available = inputStream.available();
            byte[] classBytes = new byte[available];
            inputStream.read(classBytes);
            return this.defineClass(name, classBytes, 0, classBytes.length);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new ClassNotFoundException("load class from file fail", e);
        }
    }

    private Class<?> loadClassFormFile(String name, URL classURL) throws ClassNotFoundException
    {
        try
        {
            URI classURI = classURL == null ? null : classURL.toURI();
            Path path = classURI == null ? null : Paths.get(classURI);
            if(path == null)
                throw new ClassNotFoundException(
                        String.format(
                                "can not find file from url: %s",
                                classURL.toString()
                        )
                );
            byte[] classBytes = Files.readAllBytes(path);
            return this.defineClass(name, classBytes, 0, classBytes.length);
        }
        catch (URISyntaxException | IOException e)
        {
            e.printStackTrace();
            throw new ClassNotFoundException("load class from file fail", e);
        }
    }
}
