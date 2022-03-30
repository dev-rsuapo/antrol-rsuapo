/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.api.controllers;

import com.rsuapo.antrol.api.entities.Antrean;
import com.rsuapo.antrol.api.entities.JadwalDokter;
import com.rsuapo.antrol.api.entities.Operasi;
import com.rsuapo.antrol.api.entities.Pasien;
import com.rsuapo.antrol.api.models.ResponseModel;
import com.rsuapo.antrol.api.services.AntreanService;
import com.rsuapo.antrol.api.services.DokterService;
import com.rsuapo.antrol.api.services.JadwalDokterService;
import com.rsuapo.antrol.api.services.PengaturanNomorService;
import com.rsuapo.antrol.api.services.OperasiService;
import com.rsuapo.antrol.api.services.PasienService;
import com.rsuapo.antrol.api.models.AmbilAntreanModel;
import com.rsuapo.antrol.api.models.AntreanBatalModel;
import com.rsuapo.antrol.api.models.CheckInModel;
import com.rsuapo.antrol.api.models.JadwalOperasiPasienModel;
import com.rsuapo.antrol.api.models.JadwalOperasiRsModel;
import com.rsuapo.antrol.api.models.PasienBaruModel;
import com.rsuapo.antrol.api.models.SisaAntreanModel;
import com.rsuapo.antrol.api.models.StatusAntreanModel;
import com.rsuapo.antrol.library.service.AntrolService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author RSU Aisyiyah Ponorogo <rsuapo@yahoo.co.id>
 */
@RestController
public class AntreanRsController {

    @Autowired
    private JadwalDokterService jadwalDokterService;

    @Autowired
    private OperasiService operasiService;

    @Autowired
    private PengaturanNomorService pengaturanNomorService;

    @Autowired
    private AntreanService antreanService;

    @Autowired
    private PasienService pasienService;

    @Autowired
    private DokterService dokterService;

    @Autowired
    private AntrolService antrolService;

    private static final Logger LOG = Logger.getLogger(AntreanRsController.class.getName());

    @PostMapping("/antrean/status")
    public ResponseEntity statusAntrean(@Validated @RequestBody StatusAntreanModel s) {
        try {
            JadwalDokter jadwalDokter = jadwalDokterService.findJadwalDokter(s.getKodepoli(), s.getKodedokter(), s.getTanggalperiksa(), s.getJampraktek());
            long totalJkn = antreanService.countAntreanJenis(s.getKodepoli(), s.getKodedokter(), s.getTanggalperiksa(), s.getJampraktek(), "JKN");
            long totalNonJkn = antreanService.countAntreanJenis(s.getKodepoli(), s.getKodedokter(), s.getTanggalperiksa(), s.getJampraktek(), "NONJKN");
            long totalAntrean = antreanService.countAntreanTotal(s.getKodepoli(), s.getKodedokter(), s.getTanggalperiksa(), s.getJampraktek());

            long sisaKuotaJkn = 0l;
            if (jadwalDokter.getKuotaJkn() != null) {
                sisaKuotaJkn = jadwalDokter.getKuotaJkn() - totalJkn;
            }

            long sisaKuotaNonJkn = 0l;
            if (jadwalDokter.getKuotaNonJkn() != null) {
                sisaKuotaNonJkn = jadwalDokter.getKuotaNonJkn() - totalNonJkn;
            }

            long sisaAntrean = antreanService.countAntreanBelumDipanggil(s.getKodepoli(), s.getKodedokter(), s.getTanggalperiksa(), s.getJampraktek());

            Antrean lastDipanggil = antreanService.findLastDipanggil(s.getKodepoli(), s.getKodedokter(), s.getTanggalperiksa(), s.getJampraktek());
            String antrianDipanggil = "";
            if (lastDipanggil != null) {
                antrianDipanggil = lastDipanggil.getNomorantrean();
            }

            Map<String, Object> map = new HashMap<>();
            map.put("namapoli", jadwalDokter.getNamapoli());
            map.put("namadokter", jadwalDokter.getNamadokter());
            map.put("totalantrean", totalAntrean);
            map.put("sisaantrean", sisaAntrean);
            map.put("antreanpanggil", antrianDipanggil);
            map.put("sisakuotajkn", sisaKuotaJkn);
            map.put("kuotajkn", jadwalDokter.getKuotaJkn());
            map.put("sisakuotanonjkn", sisaKuotaNonJkn);
            map.put("kuotanonjkn", jadwalDokter.getKuotaNonJkn());
            map.put("keterangan", "");

            return ResponseEntity.ok(new ResponseModel(200, "Ok", map));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseModel(201, "Gagal, " + e.getMessage()));
        }
    }

    @PostMapping("/antrean/ambil")
    public ResponseEntity ambilAntrean(@Validated @RequestBody AmbilAntreanModel a) {
        try {
            // cek apakah pasien baru?
            Pasien pasien = pasienService.findByNomorRm(a.getNorm());
            if (pasien == null) {
                return ResponseEntity.ok(new ResponseModel(202, "Pasien Baru"));
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

            antrean.setJenispasien("JKN");

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
            
            antrean.setRefDeviceId("jkn_mobile");
            antrean.setRefDeviceDeskripsi("Aplikasi JKN Mobile");

            Antrean created = antreanService.create(antrean, pasien);

            Map<String, Object> map = new HashMap<>();
            map.put("nomorantrean", created.getNomorantrean());
            map.put("angkaantrean", created.getAngkaantrean());
            map.put("kodebooking", created.getKodebooking());
            map.put("norm", created.getNorm());
            map.put("namapoli", created.getNamapoli());
            map.put("namadokter", created.getNamadokter());
            map.put("estimasidilayani", created.getEstimasidilayani());
            map.put("sisakuotajkn", created.getSisakuotajkn());
            map.put("kuotajkn", created.getKuotajkn());
            map.put("sisakuotanonjkn", created.getSisakuotanonjkn());
            map.put("kuotanonjkn", created.getKuotanonjkn());
            map.put("keterangan", created.getKeterangan());

            return ResponseEntity.ok(new ResponseModel(200, "Ok", map));
        } catch (Exception e) {
            LOG.log(Level.SEVERE, null, e);
            return ResponseEntity.ok(new ResponseModel(201, "Gagal, " + e.getMessage()));
        }
    }

    @PostMapping("/antrean/pasienBaru")
    public ResponseEntity pasienBaru(@Validated @RequestBody PasienBaruModel p) {
        try {
            if (!StringUtils.hasText(p.getNomorkartu())) {
                return ResponseEntity.ok(new ResponseModel(201, "No. Kartu harus diisi"));
            }

            if (!StringUtils.hasText(p.getNik())) {
                return ResponseEntity.ok(new ResponseModel(201, "NIK harus diisi"));
            }

            if (!StringUtils.hasText(p.getNama())) {
                return ResponseEntity.ok(new ResponseModel(201, "Nama harus diisi"));
            }

            // cek apakah nomor kartu valid
            // ..
            // cocokkan nomor kartu dengan nomor nik
            // ..
            // cek apakah nomor kartu sudah ada dalam db pasien local
            Pasien temp = pasienService.findByNomorkartu(p.getNomorkartu());
            if (temp != null) {
                return ResponseEntity.ok(new ResponseModel(201, "No. Kartu " + p.getNomorkartu() + " sudah ada dalam database pasien"));
            }

            // cek apakah nik dan nomor kartu sudah ada dalam simrs
            // ..
            // kirim data pasien baru ke simrs atau insert ke database simrs
            // ...
            // dapatkan nomor rm dari simrs
            // ...
            // sebagai sample digenerate di sini
            String nomorRm = pengaturanNomorService.generateNomorRm();

            ModelMapper modelMapper = new ModelMapper();
            Pasien pasien = modelMapper.map(p, Pasien.class);
            pasien.setNorm(nomorRm);
            pasien.setPasienbaru(1);
            Pasien created = pasienService.create(pasien);
            Map<String, Object> map = new HashMap<>();
            map.put("norm", created.getNorm());
            return ResponseEntity.ok(new ResponseModel(200, "Ok", map));
        } catch (Exception ex) {
            Logger.getLogger(AntreanRsController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.ok(new ResponseModel(201, "Data pasien gagal disimpan, " + ex.getMessage()));
        }
    }

    @PostMapping("/antrean/sisa")
    public ResponseEntity sisaAntrean(@Validated @RequestBody SisaAntreanModel s) {
        try {
            if (!StringUtils.hasText(s.getKodebooking())) {
                return ResponseEntity.ok(new ResponseModel(201, "Kode booking harus diisi"));
            }

            Antrean antrean = antreanService.findByKodebooking(s.getKodebooking());
            if (antrean == null) {
                return ResponseEntity.ok(new ResponseModel(201, "Antrean dengan kode booking " + s.getKodebooking() + " tidak ditemukan"));
            }

            long sisaAntrean = antreanService.countAntreanBelumDipanggil(
                    antrean.getKodepoli(),
                    antrean.getKodedokter(),
                    antrean.getTanggalperiksa(),
                    antrean.getJampraktek());

            Antrean lastDipanggil = antreanService.findLastDipanggil(
                    antrean.getKodepoli(),
                    antrean.getKodedokter(),
                    antrean.getTanggalperiksa(),
                    antrean.getJampraktek());
            String antrianDipanggil = "";
            if (lastDipanggil != null) {
                antrianDipanggil = lastDipanggil.getNomorantrean();
            }

            Map<String, Object> map = new HashMap<>();
            map.put("nomorantrean", antrean.getNomorantrean());
            map.put("namapoli", antrean.getNamapoli());
            map.put("namadokter", antrean.getNamadokter());
            map.put("sisaantrean", sisaAntrean);
            map.put("antreanpanggil", antrianDipanggil);
            map.put("waktutunggu", (300 * sisaAntrean));
            map.put("keterangan", "");
            return ResponseEntity.ok(new ResponseModel(200, "Ok", map));
        } catch (Exception e) {
            LOG.log(Level.SEVERE, null, e);
            return ResponseEntity.ok(new ResponseModel(201, "Gagal"));
        }
    }

    @PostMapping("/antrean/batal")
    public ResponseEntity batalAntrean(@Validated @RequestBody AntreanBatalModel a) {
        try {
            if (!StringUtils.hasText(a.getKodebooking())) {
                return ResponseEntity.ok(new ResponseModel(201, "Kode booking harus diisi"));
            }

            if (!StringUtils.hasText(a.getKeterangan())) {
                return ResponseEntity.ok(new ResponseModel(201, "Keterangan harus diisi"));
            }

            antreanService.cancelByKodebooking(a.getKodebooking(), a.getKeterangan());
            // batalkan di simrs
            // tulis catatan pembatalan
            return ResponseEntity.ok(new ResponseModel(200, "Ok"));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseModel(201, "Gagal, " + e.getMessage()));
        }
    }

    @PostMapping("/antrean/checkin")
    public ResponseEntity checkIn(@Validated @RequestBody CheckInModel c) {
        try {
            if (!StringUtils.hasText(c.getKodebooking())) {
                return ResponseEntity.ok(new ResponseModel(201, "Kode booking harus diisi"));
            }

            if (null == c.getWaktu()) {
                return ResponseEntity.ok(new ResponseModel(201, "Waktu harus diisi"));
            }

            antreanService.checkIn(c.getKodebooking(), c.getWaktu());
            return ResponseEntity.ok(new ResponseModel(200, "Ok"));
        } catch (Exception e) {
            LOG.log(Level.SEVERE, null, e);
            return ResponseEntity.ok(new ResponseModel(201, "Gagal, " + e.getMessage()));
        }
    }
    
    @PostMapping("/operasi/rs")
    public ResponseEntity findOperasiRs(@Validated @RequestBody JadwalOperasiRsModel m) {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date tanggal1 = sdf.parse(m.getTanggalawal());
            Date tanggal2 = sdf.parse(m.getTanggalakhir());
            if (tanggal2.before(tanggal1)) {
                return ResponseEntity.ok(new ResponseModel(201, "Tanggal akhir tidak boleh sebelum tanggal awal"));
            }
            
            List<Operasi> operasis = operasiService.findRange(m.getTanggalawal(), m.getTanggalakhir());
            Map<String, Object> map = new HashMap<>();
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
                list.add(itemMap);
            }
            
            map.put("list", list);
            return ResponseEntity.ok(new ResponseModel(200, "Ok", map));
            
        } catch (ParseException e) {
            LOG.log(Level.SEVERE, null, e);
            return ResponseEntity.ok(new ResponseModel(201, "Gagal, " + e.getMessage()));
        }
    }

    @PostMapping("/operasi/pasien")
    public ResponseEntity findOperasiPasien(@Validated @RequestBody JadwalOperasiPasienModel m) {
        try {
            if (!StringUtils.hasText(m.getNopeserta())) {
                return ResponseEntity.ok(new ResponseModel(201, "Nomor Peserta harus diisi"));
            }
            
            List<Operasi> operasis = operasiService.findOperasiPasien(m.getNopeserta());
            Map<String, Object> map = new HashMap<>();
            List list = new ArrayList();
            for (Operasi operasi : operasis) {
                Map<String, Object> itemMap = new HashMap<>();
                itemMap.put("kodebooking", operasi.getKodebooking());
                itemMap.put("tanggaloperasi", operasi.getTanggaloperasi());
                itemMap.put("jenistindakan", operasi.getJenistindakan());
                itemMap.put("kodepoli", operasi.getKodepoli());
                itemMap.put("namapoli", operasi.getNamapoli());
                itemMap.put("terlaksana", operasi.getTerlaksana());
                list.add(itemMap);
            }
            
            map.put("list", list);
            return ResponseEntity.ok(new ResponseModel(200, "Ok", map));            
        } catch (Exception e) {
            LOG.log(Level.SEVERE, null, e);
            return ResponseEntity.ok(new ResponseModel(201, "Gagal, " + e.getMessage()));
        }
    }
}
