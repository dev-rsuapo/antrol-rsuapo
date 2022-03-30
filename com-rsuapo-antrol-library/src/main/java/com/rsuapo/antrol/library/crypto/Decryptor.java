/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.library.crypto;

import com.rsuapo.antrol.library.crypto.Enc.AesKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author andik
 */
public class Decryptor {
    
    public static String decrypt(String response, String key) {
        try {
            AesKeySpec aesKeySpec = Enc.generateKey(key);
            String decrypted = Enc.decrypt(response, aesKeySpec.getKey(), aesKeySpec.getIv());
            String decompress = LZString.decompressFromEncodedURIComponent(decrypted);
            return decompress;
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            LOG.log(Level.SEVERE, null, e);
            return null;
            
        }
    }
    private static final Logger LOG = Logger.getLogger(Decryptor.class.getName());
    
}
