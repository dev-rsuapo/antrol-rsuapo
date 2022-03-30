package com.rsuapo.antrol.library.crypto;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Enc {

    public static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    public static AesKeySpec generateKey(String key)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] _hashKey = digest.digest(key.getBytes(StandardCharsets.UTF_8));
        byte[] _hashIv = new byte[16];
        for (int i = 0; i < 16; i++) {
            _hashIv[i] = _hashKey[i];
        }

        Enc.AesKeySpec aesKeySpec = new Enc.AesKeySpec();
        SecretKeySpec _key = new SecretKeySpec(_hashKey, "AES");
        IvParameterSpec _iv = new IvParameterSpec(_hashIv);
        aesKeySpec.setKey(_key);
        aesKeySpec.setIv(_iv);
        return aesKeySpec;
    }

    public static String decrypt(String cipherText, SecretKeySpec key, IvParameterSpec iv)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(plainText);
    }

    public static class AesKeySpec {

        private SecretKeySpec key;
        private IvParameterSpec iv;

        public SecretKeySpec getKey() {
            return key;
        }

        public void setKey(SecretKeySpec key) {
            this.key = key;
        }

        public IvParameterSpec getIv() {
            return iv;
        }

        public void setIv(IvParameterSpec iv) {
            this.iv = iv;
        }

    }

}
