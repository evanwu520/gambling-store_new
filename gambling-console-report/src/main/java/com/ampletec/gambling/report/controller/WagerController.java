package com.ampletec.gambling.report.controller;


import com.ampletec.gambling.report.entity.Wager;
import com.ampletec.gambling.report.entity.request.WagerRequest;
import com.ampletec.gambling.report.service.WagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/Wager")
public class WagerController {


    private final static Logger logger = LoggerFactory.getLogger(WagerController.class);

    @Autowired
    private WagerService wagerService;



    @RequestMapping(value = "/FindAll", method = RequestMethod.POST)
    public ResponseEntity<List<Wager>> findAll() {

        List list = null;

        try {
             list = wagerService.findAll();
        }catch (Exception e){
            logger.error("{}",e);
        }


        return ResponseEntity.ok()
                .body(list);
    }


    @RequestMapping(value = "/BatchInsert", method = RequestMethod.POST)
    public ResponseEntity<Void> batchInsert(@RequestBody List<WagerRequest> wagerList) {

        try {
            List<Wager> list = new ArrayList<>(wagerList);
            wagerService.batchInsert(list);

        }catch (Exception e){
            logger.error("{}",e);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

