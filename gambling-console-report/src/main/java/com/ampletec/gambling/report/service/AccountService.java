package com.ampletec.gambling.report.service;

import com.ampletec.gambling.report.entity.Account;

import java.util.List;

public interface  AccountService {

    List<Account> findAll();

    void add(Account account);
}
