/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.api.messages;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author RSU Aisyiyah Ponorogo <rsuapo@yahoo.co.id>
 */
@Getter
@Setter
public class AntreanMessage implements Serializable {
    private String displayChannel;
    private String displayRuang;
    private String dokterId;
    private String dokterNama;
    /**
     * type:
     * 1: pendaftaran 
     * 2: poli 
     * 3: farmasi
     */
    private Integer type; 
    
    /**
     * lokasi (unit id)
     * lokasi pendaftaran, poliId, farmasiId
     */
    private String unitId;
    private String unitNama;
    private Integer nomorAntrean;
    private String message;
    
}
