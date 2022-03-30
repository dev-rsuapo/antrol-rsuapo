/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.library.models;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author RSU Aisyiyah Ponorogo <rsuapo@yahoo.co.id>
 */
public class UpdateJadwalDokterModel implements Serializable {

    private String kodepoli;
    private String kodesubspesialis;
    private String kodedokter;
    private List<JadwalModel> jadwal;

    public UpdateJadwalDokterModel() {
    }

    public String getKodepoli() {
        return kodepoli;
    }

    public void setKodepoli(String kodepoli) {
        this.kodepoli = kodepoli;
    }

    public String getKodesubspesialis() {
        return kodesubspesialis;
    }

    public void setKodesubspesialis(String kodesubspesialis) {
        this.kodesubspesialis = kodesubspesialis;
    }

    public String getKodedokter() {
        return kodedokter;
    }

    public void setKodedokter(String kodedokter) {
        this.kodedokter = kodedokter;
    }

    public List<JadwalModel> getJadwal() {
        return jadwal;
    }

    public void setJadwal(List<JadwalModel> jadwal) {
        this.jadwal = jadwal;
    }

}
