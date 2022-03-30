/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.library.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author RSU Aisyiyah Ponorogo <rsuapo@yahoo.co.id>
 * @param <T>
 */
public class ResponseListMetadataModel<T> implements Serializable {

    private MetadataModel metadata;
    private List<T> response;

    public ResponseListMetadataModel() {
    }
    
    public ResponseListMetadataModel(MetadataModel metadata) {
        this.metadata = metadata;
        this.response = new ArrayList();
    }

    public ResponseListMetadataModel(MetadataModel metadata, List<T> data) {
        this.metadata = metadata;
        this.response = data;
    }

    public MetadataModel getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataModel metadata) {
        this.metadata = metadata;
    }

    public List<T> getResponse() {
        return response;
    }

    public void setResponse(List<T> response) {
        this.response = response;
    }

}
