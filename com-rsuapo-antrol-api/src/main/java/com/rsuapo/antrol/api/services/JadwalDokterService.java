/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.api.services;

import com.rsuapo.antrol.api.entities.JadwalDokter;
import com.rsuapo.antrol.api.respositories.JadwalDokterRepository;
import com.rsuapo.antrol.library.models.JadwalDokterModel;
import com.rsuapo.antrol.library.models.ResponseListMetadataModel;
import com.rsuapo.antrol.library.service.AntrolService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author RSU Aisyiyah Ponorogo <rsuapo@yahoo.co.id>
 */
@Service
public class JadwalDokterService {

    @Autowired
    private JadwalDokterRepository jadwalDokterRepository;

    @Autowired
    private AntrolService antrolService;

    public JadwalDokter findJadwalDokter(String kodepoli, String kodedokter, String tanggalperiksa, String jampraktek) {
        ResponseListMetadataModel<JadwalDokterModel> jadwalDokters = antrolService.getJadwalDokters(kodepoli, tanggalperiksa);
        if (200 == jadwalDokters.getMetadata().getCode()) {
            List<JadwalDokterModel> jadwalDokterModels = jadwalDokters.getResponse();
            for (JadwalDokterModel jadwalDokterModel : jadwalDokterModels) {
                Optional<JadwalDokter> optional = jadwalDokterRepository.findByHariAndKodedokterAndKodepoliAndJadwal(
                        jadwalDokterModel.getHari(),
                        jadwalDokterModel.getKodedokter(),
                        jadwalDokterModel.getKodepoli(),
                        jadwalDokterModel.getJadwal().replaceAll(" ", "")
                );
                
                if (!optional.isPresent() && jampraktek.equals(jadwalDokterModel.getJadwal().replaceAll(" ", ""))) {
                    JadwalDokter jd = new JadwalDokter(UUID.randomUUID().toString());
                    jd.setHari(jadwalDokterModel.getHari());
                    jd.setNamahari(jadwalDokterModel.getNamahari());
                    jd.setJadwal(jadwalDokterModel.getJadwal().replace(" ", ""));
                    jd.setKodedokter(jadwalDokterModel.getKodedokter());
                    jd.setNamadokter(jadwalDokterModel.getNamadokter());
                    jd.setKodepoli(jadwalDokterModel.getKodepoli());
                    jd.setNamapoli(jadwalDokterModel.getNamapoli());
                    jd.setKodesubspesialis(jadwalDokterModel.getKodesubspesialis());
                    jd.setNamasubspesialis(jadwalDokterModel.getNamasubspesialis());
                    jd.setLibur(jadwalDokterModel.getLibur());
                    jd.setKapasitaspasien(jadwalDokterModel.getKapasitaspasien());
                    jadwalDokterRepository.save(jd);
                    return jd;
                }
                
                if (optional.isPresent()) {
                    return optional.get();
                }
            }
        }
        
        return null;
    }

}
