package com.coders.laundry.service;

import com.coders.laundry.domain.entity.SearchHistoryEntity;
import com.coders.laundry.dto.Pageable;
import com.coders.laundry.dto.SearchHistory;
import com.coders.laundry.dto.SearchHistoryRegisterRequest;
import com.coders.laundry.repository.SearchHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("dev")
@ExtendWith(MockitoExtension.class)
class SearchHistoryServiceTest {

    private SearchHistoryService searchHistoryService;

    private SearchHistoryRepository searchHistoryRepository;

    @BeforeEach
    public void setup() {
        searchHistoryRepository = mock(SearchHistoryRepository.class);
        searchHistoryService = new SearchHistoryService(searchHistoryRepository);
    }

    @Test
    void findRecentSearchKeywordsCountByMemberId() {
        // Arrange
        int memberId = 1;

        // Act
        int result = searchHistoryService.findRecentSearchKeywordsCountByMemberId(memberId);

        // Assert
        assertTrue(result >= 0);
    }

    @Test
    void findRecentSearchKeywordsByMemberId() {
        // Arrange
        int memberId = 1;
        int offset = 0;
        int limit = 20;
        String sort = "created";
        String sortType = "desc";

        Pageable pageable = new Pageable(offset, limit, sort, sortType);

        // Act
        List<SearchHistory> result
                = searchHistoryService.findRecentSearchKeywordsByMemberId(memberId, pageable);

        // Assert
        assertNotNull(result);
        assertTrue(result.size() <= limit);
    }

    @Test
    void saveSearchHistory() {
        // Arrange
        int memberId = -1;
        SearchHistoryRegisterRequest request = new SearchHistoryRegisterRequest("test keyword", "board");

        SearchHistoryEntity entity = SearchHistoryEntity.builder()
                .keyword(request.getKeyword())
                .type(request.getType())
                .searchMemberId(null)
                .build();

        SearchHistoryEntity created = SearchHistoryEntity.builder()
                .searchHistoryId(10)
                .keyword(request.getKeyword())
                .type(request.getType())
                .searchMemberId(null)
                .createDate(LocalDateTime.now())
                .build();

        when(searchHistoryRepository.insert(entity)).thenReturn(1);
        when(searchHistoryRepository.selectById(entity.getSearchHistoryId())).thenReturn(created);

        // Act
        SearchHistory result = searchHistoryService.saveSearchHistory(memberId, request);

        // Assert
        assertNotNull(result);
        assertEquals(created.getSearchHistoryId(), result.getSearchHistoryId());
        assertEquals(created.getKeyword(), result.getKeyword());
        assertEquals(created.getType(), result.getType());
        assertEquals(created.getCreateDate(), result.getCreatedAt());
    }
}