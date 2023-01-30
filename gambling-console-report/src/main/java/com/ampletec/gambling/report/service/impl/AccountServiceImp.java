package com.ampletec.gambling.report.service.impl;

import com.ampletec.gambling.report.entity.Account;
import com.ampletec.gambling.report.mapper.AccountMapper;
import com.ampletec.gambling.report.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AccountServiceImp implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public List<Account> findAll() {
        return accountMapper.findAllAccount();
    }

    @Override
    public void add(Account account) {
        accountMapper.add(account);
    }



}
