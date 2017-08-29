package com.matcha.service;

public interface IBankService
{
    boolean transfer(int fromAccountId, int toAccountId, long amount);
}
