package com.passinhotv.android;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by ABC on 3/29/2018.
 */

public class GlobalVar {
    public static String strSeeds;
    public static List<String> mSeeds;
    public static String strPwd;

    public static final String KEY_INTENT_PASSWORD = "intent_password";
    public static final String KEY_INTENT_SEED = "intent_seed";
    public static final String KEY_INTENT_PRIVATE = "intent_seed";

    public static final String KEY_INTENT_LOCAL = "Encrypt";

    public static SecretKey generateKey()
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        return new SecretKeySpec(KEY_INTENT_LOCAL.getBytes(), "AES");
    }

    public static byte[] encryptMsg(String message)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException
    {
   /* Encrypt the message. */
        SecretKey secret = null;
        try {
            secret = generateKey();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        byte[] cipherText = cipher.doFinal(message.getBytes("UTF-8"));
        return cipherText;
    }

    public static String decryptMsg(byte[] cipherText)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidParameterSpecException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException
    {
    /* Decrypt the message, given derived encContentValues and initialization vector. */
        SecretKey secret = null;
        try {
            secret = generateKey();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret);
        String decryptString = new String(cipher.doFinal(cipherText), "UTF-8");
        return decryptString;
    }
}
