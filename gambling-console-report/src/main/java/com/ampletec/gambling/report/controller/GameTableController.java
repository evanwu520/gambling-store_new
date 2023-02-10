package com.ampletec.gambling.report.controller;


import com.ampletec.gambling.report.entity.GameTable;
import com.ampletec.gambling.report.entity.Wager;
import com.ampletec.gambling.report.entity.request.SaveGameTableRequest;
import com.ampletec.gambling.report.entity.response.BaseResponse;
import com.ampletec.gambling.report.entity.response.GetGameTableListResponse;
import com.ampletec.gambling.report.service.GameTableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/{SystemID}/API")
public class GameTableController {


    private final static Logger logger = LoggerFactory.getLogger(GameTableController.class);

    @Autowired
    private GameTableService gameTableService;

    @RequestMapping(value = "/GetGameTableList", method = RequestMethod.POST)
    public ResponseEntity<GetGameTableListResponse> getGameTableList(@PathVariable(name = "SystemID") Optional<Integer> systemID) {


        GetGameTableListResponse resp = new GetGameTableListResponse();

        List list = null;

        try {
             list = gameTableService.getGameTableList(systemID.get());
             resp.setList(list);
        }catch (Exception e){
            logger.error("{}",e);
            resp.setMessageCode(900);
            resp.setMessage("UNEXPECTED_ERROR");
        }

        return ResponseEntity.ok()
                .body(resp);
    }

    @RequestMapping(value = "/SaveGameTable", method = RequestMethod.POST)
    public ResponseEntity<BaseResponse> saveGameTable(@PathVariable(name = "SystemID") Optional<Integer> systemID,@RequestBody SaveGameTableRequest saveGameTableRequest) {

        BaseResponse resp = new BaseResponse();

        if (!systemID.isPresent() || systemID.get() <= 0 || saveGameTableRequest.getList().size() == 0){
            resp.setMessageCode(902);
            resp.setMessage("ILLEGAL_INPUT");
            return ResponseEntity.ok()
                    .body(resp);
        }

        try {

            List<GameTable> records  = saveGameTableRequest.getList().stream().map(p-> {
               p.setSystemid(systemID.get());
                return p;
            }).collect(Collectors.toList());
            List<GameTable> list = new ArrayList<>(records);
            gameTableService.saveGameTable(list);

        }catch (Exception e){
            logger.error("{}",e);
            resp.setMessageCode(900);
            resp.setMessage("UNEXPECTED_ERROR");
        }

        return ResponseEntity.ok()
                .body(resp);
    }

    @RequestMapping(value = "/ClearGameTable", method = RequestMethod.POST)
    public ResponseEntity<BaseResponse> clearGameTable(@PathVariable(name = "SystemID") Optional<Integer> systemID) {

        BaseResponse resp = new BaseResponse();

        try {
            gameTableService.clearGameTable(systemID.get());

        }catch (Exception e){
            logger.error("{}",e);
            resp.setMessageCode(900);
            resp.setMessage("UNEXPECTED_ERROR");
        }

        return ResponseEntity.ok()
                .body(resp);
    }
}

