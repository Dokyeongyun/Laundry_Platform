package com.coders.laundry.service;

import org.springframework.stereotype.Service;

/**
 * 해당 서비스는 Stubbing을 위해 만들어졌습니다.
 * 클래스명, 메서드 등 모두 임의로 작성한 것이니 추후 구현 시 변경 부탁드립니다.
 */
@Service
public class TokenManagerService {

    public boolean verify(String token){
        // TODO implement
        return !token.equals("Bearer fail");
    }

    public int findMemberId(String token) {
        // TODO implement
        return 1;
    }
}
