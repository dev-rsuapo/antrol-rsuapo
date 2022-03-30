/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  RSU Aisyiyah Ponorogo <rsuapo@yahoo.co.id>
 * Created: Mar 26, 2022
 */
create table antrean (
    id varchar(60) primary key,
    kodebooking varchar(60),
    jenispasien varchar(20),
    nomorkartu varchar(60),
    nik varchar(60),
    nohp varchar(60),
    kodepoli varchar(20),
    namapoli varchar(255),
    pasienbaru integer,
    norm varchar(20),
    tanggalperiksa varchar(20),
    kodedokter varchar(20),
    namadokter varchar(255),
    jampraktek varchar(20),
    jeniskunjungan integer,
    nomorreferensi varchar(60),
    nomorantrean varchar(20),
    angkaantrean integer,
    estimasidilayani bigint,
    sisakuotajkn integer,
    kuotajkn integer,
    sisakuotanonjkn integer,
    kuotanonjkn integer,
    keterangan varchar(1024),

    ref_device_id varchar(60),
    ref_device_deskripsi varchar(255),
    
    sync_status integer,    -- 0: draft, 1: proses, 2: selesai, -- 9: batal
    sync_count integer,
    sync_last_at timestamp,
    sync_method integer,

    antrean_pendaftaran_nomor integer,
    antrean_pendaftaran_sudah_dipanggil boolean,
    antrean_pendaftaran_waktu_dipanggil timestamp,

    sudah_check_in boolean,
    waktu_check_in timestamp,

    poli_sudah_dipanggil boolean,
    poli_waktu_dipanggil timestamp,

    antrean_farmasi_lokasi_id varchar(60),
    antrean_farmasi_lokasi_deskripsi varchar(255),
    antrean_farmasi_nomor integer,
    antrean_farmasi_sudah_dipanggil boolean,
    antrean_farmasi_waktu_dipanggil timestamp,

    waktu_task1 timestamp,
    waktu_task2 timestamp,
    waktu_task3 timestamp,
    waktu_task4 timestamp,
    waktu_task5 timestamp,
    waktu_task6 timestamp,
    waktu_task7 timestamp,
    waktu_task99 timestamp,

    last_executed_task_id integer,
    last_executed_at timestamp,

    status integer,         -- 0: non aktif, 1: aktif
    catatan_pembatalan varchar(255),
    waktu_dibatalkan timestamp,

    created_by varchar(60),
    created_at timestamp,
    updated_by varchar(60),
    updated_at timestamp
);

create table antrean_task (
    id varchar(60) primary key,
    antrean_id varchar(60),
    kodebooking varchar(60),
    task_id integer,
    task_deskripsi varchar(60),
    waktu bigint,

    sync_status integer,
    sync_count integer,
    sync_last_at timestamp,
    sync_method integer,

    status integer,
    created_at timestamp,
    updated_at timestamp
);

create table pasien (
    id varchar(60) primary key,
    norm varchar(60),
    nomorkartu varchar(60),
    nik varchar(60),
    nomorkk varchar(60),
    nama varchar(255),
    jeniskelamin varchar(10),
    tanggallahir varchar(10),
    nohp varchar(60),
    alamat varchar(255),
    kodeprop varchar(20),
    namaprop varchar(100),
    kodedati2 varchar(20),
    namadati2 varchar(100),
    kodekec varchar(20),
    namakec varchar(100),
    kodekel varchar(20),
    namakel varchar(100),
    rw varchar(10),
    rt varchar(10),
    pasienbaru integer,
    created_at timestamp,
    updated_at timestamp
);

create table dokter (
    id varchar(60) primary key,
    kodedokter varchar(60),
    namadokter varchar(255),
    simrs_kode_dokter varchar(60),
    display_channel varchar(60),
    display_ruang varchar(60),
    created_at timestamp,
    updated_at timestamp
);

create table poli (
    id varchar(60) primary key,
    nmpoli varchar(255),
    nmsubspesialis varchar(255),
    kdsubspesialis varchar(60),
    kdpoli varchar(60),
    simrs_kode_poli varchar(60),
    created_at timestamp,
    updated_at timestamp
);

create table jadwal_dokter (
    id varchar(60) primary key,
    kodesubspesialis varchar(60),
    namasubspesialis varchar(255),
    hari integer,
    kapasitaspasien integer,
    libur integer,
    namahari varchar(60),
    jadwal varchar(60),
    kodedokter varchar(60),
    namadokter varchar(255),
    kodepoli varchar(60),
    namapoli varchar(255),
    kuota_jkn integer,
    kuota_non_jkn integer
);

create table operasi (
    id varchar(60) primary key,
    kodebooking varchar(60),
    tanggaloperasi varchar(20),
    jenistindakan varchar(255),
    kodepoli varchar(60),
    namapoli varchar(255),
    terlaksana integer,
    nopeserta varchar(60),
    lastupdate bigint,
    norm varchar(60),
    nama varchar(255),

    status integer,
    created_by varchar(60),
    created_at timestamp,
    updated_by varchar(60),
    updated_at timestamp    
);

create table pengguna (
    id varchar(60) primary key,
    password varchar(255),
    nama varchar(255),
    email varchar(60),
    telepon varchar(60),
    user_group varchar(60),
    created_at timestamp
);

create table pengaturan_nomor (
    id varchar(60) primary key,
    last_nomor varchar(60),
    updated_at timestamp
);
