/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.api.controllers;

import com.rsuapo.antrol.api.entities.Antrean;
import com.rsuapo.antrol.api.entities.JadwalDokter;
import com.rsuapo.antrol.api.entities.Pasien;
import com.rsuapo.antrol.api.models.AntreanModel;
import com.rsuapo.antrol.api.models.ResponseModel;
import com.rsuapo.antrol.api.services.AntreanService;
import com.rsuapo.antrol.api.services.JadwalDokterService;
import com.rsuapo.antrol.api.services.PasienService;
import com.rsuapo.antrol.api.services.PengaturanNomorService;
import com.rsuapo.antrol.library.models.MetadataModel;
import com.rsuapo.antrol.library.models.ResponseMetadataModel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class PendaftaranController {

    @Autowired
    private AntreanService antreanService;
    @Autowired
    private PasienService pasienService;
    @Autowired
    private JadwalDokterService jadwalDokterService;
    @Autowired
    private PengaturanNomorService pengaturanNomorService;

    @PostMapping("/pendaftarans/ambil")
    public ResponseEntity ambilAntreanPendaftaran() {
        try {
            Antrean antrean = antreanService.ambilAntreanPendaftaran();
            Map<String, Object> map = new HashMap<>();
            map.put("kodebooking", antrean.getKodebooking());
            map.put("tanggalperiksa", antrean.getTanggalperiksa());
            map.put("antreanPendaftaranNomor", antrean.getAntreanPendaftaranNomor());
            return ResponseEntity.ok(createSuccessMessage("Ok", map));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorMessage(500, e.getMessage()));
        }
    }

    @PostMapping("/pendaftarans/panggil/{nomor}")
    public ResponseEntity panggilAntrean(@PathVariable("nomor") Integer nomor) {
        try {
            Antrean antrean = antreanService.findByNomorAntreanPendaftaran(nomor);
            if (antrean == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorMessage(404, "Antrean tidak ditemukan"));
            }

            if (Objects.isNull(antrean.getWaktuTask2())) {
                antrean.setWaktuTask2(new Date());
            }

            antreanService.panggilAntrean(antrean, "200", "5", 1);

            Map<String, Object> map = new HashMap<>();
            map.put("kodebooking", antrean.getKodebooking());
            map.put("tanggalperiksa", antrean.getTanggalperiksa());
            map.put("antreanPendaftaranNomor", antrean.getAntreanPendaftaranNomor());
            return ResponseEntity.ok(createSuccessMessage("Ok", map));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorMessage(500, e.getMessage()));
        }
    }

    @PostMapping("/pendaftarans/daftarkan/{kodebooking}")
    public ResponseEntity create(
            @PathVariable("kodebooking") String kodebooking,
            @Validated @RequestBody AntreanModel a) {
        try {
            Antrean antrean = antreanService.findByKodebooking(kodebooking);
            if (antrean == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorMessage(404, "Kode booking tidak ditemukan"));
            }

            // cek apakah pasien baru?
            Pasien pasien = pasienService.findByNomorRm(a.getNorm());
            if (pasien == null) {
                ModelMapper modelMapper = new ModelMapper();
                pasien = modelMapper.map(a, Pasien.class);
                pasien.setPasienbaru(1);
                pasien.setId(UUID.randomUUID().toString());
                pasien.setCreatedAt(new Date());
                pasien.setUpdatedAt(new Date());
            }

            // cek apakah apsien sudah terdaftar
            boolean pasienSudahTerdaftar = antreanService.isPasienSudahTerdaftar(a.getNorm(), a.getTanggalperiksa(), a.getKodedokter());
            if (pasienSudahTerdaftar) {
                return ResponseEntity.ok(new ResponseModel(201, "Pasien sudah terdaftar"));
            }

            // cek apakah nomor kartu masih aktif
            // cek apakah nomor rujukan masih aktif
            // cek apakah nomor rujukan belum pernah digunakan
            // cek apakah jadwal tersedia
            JadwalDokter jadwalDokter = jadwalDokterService.findJadwalDokter(a.getKodepoli(), a.getKodedokter(), a.getTanggalperiksa(), a.getJampraktek());
            if (jadwalDokter == null) {
                return ResponseEntity.ok(new ResponseModel(201, "Jadwal dokter tidak ditemukan"));
            }

            // cek apakah kuota tersedia
            Long totalAntreanJkn = antreanService.countAntreanJenis(a.getKodepoli(), a.getKodedokter(), a.getTanggalperiksa(), a.getJampraktek(), "JKN");
            long kuotaJkn = 0l;
            if (jadwalDokter.getKuotaJkn() != null) {
                kuotaJkn = jadwalDokter.getKuotaJkn();
            }

            // jika kuota null atau nol maka diabaikan (tanpa kuota)
            if (kuotaJkn > 0) {
                long antrianJknTersedia = jadwalDokter.getKuotaJkn() - totalAntreanJkn;
                if (antrianJknTersedia <= 0) {
                    return ResponseEntity.ok(new ResponseModel(201, "Kuota antrean JKN telah habis. Sisa kuota JKN saat ini adalah " + antrianJknTersedia));
                }
            }

            // cari dokter
            antrean.setNomorkartu(a.getNomorkartu());
            antrean.setNik(a.getNik());
            antrean.setNohp(a.getNohp());
            antrean.setKodepoli(a.getKodepoli());
            antrean.setNamapoli(jadwalDokter.getNamapoli());
            antrean.setNorm(a.getNorm());
            antrean.setTanggalperiksa(a.getTanggalperiksa());
            antrean.setKodedokter(a.getKodedokter());
            antrean.setNamadokter(jadwalDokter.getNamadokter());
            antrean.setJampraktek(a.getJampraktek());
            antrean.setJeniskunjungan(a.getJeniskunjungan());
            antrean.setNomorreferensi(a.getNomorreferensi());

            antrean.setJenispasien(a.getJenispasien());

            antrean.setAngkaantrean(pengaturanNomorService.generateNomorAntrianPoli(antrean.getKodepoli(), antrean.getKodedokter(), antrean.getJampraktek()).intValue());
            antrean.setNomorantrean(antrean.getAngkaantrean().toString());

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(a.getTanggalperiksa()));
            calendar.set(Calendar.HOUR_OF_DAY, 9);
            calendar.set(Calendar.MINUTE, 30);
            antrean.setEstimasidilayani(calendar.getTime().getTime());
            antrean.setSisakuotajkn(0);
            antrean.setSisakuotanonjkn(0);
            antrean.setKuotajkn(0);
            antrean.setKuotanonjkn(0);
            antrean.setKeterangan("Peserta harap 30 menit lebih awal");

            antrean.setPoliSudahDipanggil(false);
            antrean.setPasienbaru(pasien.getPasienbaru());

            antrean.setRefDeviceId(a.getRefDeviceId());
            antrean.setRefDeviceDeskripsi(a.getRefDeviceDeskripsi());

            antrean.setSudahCheckIn(true);
            antrean.setWaktuCheckIn(new Date());
            antrean.setWaktuTask3(new Date());

            Antrean created = antreanService.createFromKodebooking(antrean, pasien);
            return ResponseEntity.ok(createSuccessMessage("Ok", created));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorMessage(500, e.getMessage()));
        }
    }

    @GetMapping("/pendaftarans/awal/{awal}/akhir/{akhir}")
    public ResponseEntity panggilAntrean(
            @PathVariable("awal") String awal,
            @PathVariable("akhir") String akhir) {
        try {
            List<Antrean> antreans = antreanService.findRange(awal, akhir);
            return ResponseEntity.ok(createSuccessMessage("Ok", antreans));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorMessage(500, e.getMessage()));
        }
    }

    private ResponseMetadataModel<?> createErrorMessage(int code, String message) {
        return new ResponseMetadataModel<>(new MetadataModel(code, message));
    }

    private ResponseMetadataModel<?> createSuccessMessage(String message) {
        return new ResponseMetadataModel<>(new MetadataModel(200, message));
    }

    private ResponseMetadataModel<?> createSuccessMessage(String message, Object object) {
        return new ResponseMetadataModel<>(new MetadataModel(200, message), object);
    }

    private ResponseMetadataModel<Antrean> createSuccessMessage(String message, Antrean antrean) {
        return new ResponseMetadataModel<>(new MetadataModel(200, message), antrean);
    }
}
