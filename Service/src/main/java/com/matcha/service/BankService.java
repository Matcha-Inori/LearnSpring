package com.matcha.service;

import com.matcha.dao.IBankDao;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

public class BankService implements IBankService
{
    private IBankDao iBankDao;
    private DataSourceTransactionManager dataSourceTransactionManager;
    private TransactionDefinition transactionDefinition;

    @Override
    public boolean transfer(int fromAccountId, int toAccountId, long amount)
    {
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        boolean result;
        try
        {
            result = iBankDao.transfer(fromAccountId, toAccountId, amount);
            dataSourceTransactionManager.commit(transactionStatus);
            return result;
        }
        catch (Exception e)
        {
            dataSourceTransactionManager.rollback(transactionStatus);
        }
        return false;
    }

    public IBankDao getiBankDao()
    {
        return iBankDao;
    }

    public void setiBankDao(IBankDao iBankDao)
    {
        this.iBankDao = iBankDao;
    }

    public DataSourceTransactionManager getDataSourceTransactionManager()
    {
        return dataSourceTransactionManager;
    }

    public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager)
    {
        this.dataSourceTransactionManager = dataSourceTransactionManager;
    }

    public TransactionDefinition getTransactionDefinition()
    {
        return transactionDefinition;
    }

    public void setTransactionDefinition(TransactionDefinition transactionDefinition)
    {
        this.transactionDefinition = transactionDefinition;
    }
}
