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
@Table(name = "poli")
@NamedQueries({
    @NamedQuery(name = "Poli.findAll", query = "SELECT p FROM Poli p")})
public class Poli implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Column(name = "nmpoli")
    private String nmpoli;
    @Column(name = "nmsubspesialis")
    private String nmsubspesialis;
    @Column(name = "kdsubspesialis")
    private String kdsubspesialis;
    @Column(name = "kdpoli")
    private String kdpoli;
    @Column(name = "simrs_kode_poli")
    private String simrsKodePoli;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public Poli() {
    }

    public Poli(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSimrsKodePoli() {
        return simrsKodePoli;
    }

    public void setSimrsKodePoli(String simrsKodePoli) {
        this.simrsKodePoli = simrsKodePoli;
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
        if (!(object instanceof Poli)) {
            return false;
        }
        Poli other = (Poli) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rsuapo.antrol.api.entities.Poli[ id=" + id + " ]";
    }
    
}
