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
public class PoliModel implements Serializable {

    private String nmpoli;
    private String nmsubspesialis;
    private String kdsubspesialis;
    private String kdpoli;

    public PoliModel() {
    }

    public String getNmpoli() {
        return nmpoli;
    }

    public void setNmpoli(String nmpoli) {
        this.nmpoli = nmpoli;
    }

    public String getNmsubspesialis() {
        return nmsubspesialis;
    }

    public void setNmsubspesialis(String nmsubspesialis) {
        this.nmsubspesialis = nmsubspesialis;
    }

    public String getKdsubspesialis() {
        return kdsubspesialis;
    }

    public void setKdsubspesialis(String kdsubspesialis) {
        this.kdsubspesialis = kdsubspesialis;
    }

    public String getKdpoli() {
        return kdpoli;
    }

    public void setKdpoli(String kdpoli) {
        this.kdpoli = kdpoli;
    }

}
