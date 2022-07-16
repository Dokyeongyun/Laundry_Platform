package com.coders.laundry.repository;

import com.coders.laundry.domain.entity.LaundryEntity;
import com.coders.laundry.dto.LocationSearch;
import com.coders.laundry.dto.Pageable;
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

    int selectAddressSearchListCount(
            @Param("keyword") String keyword,
            @Param("locationSearch") LocationSearch locationSearch
    );

    List<LaundryEntity> selectAddressSearchList(
            @Param("keyword") String keyword,
            @Param("locationSearch") LocationSearch locationSearch,
            @Param("pageable") Pageable pageable
    );

    int selectKeywordSearchListCount(
            @Param("keyword") String keyword,
            @Param("locationSearch") LocationSearch locationSearch
    );

    List<LaundryEntity> selectKeywordSearchList(
            @Param("keyword") String keyword,
            @Param("locationSearch") LocationSearch locationSearch,
            @Param("pageable") Pageable pageable
    );
}
