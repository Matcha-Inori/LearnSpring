package com.matcha.test;

import com.matcha.mapper.BookMapper;
import com.matcha.model.Book;
import com.matcha.model.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

public class TestMybatis extends BaseTest
{
    private SqlSessionFactory sqlSessionFactory;

    public TestMybatis()
    {
        sqlSessionFactory = context.getBean("mybatisSessionFactory", SqlSessionFactory.class);
    }

    @Test
    public void testUser() throws Exception
    {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = sqlSession.selectOne(
                "com.matcha.app.model.UserMapper.selectUserByName",
                "Riven"
        );
        System.out.println(user);
    }

    @Test
    public void testInsertBook() throws Exception
    {
        Book book = new Book();
        book.setId(1);
        book.setName("First Book");
        book.setNumber("Number of First Book");
        book.setPrice(12.34d);
        SqlSession sqlSession = null;
        try
        {
            sqlSession = sqlSessionFactory.openSession();
            BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);
            bookMapper.insertBook(book);
            sqlSession.commit();
        }
        catch (Exception exception)
        {
            if(sqlSession != null)
                sqlSession.rollback();
            throw new RuntimeException(exception);
        }
        finally
        {
            if(sqlSession != null)
                sqlSession.close();
        }
    }

    @Test
    public void testQueryBook() throws Exception
    {
        try(SqlSession sqlSession = sqlSessionFactory.openSession())
        {
            BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);
            Book book = bookMapper.selectBookById(1);
            System.out.println(book);
        }
    }
}
