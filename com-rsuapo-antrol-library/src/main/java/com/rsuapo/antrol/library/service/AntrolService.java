/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.library.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsuapo.antrol.library.crypto.Decryptor;
import com.rsuapo.antrol.library.exceptions.BadRequestException;
import com.rsuapo.antrol.library.models.AntreanAddModel;
import com.rsuapo.antrol.library.models.AntreanFarmasiAddModel;
import com.rsuapo.antrol.library.models.AntreanModel;
import com.rsuapo.antrol.library.models.DashboardModel;
import com.rsuapo.antrol.library.models.DokterModel;
import com.rsuapo.antrol.library.models.EncryptedResponseModel;
import com.rsuapo.antrol.library.models.JadwalDokterModel;
import com.rsuapo.antrol.library.models.MetadataModel;
import com.rsuapo.antrol.library.models.PasienFpModel;
import com.rsuapo.antrol.library.models.PoliFpModel;
import com.rsuapo.antrol.library.models.PoliModel;
import com.rsuapo.antrol.library.models.RequestModel;
import com.rsuapo.antrol.library.models.ResponseListMetadataModel;
import com.rsuapo.antrol.library.models.ResponseMetadataModel;
import com.rsuapo.antrol.library.models.TaskModel;
import com.rsuapo.antrol.library.models.UpdateJadwalDokterModel;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 * @author RSU Aisyiyah Ponorogo <rsuapo@yahoo.co.id>
 */
public class AntrolService {

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private final String baseUrl;
    private final String userKey;
    private final String consumerId;
    private final String consumerSecret;

    private final OkHttpClient client;
    private final Headers defaultHeaders;

    public AntrolService(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.userKey = builder.userKey;
        this.consumerId = builder.consumerId;
        this.consumerSecret = builder.consumerSecret;

        this.client = new OkHttpClient();
        defaultHeaders = new Headers.Builder()
                .add("Accept", "application/json")
                .add("Content-Type", "application/json; charset=utf-8")
                .add("X-cons-id", consumerId)
                .add("user_key", userKey)
                .build();
    }

    public ResponseListMetadataModel<PoliModel> getPolis() {
        try {
            RequestModel req = createRequest(baseUrl + "/ref/poli");
            Response response = req.getResponse();
            if (response.isSuccessful()) {
                ObjectMapper objectMapper = new ObjectMapper();
                String json = response.body().string();
                EncryptedResponseModel encryptedResponse = objectMapper.readValue(json, EncryptedResponseModel.class);
                if (1 == encryptedResponse.getMetadata().getCode()) {
                    String plainText = Decryptor.decrypt(encryptedResponse.getResponse(), consumerId + consumerSecret + req.getTimestamp());
                    List<PoliModel> dokters = Arrays.asList(objectMapper.readValue(plainText, PoliModel[].class));
                    ResponseListMetadataModel<PoliModel> responseListMetadata = new ResponseListMetadataModel<>(encryptedResponse.getMetadata(), dokters);
                    return responseListMetadata;
                }

                return new ResponseListMetadataModel<>(encryptedResponse.getMetadata());
            }

            return new ResponseListMetadataModel<>(new MetadataModel(response.code(), response.message()));

        } catch (IOException ex) {
            Logger.getLogger(AntrolService.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseListMetadataModel<>(new MetadataModel(500, ex.getMessage()));
        }
    }

    public ResponseListMetadataModel<AntreanModel> getAntreanPerTanggal(String tanggal) {
        try {
            RequestModel req = createRequest(baseUrl + "/antrean/pendaftaran/tanggal/" + tanggal);
            Response response = req.getResponse();
            if (response.isSuccessful()) {
                ObjectMapper objectMapper = new ObjectMapper();
                String json = response.body().string();
                EncryptedResponseModel encryptedResponse = objectMapper.readValue(json, EncryptedResponseModel.class);
                if (1 == encryptedResponse.getMetadata().getCode()) {
                    String plainText = Decryptor.decrypt(encryptedResponse.getResponse(), consumerId + consumerSecret + req.getTimestamp());
                    List<AntreanModel> dokters = Arrays.asList(objectMapper.readValue(plainText, AntreanModel[].class));
                    ResponseListMetadataModel<AntreanModel> responseListMetadata = new ResponseListMetadataModel<>(encryptedResponse.getMetadata(), dokters);
                    return responseListMetadata;
                }

                return new ResponseListMetadataModel<>(encryptedResponse.getMetadata());
            }

            return new ResponseListMetadataModel<>(new MetadataModel(response.code(), response.message()));

        } catch (IOException ex) {
            Logger.getLogger(AntrolService.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseListMetadataModel<>(new MetadataModel(500, ex.getMessage()));
        }
    }

    public ResponseListMetadataModel<AntreanModel> getAntreanPerKodeBooking(String kodeBooking) {
        try {
            RequestModel req = createRequest(baseUrl + "/antrean/pendaftaran/kodebooking/" + kodeBooking);
            Response response = req.getResponse();
            if (response.isSuccessful()) {
                ObjectMapper objectMapper = new ObjectMapper();
                String json = response.body().string();
                EncryptedResponseModel encryptedResponse = objectMapper.readValue(json, EncryptedResponseModel.class);
                if (1 == encryptedResponse.getMetadata().getCode()) {
                    String plainText = Decryptor.decrypt(encryptedResponse.getResponse(), consumerId + consumerSecret + req.getTimestamp());
                    List<AntreanModel> dokters = Arrays.asList(objectMapper.readValue(plainText, AntreanModel[].class));
                    ResponseListMetadataModel<AntreanModel> responseListMetadata = new ResponseListMetadataModel<>(encryptedResponse.getMetadata(), dokters);
                    return responseListMetadata;
                }

                return new ResponseListMetadataModel<>(encryptedResponse.getMetadata());
            }

            return new ResponseListMetadataModel<>(new MetadataModel(response.code(), response.message()));

        } catch (IOException ex) {
            Logger.getLogger(AntrolService.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseListMetadataModel<>(new MetadataModel(500, ex.getMessage()));
        }
    }

    public ResponseListMetadataModel<AntreanModel> getAntreanBelumDilayani() {
        try {
            RequestModel req = createRequest(baseUrl + "/antrean/pendaftaran/aktif");
            Response response = req.getResponse();
            if (response.isSuccessful()) {
                ObjectMapper objectMapper = new ObjectMapper();
                String json = response.body().string();
                EncryptedResponseModel encryptedResponse = objectMapper.readValue(json, EncryptedResponseModel.class);
                if (1 == encryptedResponse.getMetadata().getCode()) {
                    String plainText = Decryptor.decrypt(encryptedResponse.getResponse(), consumerId + consumerSecret + req.getTimestamp());
                    List<AntreanModel> dokters = Arrays.asList(objectMapper.readValue(plainText, AntreanModel[].class));
                    ResponseListMetadataModel<AntreanModel> responseListMetadata = new ResponseListMetadataModel<>(encryptedResponse.getMetadata(), dokters);
                    return responseListMetadata;
                }

                return new ResponseListMetadataModel<>(encryptedResponse.getMetadata());
            }

            return new ResponseListMetadataModel<>(new MetadataModel(response.code(), response.message()));

        } catch (IOException ex) {
            Logger.getLogger(AntrolService.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseListMetadataModel<>(new MetadataModel(500, ex.getMessage()));
        }
    }

    public ResponseListMetadataModel<AntreanModel> getAntreanBelumDilayani(
            String kodePoli,
            String kodeDokter,
            String hari,
            String jamPraktek
    ) {
        try {
            RequestModel req = createRequest(baseUrl + "/antrean/pendaftaran/kodepoli/" + kodePoli + "/kodedokter/" + kodeDokter + "/hari/" + hari + "/jampraktek/" + jamPraktek);
            Response response = req.getResponse();
            if (response.isSuccessful()) {
                ObjectMapper objectMapper = new ObjectMapper();
                String json = response.body().string();
                EncryptedResponseModel encryptedResponse = objectMapper.readValue(json, EncryptedResponseModel.class);
                if (1 == encryptedResponse.getMetadata().getCode()) {
                    String plainText = Decryptor.decrypt(encryptedResponse.getResponse(), consumerId + consumerSecret + req.getTimestamp());
                    List<AntreanModel> dokters = Arrays.asList(objectMapper.readValue(plainText, AntreanModel[].class));
                    ResponseListMetadataModel<AntreanModel> responseListMetadata = new ResponseListMetadataModel<>(encryptedResponse.getMetadata(), dokters);
                    return responseListMetadata;
                }

                return new ResponseListMetadataModel<>(encryptedResponse.getMetadata());
            }

            return new ResponseListMetadataModel<>(new MetadataModel(response.code(), response.message()));

        } catch (IOException ex) {
            Logger.getLogger(AntrolService.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseListMetadataModel<>(new MetadataModel(500, ex.getMessage()));
        }
    }

    public ResponseMetadataModel<PasienFpModel> getPasienFp(String identitas, String nomorIdentitas) {
        try {
            RequestModel req = createRequest(baseUrl + "/ref/pasien/fp/identitas/" + identitas + "/noidentitas/" + nomorIdentitas);
            Response response = req.getResponse();
            if (response.isSuccessful()) {
                ObjectMapper objectMapper = new ObjectMapper();
                String json = response.body().string();
                EncryptedResponseModel encryptedResponse = objectMapper.readValue(json, EncryptedResponseModel.class);
                if (1 == encryptedResponse.getMetadata().getCode()) {
                    String plainText = Decryptor.decrypt(encryptedResponse.getResponse(), consumerId + consumerSecret + req.getTimestamp());
                    PasienFpModel pasien = objectMapper.readValue(plainText, PasienFpModel.class);
                    ResponseMetadataModel<PasienFpModel> responseMetadataModel = new ResponseMetadataModel<>(encryptedResponse.getMetadata(), pasien);
                    return responseMetadataModel;
                }

                return new ResponseMetadataModel<>(encryptedResponse.getMetadata());
            }

            return new ResponseMetadataModel<>(new MetadataModel(response.code(), response.message()));

        } catch (IOException ex) {
            Logger.getLogger(AntrolService.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseMetadataModel<>(new MetadataModel(500, ex.getMessage()));
        }
    }

    public ResponseListMetadataModel<PoliFpModel> getPoliFps() {
        try {
            RequestModel req = createRequest(baseUrl + "/ref/poli/fp");
            Response response = req.getResponse();
            if (response.isSuccessful()) {
                ObjectMapper objectMapper = new ObjectMapper();
                String json = response.body().string();
                EncryptedResponseModel encryptedResponse = objectMapper.readValue(json, EncryptedResponseModel.class);
                if (1 == encryptedResponse.getMetadata().getCode()) {
                    String plainText = Decryptor.decrypt(encryptedResponse.getResponse(), consumerId + consumerSecret + req.getTimestamp());
                    List<PoliFpModel> dokters = Arrays.asList(objectMapper.readValue(plainText, PoliFpModel[].class));
                    ResponseListMetadataModel<PoliFpModel> responseListMetadata = new ResponseListMetadataModel<>(encryptedResponse.getMetadata(), dokters);
                    return responseListMetadata;
                }

                return new ResponseListMetadataModel<>(encryptedResponse.getMetadata());
            }

            return new ResponseListMetadataModel<>(new MetadataModel(response.code(), response.message()));

        } catch (IOException ex) {
            Logger.getLogger(AntrolService.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseListMetadataModel<>(new MetadataModel(500, ex.getMessage()));
        }
    }

    public ResponseListMetadataModel<DokterModel> getDokters() {
        try {
            RequestModel req = createRequest(baseUrl + "/ref/dokter");
            Response response = req.getResponse();
            if (response.isSuccessful()) {
                ObjectMapper objectMapper = new ObjectMapper();
                String json = response.body().string();
                EncryptedResponseModel encryptedResponse = objectMapper.readValue(json, EncryptedResponseModel.class);
                if (1 == encryptedResponse.getMetadata().getCode()) {
                    String plainText = Decryptor.decrypt(encryptedResponse.getResponse(), consumerId + consumerSecret + req.getTimestamp());
                    List<DokterModel> dokters = Arrays.asList(objectMapper.readValue(plainText, DokterModel[].class));
                    ResponseListMetadataModel<DokterModel> responseListMetadata = new ResponseListMetadataModel<>(encryptedResponse.getMetadata(), dokters);
                    return responseListMetadata;
                }

                return new ResponseListMetadataModel<>(encryptedResponse.getMetadata());
            }

            return new ResponseListMetadataModel<>(new MetadataModel(response.code(), response.message()));
        } catch (IOException ex) {
            Logger.getLogger(AntrolService.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseListMetadataModel<>(new MetadataModel(500, ex.getMessage()));
        }
    }

    public ResponseListMetadataModel<JadwalDokterModel> getJadwalDokters(String kodePoli, String tanggal) {
        try {
            RequestModel req = createRequest(baseUrl + "/jadwaldokter/kodepoli/" + kodePoli + "/tanggal/" + tanggal);
            Response response = req.getResponse();
            if (response.isSuccessful()) {
                ObjectMapper objectMapper = new ObjectMapper();
                String json = response.body().string();
                EncryptedResponseModel encryptedResponse = objectMapper.readValue(json, EncryptedResponseModel.class);
                if (200 == encryptedResponse.getMetadata().getCode()) {
                    String plainText = Decryptor.decrypt(encryptedResponse.getResponse(), consumerId + consumerSecret + req.getTimestamp());
                    List<JadwalDokterModel> jadwalDokterModels = Arrays.asList(objectMapper.readValue(plainText, JadwalDokterModel[].class));
                    ResponseListMetadataModel<JadwalDokterModel> responseListMetadata = new ResponseListMetadataModel<>(encryptedResponse.getMetadata(), jadwalDokterModels);
                    return responseListMetadata;
                }

                return new ResponseListMetadataModel<>(encryptedResponse.getMetadata());
            }

            return new ResponseListMetadataModel<>(new MetadataModel(response.code(), response.message()));
        } catch (IOException ex) {
            Logger.getLogger(AntrolService.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseListMetadataModel<>(new MetadataModel(500, ex.getMessage()));
        }
    }

    public ResponseMetadataModel updateJadwalDokter(UpdateJadwalDokterModel u) {
        try {
            String payload = new ObjectMapper().writeValueAsString(u);
            RequestModel req = createRequest(baseUrl + "/jadwaldokter/updatejadwaldokter", payload);
            Response response = req.getResponse();
            if (response.isSuccessful()) {
                ObjectMapper objectMapper = new ObjectMapper();
                String json = response.body().string();
                ResponseMetadataModel responseMetadata = objectMapper.readValue(json, ResponseMetadataModel.class);
                return responseMetadata;
            }

            return new ResponseMetadataModel(new MetadataModel(response.code(), response.message()));

        } catch (IOException ex) {
            Logger.getLogger(AntrolService.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseMetadataModel(new MetadataModel(500, ex.getMessage()));
        }
    }

    public ResponseMetadataModel antreanAdd(AntreanAddModel a) {
        try {
            String payload = new ObjectMapper().writeValueAsString(a);
            System.out.println("payload antreanAdd: " + payload);
            RequestModel req = createRequest(baseUrl + "/antrean/add", payload);
            Response response = req.getResponse();
            if (response.isSuccessful()) {
                ObjectMapper objectMapper = new ObjectMapper();
                String json = response.body().string();
                ResponseMetadataModel responseMetadata = objectMapper.readValue(json, ResponseMetadataModel.class);
                return responseMetadata;
            }

            return new ResponseMetadataModel(new MetadataModel(response.code(), response.message()));

        } catch (IOException ex) {
            Logger.getLogger(AntrolService.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseMetadataModel(new MetadataModel(500, ex.getMessage()));
        }
    }

    public ResponseMetadataModel antreanFarmasiAdd(AntreanFarmasiAddModel a) {
        try {
            String payload = new ObjectMapper().writeValueAsString(a);
            System.out.println("payload antreanAdd: " + payload);
            RequestModel req = createRequest(baseUrl + "/antrean/farmasi/add", payload);
            Response response = req.getResponse();
            if (response.isSuccessful()) {
                ObjectMapper objectMapper = new ObjectMapper();
                String json = response.body().string();
                ResponseMetadataModel responseMetadata = objectMapper.readValue(json, ResponseMetadataModel.class);
                return responseMetadata;
            }

            return new ResponseMetadataModel(new MetadataModel(response.code(), response.message()));

        } catch (IOException ex) {
            Logger.getLogger(AntrolService.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseMetadataModel(new MetadataModel(500, ex.getMessage()));
        }
    }

    public ResponseMetadataModel updateWaktuAntrean(String kodeBooking, int taskId, long waktu, String jenisResep) {
        try {

            if (taskId < 1 && taskId > 7 && taskId != 99) {
                throw new BadRequestException(400, "Task ID " + taskId + " tidak termasuk dalam taskId antrian");
            }

            Map<String, Object> map = new HashMap<>();
            map.put("kodebooking", kodeBooking);
            map.put("taskid", taskId);
            map.put("waktu", waktu);
            map.put("jenisresep", jenisResep);
            String payload = new ObjectMapper().writeValueAsString(map);
            System.out.println("payload updateWaktuAntrean: " + payload);
            RequestModel req = createRequest(baseUrl + "/antrean/updatewaktu", payload);
            Response response = req.getResponse();
            if (response.isSuccessful()) {
                ObjectMapper objectMapper = new ObjectMapper();
                String json = response.body().string();
                ResponseMetadataModel responseMetadata = objectMapper.readValue(json, ResponseMetadataModel.class);
                return responseMetadata;
            }

            return new ResponseMetadataModel(new MetadataModel(response.code(), response.message()));

        } catch (IOException ex) {
            Logger.getLogger(AntrolService.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseMetadataModel(new MetadataModel(500, ex.getMessage()));
        } catch (BadRequestException ex) {
            Logger.getLogger(AntrolService.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseMetadataModel(new MetadataModel(ex.getCode(), ex.getMessage()));
        }
    }

    public ResponseMetadataModel updateWaktuAntrean(String kodeBooking, int taskId, long waktu) {
        return updateWaktuAntrean(kodeBooking, taskId, waktu, "Tidak ada");
    }

    public ResponseMetadataModel antreanBatal(String kodeBooking, String keterangan) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("kodebooking", kodeBooking);
            map.put("keterangan", keterangan);
            String payload = new ObjectMapper().writeValueAsString(map);
            RequestModel req = createRequest(baseUrl + "/antrean/batal", payload);
            Response response = req.getResponse();
            if (response.isSuccessful()) {
                ObjectMapper objectMapper = new ObjectMapper();
                String json = response.body().string();
                ResponseMetadataModel responseMetadata = objectMapper.readValue(json, ResponseMetadataModel.class);
                return responseMetadata;
            }

            return new ResponseMetadataModel(new MetadataModel(response.code(), response.message()));

        } catch (IOException ex) {
            Logger.getLogger(AntrolService.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseMetadataModel(new MetadataModel(500, ex.getMessage()));
        }
    }

    public ResponseListMetadataModel<TaskModel> getTaskList(String kodeBooking) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("kodebooking", kodeBooking);
            String payload = new ObjectMapper().writeValueAsString(map);
            RequestModel req = createRequest(baseUrl + "/antrean/getlisttask", payload);
            Response response = req.getResponse();
            if (response.isSuccessful()) {
                ObjectMapper objectMapper = new ObjectMapper();
                String json = response.body().string();
                EncryptedResponseModel encryptedResponse = objectMapper.readValue(json, EncryptedResponseModel.class);
                if (200 == encryptedResponse.getMetadata().getCode()) {
                    String plainText = Decryptor.decrypt(encryptedResponse.getResponse(), consumerId + consumerSecret + req.getTimestamp());
                    List<TaskModel> tasks = Arrays.asList(objectMapper.readValue(plainText, TaskModel[].class));
                    ResponseListMetadataModel<TaskModel> responseListMetadata = new ResponseListMetadataModel<>(encryptedResponse.getMetadata(), tasks);
                    return responseListMetadata;
                }

                return new ResponseListMetadataModel<>(encryptedResponse.getMetadata());
            }
            return new ResponseListMetadataModel<>(new MetadataModel(response.code(), response.message()));
        } catch (IOException ex) {
            Logger.getLogger(AntrolService.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseListMetadataModel<>(new MetadataModel(500, ex.getMessage()));
        }
    }

    public ResponseListMetadataModel<DashboardModel> getDashboardPerTanggalRs(String tanggal) {
        return getDashboardPerTanggal(tanggal, "rs");
    }

    public ResponseListMetadataModel<DashboardModel> getDashboardPerTanggalServer(String tanggal) {
        return getDashboardPerTanggal(tanggal, "server");
    }

    private ResponseListMetadataModel<DashboardModel> getDashboardPerTanggal(String tanggal1, String waktu) {
        try {
            RequestModel req = createRequest(baseUrl + "/dashboard/waktutunggu/tanggal/" + tanggal1 + "/waktu/" + waktu);
            Response response = req.getResponse();
            if (response.isSuccessful()) {
                ObjectMapper objectMapper = new ObjectMapper();
                String json = response.body().string();

                JsonNode rootJsonNode = objectMapper.readTree(json);
                JsonNode listJsonNode = rootJsonNode.get("response").get("list");
                String text = objectMapper.writeValueAsString(listJsonNode);
                List<DashboardModel> dashboardModels = Arrays.asList(objectMapper.readValue(text, DashboardModel[].class));
                return new ResponseListMetadataModel<>(new MetadataModel(200, "Ok"), dashboardModels);
            }

            return new ResponseListMetadataModel<>(new MetadataModel(response.code(), response.message()));
        } catch (IOException ex) {
            Logger.getLogger(AntrolService.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseListMetadataModel<>(new MetadataModel(500, ex.getMessage()));
        }
    }

    public ResponseListMetadataModel<DashboardModel> getDashboardPerBulanRs(String bulan, String tahun) {
        return getDashboardPerBulan(bulan, tahun, "rs");
    }

    public ResponseListMetadataModel<DashboardModel> getDashboardPerBulanServer(String bulan, String tahun) {
        return getDashboardPerBulan(bulan, tahun, "server");
    }

    private ResponseListMetadataModel<DashboardModel> getDashboardPerBulan(String bulan, String tahun, String waktu) {
        try {
            RequestModel req = createRequest(baseUrl + "/dashboard/waktutunggu/bulan/" + bulan + "/tahun/" + tahun + "/waktu/" + waktu);
            Response response = req.getResponse();
            if (response.isSuccessful()) {
                ObjectMapper objectMapper = new ObjectMapper();
                String json = response.body().string();

                JsonNode rootJsonNode = objectMapper.readTree(json);
                JsonNode listJsonNode = rootJsonNode.get("response").get("list");
                String text = objectMapper.writeValueAsString(listJsonNode);
                List<DashboardModel> dashboardModels = Arrays.asList(objectMapper.readValue(text, DashboardModel[].class));
                return new ResponseListMetadataModel<>(new MetadataModel(200, "Ok"), dashboardModels);
            }

            return new ResponseListMetadataModel<>(new MetadataModel(response.code(), response.message()));
        } catch (IOException ex) {
            Logger.getLogger(AntrolService.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseListMetadataModel<>(new MetadataModel(500, ex.getMessage()));
        }
    }

    public static class Builder {

        private String baseUrl;
        private String consumerId;
        private String consumerSecret;
        private String userKey;

        public Builder() {
        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder userKey(String userKey) {
            this.userKey = userKey;
            return this;
        }

        public Builder consumerSecret(String consumerSecret) {
            this.consumerSecret = consumerSecret;
            return this;
        }

        public Builder consumerId(String consumerId) {
            this.consumerId = consumerId;
            return this;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public String getUserKey() {
            return userKey;
        }

        public String getConsumerId() {
            return consumerId;
        }

        public String getConsumerSecret() {
            return consumerSecret;
        }

        public AntrolService build() {
            AntrolService animalService = new AntrolService(this);
            validateAntrolService(animalService);
            return animalService;
        }

        private void validateAntrolService(AntrolService antrolService) {

        }
    }

    private String generateSignature(String data, String consumerSecret) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(consumerSecret.getBytes("UTF-8"), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKey);
            byte[] hmacData = mac.doFinal(data.getBytes("UTF-8"));
            String encodeToString = Base64.getEncoder().encodeToString(hmacData);
            return encodeToString;
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private RequestModel createRequest(String url) throws IOException {
        String timestamp = "" + (System.currentTimeMillis() / 1000);
        String data = consumerId + "&" + timestamp;
        String signature = generateSignature(data, consumerSecret);

        Request request = new Request.Builder()
                .url(url)
                .headers(defaultHeaders)
                .header("X-timestamp", timestamp)
                .header("X-signature", signature)
                .build();
        Response response = client.newCall(request).execute();
        return new RequestModel(timestamp, data, signature, request, response);
    }

    private RequestModel createRequest(String url, String body) throws IOException {
        RequestBody requestBody = RequestBody.create(JSON, body);

        String timestamp = "" + (System.currentTimeMillis() / 1000);
        String data = consumerId + "&" + timestamp;
        String signature = generateSignature(data, consumerSecret);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .headers(defaultHeaders)
                .header("X-timestamp", timestamp)
                .header("X-signature", signature)
                .build();
        Response response = client.newCall(request).execute();
        return new RequestModel(timestamp, data, signature, request, response);
    }

    private static final Logger LOG = Logger.getLogger(AntrolService.class.getName());

}
