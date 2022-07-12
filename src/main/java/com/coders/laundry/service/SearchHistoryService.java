package com.coders.laundry.service;

import com.coders.laundry.domain.entity.SearchHistoryEntity;
import com.coders.laundry.dto.Pageable;
import com.coders.laundry.dto.SearchHistory;
import com.coders.laundry.dto.SearchHistoryRegisterRequest;
import com.coders.laundry.repository.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchHistoryService {

    private final SearchHistoryRepository searchHistoryRepository;

    public int findRecentSearchKeywordsCountByMemberId(int memberId) {
        return searchHistoryRepository.selectCountByMemberId(memberId);
    }

    public List<SearchHistory> findRecentSearchKeywordsByMemberId(int memberId, Pageable pageable) {

        String sort = pageable.getSort();
        String sortType = pageable.getSortType();
        if (sort.equals("created")) {
            sort = "create_date";
        }
        if (sortType.equals("desc")) {
            sort = "-" + sort;
        }

        List<SearchHistoryEntity> list = searchHistoryRepository.selectListByMemberId(
                memberId, pageable.getOffset(), pageable.getLimit(), sort);

        List<SearchHistory> result = new ArrayList<>();
        for (SearchHistoryEntity entity : list) {
            int searchHistoryId = entity.getSearchHistoryId();
            String keyword = entity.getKeyword();
            String type = entity.getType();
            LocalDateTime createDate = entity.getCreateDate();
            result.add(new SearchHistory(searchHistoryId, keyword, type, createDate));
        }

        return result;
    }

    public SearchHistory saveSearchHistory(int memberId, SearchHistoryRegisterRequest request) {
        SearchHistoryEntity entity = SearchHistoryEntity.builder()
                .keyword(request.getKeyword())
                .type(request.getType())
                .searchMemberId(memberId <= 0 ? null : memberId)
                .build();

        searchHistoryRepository.insert(entity);
        SearchHistoryEntity created = searchHistoryRepository.selectById(entity.getSearchHistoryId());

        return SearchHistory.builder()
                .searchHistoryId(created.getSearchHistoryId())
                .keyword(created.getKeyword())
                .type(created.getType())
                .createdAt(created.getCreateDate())
                .build();
    }
}
