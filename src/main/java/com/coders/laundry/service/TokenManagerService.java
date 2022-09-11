package com.coders.laundry.service;

import io.jsonwebtoken.*;
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

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey.getBytes())
                    .parseClaimsJws(token).getBody();
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("만료된 토큰입니다.");
            return false;
        } catch (UnsupportedJwtException e){
            System.out.println("지원되지 않는 토큰입니다.");
            return false;
        } catch (MalformedJwtException e){
            System.out.println("토큰 구성이 잘못되었습니다.");
            return false;
        } catch (JwtException e){
            System.out.println("Token Error");
            return false;
        }

    }

    public int findMemberId(String token) {

        return Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(token)
                .getBody()
                .get("id",Integer.class);

    }

    public Claims getTokenContents(String token){

        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

    }

    public String createToken(int memberId) {

        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS256")
                .setSubject("Access Token")
                .claim("id", memberId)
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
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();

    }
}
