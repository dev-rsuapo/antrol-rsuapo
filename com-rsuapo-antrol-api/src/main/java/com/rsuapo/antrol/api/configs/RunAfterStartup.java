/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.api.configs;

import com.rsuapo.antrol.api.services.DokterService;
import com.rsuapo.antrol.api.services.PoliService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author RSU Aisyiyah Ponorogo <rsuapo@yahoo.co.id>
 */
@Component
public class RunAfterStartup {
    
    @Autowired
    private PoliService poliService;
    @Autowired
    private DokterService dokterService;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        try {
            poliService.pullDataPoli();
            dokterService.pullDataDokter();
        } catch (Exception ex) {
            Logger.getLogger(RunAfterStartup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
