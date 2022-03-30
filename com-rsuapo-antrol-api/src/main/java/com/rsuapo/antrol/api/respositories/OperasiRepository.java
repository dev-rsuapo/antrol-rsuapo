/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.api.respositories;

import com.rsuapo.antrol.api.entities.Operasi;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author RSU Aisyiyah Ponorogo <rsuapo@yahoo.co.id>
 */
public interface OperasiRepository extends JpaRepository<Operasi, String> {

    @Query("SELECT o FROM Operasi o WHERE o.status = 1 AND o.tanggaloperasi >= :awal AND o.tanggaloperasi <= :akhir ORDER BY o.tanggaloperasi ASC")
    public List<Operasi> findRange(String awal, String akhir);

    public List<Operasi> findByNopesertaAndStatus(String nomorPeserta, int status);

    public List<Operasi> findByNormAndTanggaloperasiAndStatus(String norm, String tanggaloperasi, int status);

    public Optional<Operasi> findByKodebooking(String kodebooking);

    public List<Operasi> findByKodebookingAndStatus(String kodebooking, int status);

}
