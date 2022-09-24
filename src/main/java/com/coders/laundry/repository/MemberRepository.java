package com.coders.laundry.repository;

import com.coders.laundry.domain.entity.MemberEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MemberRepository {

    MemberEntity selectById(int memberId);

    int insert(MemberEntity memberEntity);

    int update(MemberEntity memberEntity);

    int delete(@Param("memberId") int memberId);

    MemberEntity selectByPhoneNumber(String phoneNumber);

}
