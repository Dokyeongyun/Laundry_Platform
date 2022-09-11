package com.coders.laundry.repository;

import com.coders.laundry.domain.entity.DeviceTokenEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DeviceTokenRepository {

    DeviceTokenEntity selectByMemberId(int memberId);

    int save(int memberId, String token, String deviceType);
}
