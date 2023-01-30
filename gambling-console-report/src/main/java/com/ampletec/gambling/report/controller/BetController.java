package com.ampletec.gambling.report.controller;


import com.ampletec.gambling.report.entity.Account;
import com.ampletec.gambling.report.service.AccountService;
import com.ampletec.gambling.report.service.BetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/bet")
public class BetController {


    @Autowired
    private BetService betService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<HashMap>> findAll() {

        List a = betService.findAll();

        return ResponseEntity.ok()
                .body(a);
    }

}

