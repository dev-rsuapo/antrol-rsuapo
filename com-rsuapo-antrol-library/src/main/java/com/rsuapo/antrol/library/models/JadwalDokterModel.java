/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.library.models;

import java.io.Serializable;

/**
 *
 * @author RSU Aisyiyah Ponorogo <rsuapo@yahoo.co.id>
 */
public class JadwalDokterModel implements Serializable {

    private String kodesubspesialis;
    private Integer hari;
    private Integer kapasitaspasien;
    private Integer libur;
    private String namahari;
    private String jadwal;
    private String namasubspesialis;
    private String namadokter;
    private String kodepoli;
    private String namapoli;
    private String kodedokter;

    public JadwalDokterModel() {
    }

    public String getKodesubspesialis() {
        return kodesubspesialis;
    }

    public void setKodesubspesialis(String kodesubspesialis) {
        this.kodesubspesialis = kodesubspesialis;
    }

    public Integer getHari() {
        return hari;
    }

    public void setHari(Integer hari) {
        this.hari = hari;
    }

    public Integer getKapasitaspasien() {
        return kapasitaspasien;
    }

    public void setKapasitaspasien(Integer kapasitaspasien) {
        this.kapasitaspasien = kapasitaspasien;
    }

    public Integer getLibur() {
        return libur;
    }

    public void setLibur(Integer libur) {
        this.libur = libur;
    }

    public String getNamahari() {
        return namahari;
    }

    public void setNamahari(String namahari) {
        this.namahari = namahari;
    }

    public String getJadwal() {
        return jadwal;
    }

    public void setJadwal(String jadwal) {
        this.jadwal = jadwal;
    }

    public String getNamasubspesialis() {
        return namasubspesialis;
    }

    public void setNamasubspesialis(String namasubspesialis) {
        this.namasubspesialis = namasubspesialis;
    }

    public String getNamadokter() {
        return namadokter;
    }

    public void setNamadokter(String namadokter) {
        this.namadokter = namadokter;
    }

    public String getKodepoli() {
        return kodepoli;
    }

    public void setKodepoli(String kodepoli) {
        this.kodepoli = kodepoli;
    }

    public String getNamapoli() {
        return namapoli;
    }

    public void setNamapoli(String namapoli) {
        this.namapoli = namapoli;
    }

    public String getKodedokter() {
        return kodedokter;
    }

    public void setKodedokter(String kodedokter) {
        this.kodedokter = kodedokter;
    }

}
