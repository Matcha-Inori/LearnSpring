package com.matcha.test;

import com.matcha.app.model.User;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.object.MappingSqlQuery;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TestJdbcTemplate
{
    private static final String springConfigPosition;

    static
    {
        springConfigPosition = "spring/transaction.xml";
    }

    private ConfigurableApplicationContext context;
    private DataSource dataSource;

    public TestJdbcTemplate()
    {
        context = new ClassPathXmlApplicationContext(springConfigPosition);
        context.registerShutdownHook();
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
        MappingSqlQuery<User> mappingSqlQuery = new MappingSqlQuery<User>()
        {
            @Override
            protected User mapRow(ResultSet rs, int rowNum) throws SQLException
            {
                String userUuidStr = rs.getString("FID");
                UUID userUuid = UUID.fromString(userUuidStr);
                String userName = rs.getString("FNAME");
                int userAge = rs.getInt("FAGE");
                User user = new User();
                user.setId(userUuid);
                user.setName(userName);
                user.setAge(userAge);
                return user;
            }
        };
        mappingSqlQuery.setDataSource(dataSource);
        mappingSqlQuery.setSql("SELECT * FROM T_USER");
        List<User> userList = mappingSqlQuery.execute();
        System.out.println(userList);
    }
}
