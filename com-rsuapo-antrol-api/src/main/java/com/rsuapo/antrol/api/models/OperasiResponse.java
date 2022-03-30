/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.api.models;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author RSU Aisyiyah Ponorogo <rsuapo@yahoo.co.id>
 */
@Getter
@Setter
public class OperasiResponse implements Serializable {

    private String id;
    private String kodebooking;
    private String tanggaloperasi;
    private String jenistindakan;
    private String kodepoli;
    private String namapoli;
    private Integer terlaksana;
    private String nopeserta;
    private Long lastupdate;
    private String norm;
    private String nama;
    private Integer status;
    private String createdBy;
    private Date createdAt;
    private String updatedBy;
    private Date updatedAt;
    
}
