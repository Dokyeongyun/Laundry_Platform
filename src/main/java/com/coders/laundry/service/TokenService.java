package com.coders.laundry.service;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class TokenService {
    private String secretKey = "laundry"; //토큰 체크 시, 필요한 암호키
    private long tokenValidTime = 1000L*60*60*24; //토큰 만료기간 24시간으로 설정

    //토큰 생성하는 메서드
    public String createdToken(String phoneNum) {
        //토큰의 키가 되는 subject를 중복되지 않는 고유값인 phoneNumber로 지정
        Claims claims = Jwts.claims().setSubject(phoneNum);
        Date now = new Date();

        String jwt = Jwts.builder()
                .setHeaderParam("typ","JWT")// Type 설정
                .setHeaderParam("alg","HS256")// HS256는 해시 알고리즘 중 하나로, base64와 같이 임의로 디코딩을 할 수 없다.
                .setClaims(claims)
                .setIssuedAt(now) //토큰 발행 시간
                .setExpiration(new Date(now.getTime() + tokenValidTime)) //토큰 만료 시간
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes()) // HS256과 key로 Sign
                .compact();//토큰 생성
        return jwt;
    }
}
