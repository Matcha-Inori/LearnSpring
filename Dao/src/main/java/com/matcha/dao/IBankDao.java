package com.matcha.dao;

public interface IBankDao
{
    boolean transfer(int fromAccountId, int toAccountId, long amount);
}
