/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.api.services;

import com.rsuapo.antrol.api.entities.Operasi;
import com.rsuapo.antrol.api.respositories.OperasiRepository;
import java.util.Date;
import java.util.List;
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
public class OperasiService {

    @Autowired
    private OperasiRepository operasiRepository;
    @Autowired
    private PengaturanNomorService nomorGeneratorService;

    public List<Operasi> findRange(String awal, String akhir) {
        return operasiRepository.findRange(awal, akhir);
    }

    public List<Operasi> findOperasiPasien(String nomorPeserta) {
        return operasiRepository.findByNopesertaAndStatus(nomorPeserta, 1);
    }

    // crud
    @Transactional(rollbackFor = Exception.class)
    public Operasi create(Operasi operasi) throws Exception {
        operasi.setId(UUID.randomUUID().toString());
        operasi.setKodebooking(nomorGeneratorService.generateNomorBookingOperasi());
        operasi.setStatus(1);
        operasi.setTerlaksana(0);
        operasi.setCreatedAt(new Date());
        operasi.setUpdatedAt(new Date());
        operasi.setLastupdate(operasi.getUpdatedAt().getTime());
        Operasi saved = operasiRepository.save(operasi);
        nomorGeneratorService.update("kodebooking.operasi", operasi.getKodebooking());
        return saved;
    }

    public boolean isSudahTerdaftar(String norm, String tanggaloperasi) {
        List<Operasi> operasis = operasiRepository.findByNormAndTanggaloperasiAndStatus(norm, tanggaloperasi, 1);
        return !operasis.isEmpty();
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancel(Operasi operasi) throws Exception {
        operasi.setStatus(0);
        operasi.setUpdatedAt(new Date());
        operasi.setLastupdate(operasi.getUpdatedAt().getTime());
        operasiRepository.save(operasi);
    }

    public Operasi findByKodebooking(String kodebooking) {
        Optional<Operasi> optional = operasiRepository.findByKodebooking(kodebooking);
        if (optional.isPresent()) {
            return optional.get();
        }

        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateTerlaksana(Operasi operasi, int terlaksana) throws Exception {
        operasi.setTerlaksana(terlaksana);
        operasi.setUpdatedAt(new Date());
        operasi.setLastupdate(operasi.getUpdatedAt().getTime());
        operasiRepository.save(operasi);
    }

    @Transactional(rollbackFor = Exception.class)
    public Operasi update(Operasi operasi) throws Exception {
        operasi.setUpdatedAt(new Date());
        operasi.setLastupdate(operasi.getUpdatedAt().getTime());
        return operasiRepository.save(operasi);
    }

    public List<Operasi> findOperasiByKodebooking(String kodebooking) {
        return operasiRepository.findByKodebookingAndStatus(kodebooking, 1);
    }

}
