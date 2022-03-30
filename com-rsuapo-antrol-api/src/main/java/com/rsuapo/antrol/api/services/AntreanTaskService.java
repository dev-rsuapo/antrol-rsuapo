/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.api.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsuapo.antrol.api.entities.Antrean;
import com.rsuapo.antrol.api.entities.AntreanTask;
import com.rsuapo.antrol.api.respositories.AntreanRepository;
import com.rsuapo.antrol.api.respositories.AntreanTaskRepository;
import com.rsuapo.antrol.library.Constants;
import com.rsuapo.antrol.library.models.ResponseMetadataModel;
import com.rsuapo.antrol.library.service.AntrolService;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author RSU Aisyiyah Ponorogo <rsuapo@yahoo.co.id>
 */
@Service
public class AntreanTaskService {
    
    @Value("${scheduled.antrian.toleransiJumlahGagal}")
    private Integer toleransiJumlahGagal;

    @Autowired
    private AntreanTaskRepository antreanTaskRepository;
    @Autowired
    private AntreanRepository antreanRepository;
    @Autowired
    private AntrolService antrolService;

    @Transactional(rollbackFor = Exception.class)
    public AntreanTask put(Antrean antrean, int taskId) throws Exception {
        if (taskId < 1 && taskId > 7 && taskId != 99) {
            throw new Exception("Taks ID salah, Task ID adalah 1 sampai 7 dan 99");
        }
        
        Optional<AntreanTask> optional = antreanTaskRepository.findByKodebookingAndTaskId(antrean.getKodebooking(), taskId);
        if (optional.isPresent()) {
            throw new Exception("Task ID " + taskId + " sebelumnya sudah dieksekusi");
        }
        
        Date waktu = new Date();

        AntreanTask at = new AntreanTask(UUID.randomUUID().toString());
        at.setAntreanId(antrean.getId());
        at.setKodebooking(antrean.getKodebooking());
        at.setTaskId(taskId);
        at.setWaktu(waktu.getTime());
        at.setSyncStatus(Constants.SYNC_STATUS_DRAFT);
        at.setSyncCount(0);
        at.setCreatedAt(waktu);
        at.setUpdatedAt(waktu);
        at.setStatus(1);

        antrean.setLastExecutedTaskId(taskId);
        antrean.setLastExecutedAt(waktu);
        
        switch (taskId) {
            case 1:
                antrean.setWaktuTask1(waktu);
                break;
            case 2:
                antrean.setWaktuTask2(waktu);
                break;
            case 3:
                antrean.setWaktuTask3(waktu);
                break;
            case 4:
                antrean.setWaktuTask4(waktu);
                break;
            case 5:
                antrean.setWaktuTask5(waktu);
                break;
            case 6:
                antrean.setWaktuTask6(waktu);
                break;
            case 7:
                antrean.setWaktuTask7(waktu);
                break;
            case 99:
                antrean.setWaktuTask99(waktu);
                break;
            default:
                break;
        }

        antreanRepository.save(antrean);
        return antreanTaskRepository.save(at);
    }
    
    @Transactional(rollbackFor = Exception.class)
    public AntreanTask put(Antrean antrean, int taskId, long date) throws Exception {
        if (taskId < 1 && taskId > 3) {
            throw new Exception("Taks ID salah, Task ID adalah 1 sampai 3");
        }
        
        Optional<AntreanTask> optional = antreanTaskRepository.findByKodebookingAndTaskId(antrean.getKodebooking(), taskId);
        if (optional.isPresent()) {
            throw new Exception("Task ID " + taskId + " sebelumnya sudah dieksekusi");
        }
        
        AntreanTask at = new AntreanTask(UUID.randomUUID().toString());
        at.setAntreanId(antrean.getId());
        at.setKodebooking(antrean.getKodebooking());
        at.setTaskId(taskId);
        at.setWaktu(date);
        at.setSyncStatus(Constants.SYNC_STATUS_DRAFT);
        at.setSyncCount(0);
        at.setCreatedAt(new Date());
        at.setUpdatedAt(new Date());
        at.setStatus(1);

        antrean.setLastExecutedTaskId(taskId);
        antrean.setLastExecutedAt(new Date());
        antreanRepository.save(antrean);
        return antreanTaskRepository.save(at);
    }

    @Transactional(rollbackFor = Exception.class)
    public void push() throws Exception {
        if (toleransiJumlahGagal == null) {
            toleransiJumlahGagal = 3;
        }
        
        //System.out.println("update task...");
        List<AntreanTask> antreanTasks = antreanTaskRepository.findAllNeedToPush(toleransiJumlahGagal);
        for (AntreanTask a : antreanTasks) {
            if (a.getSyncCount() == null) {
                a.setSyncCount(0);
            }

            if (a.getSyncCount() < toleransiJumlahGagal) {
                ResponseMetadataModel res = antrolService.updateWaktuAntrean(a.getKodebooking(), a.getTaskId(), a.getWaktu());
                System.out.println(new ObjectMapper().writeValueAsString(res));
                if (200 == res.getMetadata().getCode()) {
                    System.out.println("antrean " + a.getKodebooking() + " ok");
                    a.setSyncStatus(Constants.SYNC_STATUS_SELESAI);
                    a.setSyncLastAt(new Date());
                    a.setSyncMethod(1);
                }

                a.setSyncCount(a.getSyncCount() + 1);

                antreanTaskRepository.save(a);
            }

        }
    }
}
