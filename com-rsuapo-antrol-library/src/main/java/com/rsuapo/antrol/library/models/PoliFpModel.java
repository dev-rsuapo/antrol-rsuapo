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
public class PoliFpModel implements Serializable {

    private String kodesubspesialis;
    private String namasubspesialis;
    private String kodepoli;
    private String namapoli;

    public String getKodesubspesialis() {
        return kodesubspesialis;
    }

    public void setKodesubspesialis(String kodesubspesialis) {
        this.kodesubspesialis = kodesubspesialis;
    }

    public String getNamasubspesialis() {
        return namasubspesialis;
    }

    public void setNamasubspesialis(String namasubspesialis) {
        this.namasubspesialis = namasubspesialis;
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

}
