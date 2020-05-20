package com.project.minor.e_attendance;

import android.util.Base64;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

class EncDecUtil {

    static String encrypt(String Data, String str) throws Exception{
        String AES = "AES";
        SecretKeySpec key = generateKey(str);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE,key);
        byte[] encVal = c.doFinal(Data.getBytes());
        return Base64.encodeToString(encVal,Base64.DEFAULT);
    }

    static String decrypt(String data, String str) throws Exception{
        String AES = "AES";
        SecretKeySpec key = generateKey(str);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE,key);
        byte[] decodedValue = Base64.decode(data,Base64.DEFAULT);
        byte[] decValue = c.doFinal(decodedValue);
        return new String(decValue);
    }

    private static SecretKeySpec generateKey(String str) throws Exception{
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = str.getBytes();
        digest.update(bytes,0,bytes.length);
        byte[] key = digest.digest();
        return new SecretKeySpec(key,"AES");
    }

}
