package com.coders.laundry.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("dev")
class TokenServiceTest {

    @Autowired
    private TokenService tokenService;

    @Test
    @DisplayName("토큰 발급 메서드: 핸드폰 번호를 인자로 받았을 때, 토큰이 발급되는지 test")
    void createdToken() {

        //Arrange
        String expected = "12341234";
        String secretKey = "laundry";
        //Act
        String token = tokenService.createdToken(expected);

        //Assert
        Claims claims = Jwts.parser()
                        .setSigningKey(secretKey.getBytes())
                        .parseClaimsJws(token).getBody();
        String actual = claims.getSubject();
        assertNotNull(token);
        assertEquals(expected, actual);
    }
}