/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.api.services;

import com.rsuapo.antrol.api.entities.PengaturanNomor;
import com.rsuapo.antrol.api.respositories.AntreanRepository;
import com.rsuapo.antrol.api.respositories.PengaturanNomorRepository;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author RSU Aisyiyah Ponorogo <rsuapo@yahoo.co.id>
 */
@Service
public class PengaturanNomorService {

    @Autowired
    private PengaturanNomorRepository pengaturanNomorRepository;
    @Autowired
    private AntreanRepository antreanRepository;

    private String lastNomorBookingAntrian;
    private String lastNomorBookingOperasi;
    private String lastNomorRm;

    public String generateNomorBookingAntrean() {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String tanggalHariIni = sdf.format(new Date());
        if (lastNomorBookingAntrian == null) {
            lastNomorBookingAntrian = getLastKodebookingAntrean();
        }

        String[] split = lastNomorBookingAntrian.split("A");
        if (tanggalHariIni.equals(split[0])) {
            int lastNomor = Integer.parseInt(split[1]);
            lastNomor += 1;
            lastNomorBookingAntrian = tanggalHariIni + "A" + String.format("%04d", lastNomor);
        } else {
            lastNomorBookingAntrian = tanggalHariIni + "A0001";
        }

        return lastNomorBookingAntrian;
    }

    public String generateNomorBookingOperasi() {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String tanggalHariIni = sdf.format(new Date());
        if (lastNomorBookingOperasi == null) {
            lastNomorBookingOperasi = getLastKodebookingOperasi();
        }

        String[] split = lastNomorBookingOperasi.split("B");
        if (tanggalHariIni.equals(split[0])) {
            int lastNomor = Integer.parseInt(split[1]);
            lastNomor += 1;
            lastNomorBookingOperasi = tanggalHariIni + "B" + String.format("%04d", lastNomor);
        } else {
            lastNomorBookingOperasi = tanggalHariIni + "B0001";
        }

        return lastNomorBookingOperasi;
    }

    public String generateNomorRm() {
        if (lastNomorRm == null) {
            lastNomorRm = getLastNomorRm();
        }

        int lastNomor = Integer.parseInt(lastNomorRm);
        lastNomor += 1;
        lastNomorRm = String.format("%06d", lastNomor);

        return lastNomorRm;
    }

    public String getLastKodebookingAntrean() {
        Optional<PengaturanNomor> optional = pengaturanNomorRepository.findById("kodebooking.antrean");
        if (optional.isPresent()) {
            return optional.get().getLastNomor();
        }

        PengaturanNomor p = new PengaturanNomor("kodebooking.antrean");
        p.setLastNomor(new SimpleDateFormat("yyyyMMdd").format(new Date()) + "A0000");
        p.setUpdatedAt(new Date());
        pengaturanNomorRepository.save(p);
        return p.getLastNomor();
    }

    public String getLastKodebookingOperasi() {
        Optional<PengaturanNomor> optional = pengaturanNomorRepository.findById("kodebooking.operasi");
        if (optional.isPresent()) {
            return optional.get().getLastNomor();
        }

        PengaturanNomor p = new PengaturanNomor("kodebooking.operasi");
        p.setLastNomor(new SimpleDateFormat("yyyyMMdd").format(new Date()) + "B0000");
        p.setUpdatedAt(new Date());
        pengaturanNomorRepository.save(p);
        return p.getLastNomor();
    }

    public String getLastNomorRm() {
        Optional<PengaturanNomor> optional = pengaturanNomorRepository.findById("norm");
        if (optional.isPresent()) {
            return optional.get().getLastNomor();
        }

        PengaturanNomor p = new PengaturanNomor("norm");
        p.setLastNomor("0");
        p.setUpdatedAt(new Date());
        pengaturanNomorRepository.save(p);
        return p.getLastNomor();
    }

    public void update(String id, String lastNomor) {
        Optional<PengaturanNomor> optional = pengaturanNomorRepository.findById(id);
        if (optional.isPresent()) {
            PengaturanNomor p = optional.get();
            p.setLastNomor(lastNomor);
            p.setUpdatedAt(new Date());
            pengaturanNomorRepository.save(p);
        }
    }
    
    @Transactional(rollbackFor = Exception.class)
    public Long generateNomorAntrianPoli(String kodepoli, String kodedokter, String jampraktek) throws Exception {
        Long lastNomorAntrean = antreanRepository.getLastNomorAntreanPoli(kodepoli, kodedokter, jampraktek);
        return lastNomorAntrean + 1;
    }

    public Long generateNomorAntrianFarmasi(String farmId, String tanggal) {
        Long lastNomorAntrean = antreanRepository.getLastNomorAntreanFarmasi(farmId, tanggal);
        return lastNomorAntrean + 1;
    }
    
    public Long generateNomorAntrianPendaftaran(String tanggal) {
        Long lastNomorAntrean = antreanRepository.getLastNomorAntreanPendaftaran(tanggal);
        return lastNomorAntrean + 1;
    }
    
}
