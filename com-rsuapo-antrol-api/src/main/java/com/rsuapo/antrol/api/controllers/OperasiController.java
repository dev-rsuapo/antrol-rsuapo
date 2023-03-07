/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.api.controllers;

import com.rsuapo.antrol.api.entities.Operasi;
import com.rsuapo.antrol.api.entities.Pasien;
import com.rsuapo.antrol.api.entities.Poli;
import com.rsuapo.antrol.api.services.OperasiService;
import com.rsuapo.antrol.api.services.PasienService;
import com.rsuapo.antrol.api.services.PoliService;
import com.rsuapo.antrol.api.models.OperasiRequest;
import com.rsuapo.antrol.api.models.OperasiResponse;
import com.rsuapo.antrol.api.models.ResponseModel;
import com.rsuapo.antrol.library.models.MetadataModel;
import com.rsuapo.antrol.library.models.ResponseMetadataModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author RSU Aisyiyah Ponorogo <rsuapo@yahoo.co.id>
 */
@RestController
@RequestMapping("/api")
public class OperasiController {

    @Autowired
    private PasienService pasienService;
    @Autowired
    private PoliService poliService;
    @Autowired
    private OperasiService operasiService;

    @PostMapping("/operasis")
    public ResponseEntity create(@Validated @RequestBody OperasiRequest request) {
        try {

            if (!StringUtils.hasText(request.getTanggaloperasi())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, "Tanggal operasi harus diisi"));
            }

            if (!StringUtils.hasText(request.getJenistindakan())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, "Kode booking harus diisi"));
            }

            if (!StringUtils.hasText(request.getKodepoli())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, "Kode poli harus diisi"));
            }

            if (!StringUtils.hasText(request.getNopeserta())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, "Nomor peserta harus diisi"));
            }

            if (!StringUtils.hasText(request.getNorm())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, "Nomor RM harus diisi"));
            }

            Pasien pasien = pasienService.findByNomorRm(request.getNorm());
            if (pasien == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorMessage(404, "Data pasien dengan nomor rm " + request.getNorm() + " tidak ditemukan"));
            }

            if (!Objects.equals(pasien.getNomorkartu(), request.getNopeserta())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, "Data pasien dengan nomor kartu " + request.getNopeserta() + " tidak cocok dengan nomor kartu yang tersimpan dalam database"));
            }

            Poli poli = poliService.findByKdpoli(request.getKodepoli());
            if (poli == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorMessage(404, "Data poli dengan kode poli " + request.getKodepoli() + " tidak ditemukan"));
            }

            boolean isSudahTerdaftar = operasiService.isSudahTerdaftar(request.getNorm(), request.getTanggaloperasi());
            if (isSudahTerdaftar) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, "Pasien telah terdaftar operasi"));
            }

            Operasi o = new Operasi();
            o.setNorm(request.getNorm());
            o.setNama(pasien.getNama());
            o.setNopeserta(request.getNopeserta());
            o.setKodepoli(request.getKodepoli());
            o.setNamapoli(poli.getNmpoli());
            o.setJenistindakan(request.getJenistindakan());
            o.setTanggaloperasi(request.getTanggaloperasi());
            Operasi created = operasiService.create(o);

            ModelMapper modelMapper = new ModelMapper();
            OperasiResponse response = modelMapper.map(created, OperasiResponse.class);
            return ResponseEntity.ok(new ResponseMetadataModel<>(new MetadataModel(200, "Ok"), response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorMessage(500, e.getMessage()));
        }
    }

    @PostMapping("/operasis/batal/{kodebooking}")
    public ResponseEntity deleteOperasi(@PathVariable("kodebooking") String kodebooking) {
        try {
            Operasi operasi = operasiService.findByKodebooking(kodebooking);
            if (operasi == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorMessage(404, "Data operasi tidak ditemukan"));
            }

            if (!Objects.equals(operasi.getStatus(), 1)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, "Operasi gagal dibatalkan karena status operasi tidak aktif"));
            }

            if (Objects.equals(operasi.getTerlaksana(), 1)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, "Operasi gagal dibatalkan karena operasi telah terlaksana"));
            }

            operasiService.cancel(operasi);
            return ResponseEntity.ok(createSuccessMessage("Ok"));
        } catch (Exception e) {
            LOG.log(Level.SEVERE, null, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorMessage(500, e.getMessage()));
        }
    }

    @PostMapping("/operasis/update/{kodebooking}")
    public ResponseEntity updateOperasi(
            @PathVariable("kodebooking") String kodebooking, 
            @Validated @RequestBody OperasiRequest request) {
        try {
            Operasi operasi = operasiService.findByKodebooking(kodebooking);
            if (operasi == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorMessage(404, "Data operasi tidak ditemukan"));
            }

            if (!Objects.equals(operasi.getStatus(), 1)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, "Operasi gagal diupdate karena status operasi tidak aktif"));
            }

            if (Objects.equals(operasi.getTerlaksana(), 1)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, "Operasi gagal diupdate karena operasi telah terlaksana"));
            }

            if (!StringUtils.hasText(request.getTanggaloperasi())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, "Tanggal operasi harus diisi"));
            }

            if (!StringUtils.hasText(request.getJenistindakan())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, "Kode booking harus diisi"));
            }

            if (!StringUtils.hasText(request.getKodepoli())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, "Kode poli harus diisi"));
            }

            if (!StringUtils.hasText(request.getNopeserta())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, "Nomor peserta harus diisi"));
            }

            if (!StringUtils.hasText(request.getNorm())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, "Nomor RM harus diisi"));
            }

            Pasien pasien = pasienService.findByNomorRm(request.getNorm());
            if (pasien == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorMessage(404, "Data pasien dengan nomor rm " + request.getNorm() + " tidak ditemukan"));
            }

            if (!Objects.equals(pasien.getNomorkartu(), request.getNopeserta())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, "Data pasien dengan nomor kartu " + request.getNopeserta() + " tidak cocok dengan nomor kartu yang tersimpan dalam database"));
            }

            Poli poli = poliService.findByKdpoli(request.getKodepoli());
            if (poli == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorMessage(404, "Data poli dengan kode poli " + request.getKodepoli() + " tidak ditemukan"));
            }

            operasi.setKodepoli(request.getKodepoli());
            operasi.setNamapoli(poli.getNmpoli());
            operasi.setJenistindakan(request.getJenistindakan());
            operasi.setTanggaloperasi(request.getTanggaloperasi());
            operasi.setNorm(request.getNorm());
            operasi.setNama(pasien.getNama());
            operasi.setNopeserta(request.getNopeserta());
            Operasi updated = operasiService.update(operasi);

            ModelMapper modelMapper = new ModelMapper();
            OperasiResponse response = modelMapper.map(updated, OperasiResponse.class);
            return ResponseEntity.ok(new ResponseMetadataModel<>(new MetadataModel(200, "Ok"), response));
        } catch (Exception e) {
            LOG.log(Level.SEVERE, null, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorMessage(500, e.getMessage()));
        }
    }

    @PostMapping("/operasis/terlaksana/{kodebooking}")
    public ResponseEntity updateTerlaksana(@PathVariable("kodebooking") String kodebooking) {
        try {
            Operasi operasi = operasiService.findByKodebooking(kodebooking);
            if (operasi == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorMessage(404, "Data operasi tidak ditemukan"));
            }

            if (!Objects.equals(operasi.getStatus(), 1)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, "Operasi gagal diupdate karena status operasi tidak aktif"));
            }

            if (Objects.equals(operasi.getTerlaksana(), 1)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, "Operasi gagal diupdate menjadi terlaksana karena status operasi saat ini telah terlaksana"));
            }

            operasiService.updateTerlaksana(operasi, 1);
            return ResponseEntity.ok(createSuccessMessage("Ok"));
        } catch (Exception e) {
            LOG.log(Level.SEVERE, null, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorMessage(500, e.getMessage()));
        }
    }

    @PostMapping("/operasis/belumTerlaksana/{kodebooking}")
    public ResponseEntity updateBelumTerlaksana(@PathVariable("kodebooking") String kodebooking) {
        try {
            Operasi operasi = operasiService.findByKodebooking(kodebooking);
            if (operasi == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorMessage(404, "Data operasi tidak ditemukan"));
            }

            if (!Objects.equals(operasi.getStatus(), 1)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, "Operasi gagal diupdate karena status operasi tidak aktif"));
            }

            if (Objects.equals(operasi.getTerlaksana(), 0)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, "Operasi gagal diupdate menjadi belum terlaksana karena status operasi saat ini belum terlaksana"));
            }

            operasiService.updateTerlaksana(operasi, 0);
            return ResponseEntity.ok(createSuccessMessage("Ok"));
        } catch (Exception e) {
            LOG.log(Level.SEVERE, null, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorMessage(500, e.getMessage()));
        }
    }
    
    @GetMapping("/operasis/awal/{awal}/akhir/{akhir}")
    public ResponseEntity findOperasiRange(
            @PathVariable("awal") String awal,
            @PathVariable("akhir") String akhir) {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date tanggal1 = sdf.parse(awal);
            Date tanggal2 = sdf.parse(akhir);
            if (tanggal2.before(tanggal1)) {
                return ResponseEntity.ok(new ResponseModel(201, "Tanggal akhir tidak boleh sebelum tanggal awal"));
            }
            
            List<Operasi> operasis = operasiService.findRange(awal, akhir);
            List list = new ArrayList();
            for (Operasi operasi : operasis) {
                Map<String, Object> itemMap = new HashMap<>();
                itemMap.put("kodebooking", operasi.getKodebooking());
                itemMap.put("tanggaloperasi", operasi.getTanggaloperasi());
                itemMap.put("jenistindakan", operasi.getJenistindakan());
                itemMap.put("kodepoli", operasi.getKodepoli());
                itemMap.put("namapoli", operasi.getNamapoli());
                itemMap.put("terlaksana", operasi.getTerlaksana());
                itemMap.put("nopeserta", operasi.getNopeserta());
                itemMap.put("lastupdate", operasi.getUpdatedAt().getTime());
                itemMap.put("createdAt", sdf2.format(operasi.getCreatedAt()));
                itemMap.put("updatedAt", sdf2.format(operasi.getCreatedAt()));
                list.add(itemMap);
            }
            
            return ResponseEntity.ok(new ResponseModel(200, "Ok", list));
            
        } catch (ParseException e) {
            LOG.log(Level.SEVERE, null, e);
            return ResponseEntity.ok(new ResponseModel(201, "Gagal, " + e.getMessage()));
        }
    }

    @GetMapping("/operasis/nopeserta/{nopeserta}")
    public ResponseEntity findOperasiByNopeserta(@PathVariable("nopeserta") String nopeserta) {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            List<Operasi> operasis = operasiService.findOperasiPasien(nopeserta);
            List list = new ArrayList();
            for (Operasi operasi : operasis) {
                Map<String, Object> itemMap = new HashMap<>();
                itemMap.put("kodebooking", operasi.getKodebooking());
                itemMap.put("tanggaloperasi", operasi.getTanggaloperasi());
                itemMap.put("jenistindakan", operasi.getJenistindakan());
                itemMap.put("kodepoli", operasi.getKodepoli());
                itemMap.put("namapoli", operasi.getNamapoli());
                itemMap.put("terlaksana", operasi.getTerlaksana());
                itemMap.put("createdAt", sdf.format(operasi.getCreatedAt()));
                itemMap.put("updatedAt", sdf.format(operasi.getCreatedAt()));
                list.add(itemMap);
            }
            
            return ResponseEntity.ok(new ResponseModel(200, "Ok", list));            
        } catch (Exception e) {
            LOG.log(Level.SEVERE, null, e);
            return ResponseEntity.ok(new ResponseModel(201, "Gagal, " + e.getMessage()));
        }
    }
    
    @GetMapping("/operasis/kodebooking/{kodebooking}")
    public ResponseEntity findOperasiByKodebooking(@PathVariable("kodebooking") String kodebooking) {
        try {
            List<Operasi> operasis = operasiService.findOperasiByKodebooking(kodebooking);
            List list = new ArrayList();
            
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            for (Operasi operasi : operasis) {
                Map<String, Object> itemMap = new HashMap<>();
                itemMap.put("kodebooking", operasi.getKodebooking());
                itemMap.put("tanggaloperasi", operasi.getTanggaloperasi());
                itemMap.put("jenistindakan", operasi.getJenistindakan());
                itemMap.put("kodepoli", operasi.getKodepoli());
                itemMap.put("namapoli", operasi.getNamapoli());
                itemMap.put("terlaksana", operasi.getTerlaksana());
                itemMap.put("createdAt", sdf.format(operasi.getCreatedAt()));
                itemMap.put("updatedAt", sdf.format(operasi.getCreatedAt()));
                list.add(itemMap);
            }
            
            return ResponseEntity.ok(new ResponseModel(200, "Ok", list));            
        } catch (Exception e) {
            LOG.log(Level.SEVERE, null, e);
            return ResponseEntity.ok(new ResponseModel(201, "Gagal, " + e.getMessage()));
        }
    }

    private static final Logger LOG = Logger.getLogger(OperasiController.class.getName());

    private ResponseMetadataModel<?> createErrorMessage(int code, String message) {
        return new ResponseMetadataModel<>(new MetadataModel(code, message));
    }

    private ResponseMetadataModel<?> createSuccessMessage(String message) {
        return new ResponseMetadataModel<>(new MetadataModel(200, message));
    }

}
