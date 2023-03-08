/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.api.controllers;

import com.rsuapo.antrol.api.entities.Antrean;
import com.rsuapo.antrol.api.entities.Dokter;
import com.rsuapo.antrol.api.entities.JadwalDokter;
import com.rsuapo.antrol.api.entities.Pasien;
import com.rsuapo.antrol.api.exceptions.BadRequestException;
import com.rsuapo.antrol.api.exceptions.NotFoundException;
import com.rsuapo.antrol.api.services.AntreanService;
import com.rsuapo.antrol.api.services.JadwalDokterService;
import com.rsuapo.antrol.api.services.PasienService;
import com.rsuapo.antrol.api.services.PengaturanNomorService;
import com.rsuapo.antrol.api.models.AntreanBatalModel;
import com.rsuapo.antrol.api.models.AntreanModel;
import com.rsuapo.antrol.api.models.ResponseModel;
import com.rsuapo.antrol.api.services.DokterService;
import com.rsuapo.antrol.api.services.PoliService;
import com.rsuapo.antrol.library.models.MetadataModel;
import com.rsuapo.antrol.library.models.ResponseMetadataModel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
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
public class AntreanController {

    @Autowired
    private DokterService dokterService;

    @Autowired
    private AntreanService antreanService;
    @Autowired
    private PasienService pasienService;
    @Autowired
    private JadwalDokterService jadwalDokterService;
    @Autowired
    private PengaturanNomorService pengaturanNomorService;

    @PostMapping("/antreans/checkin/{kodebooking}")
    public ResponseEntity checkIn(@PathVariable("kodebooking") String kodebooking) {
        try {
            if (!StringUtils.hasText(kodebooking)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, "Kode booking harus diisi"));
            }

            antreanService.checkIn(kodebooking, new Date().getTime());
            return ResponseEntity.ok(createSuccessMessage("Ok"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorMessage(500, e.getMessage()));
        }
    }

    @PostMapping("/antreans/checkin/norm/{norm}")
    public ResponseEntity checkInByNorm(@PathVariable("norm") String norm) {
        try {
            if (!StringUtils.hasText(norm)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, "Kode booking harus diisi"));
            }

            antreanService.checkInByNorm(norm, new Date().getTime());
            return ResponseEntity.ok(createSuccessMessage("Ok"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorMessage(500, e.getMessage()));
        }
    }

    @PostMapping("/antreans/poli/mulai/{kodebooking}")
    public ResponseEntity poliMulai(@PathVariable("kodebooking") String kodebooking) {
        try {
            if (!StringUtils.hasText(kodebooking)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, "Kode booking harus diisi"));
            }

            antreanService.poliMulai(kodebooking, new Date().getTime());
            return ResponseEntity.ok(createSuccessMessage("Ok"));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, e.getMessage()));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorMessage(404, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorMessage(500, e.getMessage()));
        }
    }

    @PostMapping("/antreans/poli/selesai/{kodebooking}")
    public ResponseEntity poliSelesai(@PathVariable("kodebooking") String kodebooking) {
        try {
            if (!StringUtils.hasText(kodebooking)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, "Kode booking harus diisi"));
            }

            Antrean saved = antreanService.poliSelesai(kodebooking, new Date().getTime());
            Map<String, Object> map = new HashMap<>();
            map.put("antreanFarmasiLokasiId", saved.getAntreanFarmasiLokasiId());
            map.put("antreanFarmasiLokasiDeskripsi", saved.getAntreanFarmasiLokasiDeskripsi());
            map.put("antreanFarmasiNomor", saved.getAntreanFarmasiNomor());
            return ResponseEntity.ok(createSuccessMessage("Ok", map));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, e.getMessage()));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorMessage(404, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorMessage(500, e.getMessage()));
        }
    }

    @PostMapping("/antreans/farmasi/mulai/{kodebooking}")
    public ResponseEntity farmasiMulai(@PathVariable("kodebooking") String kodebooking) {
        try {
            if (!StringUtils.hasText(kodebooking)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, "Kode booking harus diisi"));
            }

            antreanService.farmasiMulai(kodebooking, new Date().getTime());
            return ResponseEntity.ok(createSuccessMessage("Ok"));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, e.getMessage()));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorMessage(404, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorMessage(500, e.getMessage()));
        }
    }

    @PostMapping("/antreans/farmasi/selesai/{kodebooking}")
    public ResponseEntity farmasiSelesai(@PathVariable("kodebooking") String kodebooking) {
        try {
            if (!StringUtils.hasText(kodebooking)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, "Kode booking harus diisi"));
            }

            antreanService.farmasiSelesai(kodebooking, new Date().getTime());
            return ResponseEntity.ok(createSuccessMessage("Ok"));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, e.getMessage()));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorMessage(404, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorMessage(500, e.getMessage()));
        }
    }

    @PostMapping("/antreans/batal")
    public ResponseEntity cancel(@Validated @RequestBody AntreanBatalModel m) {
        try {
            if (!StringUtils.hasText(m.getKodebooking())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, "Kode booking harus diisi"));
            }

            if (!StringUtils.hasText(m.getKeterangan())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, "Keterangan harus diisi"));
            }

            antreanService.cancelByKodebooking(m.getKodebooking(), m.getKeterangan());
            return ResponseEntity.ok(createSuccessMessage("Ok"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorMessage(500, e.getMessage()));
        }
    }

    @PostMapping("/antreans")
    public ResponseEntity create(@Validated @RequestBody AntreanModel a) {
        try {
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
            Antrean antrean = new Antrean();
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

            Antrean created = antreanService.create(antrean, pasien);
            return ResponseEntity.ok(createSuccessMessage("Ok", created));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorMessage(500, e.getMessage()));
        }
    }

    @PostMapping("/antreans/panggil/{kodebooking}/{channel}/{ruang}/{type}")
    public ResponseEntity panggil(
            @PathVariable("kodebooking") String kodebooking,
            @PathVariable("channel") String channel,
            @PathVariable("type") Integer type) {
        Antrean antrean = antreanService.findByKodebooking(kodebooking);
        if (antrean == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorMessage(404, "Kode booking tidak ditemukan"));
        }

        if (null != type) {
            switch (type) {
                case 1:
                    if (antrean.getAntreanPendaftaranNomor() == null) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, "Nomor antrean pendaftaran kosong"));
                    }
                    break;
                case 2:
                    if (antrean.getAngkaantrean() == null) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, "Nomor antrean poli kosong"));
                    }
                    break;
                case 3:
                    if (antrean.getAntreanFarmasiNomor() == null) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(400, "Nomor antrean farmasi kosong"));
                    }
                    break;
                default:
                    break;
            }
        }

        Dokter dokter = dokterService.findByKodedokter(antrean.getKodedokter());
        antreanService.panggilAntrean(antrean, channel, dokter.getNamadokter(), Integer.parseInt(dokter.getDisplayRuang()), type);
        return ResponseEntity.ok(createSuccessMessage("Ok"));
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
