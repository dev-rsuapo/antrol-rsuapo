/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.api.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author RSU Aisyiyah Ponorogo <rsuapo@yahoo.co.id>
 */
@Entity
@Table(name = "jadwal_dokter")
@NamedQueries({
    @NamedQuery(name = "JadwalDokter.findAll", query = "SELECT j FROM JadwalDokter j")})
public class JadwalDokter implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Column(name = "kodesubspesialis")
    private String kodesubspesialis;
    @Column(name = "namasubspesialis")
    private String namasubspesialis;
    @Column(name = "hari")
    private Integer hari;
    @Column(name = "kapasitaspasien")
    private Integer kapasitaspasien;
    @Column(name = "libur")
    private Integer libur;
    @Column(name = "namahari")
    private String namahari;
    @Column(name = "jadwal")
    private String jadwal;
    @Column(name = "kodedokter")
    private String kodedokter;
    @Column(name = "namadokter")
    private String namadokter;
    @Column(name = "kodepoli")
    private String kodepoli;
    @Column(name = "namapoli")
    private String namapoli;
    @Column(name = "kuota_jkn")
    private Integer kuotaJkn;
    @Column(name = "kuota_non_jkn")
    private Integer kuotaNonJkn;

    public JadwalDokter() {
    }

    public JadwalDokter(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKodesubspesialis() {
        return kodesubspesialis;
    }

    public void setKodesubspesialis(String kodesubspesialis) {
        this.kodesubspesialis = kodesubspesialis;
    }

    public String getNamasubspesialis() {
        return namasubspesialis;
    }

    public void setNamasubspesialis(String namasubspesialis) {
        this.namasubspesialis = namasubspesialis;
    }

    public Integer getHari() {
        return hari;
    }

    public void setHari(Integer hari) {
        this.hari = hari;
    }

    public Integer getKapasitaspasien() {
        return kapasitaspasien;
    }

    public void setKapasitaspasien(Integer kapasitaspasien) {
        this.kapasitaspasien = kapasitaspasien;
    }

    public Integer getLibur() {
        return libur;
    }

    public void setLibur(Integer libur) {
        this.libur = libur;
    }

    public String getNamahari() {
        return namahari;
    }

    public void setNamahari(String namahari) {
        this.namahari = namahari;
    }

    public String getJadwal() {
        return jadwal;
    }

    public void setJadwal(String jadwal) {
        this.jadwal = jadwal;
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

    public Integer getKuotaJkn() {
        return kuotaJkn;
    }

    public void setKuotaJkn(Integer kuotaJkn) {
        this.kuotaJkn = kuotaJkn;
    }

    public Integer getKuotaNonJkn() {
        return kuotaNonJkn;
    }

    public void setKuotaNonJkn(Integer kuotaNonJkn) {
        this.kuotaNonJkn = kuotaNonJkn;
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
        if (!(object instanceof JadwalDokter)) {
            return false;
        }
        JadwalDokter other = (JadwalDokter) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rsuapo.antrol.api.entities.JadwalDokter[ id=" + id + " ]";
    }
    
}
