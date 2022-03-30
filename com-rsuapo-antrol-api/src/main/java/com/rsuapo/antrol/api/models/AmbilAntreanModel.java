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
public class AmbilAntreanModel implements Serializable {

    private String nomorkartu;
    private String nik;
    private String nohp;
    private String kodepoli;
    private String norm;
    private String tanggalperiksa;
    private String kodedokter;
    private String jampraktek;
    private Integer jeniskunjungan;
    private String nomorreferensi;
}
