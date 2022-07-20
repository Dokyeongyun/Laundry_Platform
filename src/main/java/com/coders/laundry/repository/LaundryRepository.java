package com.coders.laundry.repository;

import com.coders.laundry.domain.entity.LaundryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LaundryRepository {

    int insert(LaundryEntity laundry);

    LaundryEntity selectById(@Param("laundryId") int laundryId);

    int update(LaundryEntity laundry);

    int delete(@Param("laundryId") int laundryId);

    List<LaundryEntity> selectAll();
}
