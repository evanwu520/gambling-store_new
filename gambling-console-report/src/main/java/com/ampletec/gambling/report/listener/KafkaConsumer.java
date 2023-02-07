package com.ampletec.gambling.report.listener;

import com.alibaba.fastjson.JSON;
import com.ampletec.gambling.report.entity.Wager;
import com.ampletec.gambling.report.service.WagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KafkaConsumer {


    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    private WagerService wagerService;


    @KafkaListener(containerFactory = "kafkaListenerContainerFactory", id = "wager-listener", groupId = "group_wager", topics = "wager")
    public void wagerReceive(List<String> messages, Acknowledgment ack) {

        logger.info("received size='{}'", messages.size());


        Boolean success = true;


        try {

            List<Wager> list = new ArrayList<>();

            for (String message : messages) {
                Object o = JSON.parse(message);
                Wager w = JSON.parseObject(o.toString(), Wager.class);
                list.add(w);
            }
            wagerService.batchInsert(list);
        }catch (Exception e){
            logger.error("{}",e);
            success = false;
        }

        // success ack
        if (success) {
            ack.acknowledge();
        }
    }

}
