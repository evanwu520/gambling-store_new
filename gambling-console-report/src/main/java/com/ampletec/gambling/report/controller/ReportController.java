package com.ampletec.gambling.report.controller;


import com.ampletec.gambling.report.entity.WinloseReport;
import com.ampletec.gambling.report.entity.WinloseReportStatistic;
import com.ampletec.gambling.report.entity.request.WinloseReportRequest;
import com.ampletec.gambling.report.entity.request.WinloseReportStatisticRequest;
import com.ampletec.gambling.report.entity.response.WinloseReportResponse;
import com.ampletec.gambling.report.entity.response.WinloseReportStatisticResponse;
import com.ampletec.gambling.report.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/{SystemID}/Report")
public class ReportController {


    private final static Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/GetGameTableWinloseReportStatistic", method = RequestMethod.POST)
    public ResponseEntity<WinloseReportStatisticResponse> gameTableWinloseReportStatistic(@PathVariable(name = "SystemID") Optional<Integer> systemID, @RequestBody WinloseReportStatisticRequest req) {

        WinloseReportStatisticResponse resp = new WinloseReportStatisticResponse();

        Date start = null;
        Date end = null;
                
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        try {
            start = sdFormat.parse(req.getStartDateTime());
            end = sdFormat.parse(req.getEndDateTime());
        } catch (ParseException e) {
            logger.error("{}",e);
            resp.setMessageCode(902);
            resp.setMessage("ILLEGAL_INPUT");
            return ResponseEntity.ok()
                    .body(resp);
        }

        String[] currencys = req.getCurrencys();
        String[] gameTitles = req.getGameTitles();
        Long parentID = req.getParentID();

        logger.info("{}",req);
        logger.info("{} {}",start, end);

        List<WinloseReportStatistic> list = null;

        try {


            list = reportService.winloseReportStatisticList(systemID.get(),start, end, parentID, currencys, gameTitles);

            if (list != null && list.size() > 0) {
                resp.setList(list);
                resp.setTotalCount(list.size());
            }

        } catch (Exception e) {
            logger.error("{}",e);
            resp.setMessageCode(900);
            resp.setMessage("UNEXPECTED_ERROR");
            return ResponseEntity.ok()
                    .body(resp);
        }

        return ResponseEntity.ok()
                .body(resp);
    }


    @RequestMapping(value = "/GetGameTableWinloseReportList", method = RequestMethod.POST)
    public ResponseEntity<WinloseReportResponse> gameTableWinloseReportList(@PathVariable(name = "SystemID") Optional<Integer> systemID, @RequestBody WinloseReportRequest req) {

        WinloseReportResponse resp = new WinloseReportResponse();

        Date start = null;
        Date end = null;

        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        try {
            start = sdFormat.parse(req.getStartDateTime());
            end = sdFormat.parse(req.getEndDateTime());
        } catch (ParseException e) {
            logger.error("{}",e);
            resp.setMessageCode(902);
            resp.setMessage("ILLEGAL_INPUT");
            return ResponseEntity.ok()
                    .body(resp);
        }

        logger.info("{}",req);
        logger.info("{} {}",start, end);

        String[] currencys = req.getCurrencys();
        String[] gameTitles = req.getGameTitles();
        Long parentID = req.getParentID();

        List<WinloseReport> list = null;

        try {
            list = reportService.winloseReportList(systemID.get(),start, end, parentID, currencys, gameTitles);

            if (list != null && list.size() > 0) {

                list.stream().map(data->{
                    data.setStartDateTimeFormat(sdFormat.format(data.getStartDateTime()));
                    data.setEndDateTimeFormat(sdFormat.format(data.getEndDateTime()));
                    return data;
                }).collect(Collectors.toList());

                resp.setList(list);
                resp.setTotalCount(list.size());
            }

        } catch (Exception e) {
            logger.error("{}",e);
            resp.setMessageCode(900);
            resp.setMessage("UNEXPECTED_ERROR");
            return ResponseEntity.ok()
                    .body(resp);
        }
//        return new ResponseEntity<>(HttpStatus.OK);
        return ResponseEntity.ok()
                .body(resp);
    }

}
