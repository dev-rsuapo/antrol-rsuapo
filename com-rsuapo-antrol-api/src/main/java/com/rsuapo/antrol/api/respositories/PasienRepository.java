/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.api.respositories;

import com.rsuapo.antrol.api.entities.Pasien;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author RSU Aisyiyah Ponorogo <rsuapo@yahoo.co.id>
 */
public interface PasienRepository extends JpaRepository<Pasien, String> {

    public Optional<Pasien> findByNorm(String nomorRm);

    public Optional<Pasien> findByNomorkartu(String nomorKartu);

    public Optional<Pasien> findByNomorkartuAndNik(String nomorkartu, String nik);
    
}
