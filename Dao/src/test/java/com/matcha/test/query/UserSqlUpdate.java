package com.matcha.test.query;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

import javax.sql.DataSource;
import java.sql.Types;

public class UserSqlUpdate extends SqlUpdate
{
    public UserSqlUpdate(DataSource dataSource)
    {
        super(dataSource, "UPDATE T_TEST SET FNAME = ? WHERE FID = ?");
        this.setParameters(new SqlParameter[]{
                new SqlParameter("FNAME", Types.VARCHAR),
                new SqlParameter("FID", Types.INTEGER)
        });
    }

    public int update(int id, String newName)
    {
        return this.update(newName, id);
    }
}
