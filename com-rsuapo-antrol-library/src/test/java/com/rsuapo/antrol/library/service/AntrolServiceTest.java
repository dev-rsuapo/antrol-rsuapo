/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.library.service;

import com.rsuapo.antrol.library.models.AntreanModel;
import com.rsuapo.antrol.library.models.DokterModel;
import com.rsuapo.antrol.library.models.JadwalDokterModel;
import com.rsuapo.antrol.library.models.JadwalModel;
import com.rsuapo.antrol.library.models.PasienFpModel;
import com.rsuapo.antrol.library.models.PoliFpModel;
import com.rsuapo.antrol.library.models.PoliModel;
import com.rsuapo.antrol.library.models.ResponseListMetadataModel;
import com.rsuapo.antrol.library.models.ResponseMetadataModel;
import com.rsuapo.antrol.library.models.UpdateJadwalDokterModel;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 *
 * @author RSU Aisyiyah Ponorogo <rsuapo@yahoo.co.id>
 */
public class AntrolServiceTest {

    private final AntrolService antrolService;

    public AntrolServiceTest() {
        antrolService = new AntrolService.Builder()
                .baseUrl("https://apijkn-dev.bpjs-kesehatan.go.id/antreanrs_dev")
                .consumerId("")
                .consumerSecret("")
                .userKey("")
                .build();
    }

    @Test
    public void testGetPolis() {
        System.out.println("Test getPoli()");
        ResponseListMetadataModel<PoliModel> polis = antrolService.getPolis();
        for (PoliModel p : polis.getResponse()) {
            System.out.println("Kode                : " + p.getKdpoli());
            System.out.println("Nama                : " + p.getNmpoli());
            System.out.println("Kode Sub Spesialis  : " + p.getKdsubspesialis());
            System.out.println("Nama Sub Spesialis  : " + p.getNmsubspesialis());
            System.out.println("----------------------------------------------------");
        }
    }

    @Test
    public void testGetPoliFps() {
        System.out.println("Test getPoliFp()");
        ResponseListMetadataModel<PoliFpModel> poliFps = antrolService.getPoliFps();
        for (PoliFpModel p : poliFps.getResponse()) {
            System.out.println("Kode                : " + p.getKodepoli());
            System.out.println("Nama                : " + p.getNamapoli());
            System.out.println("Kode Sub Spesialis  : " + p.getKodesubspesialis());
            System.out.println("Nama Sub Spesialis  : " + p.getNamasubspesialis());
            System.out.println("----------------------------------------------------");
        }
    }
    
    @Test
    public void testGetDokters() {
        System.out.println("getDokters()");
        ResponseListMetadataModel<DokterModel> dokters = antrolService.getDokters();
        for (DokterModel d : dokters.getResponse()) {
            System.out.println("Kode Dokter : " + d.getKodedokter());
            System.out.println("Nama Dokter : " + d.getNamadokter());
            System.out.println("----------------------------------------------------");
        }
    }
    
    @Test
    public void testGetPasienFp() {
        System.out.println("getPasienFp()");
        ResponseMetadataModel<PasienFpModel> pasienFp = antrolService.getPasienFp("noka", "0001884392155");
        System.out.println("Nik: " + pasienFp.getResponse().getNik() + " FP: " + pasienFp.getResponse().getDaftarfp());
    }
    
    @Test
    public void testGetAntrean() {
        System.out.println("getAntreanPerTanggal()");
        ResponseListMetadataModel<AntreanModel> antreanPerTanggal = antrolService.getAntreanPerTanggal("2023-03-07");
        List<AntreanModel> models = antreanPerTanggal.getResponse();
        for (AntreanModel model : models) {
            System.out.println("Kode Booking: " + model.getKodebooking());
        }
    }
    
    @Test
    public void testAddJadwal() {
        System.out.println("updateJadwalDokter()");
        UpdateJadwalDokterModel u = new UpdateJadwalDokterModel();
        u.setKodedokter("12851");
        u.setKodepoli("BED");
        u.setKodesubspesialis("BED");
        
        List<JadwalModel> jadwalModels = new ArrayList<>();
        jadwalModels.add(new JadwalModel("5", "08:00", "15:00"));
        jadwalModels.add(new JadwalModel("6", "08:00", "15:00"));
        u.setJadwal(jadwalModels);
        ResponseMetadataModel res = antrolService.updateJadwalDokter(u);
        System.out.println(res.getMetadata().getCode() + " " + res.getMetadata().getMessage());
    }
    
    @Test
    public void testGetJadwal() {
        System.out.println("getJadwalDokters()");
        ResponseListMetadataModel<JadwalDokterModel> jadwalDokters = antrolService.getJadwalDokters("BED", "2023-03-09");
        List<JadwalDokterModel> responses = jadwalDokters.getResponse();
        for (JadwalDokterModel response : responses) {
            System.out.println(response.getJadwal() + " | " + response.getKodedokter() + " | " + response.getNamadokter());
        }
    }
}
