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
@Table(name = "antrean_task")
@NamedQueries({
    @NamedQuery(name = "AntreanTask.findAll", query = "SELECT a FROM AntreanTask a")})
public class AntreanTask implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Column(name = "antrean_id")
    private String antreanId;
    @Column(name = "kodebooking")
    private String kodebooking;
    @Column(name = "task_id")
    private Integer taskId;
    @Column(name = "task_deskripsi")
    private String taskDeskripsi;
    @Column(name = "waktu")
    private Long waktu;
    @Column(name = "sync_status")
    private Integer syncStatus;
    @Column(name = "sync_count")
    private Integer syncCount;
    @Column(name = "sync_last_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date syncLastAt;
    @Column(name = "sync_method")
    private Integer syncMethod;
    @Column(name = "status")
    private Integer status;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public AntreanTask() {
    }

    public AntreanTask(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAntreanId() {
        return antreanId;
    }

    public void setAntreanId(String antreanId) {
        this.antreanId = antreanId;
    }

    public String getKodebooking() {
        return kodebooking;
    }

    public void setKodebooking(String kodebooking) {
        this.kodebooking = kodebooking;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTaskDeskripsi() {
        return taskDeskripsi;
    }

    public void setTaskDeskripsi(String taskDeskripsi) {
        this.taskDeskripsi = taskDeskripsi;
    }

    public Long getWaktu() {
        return waktu;
    }

    public void setWaktu(Long waktu) {
        this.waktu = waktu;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        if (!(object instanceof AntreanTask)) {
            return false;
        }
        AntreanTask other = (AntreanTask) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rsuapo.antrol.api.entities.AntreanTask[ id=" + id + " ]";
    }
    
}
