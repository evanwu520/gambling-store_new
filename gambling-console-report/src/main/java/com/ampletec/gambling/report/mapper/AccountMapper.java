package com.ampletec.gambling.report.mapper;

import com.ampletec.gambling.report.entity.Account;

import java.util.List;

public interface AccountMapper {

    public List<Account> findAllAccount();

    public void add(Account account);
}
