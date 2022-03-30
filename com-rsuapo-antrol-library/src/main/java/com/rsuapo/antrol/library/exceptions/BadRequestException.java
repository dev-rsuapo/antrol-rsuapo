/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.library.exceptions;

/**
 *
 * @author RSU Aisyiyah Ponorogo <rsuapo@yahoo.co.id>
 */
public class BadRequestException extends Exception {

    private final int code;

    public BadRequestException(int code, String string) {
        super(string);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
