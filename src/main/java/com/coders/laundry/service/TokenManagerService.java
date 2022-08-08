package com.coders.laundry.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 해당 서비스는 Stubbing을 위해 만들어졌습니다.
 * 클래스명, 메서드 등 모두 임의로 작성한 것이니 추후 구현 시 변경 부탁드립니다.
 */
@Service
public class TokenManagerService {

    private String secretKey = "laundryJwtSecret";
    private long tokenValidTime = 1000L*60*60;
    private long refreshTokenValidTime = 1000L*60*60*24;

    public boolean verify(String token){
        // TODO implement
        return !token.equals("Bearer fail");
    }

    public int findMemberId(String token) {
        // TODO implement
        return 1;
    }

    public String createToken(int memberId) {

        String id = Integer.toString(memberId);
        Claims claims = Jwts.claims().setSubject(id);
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS256")
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();

    }

    public String createRefreshToken(){

        Date now = new Date();

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

    }
}
