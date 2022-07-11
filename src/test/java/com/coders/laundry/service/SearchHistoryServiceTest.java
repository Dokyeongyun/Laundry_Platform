package com.coders.laundry.service;

import com.coders.laundry.dto.Pageable;
import com.coders.laundry.dto.SearchHistory;
import com.coders.laundry.repository.SearchHistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
@ExtendWith(MockitoExtension.class)
class SearchHistoryServiceTest {

    @InjectMocks
    private SearchHistoryService searchHistoryService;

    @Mock
    private SearchHistoryRepository searchHistoryRepository;

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
}