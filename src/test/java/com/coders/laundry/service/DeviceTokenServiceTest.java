package com.coders.laundry.service;

import com.coders.laundry.repository.DeviceTokenRepository;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.thymeleaf.util.ArrayUtils;

@SpringBootTest
@ActiveProfiles("dev")
@ExtendWith(MockitoExtension.class)
class DeviceTokenServiceTest {

    @Mock private DeviceTokenRepository deviceTokenRepository;

    @InjectMocks private DeviceTokenService deviceTokenService;

    @MethodSource("deviceTokens")
    @ParameterizedTest
    void saveToken(int memberId, String token, String deviceType) {
        Assertions.assertDoesNotThrow(() -> deviceTokenService.save(memberId, token, deviceType));
    }


    static Stream<Arguments> deviceTokens() {
        return Stream.of(
            Arguments.of(
                1, "token", "Android"
            ),
            Arguments.of(
                2, "token", "Apple"
            )
        );
    }
}
