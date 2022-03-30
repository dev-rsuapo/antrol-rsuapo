/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.api.respositories;

import com.rsuapo.antrol.api.entities.Antrean;
import java.util.List;
import java.util.Optional;
import javax.websocket.server.PathParam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author RSU Aisyiyah Ponorogo <rsuapo@yahoo.co.id>
 */
public interface AntreanRepository extends JpaRepository<Antrean, String> {

    @Query(value = "select count(*) from antrean a where a.norm = :norm and a.tanggalperiksa = :tanggalperiksa and a.kodedokter = :kodedokter and a.status = 1", nativeQuery = true)
    public Long countAntrean(
            @Param("norm") String norm,
            @Param("tanggalperiksa") String tanggalperiksa,
            @Param("kodedokter") String kodedokter);

    @Query(value = "select count(*) from antrean a where a.kodepoli = :kodepoli and a.kodedokter = :kodedokter and a.tanggalperiksa = :tanggalperiksa and a.jampraktek = :jampraktek and a.status = 1", nativeQuery = true)
    public Long countAntrianTotal(
            @Param("kodepoli") String kodepoli,
            @Param("kodedokter") String kodedokter,
            @Param("tanggalperiksa") String tanggalperiksa,
            @Param("jampraktek") String jampraktek);

    @Query(value = "select count(*) from antrean a where a.kodepoli = :kodepoli and a.kodedokter = :kodedokter and a.tanggalperiksa = :tanggalperiksa and a.jampraktek = :jampraktek and a.jenispasien = :jenispasien and a.status = 1", nativeQuery = true)
    public Long countAntrianJenis(
            @Param("kodepoli") String kodepoli,
            @Param("kodedokter") String kodedokter,
            @Param("tanggalperiksa") String tanggalperiksa,
            @Param("jampraktek") String jampraktek,
            @Param("jenispasien") String jenispasien);

    @Query(value = "select count(*) from antrean a where a.kodepoli = :kodepoli and a.kodedokter = :kodedokter and a.tanggalperiksa = :tanggalperiksa and a.jampraktek = :jampraktek and a.status = 1 and coalesce(a.poli_sudah_dipanggil, false) = false", nativeQuery = true)
    public Long countAntrianBelumDipanggil(
            @Param("kodepoli") String kodepoli,
            @Param("kodedokter") String kodedokter,
            @Param("tanggalperiksa") String tanggalperiksa,
            @Param("jampraktek") String jampraktek);

    public Optional<Antrean> findByKodebooking(String kodebooking);

    @Query("SELECT a FROM Antrean a WHERE a.status = 1 AND a.poliSudahDipanggil = true AND a.kodepoli = :kodepoli AND a.kodedokter = :kodedokter AND a.tanggalperiksa = :tanggalperiksa AND a.jampraktek = :jampraktek ORDER BY a.poliWaktuDipanggil DESC")
    public List<Antrean> findLastDipanggil(
            @PathParam("kodepoli") String kodepoli,
            @PathParam("kodedokter") String kodedokter,
            @PathParam("tanggalperiksa") String tanggalperiksa,
            @PathParam("jampraktek") String jampraktek,
            Pageable pageable);

    @Query("SELECT a FROM Antrean a WHERE a.status = 1 AND a.syncStatus = 0 AND a.syncCount < :toleransi ORDER BY a.createdAt ASC")
    public List<Antrean> findAllNeedToPushAdd(@Param("toleransi") Integer toleransi);

    @Query("SELECT COALESCE(MAX(a.angkaantrean), 0) FROM Antrean a WHERE a.kodepoli = :kodepoli AND a.kodedokter = :kodedokter AND a.jampraktek = :jampraktek")
    public Long getLastNomorAntreanPoli(
            @Param("kodepoli") String kodepoli,
            @Param("kodedokter") String kodedokter,
            @Param("jampraktek") String jampraktek);
    
    @Query("SELECT COALESCE(MAX(a.antreanFarmasiNomor), 0) FROM Antrean a WHERE a.antreanFarmasiLokasiId = :farmId AND a.tanggalperiksa = :tanggal")
    public Long getLastNomorAntreanFarmasi(
            @Param("farmId") String farmId,
            @Param("tanggal") String tanggal);

    @Query("SELECT COALESCE(MAX(a.antreanPendaftaranNomor), 0) FROM Antrean a WHERE a.tanggalperiksa = :tanggal")
    public Long getLastNomorAntreanPendaftaran(@Param("tanggal") String tanggal);

    public Optional<Antrean> findByAntreanPendaftaranNomorAndTanggalperiksaAndStatus(Integer nomor, String tanggal, int status);

    @Query("SELECT a FROM Antrean a WHERE a.status = 1 AND a.tanggalperiksa >= :awal AND a.tanggalperiksa <= :akhir ORDER BY a.createdAt DESC")
    public List<Antrean> findRange(
            @Param("awal") String awal, 
            @Param("akhir") String akhir);

}
