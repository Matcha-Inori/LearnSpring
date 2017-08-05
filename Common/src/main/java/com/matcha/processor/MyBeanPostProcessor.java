package com.matcha.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.SmartLifecycle;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2017/5/15.
 */
public class MyBeanPostProcessor implements BeanPostProcessor, SmartLifecycle
{
    private Path filePath;
    private SeekableByteChannel fileChannel;
    private ByteBuffer byteBuffer;
    private ReentrantLock lock;
    private Charset charset;
    private int byteBufferSize;

    public MyBeanPostProcessor(String path)
    {
        int lastIndex = path.lastIndexOf('.');
        if(lastIndex > 0)
        {
            StringBuffer newPathBuffer = new StringBuffer();
            String name = path.substring(0, lastIndex);
            newPathBuffer.append(name).append("-")
                    .append(UUID.randomUUID().toString()).append(path.substring(lastIndex));
            path = newPathBuffer.toString();
        }
        URI pathURI = findPath(path);
        init(pathURI);
    }

    public MyBeanPostProcessor(URI path)
    {
        init(path);
    }

    private void init(URI path)
    {
        this.filePath = Paths.get(path);
        this.fileChannel = null;
        this.byteBuffer = null;
        this.lock = new ReentrantLock(false);
        this.charset = Charset.forName("UTF-16");
        this.byteBufferSize = 128;
        this.start();
    }

    private URI findPath(String path)
    {
        try
        {
            ClassLoader classLoader = this.getClassLoader();
            URL pathURL = classLoader.getResource(path);
            URI pathURI = pathURL == null || (pathURI = pathURL.toURI()) == null ? URI.create(path) : pathURI;
            return pathURI;
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private ClassLoader getClassLoader()
    {
        ClassLoader classLoader;
        Thread currentThread = Thread.currentThread();
        classLoader = currentThread.getContextClassLoader();
        if(classLoader != null) return classLoader;
        classLoader = this.getClass().getClassLoader();
        if(classLoader != null) return classLoader;
        return ClassLoader.getSystemClassLoader();
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException
    {
        boolean getLock = tryGetLock(3, TimeUnit.SECONDS);
        if(!getLock) return bean;
        try
        {
            StringBuffer logBuffer = new StringBuffer(byteBufferSize);
            logBuffer.append(beanName)
                    .append(" : ")
                    .append(bean.getClass().getName())
                    .append(" - before")
                    .append('\n');
            writeLog(logBuffer.toString());
        }
        finally
        {
            this.lock.unlock();
        }
        return bean;
    }

    private void writeLog(String log)
    {
        try
        {
            byte[] logBytes = log.getBytes(charset);
            this.byteBuffer.clear();
            int remaining;
            int readLength;
            for(int offset = 0;offset < logBytes.length;)
            {
                remaining = logBytes.length - offset;
                readLength = remaining < byteBufferSize ? remaining : byteBufferSize;
                byteBuffer.put(logBytes, offset, readLength);
                offset += readLength;
                if(!byteBuffer.hasRemaining())
                {
                    this.byteBuffer.flip();
                    this.fileChannel.write(this.byteBuffer);
                    this.byteBuffer.clear();
                }
            }
            this.byteBuffer.flip();
            if(this.byteBuffer.hasRemaining()) this.fileChannel.write(this.byteBuffer);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException
    {
        boolean getLock = tryGetLock(3, TimeUnit.SECONDS);
        if(!getLock) return bean;
        try
        {
            StringBuffer logBuffer = new StringBuffer(byteBufferSize);
            logBuffer.append(beanName)
                    .append(" : ")
                    .append(bean.getClass().getName())
                    .append(" - after")
                    .append('\n');
            writeLog(logBuffer.toString());
        }
        finally
        {
            this.lock.unlock();
        }
        return bean;
    }

    private boolean tryGetLock(long time, TimeUnit timeUnit)
    {
        boolean getLock = false;
        try
        {
            getLock = this.lock.tryLock(time, timeUnit);
        }
        catch (InterruptedException e)
        {
        }
        return getLock;
    }

    @Override
    public boolean isAutoStartup()
    {
        return true;
    }

    @Override
    public void stop(Runnable callback)
    {
        if(!isRunning()) return;
        this.stop();
        callback.run();
    }

    @Override
    public void start()
    {
        if(isRunning()) return;
        try
        {
            Files.deleteIfExists(this.filePath);
            Files.createFile(this.filePath);
            this.fileChannel = Files.newByteChannel(this.filePath, StandardOpenOption.WRITE);
            this.byteBuffer = ByteBuffer.allocateDirect(byteBufferSize);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            if(this.fileChannel != null)
            {
                try
                {
                    this.fileChannel.close();
                }
                catch (IOException closeException)
                {
                }
                finally
                {
                    this.fileChannel = null;
                    this.byteBuffer = null;
                }
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop()
    {
        try
        {
            if(this.fileChannel != null) this.fileChannel.close();
        }
        catch (IOException e)
        {
        }
        finally
        {
            this.fileChannel = null;
            this.byteBuffer = null;
        }
    }

    @Override
    public boolean isRunning()
    {
        return this.fileChannel != null;
    }

    @Override
    public int getPhase()
    {
        return 0;
    }
}
