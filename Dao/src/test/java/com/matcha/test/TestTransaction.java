package com.matcha.test;

import com.matcha.dao.IBankDao;
import com.matcha.test.synchronization.MyTransactionSynchronization;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class TestTransaction extends BaseTest
{
    @Test
    public void testTransaction() throws Exception
    {
        IBankDao iBankDao = context.getBean("bankDao", IBankDao.class);
        DataSourceTransactionManager dataSourceTransactionManager =
                context.getBean("transactionManager", DataSourceTransactionManager.class);
        TransactionDefinition transactionDefinition =
                context.getBean("bankTransactionDefinition", DefaultTransactionDefinition.class);
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        TransactionSynchronizationManager.registerSynchronization(new MyTransactionSynchronization());
        try
        {
            iBankDao.transfer(1, 2, 100);
            otherTransaction();
            dataSourceTransactionManager.commit(transactionStatus);
        }
        catch (Exception e)
        {
            dataSourceTransactionManager.rollback(transactionStatus);
        }
    }

    private void otherTransaction()
    {
        BasicDataSource basicDataSource = context.getBean("basicDataSource", BasicDataSource.class);
        DataSourceTransactionManager dataSourceTransactionManager =
                context.getBean("transactionManager", DataSourceTransactionManager.class);
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try
        {
            SqlUpdate sqlUpdate = new SqlUpdate();
            sqlUpdate.setDataSource(basicDataSource);
            sqlUpdate.setSql("INSERT INTO T_TEST(FID, FNAME, FNUMBER) VALUES (5, 'Name_E', 'Number_E')");
            sqlUpdate.update();
            dataSourceTransactionManager.commit(transactionStatus);
        }
        catch (Exception e)
        {
            dataSourceTransactionManager.rollback(transactionStatus);
        }
    }

    @Test
    public void testTransactionAop() throws Exception
    {
        IBankDao iBankDao = context.getBean("bankDao", IBankDao.class);
        iBankDao.transfer(1, 2, 100);
    }
}
