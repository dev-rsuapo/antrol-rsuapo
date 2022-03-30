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
@Table(name = "operasi")
@NamedQueries({
    @NamedQuery(name = "Operasi.findAll", query = "SELECT o FROM Operasi o")})
public class Operasi implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Column(name = "kodebooking")
    private String kodebooking;
    @Column(name = "tanggaloperasi")
    private String tanggaloperasi;
    @Column(name = "jenistindakan")
    private String jenistindakan;
    @Column(name = "kodepoli")
    private String kodepoli;
    @Column(name = "namapoli")
    private String namapoli;
    @Column(name = "terlaksana")
    private Integer terlaksana;
    @Column(name = "nopeserta")
    private String nopeserta;
    @Column(name = "lastupdate")
    private Long lastupdate;
    @Column(name = "norm")
    private String norm;
    @Column(name = "nama")
    private String nama;
    @Column(name = "status")
    private Integer status;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public Operasi() {
    }

    public Operasi(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKodebooking() {
        return kodebooking;
    }

    public void setKodebooking(String kodebooking) {
        this.kodebooking = kodebooking;
    }

    public String getTanggaloperasi() {
        return tanggaloperasi;
    }

    public void setTanggaloperasi(String tanggaloperasi) {
        this.tanggaloperasi = tanggaloperasi;
    }

    public String getJenistindakan() {
        return jenistindakan;
    }

    public void setJenistindakan(String jenistindakan) {
        this.jenistindakan = jenistindakan;
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

    public Integer getTerlaksana() {
        return terlaksana;
    }

    public void setTerlaksana(Integer terlaksana) {
        this.terlaksana = terlaksana;
    }

    public String getNopeserta() {
        return nopeserta;
    }

    public void setNopeserta(String nopeserta) {
        this.nopeserta = nopeserta;
    }

    public Long getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(Long lastupdate) {
        this.lastupdate = lastupdate;
    }

    public String getNorm() {
        return norm;
    }

    public void setNorm(String norm) {
        this.norm = norm;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
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
        if (!(object instanceof Operasi)) {
            return false;
        }
        Operasi other = (Operasi) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rsuapo.antrol.api.entities.Operasi[ id=" + id + " ]";
    }
    
}
