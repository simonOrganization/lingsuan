package com.ling.suandashi.tools;

import android.annotation.SuppressLint;
import android.util.Base64;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Imxu
 * @time 2019/7/21 10:36
 * @des ${TODO}
 */
public class AesUtil {

    /**
     * 算法Key
     */
    private static final String KEY_ALGORITHM = "AES";
    /**
     * 加密算法
     */
    private static final String CIPHER_ALGORITHM = "AES";

    /**
     * 加密数据
     *
     * @param data 待加密内容
     * @param key  加密的密钥
     * @return 加密后的数据
     */
    public static String encrypt(String data, String key) {
        try {
            // 获得密钥
            Key desKey = keyGenerator(key);
            // 实例化一个密码对象
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            // 密码初始化
            cipher.init(Cipher.ENCRYPT_MODE, desKey);
            // 执行加密
            byte[] bytes = cipher.doFinal(data.getBytes("UTF-8"));
            return Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (Exception e) {
            // 解析异常
            return "";
        }
    }

    /**
     * 解密数据
     *
     * @param data 待解密的内容
     * @param key  解密的密钥
     * @return 解密后的字符串
     */
    public static String decrypt(String data, String key) {
        try {
            // 生成密钥
            Key kGen = keyGenerator(key);
            // 实例化密码对象
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            // 初始化密码对象
            cipher.init(Cipher.DECRYPT_MODE, kGen);
            // 执行解密
            byte[] bytes = cipher.doFinal(Base64.decode(data, Base64.DEFAULT));
            return new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            // 解析异常
            return "";
        }
    }

    public static byte[] decrypt(byte[] content, String password) throws Exception {
        // 创建AES秘钥
        SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES/CBC/PKCS5PADDING");
        // 创建密码器
        Cipher cipher = Cipher.getInstance("AES");
        // 初始化解密器
        cipher.init(Cipher.DECRYPT_MODE, key);
        // 解密
        return cipher.doFinal(content);
    }

    /**
     * 获取密钥
     *
     * @param key 密钥字符串
     * @return 返回一个密钥
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    private static Key keyGenerator(String key) throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchProviderException {
        KeyGenerator kGen = KeyGenerator.getInstance(KEY_ALGORITHM);
        @SuppressLint("DeletedProvider") SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG", "Crypto");
        secureRandom.setSeed(key.getBytes());
        kGen.init(128, secureRandom);
        SecretKey secretKey = kGen.generateKey();
        byte[] encoded = secretKey.getEncoded();
        return new SecretKeySpec(encoded, KEY_ALGORITHM);
    }

    /** 解密字节数组 **/
    public static byte[] decrypt(byte[] content, String password, String iv) {
        try {
            SecretKeySpec key = createKey(password);
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.DECRYPT_MODE, key, createIV(iv));
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /** 解密(输出结果为字符串) **/
    public static String decrypt(String content, String password, String iv) {
        byte[] data = null;
        try {
            data = hexStringToByteArray(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        data = decrypt(data, password, iv);
        if (data == null)
            return null;
        String result = null;
        try {
            result = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
    /** 将hex字符串转换成字节数组 **/
    public static byte[] hexStringToByteArray(String s) {
        if (s == null || s.length() < 2) {
            return new byte[0];
        }
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }


    private static byte[] hex2byte(String inputString) {
        if (inputString == null || inputString.length() < 2) {
            return new byte[0];
        }
        inputString = inputString.toLowerCase();
        int l = inputString.length() / 2;
        byte[] result = new byte[l];
        for (int i = 0; i < l; ++i) {
            String tmp = inputString.substring(2 * i, 2 * i + 2);
            result[i] = (byte) (Integer.parseInt(tmp, 16) & 0xFF);
        }
        return result;
    }

    /** 算法/模式/填充 **/
    private static final String CipherMode = "AES/CBC/NoPadding";


    /** 创建密钥 **/
    private static SecretKeySpec createKey(String key) {
        byte[] data = null;
        if (key == null) {
            key = "";
        }
        StringBuffer sb = new StringBuffer(16);
        sb.append(key);
        while (sb.length() < 16) {
            sb.append("0");
        }
        if (sb.length() > 16) {
            sb.setLength(16);
        }
        try {
            data = sb.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new SecretKeySpec(data, "AES");
    }


    private static IvParameterSpec createIV(String password) {
        byte[] data = null;
        if (password == null) {
            password = "";
        }
        StringBuffer sb = new StringBuffer(16);
        sb.append(password);
        while (sb.length() < 16) {
            sb.append("0");
        }
        if (sb.length() > 16) {
            sb.setLength(16);
        }
        try {
            data = sb.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new IvParameterSpec(data);
    }
}