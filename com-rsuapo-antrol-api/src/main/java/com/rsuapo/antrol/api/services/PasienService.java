/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.api.services;

import com.rsuapo.antrol.api.entities.Pasien;
import com.rsuapo.antrol.api.respositories.PasienRepository;
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
public class PasienService {

    @Autowired
    private PasienRepository pasienRepository;
    @Autowired
    private PengaturanNomorService pengaturanNomorService;

    public Pasien findByNomorRm(String nomorRm) {
        Optional<Pasien> optional = pasienRepository.findByNorm(nomorRm);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    public Pasien findByNomorkartu(String nomorKartu) {
        Optional<Pasien> optional = pasienRepository.findByNomorkartu(nomorKartu);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public Pasien create(Pasien pasien) throws Exception {
        pasien.setId(UUID.randomUUID().toString());
        pasien.setCreatedAt(new Date());
        pasien.setUpdatedAt(new Date());
        Pasien saved = pasienRepository.save(pasien);
        
        pengaturanNomorService.update("norm", pasien.getNorm());
        
        return saved;
    }

}
