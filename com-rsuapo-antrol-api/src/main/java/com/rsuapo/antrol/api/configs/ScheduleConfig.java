/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.api.configs;

import com.rsuapo.antrol.api.services.AntreanService;
import com.rsuapo.antrol.api.services.AntreanTaskService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 *
 * @author RSU Aisyiyah Ponorogo <rsuapo@yahoo.co.id>
 */
@Configuration
@EnableScheduling
public class ScheduleConfig {
    
    @Autowired
    private AntreanService antreanService;
    @Autowired
    private AntreanTaskService antreanTaskService;
    
    @Scheduled(fixedDelayString = "${scheduled.fixedDelay.in.milliseconds}")
    public void executePushAntrean() {
        try {
            antreanService.push();
            antreanTaskService.push();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
    private static final Logger LOG = Logger.getLogger(ScheduleConfig.class.getName());
    
}
