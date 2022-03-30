/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.api.respositories;

import com.rsuapo.antrol.api.entities.JadwalDokter;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author RSU Aisyiyah Ponorogo <rsuapo@yahoo.co.id>
 */
public interface JadwalDokterRepository extends JpaRepository<JadwalDokter, String> {

    public Optional<JadwalDokter> findByHariAndKodedokterAndKodepoliAndJadwal(Integer hari, String kodedokter, String kodepoli, String replaceAll);

}
