/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rsuapo.antrol.library.models;

import java.io.Serializable;

/**
 *
 * @author andik
 */
public class AntreanFarmasiAddModel implements Serializable {
    private String kodebooking;
    private String jenisresep;
    private Integer nomorantrean;
    private String keterangan;

    public String getKodebooking() {
        return kodebooking;
    }

    /**
     *
     * @param kodebooking kode booking yang telah digenerate saat pendaftaran
     */

    public void setKodebooking(String kodebooking) {
        this.kodebooking = kodebooking;
    }

    public String getJenisresep() {
        return jenisresep;
    }

    /**
     *
     * @param jenisresep racikan / non racikan
     */
    public void setJenisresep(String jenisresep) {
        this.jenisresep = jenisresep;
    }

    public Integer getNomorantrean() {
        return nomorantrean;
    }

    /**
     *
     * @param nomorantrean nomor urut antrean farmasi
     */
    public void setNomorantrean(Integer nomorantrean) {
        this.nomorantrean = nomorantrean;
    }

    public String getKeterangan() {
        return keterangan;
    }

    /**
     *
     * @param keterangan
     */
    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
    
    
    
}
