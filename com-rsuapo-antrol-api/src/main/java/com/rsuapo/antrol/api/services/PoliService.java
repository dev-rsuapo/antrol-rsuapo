/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.api.services;

import com.rsuapo.antrol.api.entities.Poli;
import com.rsuapo.antrol.api.respositories.PoliRepository;
import com.rsuapo.antrol.library.models.PoliModel;
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
public class PoliService {
    @Autowired
    private PoliRepository poliRepository;
    
    @Autowired
    private AntrolService antrolService;
    
    @Transactional(rollbackFor = Exception.class)
    public void pullDataPoli() throws Exception {
        int createdCount = 0;
        ResponseListMetadataModel<PoliModel> result = antrolService.getPolis();
        if (1 == result.getMetadata().getCode()) {
            for (PoliModel poliModel : result.getResponse()) {
                Optional<Poli> optional = poliRepository.findByKdsubspesialisAndKdpoli(poliModel.getKdsubspesialis(), poliModel.getKdpoli());
                if (!optional.isPresent()) {
                    Poli poli = new Poli(UUID.randomUUID().toString());
                    poli.setKdpoli(poliModel.getKdpoli());
                    poli.setKdsubspesialis(poliModel.getKdsubspesialis());
                    poli.setNmpoli(poliModel.getNmpoli());
                    poli.setNmsubspesialis(poliModel.getNmsubspesialis());
                    poli.setCreatedAt(new Date());
                    poli.setUpdatedAt(new Date());
                    poliRepository.save(poli);
                    createdCount++;
                }
            }
        }
        System.out.println("poli created " + createdCount + " records");
    }
    
    public Poli findByKdpoli(String kdpoli) {
        Optional<Poli> optional = poliRepository.findByKdsubspesialisAndKdpoli(kdpoli, kdpoli);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

}
