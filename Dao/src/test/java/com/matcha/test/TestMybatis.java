package com.matcha.test;

import com.matcha.app.model.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

public class TestMybatis extends BaseTest
{
    @Test
    public void testUser() throws Exception
    {
        SqlSessionFactory sqlSessionFactory = context.getBean("mybatisSessionFactory", SqlSessionFactory.class);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = sqlSession.selectOne(
                "com.matcha.app.model.UserMapper.selectUserByName",
                "Riven"
        );
        System.out.println(user);
    }
}
