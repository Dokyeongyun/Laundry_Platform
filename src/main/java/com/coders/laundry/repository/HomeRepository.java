package com.coders.laundry.repository;

import com.coders.laundry.domain.entity.MemberEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HomeRepository {

    List<MemberEntity> selectAllTeamMemberList();
}
