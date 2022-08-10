package com.lin.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.Map;

/**
 * JWT工具类
 */
public class JWTUtils {
    /**24小时过期*/
    public static final long EXPIRE = 1000 * 60 * 30;   // 30分钟过期
    /**secret*/
    public static final String APP_SECRET = "DJJkAksnfKDHDHUYnfanFSGelDaFndnMSFL";

    /**
     * 对称签名，获取token
     * @param map：存储有效荷载信息的集合
     * @return
     */
    public static String getJwtToken(Map<String,Object> map){
        String token = Jwts.builder()
                .setSubject("spring-security")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .addClaims(map)
                .signWith(Keys.hmacShaKeyFor(APP_SECRET.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
        return token;
    }

    /**
     * 对称签名，解析token
     * @param jwtToken
     * @return
     */
    public static Jws<Claims> decode(String jwtToken){
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(APP_SECRET.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(jwtToken);
        return claimsJws;
    }

    /**
     * rsa非对称签名，获取token
     * @param map
     * @return
     */
    public static String getJwtTokenRsa(Map<String,Object> map){
        PrivateKey privateKey = RSAUtils.getPrivateKey(RSAUtils.privateKey);
        String token = Jwts.builder()
                .setSubject("lin-jwt")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .addClaims(map)
                .signWith(Keys.hmacShaKeyFor(APP_SECRET.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
        return token;
    }

    /**
     * rsa非对称加密，解析token
     * @param jwtToken
     * @return
     */
    public static Jws<Claims> decodeRsa(String jwtToken){
        PublicKey publicKey = RSAUtils.getPublicKey(RSAUtils.publicKey);
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(APP_SECRET.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(jwtToken);
        return claimsJws;
    }
}
