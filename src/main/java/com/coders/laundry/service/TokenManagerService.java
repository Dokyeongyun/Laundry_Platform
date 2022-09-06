package com.coders.laundry.service;

import io.jsonwebtoken.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 해당 서비스는 Stubbing을 위해 만들어졌습니다.
 * 클래스명, 메서드 등 모두 임의로 작성한 것이니 추후 구현 시 변경 부탁드립니다.
 */
@Service
public class TokenManagerService {

    @Value("${jwt.secretKey}")
    private String secretKey;
    @Value("${jwt.accessValidTime}")
    private long tokenValidTime;
    @Value("${jwt.refreshValidTime}")
    private long refreshTokenValidTime;

    public boolean verify(String token){
        try { //문제없는 경우
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) { //토큰이 만료된 경우
            System.out.println("Token Expired");
            return false;
        } catch (JwtException e) {//토큰이 변조된 경우
            System.out.println("Token Error");
            return false;
        }
        //return !token.equals("Bearer fail");
    }

    public int findMemberId(String token) {
        String id = Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        return Integer.parseInt(id);
    }

    public Claims getTokenContents(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(token)
                .getBody();
        return claims;
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
