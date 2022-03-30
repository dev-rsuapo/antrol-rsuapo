/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.api.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author RSU Aisyiyah Ponorogo <rsuapo@yahoo.co.id>
 */
@Entity
@Table(name = "dokter")
@NamedQueries({
    @NamedQuery(name = "Dokter.findAll", query = "SELECT d FROM Dokter d")})
public class Dokter implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Column(name = "kodedokter")
    private String kodedokter;
    @Column(name = "namadokter")
    private String namadokter;
    @Column(name = "simrs_kode_dokter")
    private String simrsKodeDokter;
    @Column(name = "display_channel")
    private String displayChannel;
    @Column(name = "display_ruang")
    private String displayRuang;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public Dokter() {
    }

    public Dokter(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKodedokter() {
        return kodedokter;
    }

    public void setKodedokter(String kodedokter) {
        this.kodedokter = kodedokter;
    }

    public String getNamadokter() {
        return namadokter;
    }

    public void setNamadokter(String namadokter) {
        this.namadokter = namadokter;
    }

    public String getSimrsKodeDokter() {
        return simrsKodeDokter;
    }

    public void setSimrsKodeDokter(String simrsKodeDokter) {
        this.simrsKodeDokter = simrsKodeDokter;
    }

    public String getDisplayChannel() {
        return displayChannel;
    }

    public void setDisplayChannel(String displayChannel) {
        this.displayChannel = displayChannel;
    }

    public String getDisplayRuang() {
        return displayRuang;
    }

    public void setDisplayRuang(String displayRuang) {
        this.displayRuang = displayRuang;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dokter)) {
            return false;
        }
        Dokter other = (Dokter) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rsuapo.antrol.api.entities.Dokter[ id=" + id + " ]";
    }
    
}
