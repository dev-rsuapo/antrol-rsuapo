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
public class PasienFpModel implements Serializable {

    private String nomorkartu;
    private String nik;
    private String tgllahir;
    private Integer daftarfp;

    public String getNomorkartu() {
        return nomorkartu;
    }

    public void setNomorkartu(String nomorkartu) {
        this.nomorkartu = nomorkartu;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getTgllahir() {
        return tgllahir;
    }

    public void setTgllahir(String tgllahir) {
        this.tgllahir = tgllahir;
    }

    public Integer getDaftarfp() {
        return daftarfp;
    }

    public void setDaftarfp(Integer daftarfp) {
        this.daftarfp = daftarfp;
    }

}
