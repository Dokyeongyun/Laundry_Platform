package com.coders.laundry.repository;

import com.coders.laundry.domain.entity.LaundryVisitHistoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LaundryVisitHistoryRepository {
    int insert(LaundryVisitHistoryEntity laundryVisitHistory);
    LaundryVisitHistoryEntity selectById(@Param("visitHistorySeq")int visitHistorySeq);
    int update(LaundryVisitHistoryEntity laundryVisitHistoryEntity);
    int delete(@Param("visitHistorySeq")int visitHistorySeq);
}
