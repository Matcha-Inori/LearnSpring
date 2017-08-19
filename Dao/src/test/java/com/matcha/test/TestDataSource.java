package com.matcha.test;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public class TestDataSource extends BaseTest
{
    private DataSource basicDataSource;

    public TestDataSource()
    {
        basicDataSource = context.getBean("basicDataSource", DataSource.class);
    }

    @Test
    public void testBasicDataSource() throws Exception
    {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(basicDataSource);
        List<Map<String, Object>> result = jdbcTemplate.queryForList("SELECT * FROM T_TEST");
        System.out.println(result);
    }
}
