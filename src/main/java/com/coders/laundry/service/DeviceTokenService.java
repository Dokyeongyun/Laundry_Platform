package com.coders.laundry.service;

import com.coders.laundry.repository.DeviceTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceTokenService {

    private final DeviceTokenRepository repository;

    @Autowired
    public DeviceTokenService(DeviceTokenRepository repository) {
        this.repository = repository;
    }

    public int save(int memberId, String token, String deviceType) {
        // TODO: memberId 에 맞는 회원존재하는지 check 하는 로직추가
        return repository.save(memberId, token, deviceType);
    }

}
