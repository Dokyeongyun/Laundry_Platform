package com.coders.laundry.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("dev")
class TokenManagerServiceTest {

    @Autowired
    private TokenManagerService tokenManagerService;

    @Test
    @DisplayName("access token 발급 메서드: 회원번호를 인자로 받았을 때의 access token 발급 여부 test")
    void createToken() {

        //Arrange
        int id = 1;
        String expected = Integer.toString(id);
        String secretKey = "laundryJwtSecret";

        //Act
        String accessToken = tokenManagerService.createToken(id);

        //Assert
        Claims claims = Jwts.parser()
                        .setSigningKey(secretKey.getBytes())
                        .parseClaimsJws(accessToken).getBody();
        String actual = claims.getSubject();
        assertNotNull(accessToken);
        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("refresh token 발급 메서드: refresh token 발급 여부 test")
    void createRefreshToken() {

        //Arrange
        String secretKey = "laundryJwtSecret";

        //Act
        String refreshToken = tokenManagerService.createRefreshToken();

        //Assert
        assertNotNull(refreshToken);

    }
}