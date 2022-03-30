/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.api.models;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author RSU Aisyiyah Ponorogo <rsuapo@yahoo.co.id>
 */
@Getter
@Setter
public class AntreanModel implements Serializable {

    private String nomorkartu;
    private String nik;
    private String nomorkk;
    private String nama;
    private String jeniskelamin;
    private String tanggallahir;
    private String nohp;
    private String alamat;
    private String kodeprop;
    private String namaprop;
    private String kodedati2;
    private String namadati2;
    private String kodekec;
    private String namakec;
    private String kodekel;
    private String namakel;
    private String rw;
    private String rt;

    private String kodepoli;
    private String norm;
    private String tanggalperiksa;
    private String kodedokter;
    private String jampraktek;
    private Integer jeniskunjungan;
    private String nomorreferensi;
    private String jenispasien;
    
    private String refDeviceId;
    private String refDeviceDeskripsi;
    
}
