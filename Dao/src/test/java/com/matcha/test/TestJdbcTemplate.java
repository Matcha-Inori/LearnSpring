package com.matcha.test;

import com.matcha.app.model.User;
import com.matcha.test.query.UserMappingSqlQuery;
import com.matcha.test.query.UserSqlUpdate;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlFunction;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;
import java.util.Map;

public class TestJdbcTemplate extends BaseTest
{
    private DataSource dataSource;

    public TestJdbcTemplate()
    {
        dataSource = context.getBean("mySqlDataSource", DataSource.class);
    }

    @Test
    public void testQuery()
    {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> result = jdbcTemplate.queryForList("SELECT * FROM T_TEST");
        System.out.println(result);
    }

    @Test
    public void testMappingSqlQuery()
    {
        MappingSqlQuery<User> mappingSqlQuery = new UserMappingSqlQuery();
        mappingSqlQuery.setDataSource(dataSource);
        mappingSqlQuery.setSql("SELECT * FROM T_USER");
        List<User> userList = mappingSqlQuery.execute();
        System.out.println(userList);
    }

    @Test
    public void testMappingSqlQueryWithParam()
    {
        MappingSqlQuery<User> mappingSqlQuery = new UserMappingSqlQuery();
        mappingSqlQuery.setDataSource(dataSource);
        mappingSqlQuery.setSql("SELECT * FROM T_USER WHERE FNAME = ?");
        mappingSqlQuery.setParameters(new SqlParameter[]{
                new SqlParameter("FNAME", Types.VARCHAR)
        });
        List<User> userList = mappingSqlQuery.execute("Matcha");
        System.out.println(userList);
    }

    @Test
    public void testSqlUpdate() throws Exception
    {
        UserSqlUpdate userSqlUpdate = new UserSqlUpdate(dataSource);
        userSqlUpdate.update(1, "Name_AAAAAA");
    }

    @Test
    public void testSqlFunction() throws Exception
    {
        SqlFunction<User> userSqlFunction = new SqlFunction<>();
        userSqlFunction.setDataSource(dataSource);
        userSqlFunction.setSql("SELECT count(1) FROM T_USER");
        userSqlFunction.compile();
        userSqlFunction.run();
    }
}
