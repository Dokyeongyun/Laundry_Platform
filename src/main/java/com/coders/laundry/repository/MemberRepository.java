package com.coders.laundry.repository;

import com.coders.laundry.domain.entity.MemberEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MemberRepository {
    public MemberEntity selectMember(int id);
}
