/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.library.models;

import java.io.Serializable;

/**
 *
 * @author RSU Aisyiyah Ponorogo <rsuapo@yahoo.co.id>
 * @param <T>
 */
public class ResponseMetadataModel<T> implements Serializable {

    private MetadataModel metadata;
    private T response;

    public ResponseMetadataModel() {
    }

    public ResponseMetadataModel(MetadataModel metadata) {
        this.metadata = metadata;
    }

    public ResponseMetadataModel(MetadataModel metadata, T response) {
        this.metadata = metadata;
        this.response = response;
    }

    public MetadataModel getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataModel metadata) {
        this.metadata = metadata;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

}
