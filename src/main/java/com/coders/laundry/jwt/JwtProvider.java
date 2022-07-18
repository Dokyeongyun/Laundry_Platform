package com.coders.laundry.jwt;

import io.jsonwebtoken.*;

import java.util.Date;

public class JwtProvider {
    private String secretKey = "laundry"; //토큰 체크 시, 필요한 암호키
    private long tokenValidTime = 1000L*60*60*24; //토큰 만료기간 24시간으로 설정

    //토큰 생성하는 메서드
    public String createdToken(String phoneNum) {
        //토큰의 키가 되는 subject를 중복되지 않는 고유값인 phoneNumber로 지정
        Claims claims = Jwts.claims().setSubject(phoneNum);
        Date now = new Date();

        String jwt = Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        return jwt;
    }

    //토큰의 유효성을 확인해주는 메소드
    public boolean validateToken(String token){
        try{ //문제없는 경우
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return true;
        }catch(ExpiredJwtException e){ //토큰이 만료된 경우
            System.out.println("Token Expried");
            return false;
        }catch(JwtException e){//토큰이 변조된 경우
            System.out.println("Token Error");
            return false;
        }
    }
}
