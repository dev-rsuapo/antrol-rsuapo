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
public class TaskModel implements Serializable {
    private String wakturs;
    private String waktu;
    private String taskname;
    private Integer taskid;
    private String kodebooking;

    public TaskModel() {
    }

    public TaskModel(String wakturs, String waktu, String taskname, Integer taskid, String kodebooking) {
        this.wakturs = wakturs;
        this.waktu = waktu;
        this.taskname = taskname;
        this.taskid = taskid;
        this.kodebooking = kodebooking;
    }

    public String getWakturs() {
        return wakturs;
    }

    public void setWakturs(String wakturs) {
        this.wakturs = wakturs;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public Integer getTaskid() {
        return taskid;
    }

    public void setTaskid(Integer taskid) {
        this.taskid = taskid;
    }

    public String getKodebooking() {
        return kodebooking;
    }

    public void setKodebooking(String kodebooking) {
        this.kodebooking = kodebooking;
    }
    
    
}
