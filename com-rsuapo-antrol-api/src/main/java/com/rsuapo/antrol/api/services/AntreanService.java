/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.api.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsuapo.antrol.api.entities.Antrean;
import com.rsuapo.antrol.api.entities.Dokter;
import com.rsuapo.antrol.api.entities.Pasien;
import com.rsuapo.antrol.api.exceptions.BadRequestException;
import com.rsuapo.antrol.api.exceptions.NotFoundException;
import com.rsuapo.antrol.api.messages.AntreanMessage;
import com.rsuapo.antrol.api.respositories.AntreanRepository;
import com.rsuapo.antrol.api.respositories.PasienRepository;
import com.rsuapo.antrol.library.Constants;
import com.rsuapo.antrol.library.models.AntreanAddModel;
import com.rsuapo.antrol.library.models.ResponseMetadataModel;
import com.rsuapo.antrol.library.service.AntrolService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author RSU Aisyiyah Ponorogo <rsuapo@yahoo.co.id>
 */
@Service
public class AntreanService {

    @Value("${scheduled.antrian.toleransiJumlahGagal}")
    private Integer toleransiJumlahGagal;

    @Autowired
    private PasienRepository pasienRepository;
    @Autowired
    private AntreanRepository antreanRepository;
    @Autowired
    private PengaturanNomorService pengaturanNomorService;
    @Autowired
    private AntreanTaskService antreanTaskService;
    @Autowired
    private AntrolService antrolService;
    @Autowired
    private DokterService dokterService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Transactional(rollbackFor = Exception.class)
    public Antrean create(Antrean antrean, Pasien pasien) throws Exception {
        antrean.setId(UUID.randomUUID().toString());
        antrean.setCreatedAt(new Date());
        antrean.setUpdatedAt(new Date());

        antrean.setKodebooking(pengaturanNomorService.generateNomorBookingAntrean());

        antrean.setStatus(1);

        antrean.setSyncStatus(0);
        antrean.setSyncCount(0);
        antrean.setSyncMethod(0);

        Antrean saved = antreanRepository.save(antrean);
        if (Objects.equals(pasien.getPasienbaru(), 1)) {
            pasien.setPasienbaru(0);
            pasienRepository.save(pasien);
        }

        pengaturanNomorService.update("kodebooking.antrean", antrean.getKodebooking());

        return saved;
    }

    @Transactional(rollbackFor = Exception.class)
    public Antrean createFromKodebooking(Antrean antrean, Pasien pasien) throws Exception {
        antrean.setUpdatedAt(new Date());
        antrean.setStatus(1);
        antrean.setSyncStatus(0);
        antrean.setSyncCount(0);
        antrean.setSyncMethod(0);

        Antrean saved = antreanRepository.save(antrean);
        if (Objects.equals(pasien.getPasienbaru(), 1)) {
            pasien.setPasienbaru(0);
            pasienRepository.save(pasien);

            if (antrean.getWaktuTask1() != null) {
                antreanTaskService.put(antrean, 1, antrean.getWaktuTask1().getTime());
            }

            if (antrean.getWaktuTask2() != null) {
                antreanTaskService.put(antrean, 2, antrean.getWaktuTask2().getTime());
            }

            if (antrean.getWaktuTask3() != null) {
                antreanTaskService.put(antrean, 3, antrean.getWaktuTask3().getTime());
            }
        }

        return saved;
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancelByKodebooking(String kodebooking, String catatanPembatalan) throws Exception {
        Optional<Antrean> optional = antreanRepository.findByKodebooking(kodebooking);
        if (!optional.isPresent()) {
            throw new Exception("Antrean dengan kode booking " + kodebooking + " tidak ditemukan");
        }

        Antrean antrean = optional.get();

        if (!Objects.equals(antrean.getStatus(), 1)) {
            throw new Exception("Antrean dengan kode booking " + kodebooking + " tidak dapat dibatalkan karena status antrean tidak aktif");
        }

        antrean.setStatus(0);
        antrean.setCatatanPembatalan(catatanPembatalan);

        antreanRepository.save(antrean);
        antreanTaskService.put(antrean, 99);
    }

    public boolean isPasienSudahTerdaftar(String norm, String tanggalperiksa, String kodedokter) {
        Long count = antreanRepository.countAntrean(norm, tanggalperiksa, kodedokter);
        return count > 0;
    }

    public Long countAntreanTotal(
            String kodepoli,
            String kodedokter,
            String tanggalperiksa,
            String jampraktek) {
        return antreanRepository.countAntrianTotal(kodepoli, kodedokter, tanggalperiksa, jampraktek);
    }

    public Long countAntreanJenis(
            String kodepoli,
            String kodedokter,
            String tanggalperiksa,
            String jampraktek,
            String jenispasien) {
        return antreanRepository.countAntrianJenis(kodepoli, kodedokter, tanggalperiksa, jampraktek, jenispasien);
    }

    public Antrean findLastDipanggil(
            String kodepoli,
            String kodedokter,
            String tanggalperiksa,
            String jampraktek) {
        List<Antrean> antreans = antreanRepository.findLastDipanggil(
                kodepoli,
                kodedokter,
                tanggalperiksa,
                jampraktek,
                PageRequest.of(0, 1)
        );

        if (!antreans.isEmpty()) {
            return antreans.get(0);
        }

        return null;
    }

    public Antrean findByKodebooking(String kodebooking) {
        Optional<Antrean> optional = antreanRepository.findByKodebooking(kodebooking);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    public long countAntreanBelumDipanggil(String kodepoli, String kodedokter, String tanggalperiksa, String jampraktek) {
        return antreanRepository.countAntrianBelumDipanggil(kodepoli, kodedokter, tanggalperiksa, jampraktek);
    }

    @Transactional(rollbackFor = Exception.class)
    public void push() throws Exception {
        if (toleransiJumlahGagal == null) {
            toleransiJumlahGagal = 3;
        }

        //System.out.println("push antrean...");
        List<Antrean> antreans = antreanRepository.findAllNeedToPushAdd(toleransiJumlahGagal);
        for (Antrean a : antreans) {
            if (a.getSyncCount() == null) {
                a.setSyncCount(0);
            }

            if (a.getSyncCount() < toleransiJumlahGagal) {
                ModelMapper modelMapper = new ModelMapper();
                AntreanAddModel m = modelMapper.map(a, AntreanAddModel.class);
                ResponseMetadataModel res = antrolService.antreanAdd(m);
                System.out.println(new ObjectMapper().writeValueAsString(res));
                if (200 == res.getMetadata().getCode()) {
                    System.out.println("antrean " + a.getKodebooking() + " ok");
                    a.setSyncStatus(Constants.SYNC_STATUS_PROSES);
                    a.setSyncLastAt(new Date());
                    a.setSyncMethod(1);
                }

                a.setSyncCount(a.getSyncCount() + 1);

                antreanRepository.save(a);
            }

        }
    }

    /**
     * Task 3
     *
     * @param kodebooking
     * @param waktu
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public Antrean checkIn(String kodebooking, Long waktu) throws Exception {
        Antrean antrean = findByKodebooking(kodebooking);
        if (antrean == null) {
            throw new Exception("Antrean dengan kode booking " + kodebooking + " tidak ditemukan");
        }

        if (!Objects.equals(antrean.getStatus(), 1)) {
            throw new Exception("Antrean dengan kode booking " + kodebooking + " tidak dapat check in karena status antrean tidak aktif");
        }

        if (!new SimpleDateFormat("yyyy-MM-dd").format(new Date()).equals(antrean.getTanggalperiksa())) {
            throw new Exception("Check In hanya dapat dilakukan pada tanggal " + antrean.getTanggalperiksa());
        }

        if (Boolean.TRUE.equals(antrean.getSudahCheckIn())) {
            throw new Exception("Antrean dengan kode booking " + kodebooking + " sudah check in");
        }

        int lastTaskId = 0;
        if (!Objects.isNull(antrean.getLastExecutedTaskId())) {
            lastTaskId = antrean.getLastExecutedTaskId();
        }

        if (lastTaskId >= 3) {
            throw new Exception("Check In tidak dapat dilakukan karena task id saat ini adalah " + lastTaskId);
        }

        antrean.setSudahCheckIn(true);
        antrean.setWaktuCheckIn(new Date(waktu));
        antrean.setUpdatedAt(new Date());

        antreanTaskService.put(antrean, 3);

        return antrean;
    }

    /**
     * Task 4
     *
     * @param kodebooking
     * @param waktu
     * @return
     * @throws NotFoundException
     * @throws BadRequestException
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public Antrean poliMulai(String kodebooking, long waktu) throws NotFoundException, BadRequestException, Exception {
        Antrean antrean = findByKodebooking(kodebooking);
        if (antrean == null) {
            throw new NotFoundException("Antrean dengan kode booking " + kodebooking + " tidak ditemukan");
        }

        if (!Objects.equals(antrean.getStatus(), 1)) {
            throw new BadRequestException("Antrean dengan kode booking " + kodebooking + " tidak dapat check in karena status antrean tidak aktif");
        }

        if (!new SimpleDateFormat("yyyy-MM-dd").format(new Date()).equals(antrean.getTanggalperiksa())) {
            throw new BadRequestException("Pemanggilan hanya dapat dilakukan pada tanggal " + antrean.getTanggalperiksa());
        }

        if (!Boolean.TRUE.equals(antrean.getSudahCheckIn())) {
            throw new BadRequestException("Pasien dengan kode booking " + kodebooking + " belum check in");
        }

        int lastTaskId = 0;
        if (!Objects.isNull(antrean.getLastExecutedTaskId())) {
            lastTaskId = antrean.getLastExecutedTaskId();
        }

        if (lastTaskId >= 4) {
            throw new BadRequestException("Pemanggilan tidak dapat dilakukan karena task id saat ini adalah " + lastTaskId);
        }

        antrean.setUpdatedAt(new Date());
        antreanTaskService.put(antrean, 4);

        // panggilan poli
        Dokter dokter = dokterService.findByKodedokter(antrean.getKodedokter());
        String displayChannel = "200";
        if (dokter != null && dokter.getDisplayChannel() != null) {
            displayChannel = dokter.getDisplayChannel();
        }

        String displayRuang = "1";
        if (dokter != null && dokter.getDisplayRuang() != null) {
            displayRuang = dokter.getDisplayRuang();
        }

        panggilAntrean(antrean, displayChannel, displayRuang, 2);

        return antrean;
    }

    /**
     * Task 5 dieksekusi saat dokter membuat resep atau saat pasien dinyatakan
     * selesai di poli
     *
     * @param kodebooking
     * @param waktu
     * @return
     * @throws NotFoundException
     * @throws BadRequestException
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public Antrean poliSelesai(String kodebooking, long waktu) throws NotFoundException, BadRequestException, Exception {
        Antrean antrean = findByKodebooking(kodebooking);
        if (antrean == null) {
            throw new NotFoundException("Antrean dengan kode booking " + kodebooking + " tidak ditemukan");
        }

        if (!Objects.equals(antrean.getStatus(), 1)) {
            throw new BadRequestException("Antrean dengan kode booking " + kodebooking + " tidak dapat check in karena status antrean tidak aktif");
        }

        if (!new SimpleDateFormat("yyyy-MM-dd").format(new Date()).equals(antrean.getTanggalperiksa())) {
            throw new BadRequestException("Penyelesaian poli hanya dapat dilakukan pada tanggal " + antrean.getTanggalperiksa());
        }

        if (!Boolean.TRUE.equals(antrean.getSudahCheckIn())) {
            throw new BadRequestException("Pasien dengan kode booking " + kodebooking + " belum check in");
        }

        int lastTaskId = 0;
        if (!Objects.isNull(antrean.getLastExecutedTaskId())) {
            lastTaskId = antrean.getLastExecutedTaskId();
        }

        if (lastTaskId >= 5) {
            throw new BadRequestException("Penyelesaian poli tidak dapat dilakukan karena task id saat ini adalah " + lastTaskId);
        }

        /**
         * tambahkan perintah untuk membuat nomor antrean sesuaikan tujuan
         * lokasi farmasi jika farmasi lebih dari satu dalam sampel ini dibuat
         * seperti ini
         */
        antrean.setAntreanFarmasiLokasiId("farmasiRawatJalan");
        antrean.setAntreanFarmasiLokasiDeskripsi("Farmasi Rawat Jalan");
        antrean.setAntreanFarmasiNomor(pengaturanNomorService.generateNomorAntrianFarmasi("farmasiRawatJalan", antrean.getTanggalperiksa()).intValue());
        antrean.setAntreanFarmasiSudahDipanggil(false);

        antrean.setUpdatedAt(new Date());
        antreanTaskService.put(antrean, 5);

        return antrean;
    }

    /**
     * Task 6
     *
     * @param kodebooking
     * @param waktu
     * @return
     * @throws NotFoundException
     * @throws BadRequestException
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public Antrean farmasiMulai(String kodebooking, long waktu) throws NotFoundException, BadRequestException, Exception {
        Antrean antrean = findByKodebooking(kodebooking);
        if (antrean == null) {
            throw new NotFoundException("Antrean dengan kode booking " + kodebooking + " tidak ditemukan");
        }

        if (!Objects.equals(antrean.getStatus(), 1)) {
            throw new BadRequestException("Antrean dengan kode booking " + kodebooking + " tidak dapat check in karena status antrean tidak aktif");
        }

        if (!new SimpleDateFormat("yyyy-MM-dd").format(new Date()).equals(antrean.getTanggalperiksa())) {
            throw new BadRequestException("Waktu mulai farmasi hanya dapat dilakukan pada tanggal " + antrean.getTanggalperiksa());
        }

        if (!Boolean.TRUE.equals(antrean.getSudahCheckIn())) {
            throw new BadRequestException("Pasien dengan kode booking " + kodebooking + " belum check in");
        }

        int lastTaskId = 0;
        if (!Objects.isNull(antrean.getLastExecutedTaskId())) {
            lastTaskId = antrean.getLastExecutedTaskId();
        }

        if (lastTaskId >= 6) {
            throw new BadRequestException("Waktu mulai farmasi tidak dapat dilakukan karena task id saat ini adalah " + lastTaskId);
        }

        antrean.setUpdatedAt(new Date());
        antreanTaskService.put(antrean, 6);
        return antrean;
    }

    /**
     * Task 7
     *
     * @param kodebooking
     * @param waktu
     * @return
     * @throws NotFoundException
     * @throws BadRequestException
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public Antrean farmasiSelesai(String kodebooking, long waktu) throws NotFoundException, BadRequestException, Exception {
        Antrean antrean = findByKodebooking(kodebooking);
        if (antrean == null) {
            throw new NotFoundException("Antrean dengan kode booking " + kodebooking + " tidak ditemukan");
        }

        if (!Objects.equals(antrean.getStatus(), 1)) {
            throw new BadRequestException("Antrean dengan kode booking " + kodebooking + " tidak dapat check in karena status antrean tidak aktif");
        }

        if (!new SimpleDateFormat("yyyy-MM-dd").format(new Date()).equals(antrean.getTanggalperiksa())) {
            throw new BadRequestException("Penyelesaian farmasi hanya dapat dilakukan pada tanggal " + antrean.getTanggalperiksa());
        }

        if (!Boolean.TRUE.equals(antrean.getSudahCheckIn())) {
            throw new BadRequestException("Pasien dengan kode booking " + kodebooking + " belum check in");
        }

        int lastTaskId = 0;
        if (!Objects.isNull(antrean.getLastExecutedTaskId())) {
            lastTaskId = antrean.getLastExecutedTaskId();
        }

        if (lastTaskId >= 7) {
            throw new BadRequestException("Penyelesaian farmasi tidak dapat dilakukan karena task id saat ini adalah " + lastTaskId);
        }

        antrean.setUpdatedAt(new Date());
        antreanTaskService.put(antrean, 7);

        panggilAntrean(antrean, "200", "25", 3);

        return antrean;
    }

    public void panggilAntrean(Antrean antrean, String displayChannel, String displayRuang, Integer type) {
        AntreanMessage message = new AntreanMessage();
        message.setDisplayChannel(displayChannel);
        message.setDisplayRuang(displayRuang);
        message.setDokterId(antrean.getKodedokter());
        message.setDokterNama(antrean.getNamadokter());
        message.setUnitId(antrean.getKodepoli());
        message.setUnitNama(antrean.getNamapoli());
        if (null != type) {
            switch (type) {
                case 1:
                    message.setNomorAntrean(antrean.getAntreanPendaftaranNomor());
                    if (!Objects.equals(antrean.getAntreanPendaftaranSudahDipanggil(), true)) {
                        antrean.setAntreanPendaftaranSudahDipanggil(true);
                        antrean.setAntreanPendaftaranWaktuDipanggil(new Date());
                    }
                    break;
                case 2:
                    message.setNomorAntrean(antrean.getAngkaantrean());
                    if (!Objects.equals(antrean.getPoliSudahDipanggil(), true)) {
                        antrean.setPoliSudahDipanggil(true);
                        antrean.setPoliWaktuDipanggil(new Date());
                    }
                    break;
                case 3:
                    message.setNomorAntrean(antrean.getAntreanFarmasiNomor());
                    if (!Objects.equals(antrean.getAntreanFarmasiSudahDipanggil(), true)) {
                        antrean.setAntreanFarmasiSudahDipanggil(true);
                        antrean.setAntreanFarmasiWaktuDipanggil(new Date());
                    }
                    break;
                default:
                    break;
            }
        }
        message.setType(type);
        message.setMessage("Channel: " + message.getDisplayChannel() + ", Ruang: " + message.getDisplayRuang() + ", No. Antrean: " + message.getNomorAntrean());
        panggilAntrean(message);
        antreanRepository.save(antrean);
    }

    public void panggilAntrean(AntreanMessage message) {
        simpMessagingTemplate.convertAndSend("/topic/messages/" + message.getDisplayChannel(), message);
    }

    @Transactional(rollbackFor = Exception.class)
    public Antrean ambilAntreanPendaftaran() throws Exception {
        String tanggal = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Antrean antrean = new Antrean(UUID.randomUUID().toString());
        antrean.setKodebooking(pengaturanNomorService.generateNomorBookingAntrean());
        antrean.setAntreanPendaftaranNomor(pengaturanNomorService.generateNomorAntrianPendaftaran(tanggal).intValue());
        antrean.setTanggalperiksa(tanggal);
        antrean.setCreatedAt(new Date());
        antrean.setUpdatedAt(new Date());
        antrean.setRefDeviceId("pendaftaran");
        antrean.setRefDeviceDeskripsi("Onsite");

        antrean.setStatus(1);

        antrean.setSyncStatus(-1);
        antrean.setSyncCount(0);
        antrean.setSyncMethod(0);

        antrean.setWaktuTask1(new Date());
        antrean.setLastExecutedTaskId(0);
        antrean.setLastExecutedAt(new Date());

        Antrean created = antreanRepository.save(antrean);
        pengaturanNomorService.update("kodebooking.antrean", antrean.getKodebooking());

        return created;
    }

    public Antrean findByNomorAntreanPendaftaran(Integer nomor) {
        String tanggal = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Optional<Antrean> optional = antreanRepository.findByAntreanPendaftaranNomorAndTanggalperiksaAndStatus(nomor, tanggal, 1);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    public List<Antrean> findRange(String awal, String akhir) {
        return antreanRepository.findRange(awal, akhir);
    }

}
