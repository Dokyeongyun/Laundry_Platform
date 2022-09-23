package com.coders.laundry.service;

import com.coders.laundry.domain.entity.LaundryEntity;
import com.coders.laundry.dto.*;
import com.coders.laundry.repository.LaundryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
class LaundryFindServiceTest {

    private LaundryFindService laundryFindService;

    @Mock
    private LaundryRepository laundryRepository;

    @BeforeEach
    public void setup() {
        laundryFindService = new LaundryFindService(laundryRepository);
    }

    @Test
    void findById_WhenLaundryNotFound() {
        // Arrange
        int laundryId = 123;
        when(laundryRepository.selectById(laundryId)).thenReturn(null);

        // Act
        Optional<Laundry> result = laundryFindService.findById(laundryId);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void findById_WhenLaundryExists() {
        // Arrange
        int laundryId = 123;
        LaundryEntity laundry = LaundryEntity.builder().laundryId(laundryId).build();
        when(laundryRepository.selectById(laundryId)).thenReturn(laundry);

        // Act
        Optional<Laundry> result = laundryFindService.findById(laundryId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(laundryId, result.get().getLaundryId());
    }

    @Test
    void findCount() {
        // Arrange
        String keyword = "";
        LocationSearch locationSearch = new LocationSearch(new Point(37.4790788271, 127.0484256030), 10000);
        String searchMode = "address";

        // Act
        int result = laundryFindService.findCount(keyword, locationSearch, searchMode);

        // Assert
        // nothing..
    }

    @Test
    void findCount_InvalidSearchMode_IllegalArgumentException() {
        // Arrange
        String keyword = "";
        LocationSearch locationSearch = new LocationSearch(new Point(37.4790788271, 127.0484256030), 10000);
        String searchMode = "invalid";

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> laundryFindService.findCount(keyword, locationSearch, searchMode));
    }

    @Test
    void search_AddressSearchMode() {
        // Arrange
        int memberId = 1;
        String keyword = "";
        LocationSearch locationSearch = new LocationSearch(new Point(37.3467219612, 126.6894764563), 10000);
        Pageable pageable = new Pageable(0, 3, "distance", "asc");
        String searchMode = "address";

        // Act
        List<SearchedLaundry> result
                = laundryFindService.search(memberId, keyword, locationSearch, pageable, searchMode);

        // Assert
        assertNotNull(result);
    }

    @Test
    void search_KeywordSearchMode() {
        // Arrange
        int memberId = 1;
        String keyword = "";
        LocationSearch locationSearch = new LocationSearch(new Point(37.3467219612, 126.6894764563), 10000);
        Pageable pageable = new Pageable(0, 3, "review", "desc");
        String searchMode = "keyword";

        // Act
        List<SearchedLaundry> result
                = laundryFindService.search(memberId, keyword, locationSearch, pageable, searchMode);

        // Assert
        assertNotNull(result);
    }

    @Test
    void search_InvalidSearchMode_IllegalArgumentException() {
        // Arrange
        int memberId = 1;
        String keyword = "";
        LocationSearch locationSearch = new LocationSearch(new Point(37.3467219612, 126.6894764563), 10000);
        Pageable pageable = new Pageable(0, 3, "distance", "asc");
        String searchMode = "invalid";

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> laundryFindService.search(memberId, keyword, locationSearch, pageable, searchMode));
    }
}