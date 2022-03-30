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
@Table(name = "pasien")
@NamedQueries({
    @NamedQuery(name = "Pasien.findAll", query = "SELECT p FROM Pasien p")})
public class Pasien implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Column(name = "norm")
    private String norm;
    @Column(name = "nomorkartu")
    private String nomorkartu;
    @Column(name = "nik")
    private String nik;
    @Column(name = "nomorkk")
    private String nomorkk;
    @Column(name = "nama")
    private String nama;
    @Column(name = "jeniskelamin")
    private String jeniskelamin;
    @Column(name = "tanggallahir")
    private String tanggallahir;
    @Column(name = "nohp")
    private String nohp;
    @Column(name = "alamat")
    private String alamat;
    @Column(name = "kodeprop")
    private String kodeprop;
    @Column(name = "namaprop")
    private String namaprop;
    @Column(name = "kodedati2")
    private String kodedati2;
    @Column(name = "namadati2")
    private String namadati2;
    @Column(name = "kodekec")
    private String kodekec;
    @Column(name = "namakec")
    private String namakec;
    @Column(name = "kodekel")
    private String kodekel;
    @Column(name = "namakel")
    private String namakel;
    @Column(name = "rw")
    private String rw;
    @Column(name = "rt")
    private String rt;
    @Column(name = "pasienbaru")
    private Integer pasienbaru;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public Pasien() {
    }

    public Pasien(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNorm() {
        return norm;
    }

    public void setNorm(String norm) {
        this.norm = norm;
    }

    public String getNomorkartu() {
        return nomorkartu;
    }

    public void setNomorkartu(String nomorkartu) {
        this.nomorkartu = nomorkartu;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNomorkk() {
        return nomorkk;
    }

    public void setNomorkk(String nomorkk) {
        this.nomorkk = nomorkk;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJeniskelamin() {
        return jeniskelamin;
    }

    public void setJeniskelamin(String jeniskelamin) {
        this.jeniskelamin = jeniskelamin;
    }

    public String getTanggallahir() {
        return tanggallahir;
    }

    public void setTanggallahir(String tanggallahir) {
        this.tanggallahir = tanggallahir;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKodeprop() {
        return kodeprop;
    }

    public void setKodeprop(String kodeprop) {
        this.kodeprop = kodeprop;
    }

    public String getNamaprop() {
        return namaprop;
    }

    public void setNamaprop(String namaprop) {
        this.namaprop = namaprop;
    }

    public String getKodedati2() {
        return kodedati2;
    }

    public void setKodedati2(String kodedati2) {
        this.kodedati2 = kodedati2;
    }

    public String getNamadati2() {
        return namadati2;
    }

    public void setNamadati2(String namadati2) {
        this.namadati2 = namadati2;
    }

    public String getKodekec() {
        return kodekec;
    }

    public void setKodekec(String kodekec) {
        this.kodekec = kodekec;
    }

    public String getNamakec() {
        return namakec;
    }

    public void setNamakec(String namakec) {
        this.namakec = namakec;
    }

    public String getKodekel() {
        return kodekel;
    }

    public void setKodekel(String kodekel) {
        this.kodekel = kodekel;
    }

    public String getNamakel() {
        return namakel;
    }

    public void setNamakel(String namakel) {
        this.namakel = namakel;
    }

    public String getRw() {
        return rw;
    }

    public void setRw(String rw) {
        this.rw = rw;
    }

    public String getRt() {
        return rt;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public Integer getPasienbaru() {
        return pasienbaru;
    }

    public void setPasienbaru(Integer pasienbaru) {
        this.pasienbaru = pasienbaru;
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
        if (!(object instanceof Pasien)) {
            return false;
        }
        Pasien other = (Pasien) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rsuapo.antrol.api.entities.Pasien[ id=" + id + " ]";
    }
    
}
