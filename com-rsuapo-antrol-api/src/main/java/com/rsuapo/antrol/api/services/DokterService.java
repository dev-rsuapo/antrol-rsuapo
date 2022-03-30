/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.api.services;

import com.rsuapo.antrol.api.entities.Dokter;
import com.rsuapo.antrol.api.respositories.DokterRepository;
import com.rsuapo.antrol.library.models.DokterModel;
import com.rsuapo.antrol.library.models.ResponseListMetadataModel;
import com.rsuapo.antrol.library.service.AntrolService;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author RSU Aisyiyah Ponorogo <rsuapo@yahoo.co.id>
 */
@Service
public class DokterService {

    @Autowired
    private DokterRepository dokterRepository;

    @Autowired
    private AntrolService antrolService;

    @Transactional(rollbackFor = Exception.class)
    public void pullDataDokter() throws Exception {
        int createdCount = 0;
        ResponseListMetadataModel<DokterModel> result = antrolService.getDokters();
        if (1 == result.getMetadata().getCode()) {
            for (DokterModel dokterModel : result.getResponse()) {
                Optional<Dokter> optional = dokterRepository.findByKodedokter(dokterModel.getKodedokter());
                if (!optional.isPresent()) {
                    Dokter dokter = new Dokter(UUID.randomUUID().toString());
                    dokter.setKodedokter(dokterModel.getKodedokter());
                    dokter.setNamadokter(dokterModel.getNamadokter());
                    dokter.setDisplayChannel("200");
                    dokter.setDisplayRuang("1");
                    dokter.setCreatedAt(new Date());
                    dokter.setUpdatedAt(new Date());
                    dokterRepository.save(dokter);
                    createdCount++;
                }
            }
        }
        
        System.out.println("dokter created " + createdCount + " records");
    }

    public Dokter findByKodedokter(String kodedokter) {
        Optional<Dokter> optional = dokterRepository.findByKodedokter(kodedokter);
        if (optional.isPresent()) {
            return optional.get();
        }
        
        return null;
    }

}
