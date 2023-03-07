/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.library.service;

import com.rsuapo.antrol.library.models.AntreanModel;
import com.rsuapo.antrol.library.models.DokterModel;
import com.rsuapo.antrol.library.models.PasienFpModel;
import com.rsuapo.antrol.library.models.PoliFpModel;
import com.rsuapo.antrol.library.models.PoliModel;
import com.rsuapo.antrol.library.models.ResponseListMetadataModel;
import com.rsuapo.antrol.library.models.ResponseMetadataModel;
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
}
