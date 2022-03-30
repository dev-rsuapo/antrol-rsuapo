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
public class DokterModel implements Serializable {

    private String namadokter;
    private String kodedokter;

    public DokterModel() {
    }

    public DokterModel(String namadokter, String kodedokter) {
        this.namadokter = namadokter;
        this.kodedokter = kodedokter;
    }

    public String getNamadokter() {
        return namadokter;
    }

    public void setNamadokter(String namadokter) {
        this.namadokter = namadokter;
    }

    public String getKodedokter() {
        return kodedokter;
    }

    public void setKodedokter(String kodedokter) {
        this.kodedokter = kodedokter;
    }

}
