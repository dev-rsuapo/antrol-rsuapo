/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.library.models;

import java.io.Serializable;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * @author RSU Aisyiyah Ponorogo <rsuapo@yahoo.co.id>
 */
public class RequestModel implements Serializable {

    private final String timestamp;
    private final String data;
    private final String signature;
    private final Request request;
    private final Response response;

    public RequestModel(String timestamp, String data, String signature, Request request, Response response) {
        this.timestamp = timestamp;
        this.data = data;
        this.signature = signature;
        this.request = request;
        this.response = response;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getData() {
        return data;
    }

    public String getSignature() {
        return signature;
    }

    public Request getRequest() {
        return request;
    }

    public Response getResponse() {
        return response;
    }

}
