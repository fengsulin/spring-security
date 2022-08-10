package com.lin.security.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * RSA加密解密,签名和验证工具类
 */
public class RSAUtils {
    /**加密算法为RSA*/
    public static final String KEY_ALGORITHM = "RSA";

    /**自定义存储在map集合中的公钥key*/
    public static final String PUBLIC_KEY = "RSAPublicKey";

    /**自定义存储在map集合中的私钥key*/
    public static final String PRIVATE_KEY = "RSAPrivateKey";

    /**签名算法*/
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    public static String  publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCVhjBf1W3WdlnpzUFjrCipECSGtn1NW8EwX/tMlp13m7NpKOukA2eOMLYk6YdXIKpQ7hUhHVMAT76Pd/xcFGj0MgcwgkGC06Cfpe6M18s3/iyUKezq5rcGhTnCdQepF5Z0TAOSyOpziw8qkhA+0ZOkeKHx25w4MsxTP2PmpagDXwIDAQAB";
    public static String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJWGMF/VbdZ2WenNQWOsKKkQJIa2fU1bwTBf+0yWnXebs2ko66QDZ44wtiTph1cgqlDuFSEdUwBPvo93/FwUaPQyBzCCQYLToJ+l7ozXyzf+LJQp7OrmtwaFOcJ1B6kXlnRMA5LI6nOLDyqSED7Rk6R4ofHbnDgyzFM/Y+alqANfAgMBAAECgYAdNGMBbN3xvBcOv/zsG2Wrs8/2kfFCaVAAA1dBm89w27P8r7XqyJFy59fDuOsq3OH3QbFV0PTzxB+bxRtA3umqrv7QNgnuXzDJYNihTrSX1UF6nHMTVFJKAZAFsQUbYSZ7dLLaiH4xptKr4cJ6v9iisbyvnxjA0g0nAk/J79suXQJBAOjMQPJ1wFL9AoBo69X52ev51thiuwmn4pjpk0RW5We3k5qnaDfkVNBJ2cTzRF7rF6uDb+V1/BNY+EDyxMYqi00CQQCkbT1ckzlJEXAs+LYcDH6QFkiGRiTgQbOpE0WJ2d1kDmx5RqmYOBXPQOWOidV5Clr5yjGb58dpTFsTGuuzxPtbAkEA1GI0Bn+i3JNzCs+uCOMPC5g9h+BbHy4JLtQ5xKk6VWtHwFpmXEd1kSkvAd2mTKpSR+l71m3TcZtDsY1KNDhwKQJAXBb2k/+8yMike/t7+y+gPtDMI6/rfVIu7lXlz3Qew6g0ZqQF3kQjKuWc6/0Ue2sqUtZEfJ4OmFH0fDnKou+DGwJBAOdoXt5E08byr88MoW/IViJeBDbMVqWfh8isTRFMq7UwUXa2BtOnR8Gx3qOD1atBG1hjANYA8GBw3dtZT8Napo0=";

    /**每次最大加密明文长度*/
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**每次最大解密密文长度*/
    private static final int MAX_DECRYPT_BLOCK = 2048;


    /**
     * 公钥和私钥初始化
     * @return keyMap:存储公钥和私钥的map集合
     * @throws NoSuchAlgorithmException
     */
    public static Map<String,Object> initKey() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String,Object> keyMap = new HashMap<>(2);
        keyMap.put(PUBLIC_KEY,publicKey);
        keyMap.put(PRIVATE_KEY,privateKey);
        return keyMap;
    }

    /**
     * 从map集合中获取公钥字符串
     * @param keyMap
     * @return
     */
    public static String getPublicKeyStr(Map<String,Object> keyMap){
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }

    /**
     * 从map集合中获取私钥字符串
     * @param keyMap
     * @return
     */
    public static String getPrivateKeyStr(Map<String,Object> keyMap){
        // 从map中获取私钥对象，转换为key对象
        Key key = (Key)keyMap.get(PRIVATE_KEY);
        // 编码返回字符串
        return Base64.encodeBase64String(key.getEncoded());
    }

    /**
     * 获取公钥
     * @param key：公钥字符串
     * @return
     */
    public static PublicKey getPublicKey(String key){
        byte[] keyBytes;
        keyBytes = new Base64().decode(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = null;
        PublicKey publicKey = null;
        try {
            keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            publicKey = keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    /**
     * 获取私钥
     * @param key：私钥字符串
     * @return
     */
    public static PrivateKey getPrivateKey(String key){
        byte[] keyBytes;
        keyBytes = new Base64().decode(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = null;
        PrivateKey privateKey = null;
        try {
            keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            privateKey = keyFactory.generatePrivate(keySpec);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    /**
     * 字符串转为byte[]
     * @param key
     * @return
     */
    public static byte[] decryptBASE64(String key){
        return Base64.decodeBase64(key);
    }

    /**
     * byte[]转为字符串
     * @param key
     * @return
     */
    public static String encryptBASE64(byte[] key){
        return Base64.encodeBase64String(key);
    }

    /**
     * 签名方法
     * @param data：需要签名的数据
     * @param privateKeyStr：私钥字符串
     * @return signs：签名后的byte[]
     */
    public static byte[] sign(byte[] data,String privateKeyStr){
        PrivateKey priK = getPrivateKey(privateKeyStr);
        Signature sig = null;
        byte[] signs = null;
        try {
            sig = Signature.getInstance(SIGNATURE_ALGORITHM);
            sig.initSign(priK);
            sig.update(data);
            signs = sig.sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }
        return signs;
    }

    /**
     * 验证方法
     * @param data：数据
     * @param sign：签名后的byte[]
     * @param publicKeyStr：公钥字符串
     * @return verify：是否验证通过
     */
    public static boolean verify(byte[] data,byte[] sign,String publicKeyStr){
        PublicKey pubK = getPublicKey(publicKeyStr);
        boolean verify = false;
        try {
            Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
            sig.initVerify(pubK);
            sig.update(data);
            verify = sig.verify(sign);
        } catch (SignatureException | NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return verify;
    }

    /**
     * RSA加密
     * @param plainText：需要加密的数据
     * @param publicKeyStr：公钥字符串
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] plainText,String publicKeyStr) throws Exception {
        PublicKey publicKey = getPublicKey(publicKeyStr);
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE,publicKey);
        int inputLen = plainText.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        int i = 0;
        byte[] cache;
        // 分段加密
        while (inputLen -offset >0){
            if(inputLen - offset >MAX_DECRYPT_BLOCK){
                cache = cipher.doFinal(plainText,offset,MAX_ENCRYPT_BLOCK);
            }else {
                cache = cipher.doFinal(plainText,offset,inputLen - offset);
            }
            out.write(cache,0,cache.length);
            i++;
            offset = i * MAX_DECRYPT_BLOCK;
        }
        byte[] encryptText = out.toByteArray();
        out.close();
        return encryptText;
    }

    /**
     * RSA解密
     * @param encryptText：需要解密的数据
     * @param privateKeyStr：私钥字符串
     * @return plainText：解密后的数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] encryptText, String privateKeyStr) throws Exception {
        PrivateKey privateKey = getPrivateKey(privateKeyStr);
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE,privateKey);
        int inputLen = encryptText.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptText, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptText, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] plainText = out.toByteArray();
        out.close();
        return plainText;
    }


// 测试
    public static void main(String[] args) throws Exception {
        String name = "hello word";
        Map<String, Object> keyMap = initKey();
        String publicKeyStr = getPublicKeyStr(keyMap);
        String privateKeyStr = getPrivateKeyStr(keyMap);
        System.out.println("publicKeyStr:"+publicKeyStr);
        System.out.println("privateKeyStr:"+privateKeyStr);

        byte[] encrypt = encrypt(name.getBytes(StandardCharsets.UTF_8), publicKeyStr);
        System.out.println("加密后为："+new String(encrypt));

        byte[] decrypt = decrypt(encrypt, privateKeyStr);
        System.out.println("解密后为："+new String(decrypt));

        TreeMap map = new TreeMap<>();
        map.put("age",20);
        map.put("name","张三");
        String str = JSONObject.toJSONString(map);
        System.out.println(str);
        byte[] sign = sign(str.getBytes(StandardCharsets.UTF_8), privateKeyStr);
        System.out.println(Base64.encodeBase64String(sign));

        boolean verify = verify(str.getBytes(StandardCharsets.UTF_8), sign, publicKeyStr);
        System.out.println(verify);


    }
}
