package com.ampletec.gambling.report.controller;


import com.ampletec.gambling.report.entity.Account;
import com.ampletec.gambling.report.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {


    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Account>> findAll() {

        List a = accountService.findAll();

        return ResponseEntity.ok()
                .body(a);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<List<Account>> add(@RequestBody Account  req) {

        accountService.add(req);

        List a = accountService.findAll();

        return ResponseEntity.ok()
                .body(a);
    }
}

