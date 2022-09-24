package com.coders.laundry.service;

import com.coders.laundry.domain.entity.LaundryEntity;
import com.coders.laundry.dto.LocationSearch;
import com.coders.laundry.dto.Pageable;
import com.coders.laundry.dto.Point;
import com.coders.laundry.dto.SearchedLaundry;
import com.coders.laundry.repository.LaundryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
@ActiveProfiles("dev")
@ExtendWith(MockitoExtension.class)
class LaundryFindServiceTest {

    private LaundryFindService laundryFindService;

    private LaundryRepository laundryRepository;

    @BeforeEach
    public void setup() {
        laundryRepository = mock(LaundryRepository.class);
        laundryFindService = new LaundryFindService(laundryRepository);
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