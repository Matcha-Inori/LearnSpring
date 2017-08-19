package com.matcha.test.query;

import com.matcha.app.model.User;
import org.springframework.jdbc.object.MappingSqlQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserMappingSqlQuery extends MappingSqlQuery<User>
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
}
