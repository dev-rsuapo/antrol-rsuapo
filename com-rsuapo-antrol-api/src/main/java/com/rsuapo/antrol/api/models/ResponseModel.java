/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.api.models;

import java.io.Serializable;

/**
 *
 * @author RSU Aisyiyah Ponorogo <rsuapo@yahoo.co.id>
 */
public class ResponseModel implements Serializable {

    private final Metadata metadata;
    private Object response;
    
    public ResponseModel(int code, String message) {
        this.metadata = new Metadata(code, message);
    }

    public ResponseModel(int code, String message, Object response) {
        this.metadata = new Metadata(code, message);
        this.response = response;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public Object getResponse() {
        return response;
    }
    
    public static class Metadata implements Serializable {

        private final int code;
        private final String message;

        public Metadata(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

    }
}
