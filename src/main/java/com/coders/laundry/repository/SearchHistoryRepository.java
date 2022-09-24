package com.coders.laundry.repository;

import com.coders.laundry.domain.entity.SearchHistoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SearchHistoryRepository {
    int insert(SearchHistoryEntity searchHistory);

    SearchHistoryEntity selectById(@Param("searchHistoryId") int searchHistoryId);

    int delete(@Param("searchHistoryId") int searchHistoryId);

    List<SearchHistoryEntity> selectAll();

    int selectCountByMemberId(@Param("searchMemberId") int searchMemberId);

    List<SearchHistoryEntity> selectListByMemberId(
            @Param("searchMemberId") int searchMemberId,
            @Param("offset") int offset,
            @Param("limit") int limit,
            @Param("sort") String sort);

    List<SearchHistoryEntity> selectAllByMemberId(@Param("searchMemberId") int searchMemberId);
}
