package com.coders.laundry.controller;

import com.coders.laundry.domain.dto.DeviceTokenSaveRequestDTO;
import com.coders.laundry.service.DeviceTokenService;
import com.coders.laundry.view.IntegerView;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/device-token")
@RequiredArgsConstructor
public class DeviceTokenController {

    private final DeviceTokenService service;

    @PostMapping
    public IntegerView save(
        @RequestBody @Valid DeviceTokenSaveRequestDTO requestDTO
    ) {
        int savedRow =  service.save(
            requestDTO.getMemberId(),
            requestDTO.getToken(),
            requestDTO.getDeviceType()
        );

        return new IntegerView(savedRow);
    }
}
