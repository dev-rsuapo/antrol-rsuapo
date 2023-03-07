/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.api.respositories;

import com.rsuapo.antrol.api.entities.AntreanTask;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author RSU Aisyiyah Ponorogo <rsuapo@yahoo.co.id>
 */
public interface AntreanTaskRepository extends JpaRepository<AntreanTask, String> {

    public Optional<AntreanTask> findByKodebookingAndTaskId(String kodebooking, int taskId);

    @Query("SELECT a FROM AntreanTask a WHERE a.status = 1 AND a.syncStatus = 0 AND a.syncCount < :toleransi ORDER BY a.createdAt ASC")
    public List<AntreanTask> findAllNeedToPush(@Param("toleransi") Integer toleransi);

}
