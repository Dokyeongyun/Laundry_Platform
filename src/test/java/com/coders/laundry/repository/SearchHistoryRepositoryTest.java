package com.coders.laundry.repository;

import com.coders.laundry.domain.entity.SearchHistoryEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
class SearchHistoryRepositoryTest {

    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    @Test
    void insert() {
        // Arrange
        SearchHistoryEntity expected = SearchHistoryEntity.builder()
                .keyword("양재")
                .type("laundry")
                .build();

        // Act
        int result = searchHistoryRepository.insert(expected);

        // Assert
        assertEquals(1, result);

        int generatedId = expected.getSearchHistoryId();
        SearchHistoryEntity actual = searchHistoryRepository.selectById(generatedId);

        assertEquals(expected.getSearchHistoryId(), actual.getSearchHistoryId());
        assertEquals(expected.getKeyword(), actual.getKeyword());
        assertEquals(expected.getType(), actual.getType());
        assertEquals(expected.getSearchMemberId(), actual.getSearchMemberId());
        assertNotNull(actual.getCreateDate());
    }

    @Test
    void selectById() {
        // Arrange
        SearchHistoryEntity entity = SearchHistoryEntity.builder()
                .keyword("양재")
                .type("laundry")
                .searchMemberId(1)
                .build();

        int insertedCount = searchHistoryRepository.insert(entity);
        assertEquals(1, insertedCount);

        int searchHistoryId = entity.getSearchHistoryId();

        // Act
        SearchHistoryEntity result = searchHistoryRepository.selectById(searchHistoryId);

        // Assert
        assertEquals(entity.getSearchHistoryId(), result.getSearchHistoryId());
        assertEquals(entity.getKeyword(), result.getKeyword());
        assertEquals(entity.getType(), result.getType());
        assertEquals(entity.getSearchMemberId(), result.getSearchMemberId());
        assertNotNull(result.getCreateDate());
    }

    @Test
    void delete() {
        // Arrange
        SearchHistoryEntity entity = SearchHistoryEntity.builder()
                .keyword("양재")
                .type("laundry")
                .searchMemberId(1)
                .build();

        int insertedCount = searchHistoryRepository.insert(entity);
        assertEquals(1, insertedCount);

        int searchHistoryId = entity.getSearchHistoryId();

        // Act
        int result = searchHistoryRepository.delete(searchHistoryId);

        // Assert
        assertEquals(1, result);
        SearchHistoryEntity deleted = searchHistoryRepository.selectById(searchHistoryId);
        assertNull(deleted);
    }

    @Test
    void selectAll() {
        // Act
        List<SearchHistoryEntity> result = searchHistoryRepository.selectAll();

        // Assert
        assertNotNull(result);
    }

    @Test
    void selectCountByMemberId() {
        // Arrange
        int searchMemberId = 1;

        // Act
        int result = searchHistoryRepository.selectCountByMemberId(searchMemberId);

        // Assert
        assertTrue(result >= 0);
    }

    @Test
    void selectListByMemberId() {
        // Arrange
        int searchMemberId = 1;
        int offset = 0;
        int limit = 20;
        String sort = "create_date";

        // Act
        List<SearchHistoryEntity> result
                = searchHistoryRepository.selectListByMemberId(searchMemberId, offset, limit, sort);

        // Assert
        assertNotNull(result);
    }

    @Test
    void selectAllByMemberId() {
        // Arrange
        int searchMemberId = 1;

        // Act
        List<SearchHistoryEntity> result = searchHistoryRepository.selectAllByMemberId(searchMemberId);

        // Assert
        assertNotNull(result);
        for (SearchHistoryEntity entity : result) {
            assertEquals(searchMemberId, entity.getSearchMemberId());
        }

    }
}