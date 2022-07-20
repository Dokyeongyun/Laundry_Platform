package com.coders.laundry.service;

import com.coders.laundry.domain.entity.MemberEntity;
import com.coders.laundry.repository.HomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {

    @Autowired
    private HomeRepository homeRepository;

    public List<MemberEntity> findAllTeamMemberList() {
        return homeRepository.selectAllTeamMemberList();
    }
}
