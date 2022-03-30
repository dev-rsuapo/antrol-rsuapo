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
@Table(name = "antrean")
@NamedQueries({
    @NamedQuery(name = "Antrean.findAll", query = "SELECT a FROM Antrean a")})
public class Antrean implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Column(name = "kodebooking")
    private String kodebooking;
    @Column(name = "jenispasien")
    private String jenispasien;
    @Column(name = "nomorkartu")
    private String nomorkartu;
    @Column(name = "nik")
    private String nik;
    @Column(name = "nohp")
    private String nohp;
    @Column(name = "kodepoli")
    private String kodepoli;
    @Column(name = "namapoli")
    private String namapoli;
    @Column(name = "pasienbaru")
    private Integer pasienbaru;
    @Column(name = "norm")
    private String norm;
    @Column(name = "tanggalperiksa")
    private String tanggalperiksa;
    @Column(name = "kodedokter")
    private String kodedokter;
    @Column(name = "namadokter")
    private String namadokter;
    @Column(name = "jampraktek")
    private String jampraktek;
    @Column(name = "jeniskunjungan")
    private Integer jeniskunjungan;
    @Column(name = "nomorreferensi")
    private String nomorreferensi;
    @Column(name = "nomorantrean")
    private String nomorantrean;
    @Column(name = "angkaantrean")
    private Integer angkaantrean;
    @Column(name = "estimasidilayani")
    private Long estimasidilayani;
    @Column(name = "sisakuotajkn")
    private Integer sisakuotajkn;
    @Column(name = "kuotajkn")
    private Integer kuotajkn;
    @Column(name = "sisakuotanonjkn")
    private Integer sisakuotanonjkn;
    @Column(name = "kuotanonjkn")
    private Integer kuotanonjkn;
    @Column(name = "keterangan")
    private String keterangan;
    @Column(name = "ref_device_id")
    private String refDeviceId;
    @Column(name = "ref_device_deskripsi")
    private String refDeviceDeskripsi;
    @Column(name = "sync_status")
    private Integer syncStatus;
    @Column(name = "sync_count")
    private Integer syncCount;
    @Column(name = "sync_last_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date syncLastAt;
    @Column(name = "sync_method")
    private Integer syncMethod;
    @Column(name = "antrean_pendaftaran_nomor")
    private Integer antreanPendaftaranNomor;
    @Column(name = "antrean_pendaftaran_sudah_dipanggil")
    private Boolean antreanPendaftaranSudahDipanggil;
    @Column(name = "antrean_pendaftaran_waktu_dipanggil")
    @Temporal(TemporalType.TIMESTAMP)
    private Date antreanPendaftaranWaktuDipanggil;
    @Column(name = "sudah_check_in")
    private Boolean sudahCheckIn;
    @Column(name = "waktu_check_in")
    @Temporal(TemporalType.TIMESTAMP)
    private Date waktuCheckIn;
    @Column(name = "poli_sudah_dipanggil")
    private Boolean poliSudahDipanggil;
    @Column(name = "poli_waktu_dipanggil")
    @Temporal(TemporalType.TIMESTAMP)
    private Date poliWaktuDipanggil;
    @Column(name = "antrean_farmasi_lokasi_id")
    private String antreanFarmasiLokasiId;
    @Column(name = "antrean_farmasi_lokasi_deskripsi")
    private String antreanFarmasiLokasiDeskripsi;
    @Column(name = "antrean_farmasi_nomor")
    private Integer antreanFarmasiNomor;
    @Column(name = "antrean_farmasi_sudah_dipanggil")
    private Boolean antreanFarmasiSudahDipanggil;
    @Column(name = "antrean_farmasi_waktu_dipanggil")
    @Temporal(TemporalType.TIMESTAMP)
    private Date antreanFarmasiWaktuDipanggil;
    @Column(name = "waktu_task1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date waktuTask1;
    @Column(name = "waktu_task2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date waktuTask2;
    @Column(name = "waktu_task3")
    @Temporal(TemporalType.TIMESTAMP)
    private Date waktuTask3;
    @Column(name = "waktu_task4")
    @Temporal(TemporalType.TIMESTAMP)
    private Date waktuTask4;
    @Column(name = "waktu_task5")
    @Temporal(TemporalType.TIMESTAMP)
    private Date waktuTask5;
    @Column(name = "waktu_task6")
    @Temporal(TemporalType.TIMESTAMP)
    private Date waktuTask6;
    @Column(name = "waktu_task7")
    @Temporal(TemporalType.TIMESTAMP)
    private Date waktuTask7;
    @Column(name = "waktu_task99")
    @Temporal(TemporalType.TIMESTAMP)
    private Date waktuTask99;
    @Column(name = "last_executed_task_id")
    private Integer lastExecutedTaskId;
    @Column(name = "last_executed_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastExecutedAt;
    @Column(name = "status")
    private Integer status;
    @Column(name = "catatan_pembatalan")
    private String catatanPembatalan;
    @Column(name = "waktu_dibatalkan")
    @Temporal(TemporalType.TIMESTAMP)
    private Date waktuDibatalkan;
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

    public Antrean() {
    }

    public Antrean(String id) {
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

    public String getJenispasien() {
        return jenispasien;
    }

    public void setJenispasien(String jenispasien) {
        this.jenispasien = jenispasien;
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

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
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

    public Integer getPasienbaru() {
        return pasienbaru;
    }

    public void setPasienbaru(Integer pasienbaru) {
        this.pasienbaru = pasienbaru;
    }

    public String getNorm() {
        return norm;
    }

    public void setNorm(String norm) {
        this.norm = norm;
    }

    public String getTanggalperiksa() {
        return tanggalperiksa;
    }

    public void setTanggalperiksa(String tanggalperiksa) {
        this.tanggalperiksa = tanggalperiksa;
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

    public String getJampraktek() {
        return jampraktek;
    }

    public void setJampraktek(String jampraktek) {
        this.jampraktek = jampraktek;
    }

    public Integer getJeniskunjungan() {
        return jeniskunjungan;
    }

    public void setJeniskunjungan(Integer jeniskunjungan) {
        this.jeniskunjungan = jeniskunjungan;
    }

    public String getNomorreferensi() {
        return nomorreferensi;
    }

    public void setNomorreferensi(String nomorreferensi) {
        this.nomorreferensi = nomorreferensi;
    }

    public String getNomorantrean() {
        return nomorantrean;
    }

    public void setNomorantrean(String nomorantrean) {
        this.nomorantrean = nomorantrean;
    }

    public Integer getAngkaantrean() {
        return angkaantrean;
    }

    public void setAngkaantrean(Integer angkaantrean) {
        this.angkaantrean = angkaantrean;
    }

    public Long getEstimasidilayani() {
        return estimasidilayani;
    }

    public void setEstimasidilayani(Long estimasidilayani) {
        this.estimasidilayani = estimasidilayani;
    }

    public Integer getSisakuotajkn() {
        return sisakuotajkn;
    }

    public void setSisakuotajkn(Integer sisakuotajkn) {
        this.sisakuotajkn = sisakuotajkn;
    }

    public Integer getKuotajkn() {
        return kuotajkn;
    }

    public void setKuotajkn(Integer kuotajkn) {
        this.kuotajkn = kuotajkn;
    }

    public Integer getSisakuotanonjkn() {
        return sisakuotanonjkn;
    }

    public void setSisakuotanonjkn(Integer sisakuotanonjkn) {
        this.sisakuotanonjkn = sisakuotanonjkn;
    }

    public Integer getKuotanonjkn() {
        return kuotanonjkn;
    }

    public void setKuotanonjkn(Integer kuotanonjkn) {
        this.kuotanonjkn = kuotanonjkn;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getRefDeviceId() {
        return refDeviceId;
    }

    public void setRefDeviceId(String refDeviceId) {
        this.refDeviceId = refDeviceId;
    }

    public String getRefDeviceDeskripsi() {
        return refDeviceDeskripsi;
    }

    public void setRefDeviceDeskripsi(String refDeviceDeskripsi) {
        this.refDeviceDeskripsi = refDeviceDeskripsi;
    }

    public Integer getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(Integer syncStatus) {
        this.syncStatus = syncStatus;
    }

    public Integer getSyncCount() {
        return syncCount;
    }

    public void setSyncCount(Integer syncCount) {
        this.syncCount = syncCount;
    }

    public Date getSyncLastAt() {
        return syncLastAt;
    }

    public void setSyncLastAt(Date syncLastAt) {
        this.syncLastAt = syncLastAt;
    }

    public Integer getSyncMethod() {
        return syncMethod;
    }

    public void setSyncMethod(Integer syncMethod) {
        this.syncMethod = syncMethod;
    }

    public Integer getAntreanPendaftaranNomor() {
        return antreanPendaftaranNomor;
    }

    public void setAntreanPendaftaranNomor(Integer antreanPendaftaranNomor) {
        this.antreanPendaftaranNomor = antreanPendaftaranNomor;
    }

    public Boolean getAntreanPendaftaranSudahDipanggil() {
        return antreanPendaftaranSudahDipanggil;
    }

    public void setAntreanPendaftaranSudahDipanggil(Boolean antreanPendaftaranSudahDipanggil) {
        this.antreanPendaftaranSudahDipanggil = antreanPendaftaranSudahDipanggil;
    }

    public Date getAntreanPendaftaranWaktuDipanggil() {
        return antreanPendaftaranWaktuDipanggil;
    }

    public void setAntreanPendaftaranWaktuDipanggil(Date antreanPendaftaranWaktuDipanggil) {
        this.antreanPendaftaranWaktuDipanggil = antreanPendaftaranWaktuDipanggil;
    }

    public Boolean getSudahCheckIn() {
        return sudahCheckIn;
    }

    public void setSudahCheckIn(Boolean sudahCheckIn) {
        this.sudahCheckIn = sudahCheckIn;
    }

    public Date getWaktuCheckIn() {
        return waktuCheckIn;
    }

    public void setWaktuCheckIn(Date waktuCheckIn) {
        this.waktuCheckIn = waktuCheckIn;
    }

    public Boolean getPoliSudahDipanggil() {
        return poliSudahDipanggil;
    }

    public void setPoliSudahDipanggil(Boolean poliSudahDipanggil) {
        this.poliSudahDipanggil = poliSudahDipanggil;
    }

    public Date getPoliWaktuDipanggil() {
        return poliWaktuDipanggil;
    }

    public void setPoliWaktuDipanggil(Date poliWaktuDipanggil) {
        this.poliWaktuDipanggil = poliWaktuDipanggil;
    }

    public String getAntreanFarmasiLokasiId() {
        return antreanFarmasiLokasiId;
    }

    public void setAntreanFarmasiLokasiId(String antreanFarmasiLokasiId) {
        this.antreanFarmasiLokasiId = antreanFarmasiLokasiId;
    }

    public String getAntreanFarmasiLokasiDeskripsi() {
        return antreanFarmasiLokasiDeskripsi;
    }

    public void setAntreanFarmasiLokasiDeskripsi(String antreanFarmasiLokasiDeskripsi) {
        this.antreanFarmasiLokasiDeskripsi = antreanFarmasiLokasiDeskripsi;
    }

    public Integer getAntreanFarmasiNomor() {
        return antreanFarmasiNomor;
    }

    public void setAntreanFarmasiNomor(Integer antreanFarmasiNomor) {
        this.antreanFarmasiNomor = antreanFarmasiNomor;
    }

    public Boolean getAntreanFarmasiSudahDipanggil() {
        return antreanFarmasiSudahDipanggil;
    }

    public void setAntreanFarmasiSudahDipanggil(Boolean antreanFarmasiSudahDipanggil) {
        this.antreanFarmasiSudahDipanggil = antreanFarmasiSudahDipanggil;
    }

    public Date getAntreanFarmasiWaktuDipanggil() {
        return antreanFarmasiWaktuDipanggil;
    }

    public void setAntreanFarmasiWaktuDipanggil(Date antreanFarmasiWaktuDipanggil) {
        this.antreanFarmasiWaktuDipanggil = antreanFarmasiWaktuDipanggil;
    }

    public Date getWaktuTask1() {
        return waktuTask1;
    }

    public void setWaktuTask1(Date waktuTask1) {
        this.waktuTask1 = waktuTask1;
    }

    public Date getWaktuTask2() {
        return waktuTask2;
    }

    public void setWaktuTask2(Date waktuTask2) {
        this.waktuTask2 = waktuTask2;
    }

    public Date getWaktuTask3() {
        return waktuTask3;
    }

    public void setWaktuTask3(Date waktuTask3) {
        this.waktuTask3 = waktuTask3;
    }

    public Date getWaktuTask4() {
        return waktuTask4;
    }

    public void setWaktuTask4(Date waktuTask4) {
        this.waktuTask4 = waktuTask4;
    }

    public Date getWaktuTask5() {
        return waktuTask5;
    }

    public void setWaktuTask5(Date waktuTask5) {
        this.waktuTask5 = waktuTask5;
    }

    public Date getWaktuTask6() {
        return waktuTask6;
    }

    public void setWaktuTask6(Date waktuTask6) {
        this.waktuTask6 = waktuTask6;
    }

    public Date getWaktuTask7() {
        return waktuTask7;
    }

    public void setWaktuTask7(Date waktuTask7) {
        this.waktuTask7 = waktuTask7;
    }

    public Date getWaktuTask99() {
        return waktuTask99;
    }

    public void setWaktuTask99(Date waktuTask99) {
        this.waktuTask99 = waktuTask99;
    }

    public Integer getLastExecutedTaskId() {
        return lastExecutedTaskId;
    }

    public void setLastExecutedTaskId(Integer lastExecutedTaskId) {
        this.lastExecutedTaskId = lastExecutedTaskId;
    }

    public Date getLastExecutedAt() {
        return lastExecutedAt;
    }

    public void setLastExecutedAt(Date lastExecutedAt) {
        this.lastExecutedAt = lastExecutedAt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCatatanPembatalan() {
        return catatanPembatalan;
    }

    public void setCatatanPembatalan(String catatanPembatalan) {
        this.catatanPembatalan = catatanPembatalan;
    }

    public Date getWaktuDibatalkan() {
        return waktuDibatalkan;
    }

    public void setWaktuDibatalkan(Date waktuDibatalkan) {
        this.waktuDibatalkan = waktuDibatalkan;
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
        if (!(object instanceof Antrean)) {
            return false;
        }
        Antrean other = (Antrean) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rsuapo.antrol.api.entities.Antrean[ id=" + id + " ]";
    }
    
}
