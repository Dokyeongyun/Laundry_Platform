package com.coders.laundry.service;

import com.coders.laundry.domain.entity.SearchHistoryEntity;
import com.coders.laundry.domain.exceptions.NotAuthorizedException;
import com.coders.laundry.dto.Pageable;
import com.coders.laundry.dto.SearchHistory;
import com.coders.laundry.dto.SearchHistoryRegisterRequest;
import com.coders.laundry.dto.SearchHistoryRemoveRequest;
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
import static org.mockito.Mockito.*;

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
    void findCountByMemberId() {
        // Arrange
        int memberId = 1;

        // Act
        int result = searchHistoryService.findCountByMemberId(memberId);

        // Assert
        assertTrue(result >= 0);
    }

    @Test
    void findByMemberId() {
        // Arrange
        int memberId = 1;
        int offset = 0;
        int limit = 20;
        String sort = "created";
        String sortType = "desc";

        Pageable pageable = new Pageable(offset, limit, sort, sortType);

        // Act
        List<SearchHistory> result = searchHistoryService.findByMemberId(memberId, pageable);

        // Assert
        assertNotNull(result);
        assertTrue(result.size() <= limit);
    }

    @Test
    void save() {
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
        SearchHistory result = searchHistoryService.save(memberId, request);

        // Assert
        assertNotNull(result);
        assertEquals(created.getSearchHistoryId(), result.getSearchHistoryId());
        assertEquals(created.getKeyword(), result.getKeyword());
        assertEquals(created.getType(), result.getType());
        assertEquals(created.getCreateDate(), result.getCreatedAt());
    }

    @Test
    void remove() {
        // Assert
        int memberId = 1;
        SearchHistoryRemoveRequest request = new SearchHistoryRemoveRequest(1);

        SearchHistoryEntity entity = SearchHistoryEntity.builder()
                .searchHistoryId(1)
                .keyword("테스트")
                .type("laundry")
                .searchMemberId(memberId)
                .createDate(LocalDateTime.now())
                .build();

        when(searchHistoryRepository.selectById(request.getSearchHistoryId())).thenReturn(entity);
        when(searchHistoryRepository.delete(request.getSearchHistoryId())).thenReturn(1);

        // Act
        searchHistoryService.remove(memberId, request);

        // Assert
        // noting to assert
    }

    @Test
    void remove_NotAuthorizedException() {
        // Assert
        int memberId = 1;
        SearchHistoryRemoveRequest request = new SearchHistoryRemoveRequest(1);

        SearchHistoryEntity entity = SearchHistoryEntity.builder()
                .searchHistoryId(1)
                .keyword("테스트")
                .type("laundry")
                .searchMemberId(memberId + 10)
                .createDate(LocalDateTime.now())
                .build();

        when(searchHistoryRepository.selectById(request.getSearchHistoryId())).thenReturn(entity);

        // Act & Assert
        assertThrows(NotAuthorizedException.class,
                () -> searchHistoryService.remove(memberId, request)
        );
    }
}