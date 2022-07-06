package com.coders.laundry.repository;

import com.coders.laundry.domain.entity.SearchHistoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SearchHistoryRepository {
    int insert(SearchHistoryEntity searchHistory);

    SearchHistoryEntity selectById(@Param("searchHistoryId") int searchHistoryId);
}
