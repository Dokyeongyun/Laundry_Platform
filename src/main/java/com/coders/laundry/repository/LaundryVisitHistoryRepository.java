package com.coders.laundry.repository;

import com.coders.laundry.domain.entity.LaundryVisitHistoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LaundryVisitHistoryRepository {
    int insert(LaundryVisitHistoryEntity laundryVisitHistory);
}
