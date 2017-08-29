package com.matcha.dao;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlFunction;
import org.springframework.jdbc.object.SqlUpdate;

import javax.sql.DataSource;
import java.sql.Types;

public class BankDao implements IBankDao
{
    private DataSource dataSource;

    @Override
    public boolean transfer(int fromAccountId, int toAccountId, long amount)
    {
        SqlFunction<Long> queryAmount = new SqlFunction();
        queryAmount.setDataSource(dataSource);
        queryAmount.setSql("SELECT FAMOUNT FROM T_BANK_ACCOUNT WHERE FID = ?");
        queryAmount.setParameters(new SqlParameter[]{
                new SqlParameter("FAMOUNT", Types.BIGINT)
        });
        Long fromAmount = (Long) queryAmount.runGeneric(fromAccountId);
        if(fromAmount < amount)
            return false;
        Long toAmount = (Long) queryAmount.runGeneric(toAccountId);
        Long newFromAmount = fromAmount - amount;
        Long newToAmount = toAmount + amount;
        SqlUpdate updateAmount = new SqlUpdate();
        updateAmount.setDataSource(dataSource);
        updateAmount.setSql("UPDATE T_BANK_ACCOUNT SET FAMOUNT = ? WHERE FID = ?");
        updateAmount.setParameters(new SqlParameter[]{
                new SqlParameter("FAMOUNT", Types.BIGINT),
                new SqlParameter("FID", Types.INTEGER)
        });
        updateAmount.update(newFromAmount, fromAccountId);
        updateAmount.update(newToAmount, toAccountId);
        return true;
    }

    public DataSource getDataSource()
    {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }
}
