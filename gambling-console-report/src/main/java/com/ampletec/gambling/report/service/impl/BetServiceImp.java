package com.ampletec.gambling.report.service.impl;

import com.ampletec.gambling.report.entity.Account;
import com.ampletec.gambling.report.mapper.AccountMapper;
import com.ampletec.gambling.report.mapper.BetMapper;
import com.ampletec.gambling.report.service.AccountService;
import com.ampletec.gambling.report.service.BetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


@Service
public class BetServiceImp implements BetService {

    @Autowired
    private BetMapper betMapper;

    @Override
    public List<HashMap> findAll() {
        return betMapper.findAllBet();
    }




}
